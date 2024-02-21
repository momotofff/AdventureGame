package entity;

import main.screens.interfaces.IPlayerCollisionChecker;
import main.screens.interfaces.ITileCollisionChecker;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Magician extends Entity
{
    int animationsTimeout = 0;
    public ArrayList<String> dialogues = new ArrayList<>();


    private IPlayerCollisionChecker playerCollisionChecker;

    public Magician(Point defaultWorldPosition, String path, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        super(defaultWorldPosition, tileCollisionChecker);

        this.playerCollisionChecker = playerCollisionChecker;
        movementSpeed = 1;
        direction = getRandomDirection();
        loadDialogs(path);
    }

    public Magician()
    {
        super();
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
        switch (playerDirection)
        {
            case Left ->    direction = Direction.Right;
            case Right ->   direction = Direction.Left;
            case Up ->      direction = Direction.Down;
            case Down ->    direction = Direction.Up;
        }
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
}
