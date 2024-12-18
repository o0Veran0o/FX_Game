package org.example.Logic;

import org.example.entity.Entity;
import org.example.entity.Player;
import org.example.view.GamePanel;

public class AttackHandler {
    GamePanel gamePanel;

    public AttackHandler(GamePanel gp) {
        this.gamePanel = gp;
    }

    public void checkTile(Player player) {
        int playerLeftWorldX = (int) (player.x + player.solidArea.getX());
        int playerRightWorldX = (int) (player.x + player.solidArea.getX() + player.solidArea.getWidth());
        //int playerTopWorldY = (int) (player.y + player.solidArea.getY());
        int playerBottomWorldY = (int) (player.y + player.solidArea.getY() + player.solidArea.getHeight());

        int playerColumLeft = playerLeftWorldX / gamePanel.tile_size;
        int playerColumRight = playerRightWorldX / gamePanel.tile_size;
        int playerRow = playerBottomWorldY / gamePanel.tile_size;

        int startCol, endCol;
        switch (player.direction) {
            case "left":
                startCol = ((playerLeftWorldX - player.attackRange) / gamePanel.tile_size) -1;
                endCol = playerColumLeft;
              System.out.println(startCol+"   "+ endCol);
                break;
            case "right":
                startCol = playerColumRight-1;
                endCol = ( playerRightWorldX + player.attackRange) / gamePanel.tile_size;
                System.out.println(startCol+"     "+ endCol);
                break;
            default:
                startCol = playerColumLeft;
                endCol = playerColumLeft;
                break;
        }

        for (int col = startCol; col <= endCol; col++) {
            if (col < 0 || col >= gamePanel.enemyManager.enemiesMap.length) {
                continue;  // Skip out-of-bounds columns
            }
            for (int row = playerRow - 1; row <= playerRow + 1; row++) {  // Check 3 rows: top, center, bottom
                if (row < 0 || row >= gamePanel.enemyManager.enemiesMap.length) {
                    continue;  // Skip out-of-bounds rows
                }
                //System.out.println("col"+col+" row:" +row);
                Entity enemy = gamePanel.enemyManager.enemiesMap[col][row];
//                if(enemy!=null)
//                   // System.out.println(enemy.x+ "   "+ player.x);
                if (enemy != null) {

                    double distance = Math.abs(player.x - enemy.x);
                    if (distance <= 2*player.attackRange) {
                        System.out.println("HIT!!");

                        if (enemy.health > 0) {
                            enemy.health -= player.damage;
                            enemy.img=null;
                            if (enemy.health <= 0) {
                                gamePanel.enemyManager.enemiesMap[col][row].death();
                            }
                        }
                    }
                }
            }
        }
    }
}
