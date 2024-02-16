package entity;

import main.Parameters;
import main.Sound;
import main.Sounds;
import main.screens.interfaces.ITileCollisionChecker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Entity
{
    public int movementSpeed = 0;
    public int animationSpeed = 10;
    public Point worldPosition = new Point();
    public Direction direction;
    public Rectangle collisionArea;
    public Point screenCoordinates = new Point();
    public String name = null;

    private int spriteCounter = 0;
    private int spriteNumber = 1;

    private final Map<Direction, ArrayList<BufferedImage>> animations = new HashMap<>();

    ITileCollisionChecker tileCollisionChecker;

    public void setWorldPosition(Point defaultWorldPosition) {
        this.worldPosition.x = defaultWorldPosition.x * Parameters.tileSize;
        this.worldPosition.y = defaultWorldPosition.y * Parameters.tileSize;
    }

    public void setScreenCoordinates(Point defaultWorldPosition) {
        this.screenCoordinates.x = defaultWorldPosition.x * Parameters.tileSize;
        this.screenCoordinates.y = defaultWorldPosition.y * Parameters.tileSize;
    }

    Entity(Point defaultWorldPosition, ITileCollisionChecker tileCollisionChecker)
    {
        this.tileCollisionChecker = tileCollisionChecker;

        setScreenCoordinates(defaultWorldPosition);
        setWorldPosition(defaultWorldPosition);
        collisionArea = new Rectangle(worldPosition.x + 24, worldPosition.y + 24, 24, 24);

        for (Direction direction: Direction.values())
            animations.put(direction, new ArrayList<>());

        loadImages();
    }

    public Entity()
    {
    }

    protected void loadAnimation(Direction direction, String path)
    {
        try
        {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            animations.get(direction).add(image);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected BufferedImage getAnimationFrame(Direction direction, int index)
    {
        ArrayList<BufferedImage> animation = animations.get(direction);

        if (animation.isEmpty())
            throw new RuntimeException("No animations were loaded");

        return animation.get(index % animation.size());
    }

    public abstract void loadImages();

    public void drawing(Graphics2D graphics2D, int tileSize)
    {
        BufferedImage image = getAnimationFrame(direction, spriteNumber);
        graphics2D.drawImage(image, screenCoordinates.x, screenCoordinates.y, tileSize, tileSize, null);
        graphics2D.drawRect(screenCoordinates.x + 24, screenCoordinates.y + 24, collisionArea.width, collisionArea.height);
    }

    protected void makeStep(boolean playSound, Sound sound)
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
        }

        if (++spriteCounter > animationSpeed)
        {
            ++spriteNumber;
            spriteCounter = 0;

            if (playSound && spriteNumber % 2 != 0)
                sound.playEffect(Sounds.Step);
        }
    }

    public Direction getRandomDirection()
    {
        Direction[] directions = Direction.values();
        return directions[((int) (Math.random() * directions.length))];
    }

    public void changeDirection()
    {
        Direction newDirection = direction;

        while (newDirection == direction)
            newDirection = getRandomDirection();

        direction = newDirection;
    }

    protected void resetAnimation()
    {
        spriteNumber = 0;
    }

    public void update(Player player) {
    }
    public BufferedImage getTile()
    {
        return animations.get(Direction.Down).get(0);
    }
}
