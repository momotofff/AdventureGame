package main.screens;

import main.KeyHandler;
import main.Parameters;
import main.screens.interfaces.IScreenSwitcher;

import java.awt.*;

public abstract class AbstractScreen
{
    protected final IScreenSwitcher switcher;
    protected final KeyHandler keyHandler;

    protected Color edging = new Color(100, 60, 20);
    protected Color filling = new Color(150, 120, 50);

    private final int speedAnimation = 20;
    protected final Point startSizeWindow = new Point(Parameters.screenSize.x / 2 - (Parameters.tileSize * 6), Parameters.screenSize.y / 2 - (Parameters.tileSize * 4));
    protected Point endSizeWindow;
    protected Point scaleCountWindow = (Point) startSizeWindow.clone();
    protected boolean isFinishAnimation = false;

    AbstractScreen(IScreenSwitcher switcher, KeyHandler keyHandler, int scale)
    {
        this.switcher = switcher;
        this.keyHandler = keyHandler;
        endSizeWindow = new Point(Parameters.screenSize.x / 2, Parameters.screenSize.y / scale);
    }

    public abstract void draw(Graphics2D graphics2D, Font font);
    public void update() {};
    public abstract void activate();
    public abstract void deactivate();

    protected void drawWindow(Graphics2D graphics2D)
    {
        if (!isFinishAnimation)
            if (scaleCountWindow.x <= endSizeWindow.x)
                scaleCountWindow.x += speedAnimation;

        if(scaleCountWindow.y <= endSizeWindow.y)
            scaleCountWindow.y += speedAnimation;

        Rectangle window = new Rectangle(startSizeWindow.x, startSizeWindow.y, scaleCountWindow.x, scaleCountWindow.y);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);

        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );

        if (scaleCountWindow.x >= endSizeWindow.x && scaleCountWindow.y >= endSizeWindow.y)
            isFinishAnimation = true;
    }
}
