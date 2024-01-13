package objects;

import main.Sounds;

import java.awt.*;

public class Key extends BaseObject
{
    public Key(Point mapPosition)
    {

        super("/assets/gameElements/key.png", Sounds.Key, mapPosition);
    }

    public Key()
    {
        super("/assets/gameElements/key.png", null);
    }
}
