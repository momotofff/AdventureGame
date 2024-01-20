package objects;

import entity.Player;
import main.Parameters;
import main.Sounds;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class BaseObject
{
    public BufferedImage image;
    public Point worldPosition;
    public Point screenCoordinates;
    public Rectangle areaCollision;
    public Sounds soundEffect = Sounds.None;

    public BaseObject(String tile, Sounds audio, Point mapPosition)
    {
        this(tile, audio);
        this.worldPosition = new Point(mapPosition.x  * Parameters.tileSize, mapPosition.y * Parameters.tileSize);
        this.screenCoordinates = worldPosition;

        areaCollision = new Rectangle(worldPosition.x + 8, worldPosition.y + 8, 48, 48);
    }

    public BaseObject(String tile, Sounds sound)
    {
        this.soundEffect = sound;

        try
        {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(tile)));
        }
        catch (IOException e)
        {
            System.out.println("Failed to load resource: " + tile);
            System.exit(1);
        }
    }

    public void update(Player player)
    {
        screenCoordinates = new Point(
                worldPosition.x - player.worldPosition.x + player.screenCoordinates.x,
                worldPosition.y - player.worldPosition.y + player.screenCoordinates.y
        );
    }

    public void drawing(Graphics2D graphics2D, int tileSize)
    {
        graphics2D.drawImage(image, screenCoordinates.x, screenCoordinates.y, Parameters.tileSize, tileSize, null);
    }
}
