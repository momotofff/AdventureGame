package main;

import entity.Entity;
import entity.Magician;
import entity.Player;
import main.screens.interfaces.IDialogueStarter;
import main.screens.interfaces.IMessages;
import objects.BaseObject;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GameCommons
{
    final public TileManager tileManager = new TileManager();
    final public CollisionChecker collisionChecker = new CollisionChecker(this);
    final public Sound sound = new Sound();

    final public Player player;
    final public HashSet<BaseObject> items = new HashSet<>();
    final public ArrayList<Magician> NPC = new ArrayList<>();
    final public ArrayList<Entity> animals = new ArrayList<>();

    public GameCommons(KeyHandler keyHandler, IDialogueStarter dialogueStarter)
    {
        AssetSetter assetSetter = new AssetSetter();
        assetSetter.initObjects(tileManager.getFreePlaces(), items);
        assetSetter.initEntity(tileManager.getFreePlaces(), NPC, animals);

        player = new Player(this, keyHandler, dialogueStarter, new Point(20 * Parameters.tileSize, 20 * Parameters.tileSize));
    }
}
