package entity;

import main.CollisionChecker;
import java.awt.*;
import java.util.ArrayList;

public class Magician extends Entity
{
    int animationsTimeout = 0;
    public ArrayList<String> dialogues = new ArrayList<>();

    public Magician(Point defaultWorldPosition)
    {
        super(defaultWorldPosition);

        collisionArea = new Rectangle(defaultWorldPosition.x + 12, defaultWorldPosition.y + 12, 24, 24);
        worldPosition = defaultWorldPosition;
        movementSpeed = 1;

        direction = getRandomDirection();
    }

    public void update(Player player, CollisionChecker collisionChecker)
    {
        screenCoordinates = new Point(
                worldPosition.x - player.worldPosition.x + player.screenCoordinates.x,
                worldPosition.y - player.worldPosition.y + player.screenCoordinates.y
        );

        if (!collisionChecker.checkPlayer(this))
        {
            if (--animationsTimeout < 0)
            {
                direction = getRandomDirection();
                animationsTimeout = 300;
            }

            if (!collisionChecker.checkTile(this))
                makeStep(false, null);
            else
                changeDirection();
        }
    }

    @Override
    public void loadImages()
    {
        loadAnimation(Direction.Up, "/assets/Magician/up1.png");
        loadAnimation(Direction.Up, "/assets/Magician/up2.png");
        loadAnimation(Direction.Up, "/assets/Magician/up3.png");
        loadAnimation(Direction.Up, "/assets/Magician/up4.png");

        loadAnimation(Direction.Left, "/assets/Magician/left1.png");
        loadAnimation(Direction.Left, "/assets/Magician/left2.png");
        loadAnimation(Direction.Left, "/assets/Magician/left3.png");
        loadAnimation(Direction.Left, "/assets/Magician/left4.png");

        loadAnimation(Direction.Down, "/assets/Magician/down1.png");
        loadAnimation(Direction.Down, "/assets/Magician/down2.png");
        loadAnimation(Direction.Down, "/assets/Magician/down3.png");
        loadAnimation(Direction.Down, "/assets/Magician/down4.png");

        loadAnimation(Direction.Right, "/assets/Magician/right1.png");
        loadAnimation(Direction.Right, "/assets/Magician/right2.png");
        loadAnimation(Direction.Right, "/assets/Magician/right3.png");
        loadAnimation(Direction.Right, "/assets/Magician/right4.png");
    }

    public void rotateToPlayer(Direction playerDirection)
    {
        switch (playerDirection)
        {
            case Left ->    direction = Direction.Right;
            case Right ->   direction = Direction.Left;
            case Up ->      direction = Direction.Down;
            case Down ->    direction = Direction.Up;
        }
    }
}

