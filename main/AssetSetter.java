package main;

import entity.Entity;
import entity.Magician;
import entity.Rabbit;
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
    private final int tileSize;

    public AssetSetter(int tileSize)
    {
        this.tileSize = tileSize;
    }

    public void initObjects(ArrayList<Point> freePlaces, HashSet<BaseObject> items)
    {
        items.add(new Key(new Point(19 * tileSize, 14 * tileSize)));
        items.add(new Key(new Point(39 * tileSize, 16 * tileSize)));
        items.add(new Key(new Point(15 * tileSize, 13 * tileSize)));
        items.add(new Key(new Point(29 * tileSize, 20 * tileSize)));
        items.add(new Box(new Point(15 * tileSize, 25 * tileSize)));
        items.add(new Box(new Point(50 * tileSize, 32 * tileSize)));
        items.add(new Boots(new Point(19 * tileSize, 19 * tileSize)));
    }

    public void initEntity(ArrayList<Point> freePlaces, ArrayList<Magician> NPC, ArrayList<Entity> animals)
    {
        Magician magician1 = new Magician(new Point(15 * tileSize, 15 * tileSize));
        magician1.dialogues.add("Привет путешественник. ");
        magician1.dialogues.add("Могу помочь тебе с поиском сокровищ.");
        magician1.dialogues.add("Заранее хочу тебя предупредить что тебя могут ждать /nтрудности.");
        magician1.dialogues.add("Вот тебе первая подсказка для начала твоего путешествия. /nУдачи.");
        magician1.name = "Mag";
        NPC.add(magician1);

        Magician magician2 = new Magician(new Point(15 * tileSize, 20 * tileSize));
        magician2.dialogues.add("Салам братуха борцуха. ");
        magician2.dialogues.add("Ты уже отработал бархатные тяги?");
        magician2.dialogues.add("Я в благородство играть не стану, /nчерез два моста найдешь приору посаженую.");
        magician2.dialogues.add("Вот тебе макасы, в них с толкача удобней заводить.");
        magician2.name = "Mag борэц";
        NPC.add(magician2);

        for (int i = 0; i < 20; ++i)
            animals.add(createEntity(Rabbit.class, freePlaces));
    }

    private Entity createEntity(Class<? extends Entity> cls, ArrayList<Point> freePlaces)
    {
        Point position = new Point(freePlaces.get((int) (Math.random() * freePlaces.size())));
        position.x *= tileSize;
        position.y *= tileSize;

        try
        {
            Constructor<? extends Entity> constructor = cls.getConstructor(Point.class);
            return constructor.newInstance(position);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
