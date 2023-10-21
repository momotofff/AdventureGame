package objects;

import main.GamePanel;

import java.awt.*;

public class Box extends BaseObject
{
    public Box(Point worldPosition, GamePanel gamePanel)
    {
        super("/assets/gameElements/box.png", null, worldPosition, gamePanel);
    }
}
