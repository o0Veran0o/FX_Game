package org.example.objects;

import org.example.view.GamePanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ObjectsManager {
    GamePanel gp;
    public Objects[] Object;
    public Objects[][] mapObjectNumber;
    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String map;
    public ObjectsManager(GamePanel gp) throws IOException {
        this.gp = gp;
        Object = new Objects[10];
        map= "/objects/Objectmap1.txt";
        mapObjectNumber = new Objects[gp.column][gp.row];
        getObjectImage();
        LoadMap();
    }

    public void LoadMap() {
        for (int col = 0; col < mapObjectNumber.length; col++) {
            for (int row = 0; row < mapObjectNumber[col].length; row++) {
                mapObjectNumber[col][row] = null;
            }
        }
        try {
            InputStream is = getClass().getResourceAsStream(map);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (row < gp.row) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while (col < gp.column) {
                    int num = Integer.parseInt(numbers[col]);
                    Objects obj = createObject(num);
                    if(obj!=null) {
                        mapObjectNumber[col][row] = new Objects(obj.img, obj.collision, obj.name);
                        mapObjectNumber[col][row].setPosition(col * gp.tile_size, row * gp.tile_size);
                    }
                    col++;
                }
                if (col == gp.column) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getObjectImage() throws IOException {
        Object[1] = new Objects(new Image(getClass().getResourceAsStream("/objects/chest_closed.png")), false, "closed chest");
        Object[2] = new Objects(new Image(getClass().getResourceAsStream("/objects/window.png")), false, "window");
        Object[3] = new Objects(new Image(getClass().getResourceAsStream("/objects/door1.png")), false, "closed door");
    }

    private Objects createObject(int num) {
        if (num == 0) return null;
        return Object[num];
    }

    public void draw(GraphicsContext gc) {
        for (int col = 0; col < gp.column; col++) {
            for (int row = 0; row < gp.row; row++) {
                Objects obj = mapObjectNumber[col][row];
                if (obj != null) {
                    gc.drawImage(obj.img, obj.x, obj.y + 60, gp.tile_size - 30, gp.tile_size - 60);
                }
            }
        }
    }
}
