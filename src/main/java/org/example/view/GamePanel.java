package org.example.view;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.EnemyManager.EnemyManager;
import org.example.Logic.*;
import org.example.Menu.OptionsWindow;
import org.example.entity.Entity;
import org.example.entity.Player;
import org.example.items.Item;
import org.example.tiles.TileManager;

import org.example.objects.ObjectsManager;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends Pane {

    public boolean pause=false;

    public int currentLevel=1;
    private final int original_tile_size = 40;
    private final int scale = 3;
    public int tile_size = original_tile_size * scale;
    public final int row = 10;
    public final int column = 12;
    private final int screen_width = column * tile_size;
    private final int screen_height = row * tile_size;
    private final int FPS = 60;
    public static double nano = 1000000000;

    public CollisonChecker checker = new CollisonChecker(this);
    public AttackHandler attackHandler = new AttackHandler(this);

    MouseHandler mouseHandler = new MouseHandler();
    private OptionsWindow optionWindow;

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }


    KeyHandler keyHandler;
    public  ArrayList<Item> itemsOnGround = new ArrayList<>(); // List to track items on the ground

    private double fps;

    public TileManager tileManager = new TileManager(this);
    public ObjectsManager objectManager = new ObjectsManager(this);

    public InterationHandler interationHandler = new InterationHandler(this);
    Player player;
    public EnemyManager enemyManager;
    public void setInstanceF(KeyHandler keyHandler) throws IOException {
        this.keyHandler = keyHandler;
        optionWindow= new OptionsWindow();
        player= new Player(this, keyHandler, mouseHandler, optionWindow);
        enemyManager = new EnemyManager(this,player);
        optionWindow.setEnemyManager(enemyManager);
    }

    ArrayList<GraphicsContext> obj = new ArrayList<>();

    private Canvas canvas;
    private AnimationTimer gameLoop;

    public GamePanel() throws IOException {
        this.setPrefSize(screen_width, screen_height);
        this.setStyle("-fx-background-color: darkgray;");

        canvas = new Canvas(screen_width, screen_height);
        this.getChildren().add(canvas);


        this.setOnMousePressed(this::handleMousePressed);
        this.setOnMouseReleased(this::handleMouseReleased);

        this.setFocusTraversable(true);




        startGame();
    }
    public void nextLevel() throws IOException {
        currentLevel++;
        if (currentLevel ==3) {
            endGame();
        } else {
            String levelSuffix = "map" + currentLevel;
            tileManager.setMap("/maps/" + levelSuffix + ".txt");
            enemyManager.setMap("/enemy_maps/" + levelSuffix + ".txt");
            objectManager.setMap("/objects/Object" + levelSuffix + ".txt");
            tileManager.LoadMap();
            objectManager.LoadMap();
            enemyManager.loadEnemy();
            player.x = 100;
            player.y= 100;
        }
    }

    public void SetLevel() throws IOException {
        if (currentLevel ==3) {
            endGame();
        } else {
            String levelSuffix = "map" + currentLevel;
            tileManager.setMap("/maps/" + levelSuffix + ".txt");
            objectManager.setMap("/objects/Object" + levelSuffix + ".txt");
            tileManager.LoadMap();
            objectManager.LoadMap();
        }
    }
    private void endGame() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Congrats!! You finished the game!");
            alert.showAndWait();
            Platform.exit();
        });
    }
    private void handleKeyPressed(KeyEvent event) {
        keyHandler.handle(event);
    }
    private void handleKeyReleased(KeyEvent event) {
        keyHandler.handle(event);
    }

    private void handleMousePressed(MouseEvent event) {
        mouseHandler.handle(event);
    }

    private void handleMouseReleased(MouseEvent event) {
        mouseHandler.handle(event);
    }

    public void startGame() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double elapsedSeconds = (now - lastUpdate) / nano;
                if (elapsedSeconds >= 1.0 / FPS) {
                    try {
                        update();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    render();
                    lastUpdate = now;
                }

                // Calculate FPS
                fps = 1 / elapsedSeconds;
            }
        };
        gameLoop.start();
    }

    public void update() throws IOException {
        player.update();
        checkPlayerItemCollisions();
        for (int col = 0; col < column; col++) {
            for (int row = 0; row < this.row; row++) {
                if (enemyManager.enemiesMap[col][row] != null) {
                    Entity enemy = enemyManager.enemiesMap[col][row];
                    enemy.update();
                    int enX = enemy.x;

                    // Calculate the new row based on the enemy's y position
                    int newCol = enX / tile_size;

                    // If the row has changed, update the enemiesMap
                    if (newCol != col) {
                      //  System.out.println("old:"+col+" new:"+newCol);
                        enemyManager.enemiesMap[col][row] = null;  // Remove from old position// Check bounds
                        enemyManager.enemiesMap[newCol][row] = enemy;  // Add to new position
                       // System.out.println( enemyManager.enemiesMap[newCol][row].x);

                    }
                }
            }
        }
    }



    public void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, screen_width, screen_height);
        tileManager.draw(gc);
        objectManager.draw(gc);
        enemyManager.draw(gc);
        player.draw(gc);
        for (Item item : itemsOnGround) {
            gc.drawImage(item.getImage(), item.getX(), item.getY(), tile_size/4, tile_size/4);
        }

        // Display player stats
        gc.setFill(Color.WHITE);
        gc.fillText("FPS: " + (int) fps, 10, 20);

        // Display player stats at top right corner
       // gc.setFill(Color.WHITE);
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Set font size to 16
        gc.fillText("Health: " + player.health + "/" + player.MAX_HEALTH, screen_width - 150, 20);
        gc.fillText("Damage: " + player.damage, screen_width - 150, 40);

    }


    private void checkPlayerItemCollisions() {
        ArrayList<Item> pickedUpItems = new ArrayList<>();
        for (Item item : itemsOnGround) {
            double distance = Math.sqrt(Math.pow(player.x - item.getX(), 2) + Math.pow(player.y - item.getY()+90, 2));
            if (distance < 30) {
                player.addItemToInventory(item);
                pickedUpItems.add(item);
            }
        }
        itemsOnGround.removeAll(pickedUpItems);
    }

}
