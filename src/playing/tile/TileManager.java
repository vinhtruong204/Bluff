package playing.tile;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;

import helpmethods.LoadSave;

public class TileManager {

    private final int TILE_SIZE = 48;
    private final int MAX_WORLD_COL = 42;
    private final int MAX_WORLD_ROW = 12;
    private int mapTileNum[][];
    private Tile tile[];

    public TileManager()
    {
        mapTileNum= new int[MAX_WORLD_COL][MAX_WORLD_ROW];
        tile = new Tile[10];
        GetTileMap();
        LoadMap();
    }

    private void GetTileMap()
    {
        tile[0]= new Tile();
        tile[0].setImage(LoadSave.loadImage("img/Tile/wood03.png"));

        tile[1]= new Tile();
        tile[1].setImage(LoadSave.loadImage("img/Tile/wood01.png"));

        tile[2]= new Tile();
        tile[2].setImage(LoadSave.loadImage("img/Tile/wood02.png"));
    }

    private void LoadMap()
    {
        try{
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
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    

    public void update() {
        
    }

    public void render(Graphics g) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < MAX_WORLD_COL && worldRow < MAX_WORLD_ROW) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * TILE_SIZE;
            int worldY = worldRow * TILE_SIZE;

            g.drawImage(tile[tileNum].getImage(), worldX, worldY, TILE_SIZE, TILE_SIZE, null);
            worldCol++;
            if (worldCol == MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
