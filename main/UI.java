package main;

import objects.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI
{
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font maruMonica;
    BufferedImage bufferedImage;

    private String message = null;

    int messageCounter = 180;

    Color edging = new Color(100, 60, 20);
    Color filling = new Color(150, 120, 50, 220);

    public UI(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        InputStream inputStream = getClass().getResourceAsStream("/assets/Font/MaruMonica.ttf");

        try
        {
            if (inputStream != null)
            {
                maruMonica = Font.createFont(Font.TRUETYPE_FONT, inputStream);
                maruMonica.deriveFont(30f);
            }
        }
        catch (FontFormatException | IOException e)
        {
            throw new RuntimeException(e);
        }

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
            case Dialog : drawDialog(); break;
        }
    }

    public void drawPausedScreen()
    {
        Rectangle window = new Rectangle(gamePanel.tileSize * 6, gamePanel.tileSize * 4, gamePanel.screenSize.x - (gamePanel.tileSize * 12), gamePanel.tileSize * 4);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );

        graphics2D.setFont(maruMonica.deriveFont(Font.PLAIN, 100));
        graphics2D.setColor(edging);

        String paused = "PAUSED";
        int length = (int) graphics2D.getFontMetrics().getStringBounds(paused, graphics2D).getWidth();
        int x = gamePanel.screenSize.x / 2 - length / 2;
        int y = gamePanel.screenSize.y / 2 - 30;
        
        graphics2D.drawString(paused, x, y);
    }

    public void drawInterphase()
    {
        graphics2D.setFont(maruMonica.deriveFont(30F));
        graphics2D.setColor(new Color(230,200,170));
        graphics2D.drawImage(bufferedImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, null);
        graphics2D.drawString(" x " + gamePanel.player.keysCount, 60, 60);

        if (message != null)
        {
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
        Rectangle window = new Rectangle(gamePanel.tileSize * 3, gamePanel.tileSize, gamePanel.screenSize.x - (gamePanel.tileSize * 6), gamePanel.tileSize * 4);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );

        graphics2D.setFont(maruMonica.deriveFont(30f));
        graphics2D.setColor(edging);
        graphics2D.drawImage(bufferedImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, null);

        graphics2D.drawString("Тут будет отображаться инвентарь", 210, 100);

        if (message != null)
        {
            graphics2D.setFont(maruMonica.deriveFont(30f));
            graphics2D.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize * 2);

            if (--messageCounter < 0)
            {
                messageCounter = 180;
                message = null;
            }
        }
    }

    private void drawDialog()
    {
        Rectangle window = new Rectangle(gamePanel.tileSize * 3, gamePanel.tileSize, gamePanel.screenSize.x - (gamePanel.tileSize * 6), gamePanel.tileSize * 4);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );
        graphics2D.setFont(maruMonica.deriveFont(Font.PLAIN, 40));

        for (String line : gamePanel.currentDialogue.getText().split("/n"))
        {
            graphics2D.drawString(line, window.x + gamePanel.tileSize, window.y + gamePanel.tileSize);
            window.y += gamePanel.tileSize;
        }
    }
}

