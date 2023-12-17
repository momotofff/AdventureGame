package main;

import entity.Entity;
import entity.Magician;
import entity.Rabbit;
import objects.Boots;
import objects.Box;
import objects.Key;

import java.awt.*;
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
        setNpc(freePlaces);
        setAnimals();
    }

    private void setObjects()
    {
        gamePanel.items.add(new Key(new Point(19 * gamePanel.tileSize,14 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Key(new Point(39 * gamePanel.tileSize,16 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Key(new Point(15 * gamePanel.tileSize,13 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Key(new Point(29 * gamePanel.tileSize,20 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Box(new Point(15 * gamePanel.tileSize,25 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Box(new Point(50 * gamePanel.tileSize,32 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Boots(new Point(19 * gamePanel.tileSize,19 * gamePanel.tileSize), gamePanel));
    }

    private void setNpc(ArrayList<Point> freePlaces)
    {
        for (int i = 0; i < 20; ++i)
            gamePanel.NPC.add(addMagician(setNpcPosition(freePlaces)));

    }

    private void setAnimals()
    {
        gamePanel.animals.add(addRabbit(new Point(15, 16)));
        gamePanel.animals.add(addRabbit(new Point(15, 20)));
        gamePanel.animals.add(addRabbit(new Point(15, 23)));
    }

    private Entity addMagician(Point position)
    {
        return new Magician(gamePanel, new Point(position.x * gamePanel.tileSize, position.y * gamePanel.tileSize));
    }

    private Entity addRabbit(Point position)
    {
        return new Rabbit(gamePanel, new Point(position.x * gamePanel.tileSize, position.y * gamePanel.tileSize));
    }

    private Point setNpcPosition(ArrayList<Point> freePlaces)
    {
        return freePlaces.get((int) (Math.random() * freePlaces.size()));
    }
}
