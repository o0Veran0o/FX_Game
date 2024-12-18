package org.example.Logic;

import org.example.entity.Entity;
import org.example.view.GamePanel;

public class CollisonChecker {
    GamePanel gamePanel;

    public CollisonChecker(GamePanel gp) {
        this.gamePanel = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = (int) (entity.x + entity.solidArea.getX());
        int entityRightWorldX = (int) (entity.x + entity.solidArea.getX() + entity.solidArea.getWidth());
        int entityTopWorldY = (int) (entity.y + entity.solidArea.getY());
        int entityBottomWorldY = (int) (entity.y + entity.solidArea.getY() + entity.solidArea.getHeight());

        int entityColumLeft = entityLeftWorldX / (gamePanel.tile_size);
        int entityColumRight = entityRightWorldX / (gamePanel.tile_size);
        int entityRowTop = entityTopWorldY / gamePanel.tile_size;
        int entityRowBottom = entityBottomWorldY / gamePanel.tile_size;
        int tile1, tile2;
        int obj1;
        switch (entity.direction) {
//            case "up":
//                entityRowTop= (entityTopWorldY - entity.speed )/ gamePanel.tile_size;
//                tile1= gamePanel.tileManager.mapTileNumber[entityColumLeft][entityRowTop];
//                tile2= gamePanel.tileManager.mapTileNumber[entityColumRight][entityRowTop];
//                if(gamePanel.tileManager.tile[tile1].collision||gamePanel.tileManager.tile[tile2].collision ){
//                    entity.collisionY=true;
//                }
//                break;
//            case "down":
//
//                }
//                break;
            case "left":
                entityColumLeft = (entityLeftWorldX - entity.speed) / gamePanel.tile_size;
                tile1 = gamePanel.tileManager.mapTileNumber[entityColumLeft][entityRowTop];
                tile2 = gamePanel.tileManager.mapTileNumber[entityColumLeft][entityRowBottom];

//                obj1 = gamePanel.objectManager.mapObjectNumber[entityColumLeft][entityRowTop];

                    if (gamePanel.tileManager.tile[tile1].collision || gamePanel.tileManager.tile[tile2].collision) {
                        entity.collisionX = true;
                    }
                else {
                    if (gamePanel.tileManager.tile[tile1].collision || gamePanel.tileManager.tile[tile2].collision) {
                        entity.collisionX = true;
                    }
                }
                break;
            case "right":
                entityColumRight = (entityRightWorldX + entity.speed) / gamePanel.tile_size;
                tile1 = gamePanel.tileManager.mapTileNumber[entityColumRight][entityRowTop];
                tile2 = gamePanel.tileManager.mapTileNumber[entityColumRight][entityRowBottom];
//                obj1 = gamePanel.objectManager.mapObjectNumber[entityColumLeft][entityRowTop];

                    if (gamePanel.tileManager.tile[tile1].collision || gamePanel.tileManager.tile[tile2].collision ) {
                        entity.collisionX = true;
                    }
                else {
                    if (gamePanel.tileManager.tile[tile1].collision || gamePanel.tileManager.tile[tile2].collision) {
                        entity.collisionX = true;
                    }
                    break;
                }
        }

                entityRowBottom = (entityBottomWorldY + entity.speed) / (gamePanel.tile_size);
                tile1 = gamePanel.tileManager.mapTileNumber[entityColumLeft][entityRowBottom];
                tile2 = gamePanel.tileManager.mapTileNumber[entityColumRight][entityRowBottom];

                if (entity.collisionX) {
                    if (gamePanel.tileManager.tile[tile1].collision && gamePanel.tileManager.tile[tile2].collision) {
                        entity.collisionY = true;
                    }
                } else {
                    if (gamePanel.tileManager.tile[tile1].collision || gamePanel.tileManager.tile[tile2].collision) {
                        entity.collisionY = true;
                    }
                }

                if (entity.inAir) {
                    entityRowTop = (entityTopWorldY - 1) / gamePanel.tile_size;
                    tile1 = gamePanel.tileManager.mapTileNumber[entityColumLeft][entityRowTop];
                    tile2 = gamePanel.tileManager.mapTileNumber[entityColumRight][entityRowTop];
                    if (gamePanel.tileManager.tile[tile1].collision || gamePanel.tileManager.tile[tile2].collision) {
                        entity.collisionY = true;
                    }
                }
        }
    }

