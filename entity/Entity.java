package entity;

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
    public int speed;
    public Point worldPosition;
    public Direction direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle solidArea;
    public boolean collision = false;

    private final Map<Direction, ArrayList<BufferedImage>> animations;

    Entity()
    {
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
}
