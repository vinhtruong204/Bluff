package playing.level;

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
        tiles[Tile.BLUE] = new Tile("img/Tile/Blue.png");
        tiles[Tile.ELEVATION] = new Tile("img/Tile/Tilemap_Elevation.png");
    }

    private void setIndexMap(String filePath) {
        switch (filePath) {
            case "Map/Map01.txt":
                maxCol = 42;
                maxRow = 14;
                break;
            case "Map/Map02.txt":
                maxCol = 42;
                maxRow = 24;
                break;
            case "Map/Map03.txt":
                maxCol = 42;
                maxRow = 24;
                break;
            case "Map/Map04.txt":

                break;
            case "Map/Map05.txt":

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
