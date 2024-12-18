package org.example.items;

import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class InventoryWindow {
    private Stage stage;
    private final ListView<ItemDisplay> inventoryList;

    Player player;
    public InventoryWindow(Player player) {
        stage = new Stage();
        inventoryList = new ListView<>();

        this.player = player;
        VBox layout = new VBox(10);
        layout.getChildren().add(inventoryList);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Inventory");

        // Set custom cell factory
        inventoryList.setCellFactory(new Callback<ListView<ItemDisplay>, ListCell<ItemDisplay>>() {
            @Override
            public ListCell<ItemDisplay> call(ListView<ItemDisplay> param) {
                return new ListCell<ItemDisplay>() {
                    @Override
                    protected void updateItem(ItemDisplay item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            HBox hBox = new HBox(10);
                            ImageView imageView = new ImageView(item.getImage());
                            imageView.setFitWidth(32);
                            imageView.setFitHeight(32);
                            VBox vBox = new VBox();
                            Text nameText = new Text(item.getName());
                            Text descriptionText = new Text(item.getDescription());
                            Text countText = new Text("Count: " + item.getCount());
                            vBox.getChildren().addAll(nameText, descriptionText, countText);
                            hBox.getChildren().addAll(imageView, vBox);
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });
    }

    public void show(Item[] inventory, int inventoryCount) {
        inventoryList.getItems().clear();

        // Use a HashMap to count occurrences of each item
        Map<String, Integer> itemCounts = new HashMap<>();
        Map<String, String> itemDescriptions = new HashMap<>();
        Map<String, Image> itemImages = new HashMap<>();

        for (int i = 0; i < inventoryCount; i++) {
            if(inventory[i]!=null) {
                Item item = inventory[i];
                itemCounts.put(item.name, itemCounts.getOrDefault(item.name, 0) + 1);
                itemDescriptions.putIfAbsent(item.name, item.description);
                itemImages.putIfAbsent(item.name, item.getImage());
            }
        }

        // Display the items with their counts
        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            String itemName = entry.getKey();
            int count = entry.getValue();
            String description = itemDescriptions.get(itemName);
            Image image = itemImages.get(itemName);
            ItemDisplay itemDisplay = new ItemDisplay(itemName, description, count, image);
            inventoryList.getItems().add(itemDisplay);
        }

        inventoryList.setOnMouseClicked(event -> {
            if (inventoryList.getSelectionModel().getSelectedItem() != null) {
                String selectedItem = inventoryList.getSelectionModel().getSelectedItem().getName();
                if (selectedItem != null) {
                    String itemName = selectedItem.split("-")[0]; // Get the item name
                    for (Item item : inventory) {
                        if (item != null && item.name.equals(itemName)) {
                            player.consumeItem(item); // Consume the selected item
                            show(player.getInventory(), player.getInventoryCount()); // Update the inventory display

                            break;
                        }
                    }
                }
            }
        });

        stage.show();

    }

    public void hide() {
        stage.hide();
    }
}
