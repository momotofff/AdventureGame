package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity
{
    GamePanel gamePanel;
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler)
    {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenSize.x / 2;
        screenY = gamePanel.screenSize.y / 2;

        speed = 4;
        position = new Point(gamePanel.tileSize * gamePanel.worldSize.x, gamePanel.tileSize * gamePanel.worldSize.y);
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
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    public void getImage()
    {
        LoadAnimation(Direction.Up, "/assets/player/up1.png");
        LoadAnimation(Direction.Up, "/assets/player/up2.png");
        LoadAnimation(Direction.Left, "/assets/player/left1.png");
        LoadAnimation(Direction.Left, "/assets/player/left2.png");
        LoadAnimation(Direction.Down, "/assets/player/down1.png");
        LoadAnimation(Direction.Down, "/assets/player/down2.png");
        LoadAnimation(Direction.Right, "/assets/player/right1.png");
        LoadAnimation(Direction.Right, "/assets/player/right2.png");
    }
}