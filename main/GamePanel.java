package main;

import entity.Player;
import objects.BaseObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class GamePanel extends JPanel implements Runnable
{
    final int originalTileSize = 16;
    final public int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final public Point maxBlocksScreen = new Point(16, 12);
    public final Point screenSize = new Point(maxBlocksScreen.x * tileSize, maxBlocksScreen.y * tileSize);

    final public Point maxWorldCountTile = new Point(50, 50);

    final int FPS = 60;

    TileManager tileManager;
    KeyHandler keyHandler = new KeyHandler();
    public Sound sound = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter;
    public UI ui = new UI(this);
    Thread gameThread;

    public Player player;
    public HashSet<BaseObject> items = new HashSet<>();

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenSize.x, screenSize.y));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        tileManager = new TileManager(this);
        player = new Player(this, keyHandler, tileManager.defaultWorldPosition);
        assetSetter = new AssetSetter(this);
    }

    public void setupGame()
    {
        assetSetter.setObject();
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
        player.update();

        for (BaseObject item : items)
        {
            item.update();
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

        player.drawing(graphics2D);
        ui.draw(graphics2D);

        graphics2D.dispose();
    }
}
