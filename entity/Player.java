package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity
{
    GamePanel gamePanel;
    KeyHandler keyHandler;
    public final Point screenCoordinates;

    public Player(GamePanel gamePanel, KeyHandler keyHandler)
    {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenCoordinates = new Point(gamePanel.screenSize.x / 2 - (gamePanel.tileSize / 2), gamePanel.screenSize.y / 2 - (gamePanel.tileSize / 2));

        speed = 4;
        world_X_Y = new Point(gamePanel.tileSize * 23, gamePanel.tileSize * 21);
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
                world_X_Y.y -= speed;
            }
            if (keyHandler.leftPressed)
            {
                direction = Direction.Left;
                world_X_Y.x -= speed;
            }
            if (keyHandler.rightPressed)
            {
                direction = Direction.Right;
                world_X_Y.x += speed;
            }
            if (keyHandler.downPressed)
            {
                direction = Direction.Down;
                world_X_Y.y += speed;
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
        g2.drawImage(image, screenCoordinates.x, screenCoordinates.y, gamePanel.tileSize, gamePanel.tileSize, null);
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