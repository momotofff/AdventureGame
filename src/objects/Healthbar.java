package objects;

import main.Parameters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Healthbar
{
    public Point screenCoordinates = new Point(Parameters.tileSize / 2, Parameters.tileSize / 2);
    public BufferedImage fullImage;
    public BufferedImage halfImage;
    public BufferedImage blankImage;

    public Healthbar()
    {
        try
        {
            fullImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Healthbar/full.png")));
            halfImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Healthbar/half.png")));
            blankImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Healthbar/blank.png")));
        }
        catch (IOException e)
        {
            System.out.println("Failed to load resource: ");
            System.exit(1);
        }
    }

    public void drawing(Graphics2D graphics2D, int hitPoint)
    {
        Point drawCoordinates = new Point(screenCoordinates);
        int full = hitPoint / 2;
        int half = 0;
        if (hitPoint % 2 > 0)
            half = 1;

        int blank = 5 - full - half;

        for(int i = 0; i < full; ++i)
        {
            graphics2D.drawImage(fullImage,drawCoordinates.x, drawCoordinates.y, Parameters.tileSize, Parameters.tileSize,null);
            drawCoordinates.x += Parameters.tileSize;
        }
        for(int i = 0; i < half; ++i)
        {
            graphics2D.drawImage(halfImage,drawCoordinates.x, drawCoordinates.y, Parameters.tileSize, Parameters.tileSize,null);
            drawCoordinates.x += Parameters.tileSize;
        }
        for(int i = 0; i < blank; ++i)
        {
            graphics2D.drawImage(blankImage,drawCoordinates.x, drawCoordinates.y, Parameters.tileSize, Parameters.tileSize,null);
            drawCoordinates.x += Parameters.tileSize;
        }

    }
}
