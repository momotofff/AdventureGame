package entity;

import main.screens.interfaces.IPlayerCollisionChecker;
import main.screens.interfaces.ITileCollisionChecker;

import java.awt.*;

public class Magician extends BaseMagician
{
    public Magician(Point defaultWorldPosition, String path, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker) {
        super(defaultWorldPosition, path, tileCollisionChecker, playerCollisionChecker);
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
}

