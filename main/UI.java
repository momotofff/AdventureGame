package main;

import entity.Entity;
import main.screens.*;
import objects.BaseObject;
import objects.Key;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI extends JPanel implements Runnable, IScreenSwitcher
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
    Thread gameThread;
    public KeyHandler keyHandler = new KeyHandler();

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
            case StartScreen -> currentScreen = new StartMenu(this, keyHandler);
            case Paused -> currentScreen = new Pause(this, keyHandler);
            case Inventory -> currentScreen = new Inventory(this, keyHandler);
            case Running -> currentScreen = new Running(this, keyHandler);
        }

        currentScreen.activate();
    }

    public UI(GamePanel gamePanel)
    {
        this.setPreferredSize(new Dimension(Parameters.screenSize.x, Parameters.screenSize.y));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

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

    @Override
    public void run()
    {
        final int FPS = 60;
        long drawInterval = 1000 / FPS;
        long nextDrawTime = System.currentTimeMillis() + drawInterval;

        while (gameThread != null)
        {
            //update();
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

    public void startGame()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        if (currentScreen == null)
            switchScreen(GameState.StartScreen);

        currentScreen.draw(graphics2D, maruMonica);
        graphics2D.dispose();
    }
}

