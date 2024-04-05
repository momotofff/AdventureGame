package entity;

import main.screens.interfaces.IPlayerCollisionChecker;
import main.screens.interfaces.ITileCollisionChecker;

import java.awt.*;

public class Ghost extends BaseNpc
{
    public Ghost(Point defaultWorldPosition, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker) {
        super(defaultWorldPosition,  tileCollisionChecker, playerCollisionChecker);
    }

    public void loadImages()
    {
        loadAnimation(Direction.Up, "/assets/Ghost/up1.png");
        loadAnimation(Direction.Up, "/assets/Ghost/up2.png");
        loadAnimation(Direction.Up, "/assets/Ghost/up3.png");
        loadAnimation(Direction.Up, "/assets/Ghost/up4.png");

        loadAnimation(Direction.Left, "/assets/Ghost/left1.png");
        loadAnimation(Direction.Left, "/assets/Ghost/left2.png");
        loadAnimation(Direction.Left, "/assets/Ghost/left3.png");
        loadAnimation(Direction.Left, "/assets/Ghost/left4.png");

        loadAnimation(Direction.Down, "/assets/Ghost/down1.png");
        loadAnimation(Direction.Down, "/assets/Ghost/down2.png");
        loadAnimation(Direction.Down, "/assets/Ghost/down3.png");
        loadAnimation(Direction.Down, "/assets/Ghost/down4.png");

        loadAnimation(Direction.Right, "/assets/Ghost/right1.png");
        loadAnimation(Direction.Right, "/assets/Ghost/right2.png");
        loadAnimation(Direction.Right, "/assets/Ghost/right3.png");
        loadAnimation(Direction.Right, "/assets/Ghost/right4.png");
    }
}
