package org.example.items;

import javafx.scene.image.Image;

public class ItemDisplay {
    private String name;
    private String description;
    private int count;
    private Image image;

    public ItemDisplay(String name, String description, int count, Image image) {
        this.name = name;
        this.description = description;
        this.count = count;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCount() {
        return count;
    }

    public Image getImage() {
        return image;
    }
}
