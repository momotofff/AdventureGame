package main;

import main.screens.AbstractScreen;
import main.screens.IScreenSwitcher;
import main.screens.StartMenu;
import objects.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI implements IScreenSwitcher
{
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font maruMonica;
    BufferedImage bufferedImage;

    private String message = null;

    int messageCounter = 180;

    Color edging = new Color(100, 60, 20);
    Color filling = new Color(150, 120, 50, 220);

    private AbstractScreen currentScreen;

    public static CommandsMenu commandMenu;
    public static CommandsPause commandPause;

    @Override
    public void switchScreen(GameState newState)
    {
        GamePanel.state = newState;

        if (newState == GameState.Running)
        {
            GamePanel.sound.play(Sounds.Theme);
            GamePanel.sound.loop();
        }

        if (currentScreen != null)
            currentScreen.deactivate();

        switch (newState)
        {
            case StartScreen -> currentScreen = new StartMenu(this, gamePanel.keyHandler);
        }

        currentScreen.activate();
    }

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

        if (state == GameState.Running)
        {
            // TODO: Should be removed after refactor
            drawInterphase();
            return;
        }

        if (currentScreen == null)
            switchScreen(GameState.StartScreen);

        currentScreen.draw(graphics2D, maruMonica);

        /*
        switch (state)
        {
            case Running -> drawInterphase();
            case Paused -> drawPausedScreen();
            case Inventory -> drawInventory();
            case Dialog -> drawDialog();
        }
        */
    }

    public void drawPausedScreen()
    {
        Rectangle window = new Rectangle(Parameters.tileSize * 6, Parameters.tileSize * 4, Parameters.screenSize.x - (Parameters.tileSize * 12), Parameters.tileSize * 9);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );

        graphics2D.setFont(maruMonica.deriveFont(Font.PLAIN, 100));
        graphics2D.setColor(edging);

        String paused = "PAUSED";
        int length = (int) graphics2D.getFontMetrics().getStringBounds(paused, graphics2D).getWidth();
        int x = Parameters.screenSize.x / 2 - length / 2;
        int y = Parameters.screenSize.y / 2 - 30;
        
        graphics2D.drawString(paused, x, y);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Continue", x, y + Parameters.tileSize * 2);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Load", x, y + Parameters.tileSize * 3);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Save", x, y + Parameters.tileSize * 4);

        graphics2D.setColor(edging);
        graphics2D.setFont(maruMonica.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Exit", x, y + Parameters.tileSize * 5);

        switch (commandPause)
        {
            case Continue :graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 2); break;
            case Load : graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 3);break;
            case Save : graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 4);break;
            case Exit : graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 5);break;
        }
    }

    public void drawInterphase()
    {
        graphics2D.setFont(maruMonica.deriveFont(30F));
        graphics2D.setColor(new Color(230,200,170));
        graphics2D.drawImage(bufferedImage, Parameters.tileSize / 2, Parameters.tileSize / 2, Parameters.tileSize / 2, Parameters.tileSize / 2, null);
        graphics2D.drawString(" x " + gamePanel.player.keysCount, 60, 60);

        if (message != null)
        {
            graphics2D.drawString(message, Parameters.tileSize / 2, Parameters.tileSize * 2);

            if (--messageCounter < 0)
            {
                messageCounter = 180;
                message = null;
            }
        }
    }

    public void drawInventory()
    {
        Rectangle window = new Rectangle(Parameters.tileSize * 3, Parameters.tileSize, Parameters.screenSize.x - (Parameters.tileSize * 6), Parameters.tileSize * 4);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );

        graphics2D.setFont(maruMonica.deriveFont(30f));
        graphics2D.setColor(edging);
        graphics2D.drawImage(bufferedImage, Parameters.tileSize / 2, Parameters.tileSize / 2, Parameters.tileSize / 2, Parameters.tileSize / 2, null);

        graphics2D.drawString("Тут будет отображаться инвентарь", 210, 100);

        if (message != null)
        {
            graphics2D.setFont(maruMonica.deriveFont(30f));
            graphics2D.drawString(message, Parameters.tileSize / 2, Parameters.tileSize * 2);

            if (--messageCounter < 0)
            {
                messageCounter = 180;
                message = null;
            }
        }
    }

    private void drawDialog()
    {
        Rectangle window = new Rectangle(Parameters.tileSize * 3, Parameters.tileSize, Parameters.screenSize.x - (Parameters.tileSize * 6), Parameters.tileSize * 4);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );
        graphics2D.setFont(maruMonica.deriveFont(Font.PLAIN, 40));

        for (String line : gamePanel.currentDialogue.getText().split("/n"))
        {
            graphics2D.drawString(line, window.x + Parameters.tileSize, window.y + Parameters.tileSize);
            window.y += Parameters.tileSize;
        }
    }
}

