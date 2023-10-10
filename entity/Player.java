package entity;

import main.GamePanel;
import main.KeyHandler;
import objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity
{
    GamePanel gamePanel;
    KeyHandler keyHandler;
    public final Point screenCoordinates;
    int hasKey = 0;
    int speedAnimation = 10;
    int coolDownBoost;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, Point defaultWorldPosition)
    {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenCoordinates = new Point(gamePanel.screenSize.x / 2 - gamePanel.tileSize / 2, gamePanel.screenSize.y / 2 - gamePanel.tileSize / 2);
        areaCollision = new Rectangle(defaultWorldPosition.x + 12, defaultWorldPosition. y + 12, 24, 24);
        speed = 4;
        worldPosition = defaultWorldPosition;
        getImage();
        direction = Direction.Down;
    }

    public void update()
    {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed)
        {
            collision = false;

            if (keyHandler.upPressed)
                direction = Direction.Up;

            if (keyHandler.leftPressed)
                direction = Direction.Left;

            if (keyHandler.rightPressed)
                direction = Direction.Right;

            if (keyHandler.downPressed)
                direction = Direction.Down;

            gamePanel.collisionChecker.checkTile(this);
            BaseObject item = gamePanel.collisionChecker.checkObject(this);
            pickUpObject(item);

            if (!collision)
            {
                switch (direction)
                {
                    case Up :
                        worldPosition.y -= speed;
                        areaCollision.y -= speed;
                        break;

                    case Left:
                        worldPosition.x -= speed;
                        areaCollision.x -= speed;
                        break;

                    case Right:
                        worldPosition.x += speed;
                        areaCollision.x += speed;
                        break;

                    case Down:
                        worldPosition.y += speed;
                        areaCollision.y += speed;
                        break;
                }
            }

            if (++spriteCounter > speedAnimation)
            {
                ++spriteNumber;
                spriteCounter = 0;
            }
        }

        if (--coolDownBoost < 0)
        {
            speed = 4;
            speedAnimation = 10;
        }
    }

    public void pickUpObject(BaseObject item)
    {
        if (item == null)
            return;

        if (item instanceof Key)
            ++hasKey;
        else if (item instanceof Box)
            System.out.println("Open inventory");
        else if (item instanceof Door)
            System.out.println("Check yes or no Key");
        else if (item instanceof Boots)
        {
            speed += 2;
            speedAnimation /= 2;
            coolDownBoost = 240;
        }

        gamePanel.items.remove(item);
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