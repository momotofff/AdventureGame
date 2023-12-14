package entity;

import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class Magician extends Entity
{
    int animationsTimeout = 0;


    public Magician(GamePanel gamePanel, Point defaultWorldPosition)
    {
        super(gamePanel, defaultWorldPosition);

        collisionArea = new Rectangle(defaultWorldPosition.x + 12, defaultWorldPosition.y + 12, 24, 24);
        worldPosition = defaultWorldPosition;
        movementSpeed = 1;

        directions.add(Direction.Up);
        directions.add(Direction.Left);
        directions.add(Direction.Right);
        directions.add(Direction.Down);
        direction = directions.get((int) (Math.random() * directions.size()));
        setDialogues();
    }

    public void update()
    {
        screenCoordinates = new Point(
                worldPosition.x - gamePanel.player.worldPosition.x + gamePanel.player.screenCoordinates.x,
                worldPosition.y - gamePanel.player.worldPosition.y + gamePanel.player.screenCoordinates.y
        );

        if (!gamePanel.collisionChecker.checkPlayer(this))
        {
            if (--animationsTimeout < 0)
            {
                direction = directions.get((int) (Math.random() * directions.size()));
                animationsTimeout = 300;
            }

            if (!gamePanel.collisionChecker.checkTile(this))
                makeStep(false);
            else
                changeDirection();
        }
        else
            validDirection();

    }

    @Override
    public void loadImages()
    {
        LoadAnimation(Direction.Up, "/assets/Magician/up1.png");
        LoadAnimation(Direction.Up, "/assets/Magician/up2.png");
        LoadAnimation(Direction.Up, "/assets/Magician/up3.png");
        LoadAnimation(Direction.Up, "/assets/Magician/up4.png");

        LoadAnimation(Direction.Left, "/assets/Magician/left1.png");
        LoadAnimation(Direction.Left, "/assets/Magician/left2.png");
        LoadAnimation(Direction.Left, "/assets/Magician/left3.png");
        LoadAnimation(Direction.Left, "/assets/Magician/left4.png");

        LoadAnimation(Direction.Down, "/assets/Magician/down1.png");
        LoadAnimation(Direction.Down, "/assets/Magician/down2.png");
        LoadAnimation(Direction.Down, "/assets/Magician/down3.png");
        LoadAnimation(Direction.Down, "/assets/Magician/down4.png");

        LoadAnimation(Direction.Right, "/assets/Magician/right1.png");
        LoadAnimation(Direction.Right, "/assets/Magician/right2.png");
        LoadAnimation(Direction.Right, "/assets/Magician/right3.png");
        LoadAnimation(Direction.Right, "/assets/Magician/right4.png");
    }

    public void setDialogues()
    {
        dialogues.add("Привет путешественник.");
        dialogues.add("Могу помочь тебе с поиском сокровищ.");
        dialogues.add("Заранее хочу тебя предупредить что тебя могут ждать трудности.");
        dialogues.add("Вот тебе первая подсказка для начала твоего путеществия. Удачи.");
    }

    @Override
    public void speak()
    {
        gamePanel.ui.currentDialogue = dialogues.get(0);
    }

}

