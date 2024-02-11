package main;

import main.screens.*;
import main.screens.DialogScreen;
import main.screens.interfaces.IDialogueStarter;
import main.screens.interfaces.IMessageShower;
import main.screens.interfaces.IScreenShotter;
import main.screens.interfaces.IScreenSwitcher;
import main.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UI extends JPanel implements Runnable, IScreenSwitcher, IDialogueStarter, IScreenShotter, IMessageShower
{
    Font maruMonica;

    private final JFrame frame;
    private Thread gameThread;

    private final Map<GameState, AbstractScreen> screens = new HashMap<>();
    private final KeyHandler keyHandler = new KeyHandler();
    private final GameCommons gameCommons;
    private GameState gameState = GameState.StartScreen;

    @Override
    public void switchScreen(GameState newState)
    {
        AbstractScreen old = screens.get(gameState);
        if (old != null)
            old.deactivate();

        screens.get(newState).activate();
        gameState = newState;
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

    @Override
    public void showMessage(String message)
    {
        Running runningScreen = (Running) screens.get(GameState.Running);
        if (runningScreen == null)
            return;

        runningScreen.setMessage(message);
    }

    public UI(JFrame frame)
    {
        this.frame = frame;
        gameCommons = new GameCommons(this, keyHandler, this);

        screens.put(GameState.StartScreen, new StartMenu(this, keyHandler));
        screens.put(GameState.Paused, new Pause(this, keyHandler, this));
        screens.put(GameState.Inventory, new Inventory(this, keyHandler));
        screens.put(GameState.Running, new Running(this, keyHandler, gameCommons));
        screens.put(GameState.Dialog, new DialogScreen(this, keyHandler, this));

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

    @Override
    public void run()
    {
        final int FPS = 60;
        long drawInterval = 1000 / FPS;
        long nextDrawTime = System.currentTimeMillis() + drawInterval;

        while (gameThread != null)
        {
            AbstractScreen screen = screens.get(gameState);
            if (screen != null)
                screen.update();

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

    @Override
    public BufferedImage getScreenShot()
    {
        JRootPane root = frame.getRootPane();
        Dimension dimension = root.getSize();
        BufferedImage image = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        root.print(graphics2D);
        graphics2D.dispose();

        return ImageUtils.darker(ImageUtils.blur(image));
    }
}

