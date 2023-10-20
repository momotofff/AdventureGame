package objects;

import main.GamePanel;

import java.awt.*;

public class Key extends BaseObject
{

    public Key(Point worldPosition, GamePanel gamePanel)
    {
        super("/assets/gameElements/key.png", worldPosition, gamePanel);
    }

    public Key()
    {
        super("/assets/gameElements/key.png");
    }
}
