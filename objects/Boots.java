package objects;

import main.GamePanel;
import main.Sounds;

import java.awt.*;

public class Boots extends BaseObject
{
    public Boots(Point worldPosition, GamePanel gamePanel)
    {
        super("/assets/gameElements/boots.png", Sounds.Boots, worldPosition, gamePanel);
    }
}
