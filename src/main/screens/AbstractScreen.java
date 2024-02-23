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

    AbstractScreen(IScreenSwitcher switcher, KeyHandler keyHandler)
    {
        this.switcher = switcher;
        this.keyHandler = keyHandler;
    }

    public abstract void draw(Graphics2D graphics2D, Font font);
    public void update() {};
    public abstract void activate();
    public abstract void deactivate();
}
