package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Entity
{
    GamePanel gamePanel;
    public int speed;
    public Point worldPosition;
    public Direction direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle collisionArea;
    public Point screenCoordinates;

    private final Map<Direction, ArrayList<BufferedImage>> animations;

    Entity(GamePanel gamePanel, Point defaultWorldPosition)
    {
        this.gamePanel = gamePanel;
        collisionArea = new Rectangle(defaultWorldPosition.x + 24, defaultWorldPosition.y + 36, 12, 12);
        worldPosition = defaultWorldPosition;

        animations = new HashMap<>();
        for (Direction direction: Direction.values())
            animations.put(direction, new ArrayList<>());
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

    public void getImage() {}

    public void drawing(Graphics2D graphics2D)
    {
        BufferedImage image = GetAnimationFrame(direction, spriteNumber);
        graphics2D.drawImage(image, screenCoordinates.x, screenCoordinates.y, gamePanel.tileSize, gamePanel.tileSize, null);

    }
}
