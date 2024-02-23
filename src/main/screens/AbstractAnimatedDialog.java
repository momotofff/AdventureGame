package main.screens;

import main.KeyHandler;
import main.Parameters;
import main.screens.interfaces.IScreenSwitcher;

import java.awt.*;

public abstract class AbstractAnimatedDialog extends AbstractScreen
{
    // TODO: Adjust initial window position according to content
    private final Point windowPosition = new Point(Parameters.screenSize.x / 2 - (Parameters.tileSize * 6), Parameters.screenSize.y / 2 - (Parameters.tileSize * 4));
    private final Point endWindowSize;
    private Point currentWindowSize = (Point) windowPosition.clone();

    private boolean isAnimationFinished = false;

    AbstractAnimatedDialog(IScreenSwitcher switcher, KeyHandler keyHandler, Point endWindowSize)
    {
        super(switcher, keyHandler);
        this.endWindowSize = endWindowSize;
    }

    protected void drawAnimation(Graphics2D graphics2D)
    {
        final int AnimationSpeed = 20;

        if (currentWindowSize.x <= endWindowSize.x)
            currentWindowSize.x += AnimationSpeed;

        if (currentWindowSize.y <= endWindowSize.y)
            currentWindowSize.y += AnimationSpeed;

        Rectangle window = new Rectangle(windowPosition.x, windowPosition.y, currentWindowSize.x, currentWindowSize.y);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);

        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );

        if (currentWindowSize.x >= endWindowSize.x && currentWindowSize.y >= endWindowSize.y)
            isAnimationFinished = true;
    }

    @Override
    public void activate()
    {
        currentWindowSize = (Point) windowPosition.clone();
        isAnimationFinished = false;
    }

    public boolean isAnimationFinished()
    {
        return isAnimationFinished;
    }

    public Point getWindowPosition()
    {
        return windowPosition;
    }
}
