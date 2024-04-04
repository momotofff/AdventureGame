package main.screens;

import entity.BaseMagician;
import entity.Entity;
import main.*;
import main.screens.interfaces.IScreenSwitcher;
import objects.BaseObject;
import objects.HealthBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.TreeMap;

public class Running extends AbstractScreen
{
    private final GameCommons gameCommons;
    private final Map<Long, String> messages = new TreeMap<>();
    private HealthBar healthbar = new HealthBar();
    private long frameCounter = 0;

    public Running(IScreenSwitcher switcher, KeyHandler keyHandler, GameCommons gameCommons)
    {
        super(switcher, keyHandler);
        this.gameCommons = gameCommons;
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {
        ++frameCounter;
        gameCommons.tileManager.drawing(graphics2D, gameCommons.player);

        for (BaseObject item: gameCommons.interactiveObjects)
        {
            if (item != null)
                item.drawing(graphics2D, Parameters.tileSize);
        }

        for (BaseMagician baseMagician : gameCommons.npc)
            baseMagician.drawing(graphics2D, Parameters.tileSize);

        for (Entity entity : gameCommons.animals)
            entity.drawing(graphics2D, Parameters.tileSize);

        gameCommons.player.drawing(graphics2D, Parameters.tileSize);
        healthbar.drawing(graphics2D, gameCommons.player.hitPoint);

        graphics2D.setFont(font.deriveFont(30F));
        graphics2D.setColor(new Color(230,200,170));
        drawMessages(graphics2D);
    }

    @Override
    public void update()
    {
        gameCommons.player.update(Sound.sound);

        for (BaseMagician baseMagician : gameCommons.npc)
            baseMagician.update(gameCommons.player);

        for (BaseObject item : gameCommons.interactiveObjects)
            item.update(gameCommons.player);

        for (Entity entity : gameCommons.animals)
            entity.update(gameCommons.player);

        super.update();
    }

    @Override
    public void activate()
    {
        keyHandler.addListener(KeyEvent.VK_E, () -> switcher.switchScreen(GameState.Inventory));
        keyHandler.addListener(KeyEvent.VK_SPACE, () -> switcher.switchScreen(GameState.Paused));
        keyHandler.addListener(KeyEvent.VK_ESCAPE, () -> switcher.switchScreen(GameState.Paused));

        Sound.sound.playBacking(Sounds.Theme);
    }

    @Override
    public void deactivate()
    {
        keyHandler.removeListeners();
        Sound.sound.stopBacking();
    }

    public void addMessage(String message)
    {
        // TODO: Move timeout here and adjust it according to message length
        messages.put(frameCounter, message);
    }

    private void drawMessages(Graphics2D graphics2D)
    {
        Point position = new Point(Parameters.tileSize / 2, Parameters.tileSize * 2);
        final int Timeout = 200;

        messages.forEach((key, value) -> {
            graphics2D.setColor(new Color(0, 0, 0, 70));
            graphics2D.drawString(value, position.x + 2, position.y + 2);
            graphics2D.setColor(Color.white);
            graphics2D.drawString(value, position.x, position.y);
            position.y += 50;
        });

        messages.entrySet().removeIf(entry -> frameCounter > entry.getKey() + Timeout);
    }
}
