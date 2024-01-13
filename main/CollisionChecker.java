package main;

import entity.Entity;
import objects.BaseObject;
import tile.Tile;

import java.awt.*;

public class CollisionChecker
{
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public boolean checkTile(Entity entity)
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
                tile1 = gamePanel.tileManager.world[entityLeftCol][entityTopRow];
                tile2 = gamePanel.tileManager.world[entityRightCol][entityTopRow];
                break;

            case Down:
                entityBottomRow = (entity.collisionArea.y + entity.collisionArea.height + entity.movementSpeed) / Parameters.tileSize;
                tile1 = gamePanel.tileManager.world[entityLeftCol][entityBottomRow];
                tile2 = gamePanel.tileManager.world[entityRightCol][entityBottomRow];
                break;

            case Left:
                entityLeftCol = (entity.collisionArea.x - entity.movementSpeed) / Parameters.tileSize;
                tile1 = gamePanel.tileManager.world[entityLeftCol][entityTopRow];
                tile2 = gamePanel.tileManager.world[entityLeftCol][entityBottomRow];
                break;

            case Right:
                entityRightCol = (entity.collisionArea.x + entity.collisionArea.width + entity.movementSpeed) / Parameters.tileSize;
                tile1 = gamePanel.tileManager.world[entityRightCol][entityTopRow];
                tile2 = gamePanel.tileManager.world[entityRightCol][entityBottomRow];
                break;
        }

        if (tile1 == null || tile2 == null)
            return false;

        return tile1.collision || tile2.collision ;
    }

    public BaseObject checkObject(Entity entity)
    {
        return gamePanel.items
            .stream()
            .filter(item -> entity.collisionArea.intersects(item.areaCollision))
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
    public Entity checkEntity(Entity player)
    {
        Rectangle playerCollisionArea;

        for (Entity entity: gamePanel.NPC)
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

    public boolean checkPlayer(Entity entity)
    {
        Rectangle collisionArea = new Rectangle(entity.collisionArea);

        collisionArea.width += 100;
        collisionArea.height += 100;
        collisionArea.x -= 50;
        collisionArea.y -= 50;

        return collisionArea.intersects(gamePanel.player.collisionArea);
    }
}
