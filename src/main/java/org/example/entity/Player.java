package org.example.entity;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.example.Menu.OptionsWindow;
import org.example.items.InventoryWindow;
import org.example.items.PoolOfItems;
import org.example.view.GamePanel;
import org.example.Logic.KeyHandler;
import org.example.Logic.MouseHandler;
import org.example.items.Item;


import javax.imageio.ImageIO;
import java.io.IOException;

public class Player extends Entity {
    private OptionsWindow optionsWindow;
    KeyHandler keyHandler;
    public long lastAttackTime;
    MouseHandler mouseHandler;

    private static final int INVENTORY_SIZE = 20;  // Define the size of the inventory

    public Item[] getInventory() {
        return inventory;
    }

    public void setInventory(Item[] inventory) {
        this.inventory = inventory;
    }

    private Item[] inventory = new Item[INVENTORY_SIZE];
    private InventoryWindow inventoryWindow;

    public int getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    private int inventoryCount = 0;
    public Image[] images;
    float targetY = 0;
    private boolean isFalling = false;
    private boolean isIdle = true;
    private long attackAnimationTime;
    private boolean isAttacking=false;

    public final int MAX_HEALTH = 12;
    private static final long ATTACK_ANIMATION_DURATION = 200;  // 0.5 seconds in milliseconds



    private boolean isJumping = false;
    private boolean isOpeningInventory = false;
    private long lastJumpTime = 0;
    public Player(GamePanel gp, KeyHandler kh, MouseHandler mc,OptionsWindow optionsWindow) throws IOException {
        this.gp = gp;
        this.optionsWindow = optionsWindow;
        inventoryWindow = new InventoryWindow(this);
        this.keyHandler = kh;
        this.mouseHandler = mc;
        solidArea = new Rectangle(20, 70, gp.tile_size - 60, gp.tile_size - 70);
        setDefault();
        images = new Image[11];
        getImages();
        img = images[0];
    }


    public void setDefault() {
        x = 100;
        y = 100;
        health = MAX_HEALTH;
        speed = 4;
        damage = 5;
        attackRange = 40;
        clearInventory(); // Clear existing inventory
        lastAttackTime = System.currentTimeMillis();
        Item[] defaultItems = PoolOfItems.initializeTestItems(); // Get default items from PoolOfItems
       // System.out.println(defaultItems[0].name);
        for (int i = 0; i < defaultItems.length; i++) {
            Item currentItem = defaultItems[i];
            addItemToInventory(currentItem);
        }
    }

    public void clearInventory() {
        for (int i = 0; i < inventoryCount; i++) {
            inventory[i] = null;
        }
        inventoryCount = 0;
        System.out.println("Inventory cleared.");
    }
    public void getImages() throws IOException {
        images[0] = new Image(getClass().getResourceAsStream("/player/00.png"));
        images[1] = new Image(getClass().getResourceAsStream("/player/10.png"));
        images[2] = new Image(getClass().getResourceAsStream("/player/20.png"));
        images[3] = new Image(getClass().getResourceAsStream("/player/30.png"));
        images[4] = new Image(getClass().getResourceAsStream("/player/40.png"));
        images[5] = new Image(getClass().getResourceAsStream("/player/Idle/00.png"));
        images[6] = new Image(getClass().getResourceAsStream("/player/Idle/11.png"));
        images[7] = new Image(getClass().getResourceAsStream("/player/Idle/20.png"));
        images[8] = new Image(getClass().getResourceAsStream("/player/Idle/30.png"));
        images[9] = new Image(getClass().getResourceAsStream("/player/Jump/20.png"));
        images[10] = new Image(getClass().getResourceAsStream("/player/Attack/40.png"));
    }

    public void openInventory() {
        System.out.println(isOpeningInventory);
        if (!isOpeningInventory) {
            System.out.println(inventoryCount);
            isOpeningInventory = true;
            inventoryWindow.show(inventory, inventoryCount);
            isOpeningInventory = false;

        }
        else {
            isOpeningInventory = false;
            inventoryWindow.hide();
        }
    }
    public void addItemToInventory(Item item) {
        if (inventoryCount < INVENTORY_SIZE) {
            inventory[inventoryCount] = item;
            inventoryCount++;
            System.out.println("Added item to inventory: " + item.name);
        } else {
            System.out.println("Inventory is full. Cannot add item: " + item.name);
        }
    }

    public void deleteItem(Item item){
        for (int i = 0; i < inventoryCount; i++) {
            if (inventory[i] == item) {
                System.arraycopy(inventory, i + 1, inventory, i, inventoryCount - i - 1);
                inventory[--inventoryCount] = null;
                break;
            }
        }
    }
    public void consumeItem(Item item) {
        switch (item.name) {
            case "Healing Potion":
                health += 5;
                if(health>MAX_HEALTH)
                    health=MAX_HEALTH;
                deleteItem(item);
                System.out.println("Consumed Healing Potion. Health increased by 5.");
                break;
            case "Speed Potion":
                speed *= 1.2; // Increase speed by 20%
                deleteItem(item);
                System.out.println("Consumed Speed Potion. Speed increased by 20%.");
                break;
            case "Damage Potion":
                damage += 5; // Increase damage by 50%
                deleteItem(item);
                System.out.println("Consumed Damage Potion. Damage increased by 50%.");
                break;
            default:
                System.out.println("Unknown potion type: " + item.name);
                break;
        }
    }

    public void jump(){
        if(collisionY){
            img= images[9];
            float jumpHeight = 140; // Define the jump height

            if(!inAir)
                targetY = y - jumpHeight; // Calculate the target Y position

            // Loop to smoothly move the character upwards
            while( (y > targetY)) {
                inAir = true;

                y -= 5; // Incrementally move upwards

                collisionY = false;
                gp.checker.checkTile(this);
                if(collisionY){
                    break;
                }
            }

            // Reached the target height
            inAir = false;
            keyHandler.jump = false;


        }
    }

    public void fall(){
        if (!collisionY && !inAir) {
            y += 4;
            isFalling=true;

            spriteCounter++;
            if (spriteCounter > 10) {
                img=images[9];
                spriteCounter = 0;
            }
        }
    }

    public void restart() throws IOException {
        setDefault();
        gp.enemyManager.initializeEnemies();
        keyHandler.restart=false;
    }

    public void movement(){
//            if (keyHandler.upPressed) {
//                direction = "up";
//
//            }
//            else if (keyHandler.downPressed) {
//                direction = "down";
//
//            }
        if (keyHandler.leftPressed) {
            direction = "left";

        }
        else if(keyHandler.rightPressed){
            direction = "right";

        }

        collisionX = false;

        gp.checker.checkTile(this);
        if (!collisionX) {
            switch (direction){
                case "left":
                    x -= speed;
                    if(isFalling){
                        x-=4;
                    }
                    if(collisionY) {
                        spriteCounter++;
                        if (spriteCounter > 5 && !inAir && !isFalling) {
                            if (spriteNum < 4) {
                                spriteNum++;
                                img = images[spriteNum];

                            } else {
                                spriteNum = 0;
                                img = images[spriteNum];
                            }
                            spriteCounter = 0;
                        }
                    }
                    break;
                case "right":
                    x += speed;
                    if(isFalling){
                        x+=4;
                    }
                    if(collisionY) {
                        spriteCounter++;
                        if (spriteCounter > 5 && !inAir && !isFalling) {
                            if (spriteNum < 4) {
                                spriteNum++;
                                img = images[spriteNum];

                            } else {
                                spriteNum = 0;
                                img = images[spriteNum];


                            }
                            spriteCounter = 0;
                        }
                    }
                    break;
            }
        }
    }

    public void idle(){
        if (!inAir) {
            //img = images[5];
            if (spriteNum > 0 && spriteNum < 5) {
                spriteNum = 5;
                img = images[spriteNum];
                spriteCounter = 0;
            }
            spriteCounter++;
            if (spriteCounter > 20) {
                if (spriteNum < 6) {
                    spriteNum++;
                    img = images[spriteNum];

                } else {
                    spriteNum = 5;
                    img = images[spriteNum];
                }
                spriteCounter = 0;
            }
        }
    }

    private void openOptionsWindow() {
        optionsWindow.display(gp, this);
    }
    @Override
    public void update() throws IOException {
        if (keyHandler.OptionMenu) {
            openOptionsWindow();
            this.gp.pause = true;
            keyHandler.OptionMenu = false;
        }
        if (!gp.pause) {
            if (keyHandler.restart || health <= 0) {
                restart();
            }
            if (keyHandler.inventoryPressed) {
                System.out.println(keyHandler.inventoryPressed);
                keyHandler.inventoryPressed = false; // Reset the key released flag
                openInventory();

            }


            collisionX = false;
            collisionY = false;
            isFalling = false;
            gp.interationHandler.checkTile(this);
            gp.checker.checkTile(this);
            //System.out.println(collisionX);
            if (!isAttacking) {
                if (keyHandler.jump) {
                    // Check if enough time has passed since the last jump
                    if (System.currentTimeMillis() - lastJumpTime >= 1000) {
                        // It's time to jump
                        jump();
                        // Update the last jump time to the current time
                        lastJumpTime = System.currentTimeMillis();
                    }
                }


                fall();
                if (keyHandler.upPressed || keyHandler.rightPressed || keyHandler.leftPressed || keyHandler.downPressed) {
                    movement();
                } else {
                    idle();
                }
            }
            if (keyHandler.interationPressed) {
                gp.interationHandler.pressed_button=true;
                gp.interationHandler.checkTile(this);
                keyHandler.interationPressed = false;
                gp.interationHandler.pressed_button=false;
                System.out.println("Pressed E");
            }
            if (mouseHandler.attack) {
                // Check if enough time has passed since the last attack
                if (System.currentTimeMillis() - lastAttackTime >= 1000) {
                    // Start attack
                    gp.attackHandler.checkTile(this);
                    img = images[10];  // Set attack image
                    isAttacking = true;
                    attackAnimationTime = System.currentTimeMillis();
                    lastAttackTime = System.currentTimeMillis();
                }
                // Reset the attack flag
                mouseHandler.attack = false;
            }

            // Check if the attack animation should end
            if (isAttacking && System.currentTimeMillis() - attackAnimationTime >= ATTACK_ANIMATION_DURATION) {
                // End attack animation
                isAttacking = false;
                img = images[0];  // Reset to the default image or whatever image is appropriate
            }

        }
    }
}
