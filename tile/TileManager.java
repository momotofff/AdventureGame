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
        world = new Tile[16][12];
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

                if (color.equals(Color.white))
                {
                    world[x][y] = new Tile("/assets/world/grass.png", false);
                }
                else if (color.equals(Color.black))
                {
                    world[x][y] = new Tile("/assets/world/wall.png", true);
                }
                else if (color.equals(Color.GRAY))
                {
                    world[x][y] = new Tile("/assets/world/water.png", true);
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
                g2.drawImage(image, image.getHeight() * x * gamePanel.scale, image.getWidth() * y * gamePanel.scale, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }
    }
}
