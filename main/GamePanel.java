package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    final int originalTileSize = 16;
    final public int scale = 3;
    final public Point worldSize = new Point(50, 50);

    public final int tileSize = originalTileSize * scale;
    final public Point maxBlocksScreen = new Point(16, 12);
    public final Point screenSize = new Point(maxBlocksScreen.x * tileSize, maxBlocksScreen.y * tileSize);

    public final Point worldInScreenSize = new Point(worldSize.x * tileSize, worldSize.y * tileSize);

    final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyHandler);

    TileManager tileManager;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenSize.x, screenSize.y));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        tileManager = new TileManager(this);
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
                    Thread.sleep( remainingTime);
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
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        tileManager.drawing(g2);
        player.drawing(g2);

        g2.dispose();
    }
}
