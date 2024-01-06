package entity;

import main.CollisionChecker;

import java.awt.*;


public class Rabbit extends Entity
{
    int animationsTimeout = 0;

    public Rabbit(Point defaultWorldPosition)
    {
        super(defaultWorldPosition);

        collisionArea = new Rectangle(defaultWorldPosition.x + 24, defaultWorldPosition.y + 36, 12, 12);
        worldPosition = defaultWorldPosition;
        movementSpeed = 1;

        direction = getRandomDirection();
    }

    public void update(Player player, CollisionChecker collisionChecker)
    {
        if (--animationsTimeout < 0)
        {
            direction = getRandomDirection();
            animationsTimeout = 300;
        }

        screenCoordinates = new Point(
                worldPosition.x - player.worldPosition.x + player.screenCoordinates.x,
                worldPosition.y - player.worldPosition.y + player.screenCoordinates.y
        );

        if (!collisionChecker.checkTile(this))
            makeStep(false, null);
        else
            changeDirection();
    }

    @Override
    public Direction getRandomDirection()
    {
        Direction[] directions = {Direction.Left, Direction.Right};
        return directions[((int) (Math.random() * directions.length))];
    }

    @Override
    public void loadImages()
    {
        loadAnimation(Direction.Left, "/assets/Rabbit/left1.png");
        loadAnimation(Direction.Left, "/assets/Rabbit/left2.png");
        loadAnimation(Direction.Left, "/assets/Rabbit/left3.png");
        loadAnimation(Direction.Left, "/assets/Rabbit/left4.png");

        loadAnimation(Direction.Right, "/assets/Rabbit/right1.png");
        loadAnimation(Direction.Right, "/assets/Rabbit/right2.png");
        loadAnimation(Direction.Right, "/assets/Rabbit/right3.png");
        loadAnimation(Direction.Right, "/assets/Rabbit/right4.png");
    }
}