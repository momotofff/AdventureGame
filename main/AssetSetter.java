package main;

import entity.Entity;
import entity.Magician;
import entity.Rabbit;
import main.screens.interfaces.IPlayerCollisionChecker;
import main.screens.interfaces.ITileCollisionChecker;
import objects.BaseObject;
import objects.Boots;
import objects.Box;
import objects.Key;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;

public class AssetSetter
{
    public void initObjects(ArrayList<Point> freePlaces, HashSet<BaseObject> items)
    {
        items.add(new Key(new Point(19, 14)));
        items.add(new Key(new Point(39, 16)));
        items.add(new Key(new Point(15, 13)));
        items.add(new Key(new Point(29, 20)));
        items.add(new Box(new Point(15, 25)));
        items.add(new Box(new Point(50, 32)));
        items.add(new Boots(new Point(19, 19)));
    }

    public void initNPC(ArrayList<Point> freePlaces, ArrayList<Magician> NPC, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        Magician magician1 = new Magician(new Point(15 * Parameters.tileSize, 15 * Parameters.tileSize), "./assets/Strings/Magician1.txt", tileCollisionChecker, playerCollisionChecker);
        magician1.name = "Mag";
        NPC.add(magician1);

        Magician magician2 = new Magician(new Point(15 * Parameters.tileSize, 20 * Parameters.tileSize), "./assets/Strings/Magician2.txt", tileCollisionChecker, playerCollisionChecker);
        magician2.name = "Mag борэц";
        NPC.add(magician2);
    }

    public void initAnimals(ArrayList<Point> freePlaces, ArrayList<Entity> animals, ITileCollisionChecker tileCollisionChecker)
    {
        for (int i = 0; i < 20; ++i)
        {
            Point position = new Point(freePlaces.get((int) (Math.random() * freePlaces.size())));
            position.x *= Parameters.tileSize;
            position.y *= Parameters.tileSize;
            animals.add(new Rabbit(position, tileCollisionChecker));
        }
    }
}
