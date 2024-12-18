package org.example.Menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.EnemyManager.EnemyManager;
import org.example.enemies.Skeleton;
import org.example.entity.Entity;
import org.example.entity.Player;
import org.example.items.Item;
import org.example.items.PoolOfItems;
import org.example.view.GamePanel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OptionsWindow {

    EnemyManager enemyManager;

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public void display(GamePanel gp, Player player) {
        Stage window = new Stage();
        window.setTitle("Menu");

        // Create layout
        VBox layout = new VBox(10);

        // Create buttons
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");
        Button exitButton = new Button("Exit Game");

        // Add button actions
        saveButton.setOnAction(event -> save(gp, player));
        loadButton.setOnAction(event -> load(gp, player));
        exitButton.setOnAction(event -> exit(gp, window));
        window.setOnCloseRequest(event -> {
            change(gp);
            System.out.println("Close");
        });

        // Style buttons
        saveButton.setStyle("-fx-font-size: 18pt;");
        loadButton.setStyle("-fx-font-size: 18pt;");
        exitButton.setStyle("-fx-font-size: 18pt;");

        // Add buttons to layout
        layout.getChildren().addAll(saveButton, loadButton, exitButton);

        // Center buttons in layout
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Set up scene
        Scene scene = new Scene(layout, 800, 500);
        window.setScene(scene);
        window.show();
    }

    public void change(GamePanel gp) {
        gp.pause = false;
    }

    private void save(GamePanel gp, Player player) {
        // Save player data to file
        try (PrintWriter writer = new PrintWriter(new FileWriter("save.txt"))) {
            Map<String, Integer> itemMap = new HashMap<>();

            // Counting items in player's inventory
            for (Item item : player.getInventory()) {
                if (item != null) {
                    String itemName = item.name;
                    itemMap.put(itemName, itemMap.getOrDefault(itemName, 0) + 1);
                }
            }

            // Save player's inventory
            for (Map.Entry<String, Integer> entry : itemMap.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }

            // Save player's health
            writer.println(player.getHealth());
            // Save player's position
            writer.println(player.getX() + "," + player.getY());
            writer.println("level:" + gp.currentLevel);
            // Save enemy positions and health
            Entity[][] enemiesMap = enemyManager.enemiesMap;
            for (int col = 0; col < enemiesMap.length; col++) {
                for (int row = 0; row < enemiesMap[col].length; row++) {
                    Entity enemy = enemiesMap[col][row];
                    if (enemy != null) {
                        writer.println("enemy:" + col + "," + row + "," + enemy.getX() + "," + enemy.getY()  + "," + enemy.getStartX()+ "," + enemy.getHealth());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Save");
    }

    private void load(GamePanel gp, Player player) {
        try (BufferedReader reader = new BufferedReader(new FileReader("save.txt"))) {
            String line;
            player.clearInventory();

            // Clear existing enemies
            Entity[][] enemiesMap = enemyManager.enemiesMap;
            for (int col = 0; col < enemiesMap.length; col++) {
                for (int row = 0; row < enemiesMap[col].length; row++) {
                    enemiesMap[col][row] = null;
                }
            }

            // Read each line from the file
            while ((line = reader.readLine()) != null) {


                if (line.contains(":")) {
                    // If the line contains ":", it's either an item in inventory or an enemy
                    if (line.startsWith("level:")) {
                        // Load current level
                        gp.currentLevel = Integer.parseInt(line.split(":")[1]);
                        gp.SetLevel(); // Load the corresponding level map
                    }
                    else if (line.startsWith("enemy:")) {
                        String[] parts = line.split(":")[1].split(",");
                        int col = Integer.parseInt(parts[0]);
                        int row = Integer.parseInt(parts[1]);
                        int enemyX = Integer.parseInt(parts[2]);
                        int enemyY = Integer.parseInt(parts[3]);
                        int enemyStartX = Integer.parseInt(parts[4]);
                        int enemyHealth = Integer.parseInt(parts[5]);

                        // Create a new enemy skeleton and set its properties
                        Entity enemy = new Skeleton(10, 2, gp, true,enemyStartX, player); // Assuming SkeletonEnemy is a subclass of Entity
                        enemy.setX(enemyX);
                        enemy.setY(enemyY);
                        enemy.setHealth(enemyHealth);

                        enemiesMap[col][row] = enemy;
                    }
                    else {
                        String[] parts = line.split(":");
                        String itemName = parts[0];
                        int itemCountToAdd = Integer.parseInt(parts[1]);

                        Item item = PoolOfItems.findByName(itemName);

                        // Add the item to the player's inventory
                        for (int i = 0; i < itemCountToAdd; i++) {
                            player.addItemToInventory(item);
                        }
                    }
                } else if (line.contains(",")) {
                    // If the line contains ",", it's player position
                    String[] parts = line.split(",");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    player.setX(x);
                    player.setY(y);
                } else {
                    // Otherwise, it's player health
                    int health = Integer.parseInt(line);
                    player.setHealth(health);
                }
            }

            // Update the game panel
            gp.update(); // Update the game state after loading
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void exit(GamePanel gp, Stage window) {
        // Implement exit logic here
        System.out.println("Exit Game");
        gp.getScene().getWindow().hide(); // Close the game window
        window.close();
    }
}