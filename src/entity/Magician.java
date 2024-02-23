package entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.screens.interfaces.IPlayerCollisionChecker;
import main.screens.interfaces.ITileCollisionChecker;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Magician extends Entity
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

    public Magician(Point defaultWorldPosition, String path, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        super(defaultWorldPosition, tileCollisionChecker);

        this.playerCollisionChecker = playerCollisionChecker;
        movementSpeed = 1;
        direction = getRandomDirection();
        loadDialogs(path);
    }

    public Magician(Point defaultWorldPosition, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
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

    public static Magician fromJson(String pathToJson, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        Data data;

        try
        {
            data = objectMapper.readValue(Magician.class.getResource(pathToJson), Data.class);
        }
        catch (Exception e)
        {                                                                                                                                                                                       
            return null;
        }

        Magician magician = new Magician(data.worldPosition, tileCollisionChecker, playerCollisionChecker);
        magician.movementSpeed = data.movementSpeed;
        magician.animationSpeed = data.animationSpeed;
        magician.direction = Direction.valueOf(data.direction);
        magician.screenCoordinates = data.screenCoordinates;
        magician.name = data.name;
        magician.dialogues = data.dialogues;

        return magician;
    }
}

