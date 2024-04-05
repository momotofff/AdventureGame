package entity;

import main.screens.interfaces.IPlayerCollisionChecker;
import main.screens.interfaces.ITileCollisionChecker;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class BaseNpc extends Entity
{
    private int animationsTimeout = 0;
    public ArrayList<String> dialogues = new ArrayList<>();

    private IPlayerCollisionChecker playerCollisionChecker;

    public BaseNpc(Point defaultWorldPosition, String path, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        super(defaultWorldPosition, tileCollisionChecker);

        this.playerCollisionChecker = playerCollisionChecker;
        movementSpeed = 1;
        direction = getRandomDirection();
        loadDialogs(path);
    }
    public BaseNpc(Point defaultWorldPosition, String path, ITileCollisionChecker tileCollisionChecker)
    {
        super(defaultWorldPosition, tileCollisionChecker);

        movementSpeed = 1;
        direction = getRandomDirection();
        loadDialogs(path);
    }

    public BaseNpc(Point defaultWorldPosition, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        super(defaultWorldPosition, tileCollisionChecker);

        this.playerCollisionChecker = playerCollisionChecker;
        movementSpeed = 1;
        direction = getRandomDirection();
    }

    @Override
    public void loadImages() {

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
}
