package objects;

import main.GamePanel;
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
    public Point screenPosition;
    public Rectangle areaCollision;
    public Sounds soundEffect = Sounds.None;

    GamePanel gamePanel;

    public BaseObject(String tile, Sounds audio, Point worldPosition, GamePanel gamePanel)
    {
        this(tile, audio);
        this.worldPosition = worldPosition;
        this.gamePanel = gamePanel;
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

    public void update()
    {
        screenPosition = new Point(
                worldPosition.x - gamePanel.player.worldPosition.x + gamePanel.player.screenCoordinates.x,
                worldPosition.y - gamePanel.player.worldPosition.y + gamePanel.player.screenCoordinates.y
        );
    }

    public void drawing(Graphics2D graphics2D, GamePanel gamePanel)
    {
        graphics2D.drawImage(image, screenPosition.x, screenPosition.y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
