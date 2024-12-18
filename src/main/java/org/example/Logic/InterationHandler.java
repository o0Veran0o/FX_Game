package org.example.Logic;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.entity.Entity;
import org.example.entity.Player;
import org.example.items.Item;
import org.example.items.PoolOfItems;
import org.example.objects.Objects;
import org.example.view.GamePanel;

import java.io.IOException;
import java.util.Random;

public class InterationHandler {
    public boolean pressed_button;
    GamePanel gamePanel;
    private Text interactionText; // Track the currently displayed interaction text


    private boolean interactNear;


    public InterationHandler(GamePanel gp) {
        this.gamePanel = gp;
    }

    public void checkTile(Player player) throws IOException {
        interactNear= false;
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
                Objects object = gamePanel.objectManager.mapObjectNumber[col][row];
//                if(enemy!=null)
//                   // System.out.println(enemy.x+ "   "+ player.x);
                if (object!=null) {
                    double distance = Math.abs(player.x - object.x);
                    if (distance <= 2*player.attackRange) {
                        System.out.println("Interact!!");
                        if(object.name=="closed chest"){
                            if(!pressed_button) {
                                displayInteractText("Press E to interact with the chest", object.x, object.y);
                                interactNear = true;
                            }
                            else{
                            for (int i = 0; i < player.getInventoryCount(); i++) {
                                if (player.getInventory()[i].name == "Key to Chest") {
                                    System.out.println("Chest is opened");
                                    player.deleteItem(player.getInventory()[i]);
                                    for (int j = 0; j < 3; j++) {
                                        player.addItemToInventory(getRandomItem());
                                    }
                                    gamePanel.objectManager.mapObjectNumber[col][row].img = new Image(getClass().getResourceAsStream("/objects/chest_opened.png"));
                                    gamePanel.objectManager.mapObjectNumber[col][row].name = "Opened Chest";
                                    break;
                                }
                            }
                            }
                        }
                       else if(object.name=="closed door"){
                            for (int i = 0; i < player.getInventoryCount(); i++) {
                                if(!pressed_button) {
                                    displayInteractText("Press E to interact with the door", object.x, object.y);
                                    interactNear= true;
                                }
                                else {
                                    if (player.getInventory()[i].name == "Key to Door") {
                                        System.out.println("Door is opened");
                                        player.deleteItem(player.getInventory()[i]);
                                        gamePanel.nextLevel();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!interactNear) {
            clearInteractText();
        }
    }

    private void clearInteractText() {
        // Remove the interaction text if exists
        if (interactionText != null) {
            gamePanel.getChildren().remove(interactionText);
            interactionText = null;
        }
    }
    private void displayInteractText(String message, double x, double y) {
        // Clear the previous interaction text if exists
        clearInteractText();

        // Create a Text object to display the message
        interactionText = new Text(message);

        // Set the position of the text
        interactionText.setX(x - 50); // Adjust position as needed
        interactionText.setY(y - 20); // Adjust position as needed

        // Set the font and color of the text
        interactionText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        interactionText.setFill(Color.WHITE);

        // Add the text to the game panel
        gamePanel.getChildren().add(interactionText);
    }
    private Item getRandomItem() {
        // Get the array of test items from the PoolOfItems class
        Item[] testItems = PoolOfItems.initializeTestItems();

        // Generate a random index to select a random item from the array
        Random random = new Random();
        int randomIndex = random.nextInt(testItems.length);
        if(testItems[randomIndex].name!="Key to Chest") {
            // Return the randomly selected item
            return testItems[randomIndex];
        }
        else
            return testItems[randomIndex-1];
    }

}
