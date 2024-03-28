package entity;

import main.*;
import main.screens.interfaces.*;
import objects.*;

import java.awt.*;
import java.util.HashSet;


public class Player extends Entity
{
    final private HashSet<BaseObject> interactiveObjects;
    final private KeyHandler keyHandler;
    final private IDialogueStarter dialogueStarter;
    final private TextMessages textMessages = new TextMessages();
    final private IMessageShower messageShower;
    final private IEntityCollisionChecker entityCollisionChecker;
    final private IObjectCollisionChecker objectCollisionChecker;
    private int hitPoint = 100;

    public int keysCount = 0;
    int boostCoolDown = 0;

    public Player(HashSet<BaseObject> interactiveObjects, KeyHandler keyHandler, IDialogueStarter dialogueStarter, Point defaultWorldPosition, IMessageShower messageShower, ITileCollisionChecker tileCollisionChecker, IEntityCollisionChecker entityCollisionChecker, IObjectCollisionChecker objectCollisionChecker)
    {
        super(defaultWorldPosition, tileCollisionChecker);

        this.interactiveObjects = interactiveObjects;
        this.keyHandler = keyHandler;
        this.dialogueStarter = dialogueStarter;
        this.messageShower = messageShower;
        this.entityCollisionChecker = entityCollisionChecker;
        this.objectCollisionChecker = objectCollisionChecker;

        screenCoordinates = new Point(Parameters.screenSize.x / 2 - Parameters.tileSize / 2, Parameters.screenSize.y / 2 - Parameters.tileSize / 2);

        movementSpeed = 2;
        direction = Direction.Down;
    }

    public void update(Sound sound)
    {
        keyHandler.getPressedDirection().ifPresent(value ->
        {
            this.direction = value;

            pickUpObject(objectCollisionChecker.checkObject(this), sound);

            Entity entity = entityCollisionChecker.checkEntity(this);

            if (entity instanceof Magician magician)
            {
                System.out.println("Встретился с " + magician.name);
                magician.rotateToPlayer(direction);
                dialogueStarter.startDialogue(new AbstractDialogue(magician.dialogues));
            }

            if (entity == null)
            {
                if (!tileCollisionChecker.checkTile(this))
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
            messageShower.showMessage(textMessages.getMessage(AllBaseObject.Key));
        }
        else if (item instanceof Box)
        {
            System.out.println("Ваш инвентарь");
        }
        else if (item instanceof Door)
            messageShower.showMessage(textMessages.getMessage(AllBaseObject.Door));
        else if (item instanceof Boots)
        {
            movementSpeed += 2;
            animationSpeed /= 2;
            boostCoolDown = 1000;
            messageShower.showMessage(textMessages.getMessage(AllBaseObject.Boots));
        }

        interactiveObjects.remove(item);
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
}