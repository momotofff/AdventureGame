package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

public class TileManager
{
    GamePanel gamePanel;

    // TODO: move world, its loading and player pos to separate class
    public Tile[][] world;
    final public Point defaultWorldPosition;

    private final TileStorage tiles = new TileStorage();

    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass1.png", false, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass2.png", false, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass3.png", false, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass4.png", false, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass5.png", false, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass6.png", false, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass7.png", false, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass8.png", false, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass9.png", false, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Grass10.png", false, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Tree1.png", true, gamePanel));
        tiles.add(Tiles.Grass, new Tile("/assets/world/Tree2.png", true, gamePanel));

        tiles.add(Tiles.Water, new Tile("/assets/world/Water1.png", true, gamePanel));
        tiles.add(Tiles.Water, new Tile("/assets/world/Water2.png", true, gamePanel));
        tiles.add(Tiles.Water, new Tile("/assets/world/Water3.png", true, gamePanel));
        tiles.add(Tiles.Water, new Tile("/assets/world/Water4.png", true, gamePanel));

        tiles.add(Tiles.BorderBottom, new Tile("/assets/world/BorderBottom.png", true, gamePanel));
        tiles.add(Tiles.BorderLeft, new Tile("/assets/world/BorderLeft.png", true, gamePanel));
        tiles.add(Tiles.BorderRight, new Tile("/assets/world/BorderRight.png", true, gamePanel));
        tiles.add(Tiles.BorderTop, new Tile("/assets/world/BorderTop.png", true, gamePanel));

        tiles.add(Tiles.BorderBottomRight, new Tile("/assets/world/BorderBottomRight.png", true, gamePanel));
        tiles.add(Tiles.BorderBottomLeft, new Tile("/assets/world/BorderBottomLeft.png", true, gamePanel));
        tiles.add(Tiles.BorderTopRight, new Tile("/assets/world/BorderTopRight.png", true, gamePanel));
        tiles.add(Tiles.BorderTopLeft, new Tile("/assets/world/BorderTopLeft.png", true, gamePanel));

        tiles.add(Tiles.CornerBottomRight, new Tile("/assets/world/CornerBottomRight.png", true, gamePanel));
        tiles.add(Tiles.CornerBottomLeft, new Tile("/assets/world/CornerBottomLeft.png", true, gamePanel));
        tiles.add(Tiles.CornerTopRight, new Tile("/assets/world/CornerTopRight.png", true, gamePanel));
        tiles.add(Tiles.CornerTopLeft, new Tile("/assets/world/CornerTopLeft.png", true, gamePanel));

        tiles.add(Tiles.RoadHorizontal, new Tile("/assets/world/RoadHorizontal.png", false, gamePanel));
        tiles.add(Tiles.RoadVertical, new Tile("/assets/world/RoadVertical.png", false, gamePanel));
        tiles.add(Tiles.RoadTopLeft, new Tile("/assets/world/RoadTopLeft.png", false, gamePanel));
        tiles.add(Tiles.RoadTopRight, new Tile("/assets/world/RoadTopRight.png", false, gamePanel));
        tiles.add(Tiles.RoadBottomLeft, new Tile("/assets/world/RoadBottomLeft.png", false, gamePanel));
        tiles.add(Tiles.RoadBottomRight, new Tile("/assets/world/RoadBottomRight.png", false, gamePanel));

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
                String[] numbers = line.split("\\s+");

                for (int x = 0; x < gamePanel.maxWorldCountTile.x; ++x)
                {
                    try
                    {
                        world[x][y] = tiles.get(Integer.parseInt(numbers[x]));
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

                graphics2D.drawImage(image, screenPosition.x, screenPosition.y,null);
            }
        }
    }
}
