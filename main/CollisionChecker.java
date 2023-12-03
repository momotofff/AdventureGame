package main;

import entity.Entity;
import objects.BaseObject;
import tile.Tile;

public class CollisionChecker
{
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public boolean checkTile(Entity entity)
    {
        int entityLeftCol = entity.collisionArea.x / gamePanel.tileSize;
        int entityTopRow = entity.collisionArea.y / gamePanel.tileSize;
        int entityRightCol = (entity.collisionArea.x + entity.collisionArea.width) / gamePanel.tileSize;
        int entityBottomRow = (entity.collisionArea.y + entity.collisionArea.height) / gamePanel.tileSize;

        Tile tile1 = null, tile2 = null;

        switch (entity.direction)
        {
            case Up:
                entityTopRow = (entity.collisionArea.y - entity.movementSpeed) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.world[entityLeftCol][entityTopRow];
                tile2 = gamePanel.tileManager.world[entityRightCol][entityTopRow];
                break;

            case Down:
                entityBottomRow = (entity.collisionArea.y + entity.collisionArea.height + entity.movementSpeed) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.world[entityLeftCol][entityBottomRow];
                tile2 = gamePanel.tileManager.world[entityRightCol][entityBottomRow];
                break;

            case Left:
                entityLeftCol = (entity.collisionArea.x - entity.movementSpeed) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.world[entityLeftCol][entityTopRow];
                tile2 = gamePanel.tileManager.world[entityLeftCol][entityBottomRow];
                break;

            case Right:
                entityRightCol = (entity.collisionArea.x + entity.collisionArea.width + entity.movementSpeed) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.world[entityRightCol][entityTopRow];
                tile2 = gamePanel.tileManager.world[entityRightCol][entityBottomRow];
                break;
        }

        if (tile1 == null || tile2 == null)
            return false;

        return tile1.collision || tile2.collision;
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
}
