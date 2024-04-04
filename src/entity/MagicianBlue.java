package entity;

import main.screens.interfaces.IPlayerCollisionChecker;
import main.screens.interfaces.ITileCollisionChecker;

import java.awt.*;

public class MagicianBlue extends BaseMagician
{
    public MagicianBlue(Point defaultWorldPosition, String path, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker) {
        super(defaultWorldPosition, path, tileCollisionChecker, playerCollisionChecker);
    }

    public MagicianBlue(Point defaultWorldPosition, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        super(defaultWorldPosition, tileCollisionChecker, playerCollisionChecker);
    }


    public static BaseMagician fromJson(String pathToJson, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        return BaseMagician.fromJson(pathToJson, tileCollisionChecker, playerCollisionChecker);
    }


}

