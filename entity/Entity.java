package entity;

import main.GamePanel;
import main.Sounds;

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
    GamePanel gamePanel;
    public int movementSpeed;
    public int animationSpeed = 10;
    public Point worldPosition;
    public Direction direction;
    public Rectangle collisionArea;
    public Point screenCoordinates;

    private int spriteCounter = 0;
    private int spriteNumber = 1;

    public ArrayList<Direction> directions = new ArrayList<>();

    private final Map<Direction, ArrayList<BufferedImage>> animations;

    ArrayList<String> dialogues = new ArrayList<>();

    Entity(GamePanel gamePanel, Point defaultWorldPosition)
    {
        this.gamePanel = gamePanel;
        collisionArea = new Rectangle(defaultWorldPosition.x + 24, defaultWorldPosition.y + 24, 24, 24);
        worldPosition = defaultWorldPosition;
        screenCoordinates = defaultWorldPosition;

        animations = new HashMap<>();
        for (Direction direction: Direction.values())
            animations.put(direction, new ArrayList<>());

        loadImages();
    }

    protected void LoadAnimation(Direction direction, String path)
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

    protected BufferedImage GetAnimationFrame(Direction direction, int index)
    {
        ArrayList<BufferedImage> animation = animations.get(direction);

        if (animation.isEmpty())
            throw new RuntimeException("No animations were loaded");

        return animation.get(index % animation.size());
    }

    public abstract void loadImages();

    public void drawing(Graphics2D graphics2D)
    {
        BufferedImage image = GetAnimationFrame(direction, spriteNumber);
        graphics2D.drawImage(image, screenCoordinates.x, screenCoordinates.y, gamePanel.tileSize, gamePanel.tileSize, null);
        graphics2D.drawRect(screenCoordinates.x + 24, screenCoordinates.y + 24, collisionArea.width, collisionArea.height);
    }

    public void update()
    {}

    protected void makeStep(boolean playSound)
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

            if (playSound)
                gamePanel.sound.play(Sounds.Step);
        }
    }

    public void changeDirection()
    {
        Direction newDirection = direction;

        while (newDirection == direction)
            newDirection = directions.get((int) (Math.random() * directions.size()));

        direction = newDirection;
    }

    protected void resetAnimation()
    {
        spriteNumber = 0;
    }

    public void validDirection()
    {
        switch (gamePanel.player.direction)
        {
            case Left ->
                    direction = Direction.Right;
            case Right ->
                    direction = Direction.Left;
            case Up ->
                    direction = Direction.Down;
            case Down ->
                    direction = Direction.Up;

        }
    }

    public void speak()
    {}

}
