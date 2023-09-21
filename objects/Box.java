package objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Box extends SuperObject
{
    public Box()
    {
        name = "Box";

        try
        {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/gameElements/box.png")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
