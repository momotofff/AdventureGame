package tile;

import main.GamePanel;

import java.awt.*;

public class TileManager
{
    GamePanel gamePanel;
    Tile[] tile;

    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        getTileImage();
    }

    public void getTileImage()
    {

    }

    public void drawing(Graphics2D g2)
    {
        g2.drawImage(tile[0].image, 0, 0, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
