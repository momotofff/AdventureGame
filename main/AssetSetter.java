package main;

import entity.Entity;
import entity.Magician;
import entity.Rabbit;
import objects.Boots;
import objects.Box;
import objects.Key;

import java.awt.*;

public class AssetSetter
{
    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void setObject()
    {
        gamePanel.items.add(new Key(new Point(19 * gamePanel.tileSize,14 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Key(new Point(39 * gamePanel.tileSize,16 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Key(new Point(15 * gamePanel.tileSize,13 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Key(new Point(29 * gamePanel.tileSize,20 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Box(new Point(15 * gamePanel.tileSize,25 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Box(new Point(50 * gamePanel.tileSize,32 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Boots(new Point(19 * gamePanel.tileSize,19 * gamePanel.tileSize), gamePanel));
    }

    public void set_NPC()
    {
        for (int i = 0; i < 40; ++i)
            gamePanel.NPC.add(add_Rabbit(setNpcPosition()));
        for (int i = 0; i < 10; ++i)
            gamePanel.NPC.add(add_Magician(setNpcPosition()));;
    }


    public Entity add_Magician(Point position)
    {
        return new Magician(gamePanel, new Point(position.x * gamePanel.tileSize, position.y * gamePanel.tileSize));
    }

    public Entity add_Rabbit(Point position)
    {
        return new Rabbit(gamePanel, new Point(position.x * gamePanel.tileSize, position.y * gamePanel.tileSize));
    }

    public Point setNpcPosition()
    {
        return gamePanel.trueNpcPositions.get((int) (Math.random() * gamePanel.trueNpcPositions.size()));
    }
}
