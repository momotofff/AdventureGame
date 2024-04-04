package main;

import entity.BaseMagician;
import entity.Entity;
import entity.MagicianBlue;
import entity.Rabbit;
import main.screens.interfaces.IPlayerCollisionChecker;
import main.screens.interfaces.ITileCollisionChecker;
import objects.BaseObject;
import objects.Boots;
import objects.Box;
import objects.Key;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class AssetSetter
{
    public void initObjects(ArrayList<Point> freePlaces, HashSet<BaseObject> items)
    {
        items.add(new Key(new Point(19, 15)));
        items.add(new Key(new Point(39, 16)));
        items.add(new Key(new Point(15, 15)));
        items.add(new Key(new Point(18, 15)));
        items.add(new Key(new Point(17, 15)));
        items.add(new Key(new Point(29, 20)));
        items.add(new Box(new Point(15, 25)));
        items.add(new Box(new Point(50, 32)));
        items.add(new Boots(new Point(19, 19)));
    }

    public void initNPC(ArrayList<Point> freePlaces, ArrayList<BaseMagician> NPC, ITileCollisionChecker tileCollisionChecker, IPlayerCollisionChecker playerCollisionChecker)
    {
        MagicianBlue magicianBlue1 = new MagicianBlue(new Point(15, 15), "/assets/Strings/Magician1.txt", tileCollisionChecker, playerCollisionChecker);
        magicianBlue1.name = "Mag";
        NPC.add(magicianBlue1);

        BaseMagician magicianBlue2 = MagicianBlue.fromJson("/assets/json/magician2.json", tileCollisionChecker, playerCollisionChecker);
        NPC.add(magicianBlue2);
    }

    public void initAnimals(ArrayList<Point> freePlaces, ArrayList<Entity> animals, ITileCollisionChecker tileCollisionChecker)
    {
        for (int i = 0; i < 100; ++i)
        {
            Point position = new Point(freePlaces.get((int) (Math.random() * freePlaces.size())));
            animals.add(new Rabbit(position, tileCollisionChecker));
        }
    }
}
