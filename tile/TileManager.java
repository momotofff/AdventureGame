package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class TileManager
{
    GamePanel gamePanel;

    // TODO: move world, its loading and player pos to separate class
    public Tile[][] world;
    final public Point defaultWorldPosition;

    public final Map<Integer, ArrayList<Tile>> tiles = new HashMap<>();

    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        tiles.put(Tiles.Grass, new ArrayList<>());
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Grass1.png", false));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Grass2.png", false));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Grass3.png", false));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Grass4.png", false));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Grass5.png", false));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Grass6.png", false));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Grass7.png", false));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Grass8.png", false));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Grass9.png", false));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Grass10.png", false));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Tree1.png", true));
        tiles.get(Tiles.Grass).add(new Tile("/assets/world/Tree2.png", true));

        tiles.put(Tiles.Water, new ArrayList<>());
        tiles.get(Tiles.Water).add(new Tile("/assets/world/Water1.png", true));
        tiles.get(Tiles.Water).add(new Tile("/assets/world/Water2.png", true));
        tiles.get(Tiles.Water).add(new Tile("/assets/world/Water3.png", true));
        tiles.get(Tiles.Water).add(new Tile("/assets/world/Water4.png", true));

        tiles.put(Tiles.BorderBottom, new ArrayList<>(List.of(new Tile("/assets/world/BorderBottom.png", true))));
        tiles.put(Tiles.BorderLeft, new ArrayList<>(List.of(new Tile("/assets/world/BorderLeft.png", true))));
        tiles.put(Tiles.BorderRight, new ArrayList<>(List.of(new Tile("/assets/world/BorderRight.png", true))));
        tiles.put(Tiles.BorderTop, new ArrayList<>(List.of(new Tile("/assets/world/BorderTop.png", true))));

        tiles.put(Tiles.BorderBottomRight, new ArrayList<>(List.of(new Tile("/assets/world/BorderBottomRight.png", true))));
        tiles.put(Tiles.BorderBottomLeft, new ArrayList<>(List.of(new Tile("/assets/world/BorderBottomLeft.png", true))));
        tiles.put(Tiles.BorderTopRight, new ArrayList<>(List.of(new Tile("/assets/world/BorderTopRight.png", true))));
        tiles.put(Tiles.BorderTopLeft, new ArrayList<>(List.of(new Tile("/assets/world/BorderTopLeft.png", true))));

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
                    Integer tileId = Integer.parseInt(numbers[x]);
                    ArrayList<Tile> tile = tiles.get(tileId);

                    if (tile == null || tile.isEmpty())
                    {
                        System.out.println("Failed to load tiles for ID: " + tileId);
                        System.exit(1);
                    }

                    world[x][y] = tile.get((int) (Math.random() * tile.size()));
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
                BufferedImage image = world[x][y].image;

                Point screenPosition = new Point(
                    x * gamePanel.tileSize - gamePanel.player.worldPosition.x + gamePanel.player.screenCoordinates.x,
                    y * gamePanel.tileSize - gamePanel.player.worldPosition.y + gamePanel.player.screenCoordinates.y
                );

                graphics2D.drawImage(image, screenPosition.x, screenPosition.y, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }
    }
}
