package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TileManager
{
    GamePanel gamePanel;
    Tile[][] world;

    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        world = new Tile[gamePanel.maxWorldCountTile.x][gamePanel.maxWorldCountTile.y];
        getWorld("/assets/world/worldMap.png");
    }

    private void getWorld(String map)
    {
        BufferedImage image = getTileImage(map);

        for (int x = 0; x < image.getWidth(); ++x)
        {
            for (int y = 0; y < image.getHeight(); ++y)
            {
                int t = image.getHeight();
                int v = image.getWidth();

                Color color = new Color(image.getRGB(x, y));

                if (color.equals(Color.WHITE))
                {
                    world[x][y] = new Tile("/assets/world/grass.png", false);
                }
                else if (color.equals(Color.BLACK))
                {
                    world[x][y] = new Tile("/assets/world/wall.png", true);
                }
                else if (color.equals(Color.GRAY))
                {
                    world[x][y] = new Tile("/assets/world/water.png", true);
                }
                else if (color.equals(Color.YELLOW))
                {
                    world[x][y] = new Tile("/assets/world/sand.png", true);
                }
                else if (color.equals(Color.ORANGE))
                {
                    world[x][y] = new Tile("/assets/world/earth.png", true);
                }
                else if (color.equals(Color.RED))
                {
                    world[x][y] = new Tile("/assets/world/tree.png", true);
                }
            }
        }
    }

    public BufferedImage getTileImage(String path)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return image;
    }

    public void drawing(Graphics2D g2)
    {
        for (int x = 0; x < world.length; ++x)
        {
            for (int y = 0; y < world[0].length; ++y)
            {
                BufferedImage image = world[x][y].image;

                Point worldPosition = new Point(x * gamePanel.tileSize, y * gamePanel.tileSize);
                Point screenPosition = new Point(worldPosition.x - gamePanel.player.world_X_Y.x + gamePanel.player.screenCoordinates.x,
                                                 worldPosition.y - gamePanel.player.world_X_Y.y + gamePanel.player.screenCoordinates.y);

                g2.drawImage(image, screenPosition.x, screenPosition.y, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }
    }
}
