package main;

import entity.Entity;
import entity.Magician;
import entity.Player;
import objects.BaseObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;

public class GamePanel extends JPanel implements Runnable
{
    final int originalTileSize = 32;
    final public int scale = 2;

    public final int tileSize = originalTileSize * scale;
    final public Point maxBlocksScreen = new Point(25, 14);
    public final Point screenSize = new Point(maxBlocksScreen.x * tileSize, maxBlocksScreen.y * tileSize);

    final int FPS = 60;

    TileManager tileManager;
    public KeyHandler keyHandler = new KeyHandler();
    public Sound sound = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter;
    public UI ui = new UI(this);
    Thread gameThread;

    public Player player;
    final public HashSet<BaseObject> items = new HashSet<>();
    final ArrayList<Magician> NPC = new ArrayList<>();
    final ArrayList<Entity> animals = new ArrayList<>();

    public GameState state = GameState.Running;
    public AbstractDialogue currentDialogue;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenSize.x, screenSize.y));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        tileManager = new TileManager(tileSize);
        assetSetter = new AssetSetter(tileSize);
        assetSetter.initObjects(tileManager.getFreePlaces(), items);
        assetSetter.initEntity(tileManager.getFreePlaces(), NPC, animals);

        player = new Player(this, keyHandler, tileManager.defaultWorldPosition);

        keyHandler.addListener(KeyEvent.VK_E, () -> {
            switch (state)
            {
                case Running -> state = GameState.Inventory;
                case Inventory -> state = GameState.Running;
            }
        });

        keyHandler.addListener(KeyEvent.VK_SPACE, () -> {
            switch (state)
            {
                case Inventory : state = GameState.Running; break;
                case Dialog :
                    currentDialogue.onKeyPressed(KeyEvent.VK_SPACE);
                    if (currentDialogue.isFinished())
                        state = GameState.Running;
                    break;
                case Paused : state = GameState.Running; break;
                case Running : state = GameState.Paused; break;
            }
        });
    }

    public void setupGame()
    {
        sound.play(Sounds.Theme);
        sound.loop();
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run()
    {
        long drawInterval = 1000 / FPS;
        long nextDrawTime = System.currentTimeMillis() + drawInterval;

        while (gameThread != null)
        {
            update();
            repaint();

            long remainingTime = nextDrawTime - System.currentTimeMillis();

            try
            {
                if (remainingTime > 0)
                    Thread.sleep(remainingTime);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            nextDrawTime += drawInterval;
        }
    }

    public void update()
    {
        if (state != GameState.Running)
            return;

        player.update(sound);

        for (Entity entity : NPC)
            entity.update(player, collisionChecker);

        for (BaseObject item : items)
            item.update(player);

        for (Entity entity : animals)
            entity.update(player, collisionChecker);
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        tileManager.drawing(graphics2D, player);

        for (BaseObject item: items)
        {
            if (item != null)
                item.drawing(graphics2D, tileSize);
        }

        for (Entity entity : NPC)
            entity.drawing(graphics2D, tileSize);

        for (Entity entity : animals)
            entity.drawing(graphics2D, tileSize);

        player.drawing(graphics2D, tileSize);

        ui.draw(graphics2D);

        graphics2D.dispose();
    }

    public void startDialogue(AbstractDialogue dialogue)
    {
        currentDialogue = dialogue;
        state = GameState.Dialog;
    }
}
