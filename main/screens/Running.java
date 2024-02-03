package main.screens;

import entity.Entity;
import main.*;
import main.screens.interfaces.IScreenSwitcher;
import objects.BaseObject;
import objects.Key;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Running extends AbstractScreen
{
    private final GameCommons gameCommons;
    private final BufferedImage keyImage = new Key().image;
    private String message = null;
    private int messageCounter = 0;

    public Running(IScreenSwitcher switcher, KeyHandler keyHandler, GameCommons gameCommons)
    {
        super(switcher, keyHandler);
        this.gameCommons = gameCommons;
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {
        gameCommons.tileManager.drawing(graphics2D, gameCommons.player);

        gameCommons.player.update(gameCommons.sound);

        for (Entity entity : gameCommons.NPC)
            entity.update(gameCommons.player, gameCommons.collisionChecker);

        for (BaseObject item : gameCommons.items)
            item.update(gameCommons.player);

        for (Entity entity : gameCommons.animals)
            entity.update(gameCommons.player, gameCommons.collisionChecker);

        for (BaseObject item: gameCommons.items)
        {
            if (item != null)
                item.drawing(graphics2D, Parameters.tileSize);
        }

        for (Entity entity : gameCommons.NPC)
            entity.drawing(graphics2D, Parameters.tileSize);

        for (Entity entity : gameCommons.animals)
            entity.drawing(graphics2D, Parameters.tileSize);

        gameCommons.player.drawing(graphics2D, Parameters.tileSize);

        graphics2D.setFont(font.deriveFont(30F));
        graphics2D.setColor(new Color(230,200,170));
        graphics2D.drawImage(keyImage, Parameters.tileSize / 2, Parameters.tileSize / 2, Parameters.tileSize / 2, Parameters.tileSize / 2, null);
        graphics2D.drawString(" x " + gameCommons.player.keysCount, 60, 60);

        if (message == null)
            return;

        if (--messageCounter > 0)
            graphics2D.drawString(message, Parameters.tileSize / 2, Parameters.tileSize * 2);
        else
            message = null;
    }

    @Override
    public void activate()
    {
        keyHandler.addListener(KeyEvent.VK_E, () -> switcher.switchScreen(GameState.Inventory));
        keyHandler.addListener(KeyEvent.VK_SPACE, () -> switcher.switchScreen(GameState.Paused));

        gameCommons.sound.playBacking(Sounds.Theme);
    }

    @Override
    public void deactivate()
    {
        keyHandler.removeListeners();
        gameCommons.sound.stopBacking();
    }

    public void setMessage(String message)
    {
        messageCounter = 180;
        this.message = message;
    }
}
