package objects;

import main.GamePanel;

import java.awt.*;

public class Box extends BaseObject
{
    public Box(Point worldPosition, GamePanel gamePanel)
    {
        super("/assets/gameElements/Box.png", null, worldPosition, gamePanel);
    }
}