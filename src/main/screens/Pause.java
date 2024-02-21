package main.screens;

import main.GameState;
import main.KeyHandler;
import main.Parameters;
import main.screens.interfaces.IScreenShotter;
import main.screens.interfaces.IScreenSwitcher;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Pause extends AbstractScreen
{
    private enum Items
    {
        Continue,
        Load,
        Save,
        Exit
    }

    private Items menuPosition = Items.Continue;
    private final IScreenShotter screenShotter;
    private BufferedImage screenShot;

    public Pause(IScreenSwitcher switcher, KeyHandler keyHandler, IScreenShotter screenShotter)
    {
        super(switcher, keyHandler, 2);
        this.screenShotter = screenShotter;
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {
        if (screenShot != null)
            graphics2D.drawImage(screenShot, null, 0, 0);

        drawWindow(graphics2D);

        if (isFinishAnimation) {
            graphics2D.setFont(font.deriveFont(Font.PLAIN, 100));
            graphics2D.setColor(edging);

            String paused = "PAUSED";
            int length = (int) graphics2D.getFontMetrics().getStringBounds(paused, graphics2D).getWidth();
            int x = Parameters.screenSize.x / 2 - length / 2;
            int y = Parameters.screenSize.y / 2 - Parameters.tileSize * 2 ;

            graphics2D.drawString(paused, x, y);

            graphics2D.setColor(edging);
            graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
            graphics2D.drawString("Continue", x, y + Parameters.tileSize);

            graphics2D.setColor(edging);
            graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
            graphics2D.drawString("Load", x, y + Parameters.tileSize * 2);

            graphics2D.setColor(edging);
            graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
            graphics2D.drawString("Save", x, y + Parameters.tileSize * 3);

            graphics2D.setColor(edging);
            graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
            graphics2D.drawString("Exit", x, y + Parameters.tileSize * 4);

            switch (menuPosition) {
                case Continue:
                    graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize);
                    break;
                case Load:
                    graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 2);
                    break;
                case Save:
                    graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 3);
                    break;
                case Exit:
                    graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 4);
                    break;
            }
        }
    }

    @Override
    public void activate()
    {
        menuPosition = Items.Continue;

        keyHandler.addListener(KeyEvent.VK_W, this::onKeyW);
        keyHandler.addListener(KeyEvent.VK_S, this::onKeyS);
        keyHandler.addListener(KeyEvent.VK_SPACE, this::onKeySpace);

        screenShot = screenShotter.getScreenShot();
    }

    private void onKeyW()
    {
        switch (menuPosition)
        {
            case Continue -> menuPosition = Items.Exit;
            case Load -> menuPosition = Items.Continue;
            case Save -> menuPosition = Items.Load;
            case Exit -> menuPosition = Items.Save;
        }
    }

    private void onKeyS()
    {
        switch (menuPosition)
        {
            case Continue -> menuPosition = Items.Load;
            case Load -> menuPosition = Items.Save;
            case Save -> menuPosition = Items.Exit;
            case Exit -> menuPosition = Items.Continue;
        }
    }

    private void onKeySpace()
    {
        switch (menuPosition)
        {
            case Continue:
                switcher.switchScreen(GameState.Running);
                break;
            case Load:
                menuPosition = Items.Exit;
                break;
            case Save:
                menuPosition = Items.Exit;
                break;
            case Exit:
                switcher.switchScreen(GameState.StartScreen);
                break;
        }
    }

    @Override
    public void deactivate()
    {
        keyHandler.removeListeners();
        screenShot = null;
        scaleCountWindow = (Point) startSizeWindow.clone();
        isFinishAnimation = false;
    }
}
