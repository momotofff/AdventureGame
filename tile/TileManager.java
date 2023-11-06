package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TileManager
{
    GamePanel gamePanel;

    // TODO: move world, its loading and player pos to separate class
    public Tile[][] world;
    final public Point defaultWorldPosition;

    public final Map<Integer, Tile> tiles;
    public final Map<Integer, Tile> randomGrassTile;
    public final Map<Integer, Tile> randomWater;

    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        randomWater = new HashMap<>();
        randomWater.put(0, new Tile("/assets/world/Water1.png", true));
        randomWater.put(1, new Tile("/assets/world/Water2.png", true));
        randomWater.put(2, new Tile("/assets/world/Water3.png", true));
        randomWater.put(3, new Tile("/assets/world/Water4.png", true));

        randomGrassTile = new HashMap<>();
        randomGrassTile.put(0, new Tile("/assets/world/Grass1.png", false));
        randomGrassTile.put(1, new Tile("/assets/world/Grass2.png", false));
        randomGrassTile.put(2, new Tile("/assets/world/Grass3.png", false));
        randomGrassTile.put(3, new Tile("/assets/world/Grass4.png", false));
        randomGrassTile.put(4, new Tile("/assets/world/Grass5.png", false));
        randomGrassTile.put(5, new Tile("/assets/world/Grass6.png", false));
        randomGrassTile.put(6, new Tile("/assets/world/Grass7.png", false));
        randomGrassTile.put(7, new Tile("/assets/world/Grass8.png", false));
        randomGrassTile.put(8, new Tile("/assets/world/Grass9.png", false));
        randomGrassTile.put(9, new Tile("/assets/world/Grass10.png", false));
        randomGrassTile.put(10, new Tile("/assets/world/Tree1.png", true));
        randomGrassTile.put(11, new Tile("/assets/world/Tree2.png", true));

        tiles = new HashMap<>();
        tiles.put(0, new Tile());
        tiles.put(1, new Tile());
        tiles.put(2, new Tile("/assets/world/BorderBottom.png", true));
        tiles.put(3, new Tile("/assets/world/BorderLeft.png", true));
        tiles.put(4, new Tile("/assets/world/BorderRight.png", true));
        tiles.put(5, new Tile("/assets/world/BorderTop.png", true));
        tiles.put(6, new Tile("/assets/world/BorderBottom.png", true));
        tiles.put(7, new Tile("/assets/world/BorderBottomRight.png", true));
        tiles.put(8, new Tile("/assets/world/BorderBottomLeft.png", true));
        tiles.put(9, new Tile("/assets/world/BorderTopRight.png", true));
        tiles.put(10, new Tile("/assets/world/BorderTopLeft.png", true));


        world = new Tile[gamePanel.maxWorldCountTile.x][gamePanel.maxWorldCountTile.y];
        getWorld("/assets/world/WorldMap.txt");

        defaultWorldPosition = new Point(20 * gamePanel.tileSize, 20 * gamePanel.tileSize);
    }

    // TODO: Move to World class too, maybe as constructor?
    private void getWorld(String map)
    {
        try
        {
            InputStream inputStream = getClass().getResourceAsStream(map);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            for (int y = 0; y < gamePanel.maxWorldCountTile.y; ++y)
            {
                String line = bufferedReader.readLine();
                String[] numbers = line.split(" ");

                for (int x = 0; x < gamePanel.maxWorldCountTile.x; ++x)
                {
                    if (Integer.parseInt(numbers[x]) == 1)
                        world[x][y] = randomGrassTile.get((int)(Math.random() * randomGrassTile.size()));

                    else if (Integer.parseInt(numbers[x]) == 0)
                        world[x][y] = randomWater.get((int)(Math.random() * randomWater.size()));

                    else
                        world[x][y] = tiles.get(Integer.parseInt(numbers[x]));

                    //world[x][y].worldPosition = new Point(y * gamePanel.tileSize, x * gamePanel.tileSize);
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void drawing(Graphics2D graphics2D)
    {
        for (int y = 0; y < world.length; ++y)
        {
            for (int x = 0; x < world[y].length; ++x)
            {
                BufferedImage image = world[y][x].image;

                //Point worldPositionTile = world[x][y].worldPosition;

                Point worldPosition = new Point(y * gamePanel.tileSize, x * gamePanel.tileSize);
                Point screenPosition = new Point(worldPosition.x - gamePanel.player.worldPosition.x + gamePanel.player.screenCoordinates.x,
                                                 worldPosition.y - gamePanel.player.worldPosition.y + gamePanel.player.screenCoordinates.y);

                graphics2D.drawImage(image, screenPosition.x, screenPosition.y, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }
    }
}
