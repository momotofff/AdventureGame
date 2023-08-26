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
        direction = Direction.Down;
    }

    public void update()
    {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed)
        {
            if (keyHandler.upPressed)
            {
                direction = Direction.Up;
                position.y -= speed;
            }
            if (keyHandler.leftPressed)
            {
                direction = Direction.Left;
                position.x -= speed;
            }
            if (keyHandler.rightPressed)
            {
                direction = Direction.Right;
                position.x += speed;
            }
            if (keyHandler.downPressed)
            {
                direction = Direction.Down;
                position.y += speed;
            }

            if (++spriteCounter > 10)
            {
                ++spriteNumber;
                spriteCounter = 0;
            }
        }
    }

    public void drawing(Graphics2D g2)
    {
        BufferedImage image = GetAnimationFrame(direction, spriteNumber);
        g2.drawImage(image, position.x, position.y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    public void getImage()
    {
        LoadAnimation(Direction.Up, "/assets/up1.png");
        LoadAnimation(Direction.Up, "/assets/up2.png");
        LoadAnimation(Direction.Left, "/assets/left1.png");
        LoadAnimation(Direction.Left, "/assets/left2.png");
        LoadAnimation(Direction.Down, "/assets/down1.png");
        LoadAnimation(Direction.Down, "/assets/down2.png");
        LoadAnimation(Direction.Right, "/assets/right1.png");
        LoadAnimation(Direction.Right, "/assets/right2.png");
    }
}