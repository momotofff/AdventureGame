package main;

import entity.Entity;
import entity.Magician;
import entity.Player;
import objects.BaseObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GamePanel extends JPanel implements Runnable
{
    final int FPS = 60;

    TileManager tileManager;
    public KeyHandler keyHandler = new KeyHandler();
    public static Sound sound = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter;
    public UI ui = new UI(this);
    Thread gameThread;

    public Player player;
    final public HashSet<BaseObject> items = new HashSet<>();
    final ArrayList<Magician> NPC = new ArrayList<>();
    final ArrayList<Entity> animals = new ArrayList<>();

    public static GameState state;
    public static AbstractDialogue currentDialogue;


    public GamePanel()
    {
        this.setPreferredSize(new Dimension(Parameters.screenSize.x, Parameters.screenSize.y));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        tileManager = new TileManager();
        assetSetter = new AssetSetter();
        assetSetter.initObjects(tileManager.getFreePlaces(), items);
        assetSetter.initEntity(tileManager.getFreePlaces(), NPC, animals);

        player = new Player(this, keyHandler, new Point(20 * Parameters.tileSize, 20 * Parameters.tileSize));
    }

    public void setupGame()
    {
        state = GameState.StartScreen;
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

        if (state != GameState.StartScreen)
        {
            tileManager.drawing(graphics2D, player);

            for (BaseObject item: items)
            {
                if (item != null)
                    item.drawing(graphics2D, Parameters.tileSize);
            }

            for (Entity entity : NPC)
                entity.drawing(graphics2D, Parameters.tileSize);

            for (Entity entity : animals)
                entity.drawing(graphics2D, Parameters.tileSize);

            player.drawing(graphics2D, Parameters.tileSize);

            ui.draw(graphics2D, state);

            graphics2D.dispose();
        }

        else
        {
            ui.draw(graphics2D, state);
        }
    }

    public void startDialogue(AbstractDialogue dialogue)
    {
        currentDialogue = dialogue;
        state = GameState.Dialog;
    }

    public static GameState getState()
    {
        return state;
    }
}
