package entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.screens.interfaces.IPlayerCollisionChecker;
import main.screens.interfaces.ITileCollisionChecker;

import java.awt.*;
import java.util.ArrayList;

public class MagicianJson extends BaseMagician
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

    public MagicianJson(Point defaultWorldPosition, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        super(defaultWorldPosition, tileCollisionChecker, playerCollisionChecker);
    }

    public static MagicianJson fromJson(String pathToJson, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
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

        MagicianJson magicianJson = new MagicianJson(data.worldPosition, tileCollisionChecker, playerCollisionChecker);
        magicianJson.movementSpeed = data.movementSpeed;
        magicianJson.animationSpeed = data.animationSpeed;
        magicianJson.direction = Direction.valueOf(data.direction);
        magicianJson.screenCoordinates = data.screenCoordinates;
        magicianJson.name = data.name;
        magicianJson.dialogues = data.dialogues;

        return magicianJson;
    }

    @Override
    public void loadImages()
    {
        loadAnimation(Direction.Up, "/assets/MagicianRed/up1.png");
        loadAnimation(Direction.Up, "/assets/MagicianRed/up2.png");
        loadAnimation(Direction.Up, "/assets/MagicianRed/up3.png");
        loadAnimation(Direction.Up, "/assets/MagicianRed/up4.png");

        loadAnimation(Direction.Left, "/assets/MagicianRed/left1.png");
        loadAnimation(Direction.Left, "/assets/MagicianRed/left2.png");
        loadAnimation(Direction.Left, "/assets/MagicianRed/left3.png");
        loadAnimation(Direction.Left, "/assets/MagicianRed/left4.png");

        loadAnimation(Direction.Down, "/assets/MagicianRed/down1.png");
        loadAnimation(Direction.Down, "/assets/MagicianRed/down2.png");
        loadAnimation(Direction.Down, "/assets/MagicianRed/down3.png");
        loadAnimation(Direction.Down, "/assets/MagicianRed/down4.png");

        loadAnimation(Direction.Right, "/assets/MagicianRed/right1.png");
        loadAnimation(Direction.Right, "/assets/MagicianRed/right2.png");
        loadAnimation(Direction.Right, "/assets/MagicianRed/right3.png");
        loadAnimation(Direction.Right, "/assets/MagicianRed/right4.png");
    }

}
