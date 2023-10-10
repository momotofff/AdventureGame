package main;

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

    public void checkTile(Player player)
    {
        int entityLeftCol = player.areaCollision.x / gamePanel.tileSize;
        int entityTopRow = player.areaCollision.y / gamePanel.tileSize;
        int entityRightCol = (player.areaCollision.x + player.areaCollision.width) / gamePanel.tileSize;
        int entityBottomRow = (player.areaCollision.y + player.areaCollision.height)/ gamePanel.tileSize;

        Tile tile1, tile2;

        switch (player.direction)
        {
            case Up :
                entityTopRow = (player.areaCollision.y - player.speed) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.world[entityLeftCol][entityTopRow];
                tile2 = gamePanel.tileManager.world[entityRightCol][entityTopRow];

                if (tile1.collision || tile2.collision)
                    player.collision = true;

                break;

            case Down:
                entityBottomRow = (player.areaCollision.y + player.areaCollision.height) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.world[entityLeftCol][entityBottomRow];
                tile2 = gamePanel.tileManager.world[entityRightCol][entityBottomRow];

                if (tile1.collision || tile2.collision)
                    player.collision = true;

                break;

            case Left:
                entityLeftCol = (player.areaCollision.x - player.speed) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.world[entityLeftCol][entityTopRow];
                tile2 = gamePanel.tileManager.world[entityLeftCol][entityBottomRow];

                if (tile1.collision || tile2.collision)
                    player.collision = true;

                break;

            case Right:
                entityRightCol = (player.areaCollision.x + player.areaCollision.width) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.world[entityRightCol][entityTopRow];
                tile2 = gamePanel.tileManager.world[entityRightCol][entityBottomRow];

                if (tile1.collision || tile2.collision)
                    player.collision = true;

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

            result = helper(player, item);

            if (result != null)
                break;
        }

        return result;
    }

    private BaseObject helper(Player player, BaseObject item)
    {
        BaseObject result = null;

        if (player.areaCollision.intersects(item.areaCollision))
        {
            if (item.collision)
                player.collision = true;

            result = item;
        }

        return result;

    }
}
