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

    public int checkObject(Entity entity, boolean player)
    {
        int result = -1;

        for (BaseObject item: gamePanel.items)
        {
            if (item == null)
                continue;

            entity.solidArea.x = entity.worldPosition.x + entity.solidArea.x;
            entity.solidArea.y = entity.worldPosition.y + entity.solidArea.y;

            item.solidArea.x = item.worldPosition.x + item.solidArea.x;
            item.solidArea.y = item.worldPosition.y + item.solidArea.y;

            switch (entity.direction)
            {
                case Up : entity.solidArea.y -= entity.speed;
                    if (entity.solidArea.intersects(item.solidArea))
                    {
                        System.out.println("Up");
                    }
                    break;

                case Down : entity.solidArea.y += entity.speed;
                    if (entity.solidArea.intersects(item.solidArea))
                    {
                        System.out.println("Down");
                    }
                    break;

                case Left : entity.solidArea.x -= entity.speed;
                    if (entity.solidArea.intersects(item.solidArea))
                    {
                        System.out.println("Left");
                    }
                    break;

                case Right : entity.solidArea.x += entity.speed;
                    if (entity.solidArea.intersects(item.solidArea))
                    {
                        System.out.println("Right");
                    }
                    break;
            }

            entity.solidArea.x = entity.solidAreaDefaultPosition.x;
            entity.solidArea.y = entity.solidAreaDefaultPosition.y;

            item.solidArea.x = item.solidAreaDefaultPosition.x;
            item.solidArea.y = item.solidAreaDefaultPosition.y;
        }
        return result;
    }
}
