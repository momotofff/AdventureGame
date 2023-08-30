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

        world = new Tile[gamePanel.worldSize.x][gamePanel.worldSize.y];
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
        Point camera = new Point(1, 1);
        for (int x = camera.x; x < world.length - x; ++x)
        {
            for (int y = camera.y; y < world[0].length - y; ++y)
            {
                BufferedImage image = world[x][y].image;
                g2.drawImage(image, image.getHeight() * x * gamePanel.scale, image.getWidth() * y * gamePanel.scale, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }
    }
}
