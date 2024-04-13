package playing.level;

import helpmethods.FilePath;
import helpmethods.LoadSave;

public class Level {
    // Map
    private int map[][];
    private int maxCol, maxRow;
    private Tile[] tiles;

    // Contructor
    public Level(String filePath) {
        // Load tile image from file
        initTile();

        // Initialize row and column values of the map
        setIndexMap(filePath);

        // Load matrix map from file
        map = LoadSave.loadMap(filePath, maxCol, maxRow);
    }

    // init Tile
    private void initTile() {
        // Allocate memory
        tiles = new Tile[Tile.TOTAL_TILE_TYPE];

        // Load image of tile
        tiles[Tile.BLUE] = new Tile(FilePath.Tile.BLUE);
        tiles[Tile.ELEVATION] = new Tile(FilePath.Tile.ELEVATION);
    }

    private void setIndexMap(String filePath) {
        switch (filePath) {
            case FilePath.Map.MAP_1:
                maxCol = 42;
                maxRow = 14;
                break;
            case FilePath.Map.MAP_2:
                maxCol = 20;
                maxRow = 50;
                break;
            case FilePath.Map.MAP_3:
                maxCol = 42;
                maxRow = 28;
                break;
            case FilePath.Map.MAP_4:
                maxCol = 60;
                maxRow = 20;
                break;
            case FilePath.Map.MAP_5:
                maxCol = 60;
                maxRow = 20;
                break;
            case FilePath.Map.MAP_6:
                maxCol = 80;
                maxRow = 30;
                break;
            case FilePath.Map.MAP_7:
                maxCol = 60;
                maxRow = 20;
                break;
            default:
                break;
        }
    }

    // Getter And Setter
    public int[][] getMap() {
        return map;
    }

    public int getMaxCol() {
        return maxCol;
    }

    public int getMaxRow() {
        return maxRow;
    }

    public Tile[] getTiles() {
        return tiles;
    }

}
