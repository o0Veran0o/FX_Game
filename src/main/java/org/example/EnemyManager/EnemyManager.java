package org.example.EnemyManager;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.entity.Player;
import org.example.view.GamePanel;
import org.example.enemies.Skeleton;
import org.example.entity.Entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class EnemyManager {
    GamePanel gp;
    public Entity[] enemies;
    public int[][] mapTileNumber;

    public Entity[][] enemiesMap;

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String map;
Player player;
    public EnemyManager(GamePanel gp, Player player) throws IOException {
        this.gp = gp;
        this.player= player;
        enemies = new Entity[10];
        map="/enemy_maps/map1.txt";
        mapTileNumber = new int[gp.column][gp.row];
        enemiesMap = new Entity[gp.column][gp.row];
        loadEnemy();

    }

    public void loadEnemy() throws IOException {
        try {
            InputStream is = getClass().getResourceAsStream(map);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.column && row < gp.row) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while (col < gp.column) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumber[col][row] = num;
                    col++;
                }
                if (col == gp.column) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeEnemies();
    }
    public void removeEnemy(Entity enemyToRemove) {
        for (int col = 0; col < gp.column; col++) {
            for (int row = 0; row < gp.row; row++) {
                if (enemiesMap[col][row] == enemyToRemove) {
                    enemiesMap[col][row] = null;
                    System.out.println("ASD");
                    return; // Exit the method once the enemy is found and removed
                }
            }
        }
    }


    public void initializeEnemies() throws IOException {

        for (Entity[] entities : enemiesMap) {
            Arrays.fill(entities, null);
        }
        int col = 0;
        int row = 0;
        int x = 0, y = 0;
        while (col < gp.column && row < gp.row) {
            int tileNumber = mapTileNumber[col][row];
            if (tileNumber != 0) {
                enemiesMap[col][row] = new Skeleton(10, 2, gp, true,x, player);

                enemiesMap[col][row].x = x;
                enemiesMap[col][row].y = y;
            } else {
                enemiesMap[col][row] = null;
            }
            col++;
            x += gp.tile_size;
            if (col == gp.column) {
                col = 0;
                x = 0;
                row++;
                y += gp.tile_size;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        int col = 0;
        int row = 0;
        while (col < gp.column && row < gp.row) {
            if (enemiesMap[col][row] != null) {
                Image enemyImage = enemiesMap[col][row].img;
                String direction = enemiesMap[col][row].direction;
                int enemyX = enemiesMap[col][row].x;
                int enemyY = enemiesMap[col][row].y;

                if (direction.equals("left")) {
                    gc.drawImage(enemyImage, enemyX + gp.tile_size, enemyY, -gp.tile_size, gp.tile_size);
                } else {
                    gc.drawImage(enemyImage, enemyX, enemyY, gp.tile_size, gp.tile_size);
                }
            }
            col++;
            if (col == gp.column) {
                col = 0;
                row++;
            }
        }
    }

}
