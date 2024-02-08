package main;

import entity.Entity;
import entity.Magician;
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
    // TODO: make static and move out
    final public Sound sound = new Sound();

    final public Player player;
    final public HashSet<BaseObject> objectInteractive = new HashSet<>();
    final public ArrayList<Magician> NPC = new ArrayList<>();
    final public ArrayList<Entity> animals = new ArrayList<>();

    public GameCommons(IDialogueStarter dialogueStarter, KeyHandler keyHandler, IMessageShower messages)
    {
        AssetSetter assetSetter = new AssetSetter();
        assetSetter.initObjects(tileManager.getFreePlaces(), objectInteractive);
        assetSetter.initNPC(tileManager.getFreePlaces(), NPC, this, this);
        assetSetter.initAnimals(tileManager.getFreePlaces(), animals, this);

        player = new Player(this, keyHandler, dialogueStarter, new Point(20 * Parameters.tileSize, 20 * Parameters.tileSize), messages, this, this, this);
    }

    @Override
    public boolean checkTile(Entity entity)
    {
        return CollisionChecker.checkTile(entity, tileManager.world);
    }

    @Override
    public Entity checkEntity(Entity player) {
        return CollisionChecker.checkEntity(player, NPC);
    }

    @Override
    public BaseObject checkObject(Entity player) {
        return CollisionChecker.checkObject(player, objectInteractive);
    }

    @Override
    public boolean checkPlayer(Entity character) {
        return CollisionChecker.checkPlayer(character, player);
    }
}
