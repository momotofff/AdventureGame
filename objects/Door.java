package objects;

import java.awt.*;

public class Door extends BaseObject
{
    public Door(Point worldPosition)
    {
        super("/assets/gameElements/door.png", worldPosition);

        collision = true;
    }
}
