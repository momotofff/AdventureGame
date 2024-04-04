package entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.screens.interfaces.IPlayerCollisionChecker;
import main.screens.interfaces.ITileCollisionChecker;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class BaseMagician extends Entity
{
    private static class Data
    {
        public int movementSpeed;
        public int animationSpeed;
        public Point worldPosition;
        public String direction;
        public int collisionArea;
        public Point screenCoordinates;
        public String name;
        public ArrayList<String> dialogues;

        Data() {}
    }

    private int animationsTimeout = 0;
    public ArrayList<String> dialogues = new ArrayList<>();

    private final IPlayerCollisionChecker playerCollisionChecker;

    public BaseMagician(Point defaultWorldPosition, String path, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        super(defaultWorldPosition, tileCollisionChecker);

        this.playerCollisionChecker = playerCollisionChecker;
        movementSpeed = 1;
        direction = getRandomDirection();
        loadDialogs(path);
    }

    public BaseMagician(Point defaultWorldPosition, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        super(defaultWorldPosition, tileCollisionChecker);

        this.playerCollisionChecker = playerCollisionChecker;
        movementSpeed = 1;
        direction = getRandomDirection();
    }

    public void update(Player player)
    {
        screenCoordinates = new Point(
                worldPosition.x - player.worldPosition.x + player.screenCoordinates.x,
                worldPosition.y - player.worldPosition.y + player.screenCoordinates.y
        );

        if (!playerCollisionChecker.checkPlayer(this))
        {
            if (--animationsTimeout < 0)
            {
                direction = getRandomDirection();
                animationsTimeout = 300;
            }

            if (!tileCollisionChecker.checkTile(this))
                makeStep(false, null);
            else
                changeDirection();
        }
    }

    @Override
    public void loadImages()
    {
        loadAnimation(Direction.Up, "/assets/MagicianBlue/up1.png");
        loadAnimation(Direction.Up, "/assets/MagicianBlue/up2.png");
        loadAnimation(Direction.Up, "/assets/MagicianBlue/up3.png");
        loadAnimation(Direction.Up, "/assets/MagicianBlue/up4.png");

        loadAnimation(Direction.Left, "/assets/MagicianBlue/left1.png");
        loadAnimation(Direction.Left, "/assets/MagicianBlue/left2.png");
        loadAnimation(Direction.Left, "/assets/MagicianBlue/left3.png");
        loadAnimation(Direction.Left, "/assets/MagicianBlue/left4.png");

        loadAnimation(Direction.Down, "/assets/MagicianBlue/down1.png");
        loadAnimation(Direction.Down, "/assets/MagicianBlue/down2.png");
        loadAnimation(Direction.Down, "/assets/MagicianBlue/down3.png");
        loadAnimation(Direction.Down, "/assets/MagicianBlue/down4.png");

        loadAnimation(Direction.Right, "/assets/MagicianBlue/right1.png");
        loadAnimation(Direction.Right, "/assets/MagicianBlue/right2.png");
        loadAnimation(Direction.Right, "/assets/MagicianBlue/right3.png");
        loadAnimation(Direction.Right, "/assets/MagicianBlue/right4.png");
    }

    public void rotateToPlayer(Direction playerDirection)
    {
        direction = Direction.invert(playerDirection);
    }

    private void loadDialogs(String path)
    {
        InputStream stream = Objects.requireNonNull(getClass().getResourceAsStream(path));
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream)))
        {
            while ((line = reader.readLine()) != null)
                dialogues.add(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static BaseMagician fromJson(String pathToJson, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        Data data;

        try
        {
            data = objectMapper.readValue(BaseMagician.class.getResource(pathToJson), Data.class);
        }
        catch (Exception e)
        {
            return null;
        }

        BaseMagician baseMagician = new BaseMagician(data.worldPosition, tileCollisionChecker, playerCollisionChecker);
        baseMagician.movementSpeed = data.movementSpeed;
        baseMagician.animationSpeed = data.animationSpeed;
        baseMagician.direction = Direction.valueOf(data.direction);
        baseMagician.screenCoordinates = data.screenCoordinates;
        baseMagician.name = data.name;
        baseMagician.dialogues = data.dialogues;

        return baseMagician;
    }
}
