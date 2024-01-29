package main.screens;

import main.screens.interfaces.IScreenSwitcher;

import java.awt.*;

public abstract class AbstractScreen
{
    protected IScreenSwitcher switcher;

    protected Color edging = new Color(100, 60, 20);
    protected Color filling = new Color(150, 120, 50, 220);

    AbstractScreen(IScreenSwitcher switcher)
    {
        this.switcher = switcher;
    }

    public abstract void draw(Graphics2D graphics2D, Font font);
    public abstract void activate();
    public abstract void deactivate();

}
