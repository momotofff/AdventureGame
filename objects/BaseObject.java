package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class BaseObject
{
    public BufferedImage image;
    public boolean collision = true;
    public Point worldPosition;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Point solidAreaDefaultPosition = new Point(0,0);

    public BaseObject(String tile, Point worldPosition)
    {
        this.worldPosition = worldPosition;

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

    public void drawing(Graphics2D graphics2D, GamePanel gamePanel)
    {
        Point screenPosition = new Point(worldPosition.x - gamePanel.player.worldPosition.x + gamePanel.player.screenCoordinates.x,
                worldPosition.y - gamePanel.player.worldPosition.y + gamePanel.player.screenCoordinates.y);

        graphics2D.drawImage(image, screenPosition.x, screenPosition.y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
