package entity;

import main.GamePanel;

import java.awt.*;


public class Rabbit extends Entity {
    int animationsTimeout = 0;


    public Rabbit(GamePanel gamePanel, Point defaultWorldPosition)
    {
        super(gamePanel, defaultWorldPosition);

        collisionArea = new Rectangle(defaultWorldPosition.x + 24, defaultWorldPosition.y + 36, 12, 12);
        worldPosition = defaultWorldPosition;
        movementSpeed = 1;

        direction = getRandomDirection();
    }

    public void update()
    {
        if (--animationsTimeout < 0)
        {
            direction = getRandomDirection();
            animationsTimeout = 300;
        }

        screenCoordinates = new Point(
                worldPosition.x - gamePanel.player.worldPosition.x + gamePanel.player.screenCoordinates.x,
                worldPosition.y - gamePanel.player.worldPosition.y + gamePanel.player.screenCoordinates.y
        );

        if (!gamePanel.collisionChecker.checkTile(this))
            makeStep(false);
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
        LoadAnimation(Direction.Left, "/assets/Rabbit/left1.png");
        LoadAnimation(Direction.Left, "/assets/Rabbit/left2.png");
        LoadAnimation(Direction.Left, "/assets/Rabbit/left3.png");
        LoadAnimation(Direction.Left, "/assets/Rabbit/left4.png");

        LoadAnimation(Direction.Right, "/assets/Rabbit/right1.png");
        LoadAnimation(Direction.Right, "/assets/Rabbit/right2.png");
        LoadAnimation(Direction.Right, "/assets/Rabbit/right3.png");
        LoadAnimation(Direction.Right, "/assets/Rabbit/right4.png");
    }
}