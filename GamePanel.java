import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final Point maxBlocksScreen = new Point(16, 12);
    final Point screenSize = new Point(maxBlocksScreen.x * tileSize, maxBlocksScreen.y * tileSize);

    final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    Point playerPosition = new Point(100,100);
    int speed = 4;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenSize.x, screenSize.y));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
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
            repaint( );

            try
            {
                long remainingTime = nextDrawTime - System.currentTimeMillis();

                if (remainingTime < 0)
                    remainingTime = 0;

                Thread.sleep( remainingTime);

                nextDrawTime += drawInterval;
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public void update()
    {
        if (keyHandler.upPressed)
            playerPosition.y -= speed;
        if (keyHandler.leftPressed)
            playerPosition.x -= speed;
        if (keyHandler.rightPressed)
            playerPosition.x += speed;
        if (keyHandler.downPressed)
            playerPosition.y += speed;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        g2.fillRect(playerPosition.x, playerPosition.y, tileSize, tileSize);
        g2.dispose();
    }
}
