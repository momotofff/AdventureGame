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

    public static CommandsMenu commandMenu;
    public static CommandsPause commandPause;

    public enum CommandsMenu {
        Start, Load, Exit
    }

    public enum CommandsPause
    {
        Continue, Load, Save, Exit
    }

    public UI(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        commandMenu = CommandsMenu.Start;
        commandPause = CommandsPause.Continue;

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

    public void draw(Graphics2D graphics2D, GameState state)
    {
        this.graphics2D = graphics2D;

        switch (state)
        {
            case Running -> drawInterphase();
            case Paused -> drawPausedScreen();
            case Inventory -> drawInventory();
            case Dialog -> drawDialog();
            case StartScreen -> drawStartScreen();
        }
    }

    private void drawStartScreen()
    {
        graphics2D.setColor(filling);
        graphics2D.fillRect(0, 0, GamePanel.getScreenSize().x, GamePanel.getScreenSize().y);


        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 150f));
        String nameGame = "Adventure game";
        int length = (int) graphics2D.getFontMetrics().getStringBounds(nameGame, graphics2D).getWidth();
        int x = GamePanel.getScreenSize().x / 2 - length / 2;
        int y = GamePanel.getScreenSize().y / 4;

        graphics2D.setColor(Color.black);
        graphics2D.drawString(nameGame, x, y);

        graphics2D.setColor(edging);
        graphics2D.drawString(nameGame, x, y);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("New Game", x, y + GamePanel.tileSize * 2);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Load Game", x, y + GamePanel.tileSize * 3);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Exit", x, y + GamePanel.tileSize * 4);

        switch (commandMenu)
        {
            case Start :graphics2D.drawString(">", x - GamePanel.tileSize, y + GamePanel.tileSize * 2); break;
            case Load : graphics2D.drawString(">", x - GamePanel.tileSize, y + GamePanel.tileSize * 3);break;
            case Exit : graphics2D.drawString(">", x - GamePanel.tileSize, y + GamePanel.tileSize * 4);break;
        }
    }

    public void drawPausedScreen()
    {
        Rectangle window = new Rectangle(GamePanel.tileSize * 6, GamePanel.tileSize * 4, GamePanel.getScreenSize().x - (GamePanel.tileSize * 12), GamePanel.tileSize * 9);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );

        graphics2D.setFont(maruMonica.deriveFont(Font.PLAIN, 100));
        graphics2D.setColor(edging);

        String paused = "PAUSED";
        int length = (int) graphics2D.getFontMetrics().getStringBounds(paused, graphics2D).getWidth();
        int x = GamePanel.getScreenSize().x / 2 - length / 2;
        int y = GamePanel.getScreenSize().y / 2 - 30;
        
        graphics2D.drawString(paused, x, y);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Continue", x, y + GamePanel.tileSize * 2);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Load", x, y + GamePanel.tileSize * 3);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Save", x, y + GamePanel.tileSize * 4);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Exit", x, y + GamePanel.tileSize * 5);

        switch (commandPause)
        {
            case Continue :graphics2D.drawString(">", x - GamePanel.tileSize, y + GamePanel.tileSize * 2); break;
            case Load : graphics2D.drawString(">", x - GamePanel.tileSize, y + GamePanel.tileSize * 3);break;
            case Save : graphics2D.drawString(">", x - GamePanel.tileSize, y + GamePanel.tileSize * 4);break;
            case Exit : graphics2D.drawString(">", x - GamePanel.tileSize, y + GamePanel.tileSize * 5);break;
        }
    }

    public void drawInterphase()
    {
        graphics2D.setFont(maruMonica.deriveFont(30F));
        graphics2D.setColor(new Color(230,200,170));
        graphics2D.drawImage(bufferedImage, GamePanel.tileSize / 2, GamePanel.tileSize / 2, GamePanel.tileSize / 2, GamePanel.tileSize / 2, null);
        graphics2D.drawString(" x " + gamePanel.player.keysCount, 60, 60);

        if (message != null)
        {
            graphics2D.drawString(message, GamePanel.tileSize / 2, GamePanel.tileSize * 2);

            if (--messageCounter < 0)
            {
                messageCounter = 180;
                message = null;
            }
        }
    }

    public void drawInventory()
    {
        Rectangle window = new Rectangle(GamePanel.tileSize * 3, GamePanel.tileSize, GamePanel.getScreenSize().x - (GamePanel.tileSize * 6), GamePanel.tileSize * 4);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );

        graphics2D.setFont(maruMonica.deriveFont(30f));
        graphics2D.setColor(edging);
        graphics2D.drawImage(bufferedImage, GamePanel.tileSize / 2, GamePanel.tileSize / 2, GamePanel.tileSize / 2, GamePanel.tileSize / 2, null);

        graphics2D.drawString("Тут будет отображаться инвентарь", 210, 100);

        if (message != null)
        {
            graphics2D.setFont(maruMonica.deriveFont(30f));
            graphics2D.drawString(message, GamePanel.tileSize / 2, GamePanel.tileSize * 2);

            if (--messageCounter < 0)
            {
                messageCounter = 180;
                message = null;
            }
        }
    }

    private void drawDialog()
    {
        Rectangle window = new Rectangle(GamePanel.tileSize * 3, GamePanel.tileSize, GamePanel.getScreenSize().x - (GamePanel.tileSize * 6), GamePanel.tileSize * 4);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );
        graphics2D.setFont(maruMonica.deriveFont(Font.PLAIN, 40));

        for (String line : gamePanel.currentDialogue.getText().split("/n"))
        {
            graphics2D.drawString(line, window.x + GamePanel.tileSize, window.y + GamePanel.tileSize);
            window.y += GamePanel.tileSize;
        }
    }
}

