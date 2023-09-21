package objects;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject
{
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public Point position;

    public void drawing(Graphics2D graphics2D, GamePanel gamePanel)
    {

        Point screenPosition = new Point(position.x - gamePanel.player.worldPosition.x + gamePanel.player.screenCoordinates.x,
                position.y - gamePanel.player.worldPosition.y + gamePanel.player.screenCoordinates.y);

        graphics2D.drawImage(image, screenPosition.x, screenPosition.y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
