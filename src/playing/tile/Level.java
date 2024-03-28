package playing.tile;

import helpmethods.LoadSave;

public class Level {
    // Map
    private int map[][];
    private int maxCol, maxRow;
    private Tile[] tiles;

    // Contructor
    public Level(String filePath) {
        initTile();
        setIndexMap(filePath);
        map = LoadSave.loadMap(filePath, maxCol, maxRow);
    }

    // init Tile
    private void initTile() {
        tiles = new Tile[10];

        tiles[0] = new Tile("img/Tile/Blue.png");

        tiles[3] = new Tile("img/Tile/Blue.png");

        tiles[5] = new Tile("img/Tile/Blue.png");

        tiles[6] = new Tile("img/Tile/Blue.png");

        tiles[7] = new Tile("img/Tile/Blue.png");

        tiles[8] = new Tile("img/Tile/Blue.png");

        tiles[9] = new Tile("img/Tile/Blue.png");

        tiles[4] = new Tile("img/Tile/Blue.png");

        tiles[1] = new Tile("img/Tile/wood05.png");

        tiles[2] = new Tile("img/Tile/wood02.png");
    }

    // set Index of Map
    private void setIndexMap(String filePath) {
        switch (filePath) {
            case "Map/Map01.txt":
                maxCol = 42;
                maxRow = 14;
                break;
            case "Map/Map02.txt":
                maxCol = 21;
                maxRow = 23;
                break;
            case "Map/Map03.txt":

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
