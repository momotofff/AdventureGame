package objects;

import main.Parameters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HealthBar
{
    private BufferedImage fullImage;
    private BufferedImage halfImage;
    private BufferedImage blankImage;

    public HealthBar()
    {
        try
        {
            fullImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Healthbar/full.png")));
            halfImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Healthbar/half.png")));
            blankImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Healthbar/blank.png")));
        }
        catch (IOException e)
        {
            System.out.println("Failed to load resource of HealthBar!");
            System.exit(1);
        }
    }

    public void drawing(Graphics2D graphics2D, int hitPoint)
    {
        Point drawCoordinates = new Point(Parameters.tileSize / 2, Parameters.tileSize / 2);
        BufferedImage image;

        final int DefaultHitsCount = 10;
        final int HitsInHeart = 2;

        for (int i = Math.max(DefaultHitsCount, hitPoint); i > 0; i -= HitsInHeart)
        {
            if (hitPoint >= HitsInHeart)
                image = fullImage;
            else if (hitPoint > 0)
                image = halfImage;
            else
                image = blankImage;

            graphics2D.drawImage(image, drawCoordinates.x, drawCoordinates.y, Parameters.tileSize, Parameters.tileSize,null);
            drawCoordinates.x += Parameters.tileSize;
            hitPoint -= HitsInHeart;
        }
    }
}
