package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TileManager
{
    GamePanel gamePanel;

    // TODO: move world, its loading and player pos to separate class
    Tile[][] world;
    final public Point initialPlayerPosition;

    private final Map<Color, Tile> tiles;

    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        tiles = new HashMap<>();
        tiles.put(Color.WHITE, new Tile("/assets/world/grass.png", false));
        tiles.put(Color.BLACK, new Tile("/assets/world/wall.png", true));
        tiles.put(Color.GRAY, new Tile("/assets/world/water.png", true));
        tiles.put(Color.YELLOW, new Tile("/assets/world/sand.png", true));
        tiles.put(Color.ORANGE, new Tile("/assets/world/earth.png", true));
        tiles.put(Color.RED, new Tile("/assets/world/tree.png", true));

        world = new Tile[gamePanel.maxWorldCountTile.x][gamePanel.maxWorldCountTile.y];
        getWorld("/assets/world/worldMap.png");

        initialPlayerPosition = new Point(23 * gamePanel.tileSize, 21 * gamePanel.tileSize);
    }

    // TODO: Move to World class too, maybe as constructor?
    private void getWorld(String map)
    {
        BufferedImage image = getTileImage(map);

        for (int x = 0; x < image.getWidth(); ++x)
        {
            for (int y = 0; y < image.getHeight(); ++y)
            {
                Color color = new Color(image.getRGB(x, y));
                world[x][y] = tiles.get(color);
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
            for (int y = 0; y < world[x].length; ++y)
            {
                BufferedImage image = world[x][y].image;

                Point worldPosition = new Point(x * gamePanel.tileSize, y * gamePanel.tileSize);
                Point screenPosition = new Point(worldPosition.x - gamePanel.player.worldPosition.x + gamePanel.player.screenCoordinates.x,
                                                 worldPosition.y - gamePanel.player.worldPosition.y + gamePanel.player.screenCoordinates.y);

                g2.drawImage(image, screenPosition.x, screenPosition.y, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }
    }
}
