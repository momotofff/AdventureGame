package main.screens;

import main.GameState;
import main.KeyHandler;
import main.Parameters;
import main.screens.interfaces.IScreenSwitcher;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StartMenu extends AbstractScreen
{
    private enum MenuItems
    {
        Start,
        Load,
        Exit
    }

    private MenuItems menuPosition = MenuItems.Start;

    public StartMenu(IScreenSwitcher switcher, KeyHandler keyHandler)
    {
        super(switcher, keyHandler);
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {
        graphics2D.setColor(filling);
        graphics2D.fillRect(0, 0, Parameters.screenSize.x, Parameters.screenSize.y);

        graphics2D.setFont(font.deriveFont(Font.BOLD, 150f));
        String nameGame = "Adventure game";
        int length = (int) graphics2D.getFontMetrics().getStringBounds(nameGame, graphics2D).getWidth();
        int x = Parameters.screenSize.x / 2 - length / 2;
        int y = Parameters.screenSize.y / 4;

        graphics2D.setColor(Color.black);
        graphics2D.drawString(nameGame, x, y);

        graphics2D.setColor(edging);
        graphics2D.drawString(nameGame, x, y);

        graphics2D.setColor(edging);
        graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("New Game", x, y + Parameters.tileSize * 2);

        graphics2D.setColor(edging);
        graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Load Game", x, y + Parameters.tileSize * 3);

        graphics2D.setColor(edging);
        graphics2D.setFont(font.deriveFont(Font.BOLD, 50f));
        graphics2D.drawString("Exit", x, y + Parameters.tileSize * 4);

        switch (menuPosition)
        {
            case Start: graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 2); break;
            case Load: graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 3);break;
            case Exit: graphics2D.drawString(">", x - Parameters.tileSize, y + Parameters.tileSize * 4);break;
        }
    }

    @Override
    public void activate()
    {
        menuPosition = MenuItems.Start;

        keyHandler.addListener(KeyEvent.VK_W, this::onKeyW);
        keyHandler.addListener(KeyEvent.VK_S, this::onKeyS);
        keyHandler.addListener(KeyEvent.VK_SPACE, this::onKeySpace);
    }

    private void onKeyW()
    {
        switch (menuPosition)
        {
            case Start -> menuPosition = MenuItems.Exit;
            case Load -> menuPosition = MenuItems.Start;
            case Exit -> menuPosition = MenuItems.Load;
        }
    }

    private void onKeyS()
    {
        switch (menuPosition)
        {
            case Start -> menuPosition = MenuItems.Load;
            case Load -> menuPosition = MenuItems.Exit;
            case Exit -> menuPosition = MenuItems.Start;
        }
    }

    private void onKeySpace()
    {
        switch (menuPosition)
        {
            case Start:
                switcher.switchScreen(GameState.Running);
                break;
            case Load:
                menuPosition = MenuItems.Exit;
                break;
            case Exit:
                System.exit(0);
                break;
        }
    }

    @Override
    public void deactivate()
    {
        keyHandler.removeListeners();
    }
}
