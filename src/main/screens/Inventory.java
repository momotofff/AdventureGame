package main.screens;

import main.GameState;
import main.KeyHandler;
import main.Parameters;
import main.screens.interfaces.IScreenShotter;
import main.screens.interfaces.IScreenSwitcher;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Inventory extends AbstractAnimatedDialog
{
    private final IScreenShotter screenShotter;
    private BufferedImage screenShot;
    private int scale = 2;

    public Inventory(IScreenSwitcher switcher, KeyHandler keyHandler, IScreenShotter screenShotter)
    {
        super(switcher, keyHandler, new Point(Parameters.screenSize.x / 2, Parameters.screenSize.y / 2));
        this.screenShotter = screenShotter;
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {
        if (screenShot != null)
            graphics2D.drawImage(screenShot, null, 0, 0);

        drawAnimation(graphics2D);
        if (!isAnimationFinished())
            return;

        graphics2D.setFont(font.deriveFont(30f));
        graphics2D.drawString("Тут будет отображаться инвентарь", getWindowPosition().x + Parameters.tileSize,  getWindowPosition().y + Parameters.tileSize);
    }

    @Override
    public void activate()
    {
        super.activate();
        keyHandler.addListener(KeyEvent.VK_E, () -> switcher.switchScreen(GameState.Running));
        keyHandler.addListener(KeyEvent.VK_SPACE, () -> switcher.switchScreen(GameState.Running));
        screenShot = screenShotter.getScreenShot();
    }

    @Override
    public void deactivate()
    {
        keyHandler.removeListeners();
        screenShot = null;
    }
}
