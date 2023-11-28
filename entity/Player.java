package entity;

import assets.Strings.TextMessages;
import main.GamePanel;
import main.KeyHandler;
import main.Sounds;
import objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity
{
    GamePanel gamePanel;
    KeyHandler keyHandler;
    public final Point screenCoordinates;
    public int hasKey = 0;
    int speedAnimation = 10;
    int coolDownBoost;
    TextMessages textMessages = new TextMessages();

    public Player(GamePanel gamePanel, KeyHandler keyHandler, Point defaultWorldPosition)
    {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenCoordinates = new Point(gamePanel.screenSize.x / 2 - gamePanel.tileSize / 2, gamePanel.screenSize.y / 2 - gamePanel.tileSize / 2);
        collisionArea = new Rectangle(defaultWorldPosition.x + 24, defaultWorldPosition.y + 36, 12, 12);
        speed = 3;
        worldPosition = defaultWorldPosition;
        getImage();
        direction = Direction.Down;
    }

    public void update()
    {
        keyHandler.getPressedDirection().ifPresent(value ->
        {
            this.direction = value;

            BaseObject item = gamePanel.collisionChecker.checkObject(this);
            pickUpObject(item);

            if (!gamePanel.collisionChecker.checkTile(this))
            {
                switch (direction)
                {
                    case Up:
                        worldPosition.y -= speed;
                        collisionArea.y -= speed;
                        break;

                    case Left:
                        worldPosition.x -= speed;
                        collisionArea.x -= speed;
                        break;

                    case Right:
                        worldPosition.x += speed;
                        collisionArea.x += speed;
                        break;

                    case Down:
                        worldPosition.y += speed;
                        collisionArea.y += speed;
                        break;
                }
            }

            if (++spriteCounter > speedAnimation)
            {
                ++spriteNumber;
                spriteCounter = 0;
                gamePanel.sound.play(Sounds.Step);
            }
        });

        if (--coolDownBoost < 0)
        {
            speed = 6;
            speedAnimation = 10;
        }
    }

    public void pickUpObject(BaseObject item)
    {
        if (item == null)
            return;

        gamePanel.sound.play(item.soundEffect);

        if (item instanceof Key)
        {
            ++hasKey;
            gamePanel.ui.showMessage(textMessages.getKeyMessage());
        }
        else if (item instanceof Box)
        {
            System.out.println("Ваш инвентарь");
        }
        else if (item instanceof Door)
            System.out.println("Не факт что у тебя есть нужный ключ.");
        else if (item instanceof Boots)
        {
            speed += 2;
            speedAnimation /= 2;
            coolDownBoost = 100;
            gamePanel.ui.showMessage("Ля какие шикарные бархатные тяги");
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