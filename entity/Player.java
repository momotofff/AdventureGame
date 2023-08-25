package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity
{
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler)
    {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        speed = 4;
        position = new Point(100, 100);
        getImage();
        direction = "down";
    }

    public void update()
    {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed)
        {
            if (keyHandler.upPressed)
            {
                direction = "up";
                position.y -= speed;
            }
            if (keyHandler.leftPressed)
            {
                direction = "left";
                position.x -= speed;
            }
            if (keyHandler.rightPressed)
            {
                direction = "right";
                position.x += speed;
            }
            if (keyHandler.downPressed)
            {
                direction = "down";
                position.y += speed;
            }

            ++spriteCounter;

            if (spriteCounter > 10)
            {
                if (spriteNumber == 1)
                    spriteNumber = 2;
                else if (spriteNumber == 2)
                    spriteNumber = 1;

                spriteCounter = 0;
            }
        }



    }

    public void drawing(Graphics2D g2)
    {
        BufferedImage image = null;

        switch (direction)
        {
            case "up" :     if (spriteNumber == 1)
                                image = up1 ;
                            else
                                image = up2; break;

            case "left" :   if (spriteNumber == 1)
                                image = left1;
                            else
                                image = left2; break;

            case "right" :  if (spriteNumber == 1)
                                image = right1;
                            else
                                image = right2; break;

            case "down" :   if (spriteNumber == 1)
                                image = down1;
                            else
                                image = down2; break;
        }

        g2.drawImage(image, position.x, position.y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    public void getImage()
    {
        try
        {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/up2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/right2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/down2.png")));
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}