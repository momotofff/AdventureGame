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

    public Player(GamePanel gamePanel, KeyHandler keyHandler, Point initialPosition)
    {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenCoordinates = new Point(gamePanel.screenSize.x / 2 - gamePanel.tileSize / 2, gamePanel.screenSize.y / 2 - gamePanel.tileSize / 2);
        solidArea = new Rectangle(12, 12, 24, 24);
        solidAreaDefaultPosition = new Point(solidArea.x, solidArea.y);
        speed = 4;
        worldPosition = initialPosition;
        getImage();
        direction = Direction.Down;
    }

    public void update()
    {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed)
        {
            if (keyHandler.upPressed)
                direction = Direction.Up;

            if (keyHandler.leftPressed)
                direction = Direction.Left;

            if (keyHandler.rightPressed)
                direction = Direction.Right;

            if (keyHandler.downPressed)
                direction = Direction.Down;


            collision = false;
            gamePanel.collisionChecker.checkTile(this);

            if (collision == false)
            {
                switch (direction)
                {
                    case Up : worldPosition.y -= speed; break;
                    case Left: worldPosition.x -= speed; break;
                    case Right: worldPosition.x += speed; break;
                    case Down: worldPosition.y += speed; break;
                }
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