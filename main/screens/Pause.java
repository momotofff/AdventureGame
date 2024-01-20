package main.screens;

import main.GamePanel;
import main.GameState;
import main.KeyHandler;
import main.Parameters;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Pause extends AbstractScreen implements IScreenSwitcher
{
    private enum PauseItems
    {
        Continue,
        Load,
        Save,
        Exit
    }

    private PauseItems pausePosition = PauseItems.Continue;
    private final KeyHandler keyHandler;

    public Pause(IScreenSwitcher switcher, KeyHandler keyHandler)
    {
        super(switcher);
        this.keyHandler = keyHandler;
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {
        Rectangle window = new Rectangle(Parameters.tileSize * 6, Parameters.tileSize * 4, Parameters.screenSize.x - (Parameters.tileSize * 12), Parameters.tileSize * 9);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );

        graphics2D.setFont(font.deriveFont(Font.PLAIN, 100));
        graphics2D.setColor(edging);

        String paused = "PAUSED";
        int length = (int) graphics2D.getFontMetrics().getStringBounds(paused, graphics2D).getWidth();
        int x = Parameters.screenSize.x / 2 - length / 2;
        int y = Parameters.screenSize.y / 2 - 30;

        graphics2D.drawString(paused, x, y);

        graphics2D.setColor(edging);
        graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Continue", x, y + Parameters.tileSize * 2);

        graphics2D.setColor(edging);
        graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Load", x, y + Parameters.tileSize * 3);

        graphics2D.setColor(edging);
        graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Save", x, y + Parameters.tileSize * 4);

        graphics2D.setColor(edging);
        graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Exit", x, y + Parameters.tileSize * 5);

        switch (pausePosition)
        {
            case Continue :graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 2); break;
            case Load : graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 3);break;
            case Save : graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 4);break;
            case Exit : graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 5);break;
        }
    }

    @Override
    public void activate()
    {
        pausePosition = PauseItems.Continue;

        keyHandler.addListener(KeyEvent.VK_W, () -> {
            switch (pausePosition)
            {
                case Continue -> pausePosition = PauseItems.Exit;
                case Load -> pausePosition = PauseItems.Continue;
                case Save -> pausePosition = PauseItems.Load;
                case Exit -> pausePosition = PauseItems.Save;
            }
        });

        keyHandler.addListener(KeyEvent.VK_S, () -> {
            switch (pausePosition)
            {
                case Continue -> pausePosition = PauseItems.Load;
                case Load -> pausePosition = PauseItems.Save;
                case Save -> pausePosition = PauseItems.Exit;
                case Exit -> pausePosition = PauseItems.Continue;
            }
        });

        keyHandler.addListener(KeyEvent.VK_SPACE, () -> {
            switch (pausePosition)
            {
                case Continue:
                    switcher.switchScreen(GameState.Running);
                    break;
                case Load:
                    pausePosition = PauseItems.Exit;
                    break;
                case Save:
                    pausePosition = PauseItems.Exit;
                    break;
                case Exit:
                    switcher.switchScreen(GameState.StartScreen);
                    break;
            }
        });
    }

    @Override
    public void deactivate()
    {
        keyHandler.removeListener(KeyEvent.VK_W);
        keyHandler.removeListener(KeyEvent.VK_S);
        keyHandler.removeListener(KeyEvent.VK_SPACE);
    }

    @Override
    public void switchScreen(GameState newState)
    {
        GamePanel.state = newState;
    }
}
