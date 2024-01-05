package objects;

import main.GamePanel;
import main.Sounds;

import java.awt.*;

public class Key extends BaseObject
{
    public Key(Point worldPosition)
    {

        super("/assets/gameElements/key.png", Sounds.Key, worldPosition);
    }

    public Key()
    {
        super("/assets/gameElements/key.png", null);
    }
}
