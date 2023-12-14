package entity;

import assets.Strings.TextMessages;
import main.GamePanel;
import main.GameState;
import main.KeyHandler;
import objects.*;

import java.awt.*;

public class Player extends Entity
{
    public KeyHandler keyHandler;
    public int keysCount = 0;
    int boostCoolDown;
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

            pickUpObject(gamePanel.collisionChecker.checkObject(this));

            Entity getEntity = gamePanel.collisionChecker.checkEntity(this);

            if (getEntity == null)
            {
                if (!gamePanel.collisionChecker.checkTile(this))
                    makeStep(true);
            }
            else
            {
                System.out.println("Встретился с " + getEntity);
                getEntity.validDirection();
                gamePanel.state = GameState.Dialog;
                getEntity.speak();
            }
        });

        if (keyHandler.getPressedDirection().isEmpty())
            resetAnimation();

        if (--boostCoolDown < 0)
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
            ++keysCount;
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
            boostCoolDown = 1000;
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