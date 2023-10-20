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
    public Point screenPosition;
    public Rectangle areaCollision;
    GamePanel gamePanel;

    public BaseObject(String tile, Point worldPosition, GamePanel gamePanel)
    {
        this.worldPosition = worldPosition;
        this.gamePanel = gamePanel;
        areaCollision = new Rectangle(worldPosition.x + 12, worldPosition.y + 12, 24, 24);
        helper(tile);
    }
    public BaseObject(String tile)
    {
        helper(tile);
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

    private void helper(String tile)
    {
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
}
