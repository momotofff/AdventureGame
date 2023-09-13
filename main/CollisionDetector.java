package main;

import entity.Entity;
import tile.Tile;

import java.awt.*;

public class CollisionDetector
{
    GamePanel gamePanel;

    public CollisionDetector(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void detect(Entity entity)
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
}
