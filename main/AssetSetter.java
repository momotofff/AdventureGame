package main;

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
        gamePanel.items.add(new Key(new Point(20 * gamePanel.tileSize,12 * gamePanel.tileSize)));
        gamePanel.items.add(new Key(new Point(40 * gamePanel.tileSize,40 * gamePanel.tileSize)));
        gamePanel.items.add(new Box(new Point(22 * gamePanel.tileSize,34 * gamePanel.tileSize)));
        gamePanel.items.add(new Box(new Point(43 * gamePanel.tileSize,37 * gamePanel.tileSize)));
    }
}
