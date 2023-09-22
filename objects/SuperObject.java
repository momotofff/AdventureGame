package objects;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject
{
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public Point worldPosition;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Point solidAreaDefault = new Point(0, 0);

    public void drawing(Graphics2D graphics2D, GamePanel gamePanel)
    {

        Point screenPosition = new Point(worldPosition.x - gamePanel.player.worldPosition.x + gamePanel.player.screenCoordinates.x,
                worldPosition.y - gamePanel.player.worldPosition.y + gamePanel.player.screenCoordinates.y);

        graphics2D.drawImage(image, screenPosition.x, screenPosition.y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
