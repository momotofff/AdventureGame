package main;

import entity.Entity;
import entity.Player;
import objects.BaseObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GamePanel extends JPanel implements Runnable
{
    final int originalTileSize = 32;
    final public int scale = 2;

    public final int tileSize = originalTileSize * scale;
    final public Point maxBlocksScreen = new Point(25, 14);
    public final Point screenSize = new Point(maxBlocksScreen.x * tileSize, maxBlocksScreen.y * tileSize);

    final public Point maxWorldCountTile = new Point(100, 100);

    final int FPS = 60;

    TileManager tileManager;
    public KeyHandler keyHandler = new KeyHandler(this);
    public Sound sound = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter;
    public UI ui = new UI(this);
    Thread gameThread;

    public Player player;
    public HashSet<BaseObject> items = new HashSet<>();
    ArrayList<Entity> NPC = new ArrayList<>();
    public ArrayList<Point> trueNpcPositions = new ArrayList();

    public GameState state = GameState.Running;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenSize.x, screenSize.y));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        tileManager = new TileManager(this);

        assetSetter = new AssetSetter(this);

        player = new Player(this, keyHandler, tileManager.defaultWorldPosition);
    }

    public void setupGame()
    {
        assetSetter.setObject();
        assetSetter.set_NPC();
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
        switch (state)
        {
            case Running : player.update();
                for (Entity entity : NPC)
                    entity.update();
                for (BaseObject item : items)
                    item.update();

                break;
            case Paused: break;
        }
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        tileManager.drawing(graphics2D);

        for (BaseObject item: items)
        {
            if (item != null)
                item.drawing(graphics2D, this);
        }

        for (Entity entity : NPC)
            entity.drawing(graphics2D);

        player.drawing(graphics2D);

        ui.draw(graphics2D);

        graphics2D.dispose();
    }
}
