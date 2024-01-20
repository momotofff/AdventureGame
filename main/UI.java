package main;

import main.screens.*;
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
            case Paused -> currentScreen = new Pause(this, gamePanel.keyHandler);
            case Inventory -> currentScreen = new Inventory(this, gamePanel.keyHandler);
            case Running -> currentScreen = new Running(this, gamePanel.keyHandler);
        }

        currentScreen.activate();
    }

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

    public void draw(Graphics2D graphics2D, GameState state)
    {
        this.graphics2D = graphics2D;

        if (state == GameState.Running)
        {
            drawInterface();
            return;
        }

        if (currentScreen == null)
            switchScreen(GameState.StartScreen);

        currentScreen.draw(graphics2D, maruMonica);
    }

    public void drawInterface()
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

