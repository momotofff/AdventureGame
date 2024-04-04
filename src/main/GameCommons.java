package main;

import entity.BaseMagician;
import entity.Entity;
import entity.Player;
import main.screens.interfaces.*;
import objects.BaseObject;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GameCommons implements ITileCollisionChecker, IEntityCollisionChecker, IObjectCollisionChecker, IPlayerCollisionChecker
{
    final public TileManager tileManager = new TileManager();
    final public Player player;
    final public HashSet<BaseObject> interactiveObjects = new HashSet<>();
    final public ArrayList<BaseMagician> npc = new ArrayList<>();
    final public ArrayList<Entity> animals = new ArrayList<>();

    public GameCommons(IDialogueStarter dialogueStarter, KeyHandler keyHandler, IMessageShower messages)
    {
        AssetSetter assetSetter = new AssetSetter();
        assetSetter.initObjects(tileManager.getFreePlaces(), interactiveObjects);
        assetSetter.initNPC(tileManager.getFreePlaces(), npc, this, this);
        assetSetter.initAnimals(tileManager.getFreePlaces(), animals, this);

        player = new Player(interactiveObjects, keyHandler, dialogueStarter, new Point(20, 20), messages, this, this, this);
    }

    @Override
    public boolean checkTile(Entity entity)
    {
        return CollisionChecker.checkTile(entity, tileManager.world);
    }

    @Override
    public Entity checkEntity(Entity player) {
        return CollisionChecker.checkEntity(player, npc);
    }

    @Override
    public BaseObject checkObject(Entity player) {
        return CollisionChecker.checkObject(player, interactiveObjects);
    }

    @Override
    public boolean checkPlayer(Entity character) {
        return CollisionChecker.checkPlayer(character, player);
    }
}
