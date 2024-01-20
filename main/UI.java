package main;

import main.screens.*;
import objects.Key;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UI extends JPanel implements Runnable, IScreenSwitcher
{
    Graphics2D graphics2D;
    Font maruMonica;

    private String message = null;
    int messageCounter = 180;

    Color edging = new Color(100, 60, 20);
    Color filling = new Color(150, 120, 50, 220);

    private final Map<GameState, AbstractScreen> screens = new HashMap<>();
    private Thread gameThread;
    private final KeyHandler keyHandler = new KeyHandler();
    private final GameCommons gameCommons;
    private GameState gameState = GameState.StartScreen;

    @Override
    public void switchScreen(GameState newState)
    {
        AbstractScreen old = screens.get(gameState);
        if (old != null)
            old.deactivate();

        gameState = newState;
        screens.get(gameState).activate();
    }

    public UI()
    {
        gameCommons = new GameCommons(keyHandler);

        screens.put(GameState.StartScreen, new StartMenu(this, keyHandler));
        screens.put(GameState.Paused, new Pause(this, keyHandler));
        screens.put(GameState.Inventory, new Inventory(this, keyHandler));
        screens.put(GameState.Running, new Running(this, keyHandler, gameCommons));

        this.setPreferredSize(new Dimension(Parameters.screenSize.x, Parameters.screenSize.y));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

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
    }

    public void showMessage(String text)
    {
        message = text;
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
/*
        for (String line : gamePanel.currentDialogue.getText().split("/n"))
        {
            graphics2D.drawString(line, window.x + Parameters.tileSize, window.y + Parameters.tileSize);
            window.y += Parameters.tileSize;
        }*/
    }

    @Override
    public void run()
    {
        final int FPS = 60;
        long drawInterval = 1000 / FPS;
        long nextDrawTime = System.currentTimeMillis() + drawInterval;

        while (gameThread != null)
        {
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
        switchScreen(GameState.StartScreen);
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        AbstractScreen screen = screens.get(gameState);
        if (screen == null)
            return;

        Graphics2D graphics2D = (Graphics2D) graphics;
        screen.draw(graphics2D, maruMonica);
        graphics2D.dispose();
    }
}

