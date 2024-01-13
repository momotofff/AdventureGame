package objects;

import java.awt.*;

public class Door extends BaseObject
{
    public Door(Point mapPosition)
    {
        super("/assets/gameElements/door.png", null, mapPosition);
    }
}
