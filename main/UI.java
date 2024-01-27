package main;

import main.screens.*;
import main.screens.DialogScreen;
import main.screens.interfaces.IDialogueStarter;
import main.screens.interfaces.IScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UI extends JPanel implements Runnable, IScreenSwitcher, IDialogueStarter
{
    Font maruMonica;

    private String message = null;
    int messageCounter = 180;

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

    @Override
    public void startDialogue(AbstractDialogue dialogue)
    {
        DialogScreen dialogScreen = (DialogScreen) screens.get(GameState.Dialog);
        if (dialogScreen == null)
            return;

        dialogScreen.setDialogue(dialogue);
        switchScreen(GameState.Dialog);
    }

    public UI()
    {
        gameCommons = new GameCommons(keyHandler, this);

        screens.put(GameState.StartScreen, new StartMenu(this, keyHandler));
        screens.put(GameState.Paused, new Pause(this, keyHandler));
        screens.put(GameState.Inventory, new Inventory(this, keyHandler));
        screens.put(GameState.Running, new Running(this, keyHandler, gameCommons));
        screens.put(GameState.Dialog, new DialogScreen(this, keyHandler));

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

