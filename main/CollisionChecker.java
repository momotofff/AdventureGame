package main;

import entity.Entity;
import entity.Player;
import objects.BaseObject;
import tile.Tile;

public class CollisionChecker
{
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity)
    {
        int entityLeftWorldX = entity.worldPosition.x + entity.solidArea.x;
        int entityRightWorldX = entity.worldPosition.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldPosition.y + entity.solidArea.y;
        int entityBottomWorldY = entity.worldPosition.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        Tile tile1, tile2;

        switch (entity.direction)
        {
            case Up : entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                      tile1 = gamePanel.tileManager.world[entityLeftCol][entityTopRow];
                      tile2 = gamePanel.tileManager.world[entityRightCol][entityTopRow];

                      if (tile1.collision || tile2.collision)
                          entity.collision = true;

                      break;

            case Down: entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                      tile1 = gamePanel.tileManager.world[entityLeftCol][entityBottomRow];
                      tile2 = gamePanel.tileManager.world[entityRightCol][entityBottomRow];

                      if (tile1.collision || tile2.collision)
                        entity.collision = true;

                      break;

            case Left: entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                       tile1 = gamePanel.tileManager.world[entityLeftCol][entityTopRow];
                       tile2 = gamePanel.tileManager.world[entityLeftCol][entityBottomRow];

                       if (tile1.collision || tile2.collision)
                           entity.collision = true;

                       break;

            case Right: entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                        tile1 = gamePanel.tileManager.world[entityRightCol][entityTopRow];
                        tile2 = gamePanel.tileManager.world[entityRightCol][entityBottomRow];

                        if (tile1.collision || tile2.collision)
                            entity.collision = true;

                        break;
        }
    }

    public BaseObject checkObject(Player player)
    {
        BaseObject result = null;

        for (BaseObject item: gamePanel.items)
        {
            if (item == null)
                continue;

            player.solidArea.x = player.worldPosition.x + player.solidArea.x;
            player.solidArea.y = player.worldPosition.y + player.solidArea.y;

            item.solidArea.x = item.worldPosition.x + item.solidArea.x;
            item.solidArea.y = item.worldPosition.y + item.solidArea.y;

            switch (player.direction)
            {
                case Up : player.solidArea.y -= player.speed; break;
                case Down : player.solidArea.y += player.speed; break;
                case Left : player.solidArea.x -= player.speed; break;
                case Right : player.solidArea.x += player.speed; break;
            }

            result = helper(player, item);

            player.solidArea.x = player.solidAreaDefaultPosition.x;
            player.solidArea.y = player.solidAreaDefaultPosition.y;

            item.solidArea.x = item.solidAreaDefaultPosition.x;
            item.solidArea.y = item.solidAreaDefaultPosition.y;

            if (result != null)
                break;
        }

        return result;
    }
    private BaseObject helper(Player player, BaseObject item)
    {
        BaseObject result = null;

        if (player.solidArea.intersects(item.solidArea))
        {
            if (item.collision)
                player.collision = true;

            result = item;
        }

        return result;
    }
}
