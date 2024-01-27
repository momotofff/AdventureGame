package entity;

import main.*;
import main.screens.interfaces.IDialogueStarter;
import main.screens.interfaces.IScreenSwitcher;
import objects.*;

import java.awt.*;

public class Player extends Entity implements IScreenSwitcher
{
    final private GameCommons gameCommons;
    final private KeyHandler keyHandler;
    final private IDialogueStarter dialogueStarter;

    public int keysCount = 0;
    int boostCoolDown = 0;

    public Player(GameCommons gameCommons, KeyHandler keyHandler, IDialogueStarter dialogueStarter, Point defaultWorldPosition)
    {
        super(defaultWorldPosition);
        this.gameCommons = gameCommons;
        this.keyHandler = keyHandler;
        this.dialogueStarter = dialogueStarter;

        screenCoordinates = new Point(Parameters.screenSize.x / 2 - Parameters.tileSize / 2, Parameters.screenSize.y / 2 - Parameters.tileSize / 2);

        movementSpeed = 2;
        direction = Direction.Down;
    }

    public void update(Sound sound)
    {
        keyHandler.getPressedDirection().ifPresent(value ->
        {
            this.direction = value;

            pickUpObject(gameCommons.collisionChecker.checkObject(this), sound);

            Entity entity = gameCommons.collisionChecker.checkEntity(this);

            if (entity instanceof Magician magician)
            {
                System.out.println("Встретился с " + magician.name);
                magician.rotateToPlayer(direction);
                dialogueStarter.startDialogue(new AbstractDialogue(magician.dialogues));
            }

            if (entity == null)
            {
                if (!gameCommons.collisionChecker.checkTile(this))
                    makeStep(true, sound);
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

    public void pickUpObject(BaseObject item, Sound sound)
    {
        if (item == null)
            return;

        sound.playEffect(item.soundEffect);

        if (item instanceof Key)
        {
            ++keysCount;
            //gamePanel.ui.showMessage(textMessages.getKeyMessage());
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
            //gamePanel.ui.showMessage("Ля какие шикарные бархатные тяги");
        }

        gameCommons.items.remove(item);
    }

    @Override
    public void loadImages()
    {
        loadAnimation(Direction.Up, "/assets/Player/up1.png");
        loadAnimation(Direction.Up, "/assets/Player/up2.png");
        loadAnimation(Direction.Up, "/assets/Player/up3.png");
        loadAnimation(Direction.Up, "/assets/Player/up4.png");

        loadAnimation(Direction.Left, "/assets/Player/left1.png");
        loadAnimation(Direction.Left, "/assets/Player/left2.png");
        loadAnimation(Direction.Left, "/assets/Player/left3.png");
        loadAnimation(Direction.Left, "/assets/Player/left4.png");

        loadAnimation(Direction.Down, "/assets/Player/down1.png");
        loadAnimation(Direction.Down, "/assets/Player/down2.png");
        loadAnimation(Direction.Down, "/assets/Player/down3.png");
        loadAnimation(Direction.Down, "/assets/Player/down4.png");

        loadAnimation(Direction.Right, "/assets/Player/right1.png");
        loadAnimation(Direction.Right, "/assets/Player/right2.png");
        loadAnimation(Direction.Right, "/assets/Player/right3.png");
        loadAnimation(Direction.Right, "/assets/Player/right4.png");
    }

    @Override
    public void switchScreen(GameState newState)
    {

    }
}