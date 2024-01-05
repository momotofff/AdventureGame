package objects;

import main.GamePanel;
import main.Sounds;

import java.awt.*;

public class Boots extends BaseObject
{
    public Boots(Point worldPosition)
    {
        super("/assets/gameElements/boots.png", Sounds.Boots, worldPosition);
    }
}
