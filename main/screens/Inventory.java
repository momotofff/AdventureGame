package main.screens;

import main.GameState;
import main.KeyHandler;
import main.Parameters;
import main.screens.interfaces.IScreenShotter;
import main.screens.interfaces.IScreenSwitcher;
import objects.Key;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Inventory extends AbstractScreen
{
    private String message = null;
    int messageCounter = 180;
    BufferedImage bufferedImage = new Key().image;
    private final IScreenShotter screenShotter;
    private BufferedImage screenShot;

    public Inventory(IScreenSwitcher switcher, KeyHandler keyHandler, IScreenShotter screenShotter)
    {
        super(switcher, keyHandler);
        this.screenShotter = screenShotter;
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {
        if (screenShot != null)
            graphics2D.drawImage(screenShot, null, 0, 0);

        Rectangle window = new Rectangle(Parameters.tileSize * 3, Parameters.tileSize, Parameters.screenSize.x - (Parameters.tileSize * 6), Parameters.tileSize * 4);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );

        graphics2D.setFont(font.deriveFont(30f));
        graphics2D.setColor(edging);
        graphics2D.drawImage(bufferedImage, Parameters.tileSize / 2, Parameters.tileSize / 2, Parameters.tileSize / 2, Parameters.tileSize / 2, null);

        graphics2D.drawString("Тут будет отображаться инвентарь", 210, 100);

        if (message != null)
        {
            graphics2D.setFont(font.deriveFont(30f));
            graphics2D.drawString(message, Parameters.tileSize / 2, Parameters.tileSize * 2);

            if (--messageCounter < 0)
            {
                messageCounter = 180;
                message = null;
            }
        }
    }

    @Override
    public void activate()
    {
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
