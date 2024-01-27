package main.screens;

import main.GameCommons;
import main.GameState;
import main.KeyHandler;
import main.Parameters;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Dialog extends AbstractScreen
{
    private final KeyHandler keyHandler;
    private final GameCommons gameCommons;

    public Dialog(IScreenSwitcher switcher, KeyHandler keyHandler, GameCommons gameCommons)
    {
        super(switcher);
        this.keyHandler = keyHandler;
        this.gameCommons = gameCommons;
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {
        Rectangle window = new Rectangle(Parameters.tileSize * 3, Parameters.tileSize, Parameters.screenSize.x - (Parameters.tileSize * 6), Parameters.tileSize * 4);
        graphics2D.setColor(filling);
        graphics2D.fillRoundRect(window.x, window.y, window.width, window.height, 50, 50);
        graphics2D.setColor(edging);
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawRoundRect(window.x, window.y, window.width, window.height, 50, 50 );
        graphics2D.setFont(font.deriveFont(Font.PLAIN, 40));

        graphics2D.drawString(gameCommons.getPhrase(), window.x + Parameters.tileSize, window.y + Parameters.tileSize);
        window.y += Parameters.tileSize;
    }

    @Override
    public void activate()
    {
        keyHandler.addListener(KeyEvent.VK_SPACE, this::onKeySpace);
    }

    private void onKeySpace()
    {

    }

    @Override
    public void deactivate()
    {
        keyHandler.removeListener(KeyEvent.VK_SPACE);
    }
}
