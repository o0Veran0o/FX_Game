package org.example.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.items.Item;
import org.example.items.PoolOfItems;
import org.example.view.GamePanel;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public abstract class Entity  {
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int x,y;


    public GamePanel gp;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if(this.health<=0){
            death();
        }
    }
    public int startX;
    public int health, damage;
    public int speed;
    public int attackRange;
    public String direction="right";
    public Image img;
    public int spriteCounter=0;
    public int spriteNum=0;
    public boolean inAir=false;

    public Rectangle solidArea;
    public boolean collisionX = false;

    public boolean collisionY = false;
    private Item droppedItem;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void draw(GraphicsContext gc) {
        int imageWidth = gp.tile_size;
        int imageHeight = gp.tile_size;

        if (direction != null) {
            switch (direction) {
                case "left":
                    gc.drawImage(img, 0, 0, imageWidth, imageHeight, x + imageWidth, y, -imageWidth, imageHeight);
                    break;
                case "right":
                    gc.drawImage(img, 0, 0, imageWidth, imageHeight, x-40, y, imageWidth, imageHeight);
                    break;
                // case "up":
                //     gc.drawImage(img, x, y, imageWidth, imageHeight, 0, 0, imageWidth, imageHeight);
                //     break;
                // case "down":
                //     gc.drawImage(img, x, y, imageWidth, imageHeight, 0, 0, imageWidth, imageHeight);
                //     break;
            }
        }

//        gc.setStroke(Color.RED); // set stroke color to red for visibility
//        gc.strokeRect(x + solidArea.getX(), y + solidArea.getY(), solidArea.getWidth(), solidArea.getHeight());
    }


    public abstract void update() throws IOException;
    public void takeDamge(int damage){
        this.health-=damage;
    }
    public void die(){
        this.health-=damage;
    }
    public void attack(){

    }



    public void death() {
        // Remove the current enemy from the game panel's list of enemies

        gp.enemyManager.removeEnemy(this);
        // Generate a random item
        Item randomItem = getRandomItem();

        randomItem.setX(x);
        randomItem.setY(y+90);
        droppedItem= randomItem;
        // Spawn the random item at the current position of the enemy
        gp.itemsOnGround.add(randomItem);
    }

    private Item getRandomItem() {
        // Get the array of test items from the PoolOfItems class
        Item[] testItems = PoolOfItems.initializeTestItems();

        // Generate a random index to select a random item from the array
        Random random = new Random();
        int randomIndex = random.nextInt(testItems.length);

        // Return the randomly selected item
        return testItems[randomIndex];
    }
}
