package org.example.objects;

import javafx.scene.image.Image;

public class Objects {
    public Image img;
    public boolean collision;
    public int x, y;
    public String name;
    public Objects() {
    }

    public Objects(Image img, boolean collision, String name) {
        this.img = img;
        this.collision = collision;
        this.name= name;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
