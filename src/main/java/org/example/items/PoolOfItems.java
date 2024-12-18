package org.example.items;

import javafx.scene.image.Image;

public class PoolOfItems {
    private static final String ICON_BASE_PATH = "items/";
    private static final String ICON_EXTENSION = ".png";

    // Initialize an array to hold test items
    private static Item[] testItems;

    // Method to initialize test items with icons from the specified path
    public static Item[] initializeTestItems() {
        testItems = new Item[5];
        // Initialize test items
        // Healing Potion
        Item healingPotion = new Item("Healing Potion", "Restores health by 5 points", "", null);
        String healingPotionIconPath = ICON_BASE_PATH + "HealingPotion" + ICON_EXTENSION;
        Image healingPotionIcon = new Image("items/HealingPotion.png");
        healingPotion.setImage(healingPotionIcon);
        testItems[0] = healingPotion;

        // Speed Potion
        Item speedPotion = new Item("Speed Potion", "Increases movement speed temporarily", "", null);
        String speedPotionIconPath = ICON_BASE_PATH + "SpeedPotion" + ICON_EXTENSION;
        Image speedPotionIcon = new Image(speedPotionIconPath);
        speedPotion.setImage(speedPotionIcon);
        testItems[1] = speedPotion;

        // Damage Potion
        Item damagePotion = new Item("Damage Potion", "Increases attack damage temporarily", "", null);
        String damagePotionIconPath = ICON_BASE_PATH + "DamagePotion" + ICON_EXTENSION;
        Image damagePotionIcon = new Image(damagePotionIconPath);
        damagePotion.setImage(damagePotionIcon);
        testItems[2] = damagePotion;

        // Key
        Item key = new Item("Key to Chest", "Unlocks chests", "", null);
        String keyIconPath = ICON_BASE_PATH + "Key-to-Chest" + ICON_EXTENSION;
        Image keyIcon = new Image(keyIconPath);
        key.setImage(keyIcon);
        testItems[3] = key;


        // Key
        Item key_door = new Item("Key to Door", "Unlocks doors", "", null);
        String key_doorIconPath = ICON_BASE_PATH + "Key-to-Door" + ICON_EXTENSION;
        Image key_doorIcon = new Image(key_doorIconPath);
        key_door.setImage(key_doorIcon);
        testItems[4] = key_door;


        return testItems;
    }

    // Method to find an item by name
    public static Item findByName(String name) {
        for (Item item : testItems) {
            if (item != null && item.name.equals(name)) {
                return item;
            }
        }
        return null;
    }
}
