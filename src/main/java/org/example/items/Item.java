package org.example.items;

import javafx.scene.image.Image;

public class Item {
    public String name, description, recipe;
    public Item[] recipe_obj;
    private Image image; // Add private Image field for the item image

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

    private int x,y;
    public Item(String name, String description, String recipe, Item[] recipe_obj) {
        this.name = name;
        this.description = description;
        this.recipe = recipe;
        this.recipe_obj = recipe_obj;
    }

    // Method to set the image of the item
    public void setImage(Image image) {
        this.image = image;
    }

    // Method to get the image of the item
    public Image getImage() {
        return image;
    }
}
