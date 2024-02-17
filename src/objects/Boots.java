package objects;

import main.Sounds;

import java.awt.*;

public class Boots extends BaseObject
{
    public Boots(Point mapPosition)
    {
        super("/assets/gameElements/boots.png", Sounds.Boots, mapPosition);
    }
}
