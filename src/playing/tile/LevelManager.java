package playing.tile;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;

import helpmethods.LoadSave;


public class LevelManager {

    // set imformation of TileMap
    public static final int TILE_SIZE = 48;
    public static final int MAX_WORLD_COL = 42;
    public static final int MAX_WORLD_ROW = 14;
    private int mapTileNum[][];
    private Level tile[];
    // constructor of TileManager
    public LevelManager() {
        mapTileNum = new int[MAX_WORLD_COL][MAX_WORLD_ROW];
        tile = new Level[10];
        GetTileMap();
        LoadMap();
    }

    // Loading Tile
    public void GetTileMap() {
        tile[0] = new Level();
        tile[0].setImage(LoadSave.loadImage("img/Tile/Blue.png"));
        tile[0].setCollition(false);

        tile[5] = new Level();
        tile[5].setImage(LoadSave.loadImage("img/Tile/Blue.png"));
        tile[5].setCollition(false);

        tile[1] = new Level();
        tile[1].setImage(LoadSave.loadImage("img/Tile/wood01.png"));
        tile[1].setCollition(true);

        tile[2] = new Level();
        tile[2].setImage(LoadSave.loadImage("img/Tile/wood02.png"));
        tile[2].setCollition(true);
        
    }

    // Loading TileMap
    private void LoadMap() {
        try {
            String is = "Map/Map01.txt";
            BufferedReader br = new BufferedReader(new FileReader(is));
            int col = 0, row = 0;
            while (col < MAX_WORLD_COL && row < MAX_WORLD_ROW) {
                String Line = br.readLine();
                while (col < MAX_WORLD_COL) {
                    String Number[] = Line.split(" ");
                    int Num = Integer.parseInt(Number[col]);
                    mapTileNum[col][row] = Num;
                    col++;
                }
                if (col == MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update Map
    public void update() {
    }

    // render Map
    public void render(Graphics g) {
        
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    public Level[] getTile() {
        return tile;
    }

    
}
