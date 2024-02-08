package main;

import entity.Entity;
import entity.Magician;
import objects.BaseObject;
import tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class CollisionChecker
{
    public static boolean checkTile(Entity entity, Tile[][] world)
    {
        int entityLeftCol = entity.collisionArea.x / Parameters.tileSize;
        int entityTopRow = entity.collisionArea.y / Parameters.tileSize;
        int entityRightCol = (entity.collisionArea.x + entity.collisionArea.width) / Parameters.tileSize;
        int entityBottomRow = (entity.collisionArea.y + entity.collisionArea.height) / Parameters.tileSize;

        Tile tile1 = null, tile2 = null;

        switch (entity.direction)
        {
            case Up:
                entityTopRow = (entity.collisionArea.y - entity.movementSpeed) / Parameters.tileSize;
                tile1 = world[entityLeftCol][entityTopRow];
                tile2 = world[entityRightCol][entityTopRow];
                break;

            case Down:
                entityBottomRow = (entity.collisionArea.y + entity.collisionArea.height + entity.movementSpeed) / Parameters.tileSize;
                tile1 = world[entityLeftCol][entityBottomRow];
                tile2 = world[entityRightCol][entityBottomRow];
                break;

            case Left:
                entityLeftCol = (entity.collisionArea.x - entity.movementSpeed) / Parameters.tileSize;
                tile1 = world[entityLeftCol][entityTopRow];
                tile2 = world[entityLeftCol][entityBottomRow];
                break;

            case Right:
                entityRightCol = (entity.collisionArea.x + entity.collisionArea.width + entity.movementSpeed) / Parameters.tileSize;
                tile1 = world[entityRightCol][entityTopRow];
                tile2 = world[entityRightCol][entityBottomRow];
                break;
        }

        if (tile1 == null || tile2 == null)
            return false;

        return tile1.collision || tile2.collision ;
    }

    public static BaseObject checkObject(Entity player, HashSet<BaseObject> objectInteractive)
    {
        return objectInteractive
            .stream()
            .filter(item -> player.collisionArea.intersects(item.areaCollision))
            .findFirst()
            .orElse(null);

/*
        for (BaseObject item: gamePanel.items)
        {
            if (item == null)
                continue;

            if (entity.collisionArea.intersects(item.areaCollision))
                return item;
        }
*/
    }
    public static Entity checkEntity(Entity player, ArrayList<Magician> NPC)
    {
        Rectangle playerCollisionArea;

        for (Entity entity: NPC)
        {
            playerCollisionArea = new Rectangle(player.collisionArea);

            switch (player.direction)
            {
                case Up:
                    playerCollisionArea.y -= Parameters.tileSize;
                    break;

                case Down:
                    playerCollisionArea.y += Parameters.tileSize;
                    break;

                case Left:
                    playerCollisionArea.x -= Parameters.tileSize;
                    break;

                case Right:
                    playerCollisionArea.x += Parameters.tileSize;
                    break;
            }

            if (playerCollisionArea.intersects(entity.collisionArea))
                return entity;
        }

        return null;
    }

    public static boolean checkPlayer(Entity character, Entity player)
    {
        Rectangle collisionArea = new Rectangle(character.collisionArea);

        collisionArea.width += 100;
        collisionArea.height += 100;
        collisionArea.x -= 50;
        collisionArea.y -= 50;

        return collisionArea.intersects(player.collisionArea);
    }
}
