package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Tile
{
    public BufferedImage image;
    public boolean collision = false;

    public Tile(String path, boolean bool)
    {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collision = bool;
    }
}
