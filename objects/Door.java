package objects;

import main.GamePanel;

import java.awt.*;

public class Door extends BaseObject
{
    public Door(Point worldPosition, GamePanel gamePanel)
    {
        super("/assets/gameElements/door.png", null, worldPosition, gamePanel);
    }
}
