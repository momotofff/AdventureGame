package tile;

import entity.Player;
import main.Parameters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TileManager
{
    // TODO: move world, its loading and player pos to separate class
    public Tile[][] world;
    final public Point defaultWorldPosition;
    final public Point maxWorldCountTile = new Point(100, 100);

    private final TileStorage tiles = new TileStorage();
    private final ArrayList<Point> freePlaces = new ArrayList<>();

    public TileManager()
    {
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass1.png", false));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass2.png", false));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass3.png", false));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass4.png", false));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass5.png", false));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass6.png", false));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass7.png", false));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass8.png", false));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass9.png", false));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass10.png", false));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Tree1.png", true));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Tree2.png", true));

        tiles.add(Tiles.Water, new Tile("/assets/world/Water1.png", true));
        tiles.add(Tiles.Water, new Tile("/assets/world/Water2.png", true));
        tiles.add(Tiles.Water, new Tile("/assets/world/Water3.png", true));
        tiles.add(Tiles.Water, new Tile("/assets/world/Water4.png", true));

        tiles.add(Tiles.BorderBottom, new Tile("/assets/world/BorderBottom.png", true));
        tiles.add(Tiles.BorderLeft, new Tile("/assets/world/BorderLeft.png", true));
        tiles.add(Tiles.BorderRight, new Tile("/assets/world/BorderRight.png", true));
        tiles.add(Tiles.BorderTop, new Tile("/assets/world/BorderTop.png", true));

        tiles.add(Tiles.BorderBottomRight, new Tile("/assets/world/BorderBottomRight.png", true));
        tiles.add(Tiles.BorderBottomLeft, new Tile("/assets/world/BorderBottomLeft.png", true));
        tiles.add(Tiles.BorderTopRight, new Tile("/assets/world/BorderTopRight.png", true));
        tiles.add(Tiles.BorderTopLeft, new Tile("/assets/world/BorderTopLeft.png", true));

        tiles.add(Tiles.CornerBottomRight, new Tile("/assets/world/CornerBottomRight.png", true));
        tiles.add(Tiles.CornerBottomLeft, new Tile("/assets/world/CornerBottomLeft.png", true));
        tiles.add(Tiles.CornerTopRight, new Tile("/assets/world/CornerTopRight.png", true));
        tiles.add(Tiles.CornerTopLeft, new Tile("/assets/world/CornerTopLeft.png", true));

        tiles.add(Tiles.RoadHorizontal, new Tile("/assets/world/RoadHorizontal.png", false));
        tiles.add(Tiles.RoadVertical, new Tile("/assets/world/RoadVertical.png", false));
        tiles.add(Tiles.RoadTopLeft, new Tile("/assets/world/RoadTopLeft.png", false));
        tiles.add(Tiles.RoadTopRight, new Tile("/assets/world/RoadTopRight.png", false));
        tiles.add(Tiles.RoadBottomLeft, new Tile("/assets/world/RoadBottomLeft.png", false));
        tiles.add(Tiles.RoadBottomRight, new Tile("/assets/world/RoadBottomRight.png", false));

        world = new Tile[maxWorldCountTile.x][maxWorldCountTile.y];
        getWorld("/assets/world/WorldMap.txt");

        defaultWorldPosition = new Point(20 * Parameters.tileSize, 20 * Parameters.tileSize);
    }

    // TODO: Move to World class too, maybe as constructor?
    private void getWorld(String map)
    {
        try
        {
            InputStream inputStream = getClass().getResourceAsStream(map);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            for (int y = 0; y < maxWorldCountTile.y; ++y)
            {
                String line = bufferedReader.readLine();
                String[] numbers = line.split("\\s+");

                for (int x = 0; x < maxWorldCountTile.x; ++x)
                {
                    try
                    {
                        Tile tile = tiles.get(Integer.parseInt(numbers[x]));
                        world[x][y] = tile;

                        if (!tile.collision)
                            freePlaces.add(new Point(x, y));
                    }
                    catch (NoSuchElementException e)
                    {
                        System.out.println(e.getMessage());
                        System.exit(1);
                    }
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void drawing(Graphics2D graphics2D, Player player)
    {
        for (int y = 0; y < world.length; ++y)
        {
            for (int x = 0; x < world[y].length; ++x)
            {
                BufferedImage image = world[x][y].image;

                Point screenPosition = new Point(
                    x * Parameters.tileSize - player.worldPosition.x + player.screenCoordinates.x,
                    y * Parameters.tileSize - player.worldPosition.y + player.screenCoordinates.y
                );

                graphics2D.drawImage(image, screenPosition.x, screenPosition.y,null);
            }
        }
    }

    public ArrayList<Point> getFreePlaces()
    {
        return freePlaces;
    }
}
