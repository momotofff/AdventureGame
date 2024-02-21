package main.screens;

import entity.Entity;
import main.*;
import main.screens.interfaces.IScreenSwitcher;
import main.utils.AbstractMessage;
import objects.BaseObject;
import objects.Key;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

public class Running extends AbstractScreen
{
    private final GameCommons gameCommons;
    private final BufferedImage keyImage = new Key().image;
    private ArrayList<AbstractMessage> allMessages = new ArrayList<>();
    private int timeOut = 200;

    public Running(IScreenSwitcher switcher, KeyHandler keyHandler, GameCommons gameCommons)
    {
        super(switcher, keyHandler, 2);
        this.gameCommons = gameCommons;
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {
        gameCommons.tileManager.drawing(graphics2D, gameCommons.player);

        for (BaseObject item: gameCommons.interactiveObjects)
        {
            if (item != null)
                item.drawing(graphics2D, Parameters.tileSize);
        }

        for (Entity entity : gameCommons.npc)
            entity.drawing(graphics2D, Parameters.tileSize);

        for (Entity entity : gameCommons.animals)
            entity.drawing(graphics2D, Parameters.tileSize);

        gameCommons.player.drawing(graphics2D, Parameters.tileSize);

        graphics2D.setFont(font.deriveFont(30F));
        graphics2D.setColor(new Color(230,200,170));
        graphics2D.drawImage(keyImage, Parameters.tileSize / 2, Parameters.tileSize / 2, Parameters.tileSize / 2, Parameters.tileSize / 2, null);
        graphics2D.drawString(" x " + gameCommons.player.keysCount, 60, 60);

        if(allMessages.size() != 0)
            drawMessage(graphics2D, allMessages);
    }

    @Override
    public void update()
    {
        gameCommons.player.update(Sound.sound);

        for (Entity entity : gameCommons.npc)
            entity.update(gameCommons.player);

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

    public void setMessage(String message)
    {
        this.allMessages.add(new AbstractMessage(message));
    }

    private void drawMessage(Graphics2D graphics2D, ArrayList<AbstractMessage> allMessages)
    {
        AbstractMessage mes0 = allMessages.get(0);

        if(allMessages.size() < 2)
        {
            if (++mes0.startTime < mes0.timeOut)
            {
                graphics2D.drawString(mes0.message, mes0.startPosition.x, mes0.startPosition.y);
                return;
            }
            else
                this.allMessages = new ArrayList<>();
        }

        if (allMessages.size() == 2)
        {
            AbstractMessage mes1 = allMessages.get(1);

            if (mes1.startTime < mes1.timeOut)
            {
                if (mes0.startStep < mes0.maxStep)
                {
                    ++mes0.startStep;
                    ++mes0.startPosition.y;
                    graphics2D.drawString(mes0.message, mes0.startPosition.x, mes0.startPosition.y);
                }

                else
                {
                    ++mes1.startTime;

                    if (++mes0.startTime < mes0.timeOut)
                        graphics2D.drawString(mes0.message, mes0.startPosition.x, mes0.startPosition.y);

                    else
                    {
                        this.allMessages = new ArrayList<>();
                        this.allMessages.add(mes1);
                        return;
                    }

                    graphics2D.drawString(mes1.message, mes1.startPosition.x, mes1.startPosition.y);
                }
            }
        }
    }
}
