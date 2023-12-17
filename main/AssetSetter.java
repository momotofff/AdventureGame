package main;

import entity.Entity;
import entity.Magician;
import entity.Rabbit;
import objects.Boots;
import objects.Box;
import objects.Key;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class AssetSetter
{
    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void initObjects(ArrayList<Point> freePlaces)
    {
        setObjects();

        for (int i = 0; i < 20; ++i)
            gamePanel.NPC.add(createEntity(Magician.class, freePlaces));

        for (int i = 0; i < 20; ++i)
            gamePanel.animals.add(createEntity(Rabbit.class, freePlaces));
    }

    private void setObjects()
    {
        gamePanel.items.add(new Key(new Point(19 * gamePanel.tileSize, 14 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Key(new Point(39 * gamePanel.tileSize, 16 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Key(new Point(15 * gamePanel.tileSize, 13 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Key(new Point(29 * gamePanel.tileSize, 20 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Box(new Point(15 * gamePanel.tileSize, 25 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Box(new Point(50 * gamePanel.tileSize, 32 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Boots(new Point(19 * gamePanel.tileSize, 19 * gamePanel.tileSize), gamePanel));
    }

    private Entity createEntity(Class<? extends Entity> cls, ArrayList<Point> freePlaces)
    {
        Point position = freePlaces.get((int) (Math.random() * freePlaces.size()));
        position.x *= gamePanel.tileSize;
        position.y *= gamePanel.tileSize;

        try
        {
            Constructor<? extends Entity> constructor = cls.getConstructor(GamePanel.class, Point.class);
            return constructor.newInstance(gamePanel, position);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
