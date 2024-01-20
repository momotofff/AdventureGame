package main.screens;

import entity.Entity;
import entity.Magician;
import entity.Player;
import main.*;
import objects.BaseObject;
import tile.TileManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;

public class Running extends AbstractScreen
{
    private final KeyHandler keyHandler;
    private final GameCommons gameCommons;

    public Running(IScreenSwitcher switcher, KeyHandler keyHandler, GameCommons gameCommons)
    {
        super(switcher);
        this.keyHandler = keyHandler;
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
    }

    @Override
    public void activate()
    {
        keyHandler.addListener(KeyEvent.VK_E, this::onKeyE);
        keyHandler.addListener(KeyEvent.VK_SPACE, this::onKeySpace);
    }

    private void onKeyE()
    {
        switcher.switchScreen(GameState.Inventory);
    }

    private void onKeySpace()
    {
        switcher.switchScreen(GameState.Paused);
    }

    @Override
    public void deactivate()
    {
        keyHandler.removeListener(KeyEvent.VK_E);
        keyHandler.removeListener(KeyEvent.VK_SPACE);
    }
}
