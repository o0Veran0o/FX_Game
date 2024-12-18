package org.example.tiles;

import org.example.view.GamePanel;
import org.example.Logic.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class TileManager  {
    GamePanel gp;
    public Tiles [] tile;
    public int mapTileNumber[][];

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String map;
    public TileManager(GamePanel gp) throws IOException {
        this.gp=gp;
        tile= new Tiles[10];
        mapTileNumber = new int[gp.column][gp.row];
        map="/maps/map1.txt";
        LoadMap();
        getTileImage();
    }

    public void LoadMap(){
        try {
            InputStream is = getClass().getResourceAsStream(map);
            BufferedReader br = new BufferedReader( new InputStreamReader(is));
            int col =0;
            int row=0;
            while (col<gp.column&& row<gp.row){
                String line = br.readLine();
                while (col<gp.column) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumber[col][row] = num;
                    col++;
                }
                if(col==gp.column){
                    col=0;
                    row++;
                }
            }
            br.close();
        }
        catch (Exception e){

        }
    }
    public void getTileImage() throws IOException {
        tile[0] = new Tiles();
        tile[0].img = new Image(getClass().getResourceAsStream("/tiles/tile168.png"));

        tile[1] = new Tiles();
        tile[1].img = new Image(getClass().getResourceAsStream("/tiles/tile103.png"));
        tile[1].collision = true;
    }
    public void draw(GraphicsContext gc) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while (col < gp.column && row < gp.row) {
            int tileNumber = mapTileNumber[col][row];
            gc.drawImage(tile[tileNumber].img, x, y, gp.tile_size, gp.tile_size);
            col++;
            x += gp.tile_size;
            if (col == gp.column) {
                col = 0;
                x = 0;
                row++;
                y += gp.tile_size;
            }
        }
    }
}
