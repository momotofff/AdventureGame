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
        gamePanel.obj[0] = new Key();
        gamePanel.obj[0].position = new Point(20 * gamePanel.tileSize,12 * gamePanel.tileSize);

        gamePanel.obj[1] = new Key();
        gamePanel.obj[1].position = new Point(40 * gamePanel.tileSize,40 * gamePanel.tileSize);

        gamePanel.obj[2] = new Box();
        gamePanel.obj[2].position = new Point(22 * gamePanel.tileSize,34 * gamePanel.tileSize);

        gamePanel.obj[3] = new Box();
        gamePanel.obj[3].position = new Point(43 * gamePanel.tileSize,37 * gamePanel.tileSize);
    }

}
