package main;

import objects.Key;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UI
{
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font font;
    BufferedImage bufferedImage;

    private String message = null;

    int messageCounter = 180;

    public UI(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        font = new Font("Arial", Font.PLAIN, 30);
        bufferedImage = new Key().image;
    }

    public void showMessage(String text)
    {
        message = text;
    }

    public void draw(Graphics2D graphics2D)
    {
        this.graphics2D = graphics2D;

        switch (gamePanel.state)
        {
            case Running : drawInterphase(); break;
            case Paused : drawPausedScreen(); break;
            case Inventory : drawInventory(); break;
        }
    }

    public void drawPausedScreen()
    {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 100));
        graphics2D.setColor(new Color(255,180,100));

        String paused = "PAUSED";
        int length = (int) graphics2D.getFontMetrics().getStringBounds(paused, graphics2D).getWidth();
        int x = gamePanel.screenSize.x / 2 - length / 2;
        int y = gamePanel.screenSize.y / 2;

        graphics2D.draw(new Rectangle(0,0, 500,500));
        graphics2D.drawString(paused, x, y);

    }

    public void drawInterphase()
    {
        graphics2D.setFont(font);
        graphics2D.setColor(new Color(230,200,170));
        graphics2D.drawImage(bufferedImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, null);
        graphics2D.drawString(" x " + gamePanel.player.hasKey, 60, 60);

        if (message != null)
        {
            graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
            graphics2D.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize * 2);

            if (--messageCounter < 0)
            {
                messageCounter = 180;
                message = null;
            }
        }
    }

    public void drawInventory()
    {
        graphics2D.setFont(font);
        graphics2D.setColor(new Color(230,200,170));
        graphics2D.drawImage(bufferedImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, null);

        graphics2D.setBackground(new Color(100,100,100));
        graphics2D.drawString("Ваш инвентарь", 160, 160);






        if (message != null)
        {
            graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
            graphics2D.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize * 2);

            if (--messageCounter < 0)
            {
                messageCounter = 180;
                message = null;
            }
        }
    }
}

