package main;

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
        gamePanel.items.add(new Key(new Point(19 * gamePanel.tileSize,16 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Box(new Point(19 * gamePanel.tileSize,25 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Box(new Point(19 * gamePanel.tileSize,22 * gamePanel.tileSize), gamePanel));
        gamePanel.items.add(new Boots(new Point(19 * gamePanel.tileSize,19 * gamePanel.tileSize), gamePanel));
    }
}
