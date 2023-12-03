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
    public KeyHandler keyHandler;
    public int hasKey = 0;
    int coolDownBoost;
    TextMessages textMessages = new TextMessages();

    public Player(GamePanel gamePanel, KeyHandler keyHandler, Point defaultWorldPosition)
    {
        super(gamePanel, defaultWorldPosition);
        this.keyHandler = keyHandler;

        screenCoordinates = new Point(gamePanel.screenSize.x / 2 - gamePanel.tileSize / 2, gamePanel.screenSize.y / 2 - gamePanel.tileSize / 2);

        movementSpeed = 2;
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
                        worldPosition.y -= movementSpeed;
                        collisionArea.y -= movementSpeed;
                        break;

                    case Left:
                        worldPosition.x -= movementSpeed;
                        collisionArea.x -= movementSpeed;
                        break;

                    case Right:
                        worldPosition.x += movementSpeed;
                        collisionArea.x += movementSpeed;
                        break;

                    case Down:
                        worldPosition.y += movementSpeed;
                        collisionArea.y += movementSpeed;
                        break;

                    default:

                        break;
                }

                if (++spriteCounter > animationSpeed)
                {
                    ++spriteNumber;
                    spriteCounter = 0;
                    gamePanel.sound.play(Sounds.Step);
                }
            }
        });

        if (keyHandler.getPressedDirection().isEmpty()) {
            spriteNumber = 0;
        }

        if (--coolDownBoost < 0)
        {
            movementSpeed = 3;
            animationSpeed = 10;
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
            movementSpeed += 2;
            animationSpeed /= 2;
            coolDownBoost = 100;
            gamePanel.ui.showMessage("Ля какие шикарные бархатные тяги");
        }

        gamePanel.items.remove(item);
    }

    @Override
    public void loadImages()
    {
        LoadAnimation(Direction.Up, "/assets/Player/up1.png");
        LoadAnimation(Direction.Up, "/assets/Player/up2.png");
        LoadAnimation(Direction.Up, "/assets/Player/up3.png");
        LoadAnimation(Direction.Up, "/assets/Player/up4.png");

        LoadAnimation(Direction.Left, "/assets/Player/left1.png");
        LoadAnimation(Direction.Left, "/assets/Player/left2.png");
        LoadAnimation(Direction.Left, "/assets/Player/left3.png");
        LoadAnimation(Direction.Left, "/assets/Player/left4.png");

        LoadAnimation(Direction.Down, "/assets/Player/down1.png");
        LoadAnimation(Direction.Down, "/assets/Player/down2.png");
        LoadAnimation(Direction.Down, "/assets/Player/down3.png");
        LoadAnimation(Direction.Down, "/assets/Player/down4.png");

        LoadAnimation(Direction.Right, "/assets/Player/right1.png");
        LoadAnimation(Direction.Right, "/assets/Player/right2.png");
        LoadAnimation(Direction.Right, "/assets/Player/right3.png");
        LoadAnimation(Direction.Right, "/assets/Player/right4.png");
    }
}