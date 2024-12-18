package org.example.enemies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.items.Item;
import org.example.items.PoolOfItems;
import org.example.view.GamePanel;
import org.example.entity.Entity;
import org.example.entity.Player;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Skeleton extends Entity {


    private boolean movingRight = true;
    private final int moveDistance = 170;
    private Player player;

    private static final long ATTACK_DELAY = 1600; // Delay before attack in milliseconds
    private static final int ANIMATION_INTERVAL = 400; // Interval between animation frames in milliseconds

    private long playerSeenTime = -1; // Time when the player was first seen
    private long lastAnimationTime = 0; // Time when the last animation frame was updated

    private int walkFrameIndex = 0;
    private int attackFrameIndex = 0;

    public boolean move = false;
    public Image[] images;


    public Skeleton(int health, int damage, GamePanel gp, Boolean move, int startX, Player player) throws IOException {
        this.damage = damage;
        this.gp = gp;
        this.health = health;
        this.startX = startX;
        this.player = player;
        this.attackRange = 100;
        speed = 2;
        this.move = move;
        images = new Image[9]; // Initialize the images array
        getImages();
        img = images[0];
    }

    public void getImages() throws IOException {
        images[0] = new Image(getClass().getResourceAsStream("/Skeleton/00.png"));
        images[1] = new Image(getClass().getResourceAsStream("/Skeleton/01.png"));
        images[2] = new Image(getClass().getResourceAsStream("/Skeleton/02.png"));
        images[3] = new Image(getClass().getResourceAsStream("/Skeleton/03.png"));
        images[4] = new Image(getClass().getResourceAsStream("/Skeleton/04.png"));
        images[5] = new Image(getClass().getResourceAsStream("/Skeleton/Attack/00.png"));
        images[6] = new Image(getClass().getResourceAsStream("/Skeleton/Attack/10.png"));
        images[7] = new Image(getClass().getResourceAsStream("/Skeleton/Attack/20.png"));
        images[8] = new Image(getClass().getResourceAsStream("/Skeleton/Attack/30.png"));
    }

    @Override
    public void update() throws IOException {
        if(!gp.pause) {
            if (this.move) {
                move();
            }
            checkPlayerInRangeAndAttack();
        }
    }




    public void move() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAnimationTime >= ANIMATION_INTERVAL) {
            walkFrameIndex = (walkFrameIndex + 1) % 4; // Loop through walk frames 0-4
            img = images[walkFrameIndex];
            lastAnimationTime = currentTime;
        }

        if (movingRight) {
            direction = "right";
            x += speed;
            if (x >= startX + moveDistance) {
                movingRight = false;
            }
        } else {
            direction = "left";
            x -= speed;
            if (x <= startX) {
                x = startX;
                movingRight = true;
            }
        }
    }

    private void checkPlayerInRangeAndAttack() {
        if (player == null) {
            return;
        }

        double distanceToPlayer = Math.sqrt(Math.pow(player.x - this.x, 2) + Math.pow(player.y - this.y, 2));

        if (distanceToPlayer <= this.attackRange) {
            // Determine the direction towards the player
            if (player.x > this.x) {
                direction = "right";
            } else {
                direction = "left";
            }

            move = false;
            if (playerSeenTime == -1) {
                playerSeenTime = System.currentTimeMillis(); // Mark the time when the player is first seen
            }

            // Check if enough time has passed since the player was seen
            if (System.currentTimeMillis() - playerSeenTime >= ATTACK_DELAY) {
                attackPlayer();
                playerSeenTime = -1; // Reset the player seen time
                move = true;
            } else {
                updateAttackAnimation(); // Update attack animation during the delay
            }
        } else {
            playerSeenTime = -1; // Reset the player seen time if the player is out of range
            move = true;
        }
    }


    private void updateAttackAnimation() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAnimationTime  >= ANIMATION_INTERVAL) {
            attackFrameIndex = 5 + ((attackFrameIndex - 5 + 1) % 4); // Loop through attack frames 5-8
            img = images[attackFrameIndex];
            lastAnimationTime = currentTime;
        }
    }

    private void attackPlayer() {
        if(Objects.equals(player.direction, "right"))
            player.x-=50;
        else
            player.x+=50;
        // Image img = player.img;
        player.img=null;
//        player.img= img;
        player.health -= this.damage;
        System.out.println("Skeleton attacked the player! Player health: " + player.health);
    }
}
