package tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Tile
{
    public BufferedImage image;
    public boolean collision;

    public Tile(String path, boolean collision)
    {
        try
        {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        }
        catch (IOException e)
        {
            System.out.println("Failed to load tile: " + path);
            throw new RuntimeException(e);
        }

        this.collision = collision;
    }
}
