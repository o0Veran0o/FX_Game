package org.example.Menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.view.GamePanel;
import org.example.Logic.KeyHandler;
import java.io.IOException;

public class MenuScene {
    private Stage stage;
    private Scene menuScene;
    private Scene optionsScene; // New scene for options

    KeyHandler keyHandler = new KeyHandler();

    public MenuScene(Stage stage) {

        this.stage = stage;
        initializeMenu();
        initializeOptions();
    }

    private void initializeMenu() {
        // Load the background image
        Image backgroundImage = new Image("tiles/background_1.jpg");

        // Create background image
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );

        // Set up the buttons
        Button startNewGameButton = new Button("Start New Game");
        Button optionsButton = new Button("Options");

        // Set button sizes and font
        startNewGameButton.setPrefSize(200, 50);
        optionsButton.setPrefSize(200, 50);
        startNewGameButton.setStyle("-fx-font-size: 16px;");
        optionsButton.setStyle("-fx-font-size: 16px;");

        startNewGameButton.setOnAction(e -> {
            try {
                startNewGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        optionsButton.setOnAction(e -> showOptions());

        // Create the layout and set the background
        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.getChildren().addAll(startNewGameButton, optionsButton);
        menuLayout.setBackground(new Background(background));

        menuScene = new Scene(menuLayout, 800, 600);  // Adjust the size as needed
    }

    private void initializeOptions() {
        // Load the background image for the options menu
        Image optionsBackgroundImage = new Image("tiles/background_2.png");

        // Create background image for the options menu
        BackgroundImage optionsBackground = new BackgroundImage(
                optionsBackgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );

        // Create a GridPane for the options table
        GridPane optionsGrid = new GridPane();
        optionsGrid.setAlignment(Pos.CENTER);
        optionsGrid.setHgap(10);
        optionsGrid.setVgap(10);

        // Set the background of the options menu
        optionsGrid.setBackground(new Background(optionsBackground));

        // Add text instructions to the grid
        Text[] instructions = {
                createInstructionText("A - Move Left"),
                createInstructionText("D - Move Right"),
                createInstructionText("Space - Jump"),
                createInstructionText("Mouse One - Attack"),
                createInstructionText("E - Interact"),
                createInstructionText("I - Open Inventory")
        };

        for (int i = 0; i < instructions.length; i++) {
            optionsGrid.add(instructions[i], 0, i);
        }

        // Add a back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(menuScene)); // Go back to main menu
        backButton.setPrefSize(200, 50);
        backButton.setStyle("-fx-font-size: 16px;");

        optionsGrid.add(backButton, 0, 10);

        optionsScene = new Scene(optionsGrid, 800, 600); // Adjust the size as needed
    }

    // Helper method to create formatted instruction text
    private Text createInstructionText(String text) {
        Text instruction = new Text(text);
        instruction.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set font, weight, and size
        instruction.setFill(Color.WHITE); // Set text color
        return instruction;
    }


    private void startNewGame() throws IOException {
        GamePanel gamePanel = new GamePanel();
        gamePanel.setInstanceF(keyHandler);
        Scene scene = new Scene(gamePanel);
        scene.setOnKeyPressed(keyHandler);
        scene.setOnKeyReleased(keyHandler);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void showOptions() {
        stage.setScene(optionsScene); // Switch to options scene
    }

    public Scene getMenuScene() {
        return menuScene;
    }
}
