package playing.tile;

import helpmethods.LoadSave;

public class Level {
    private int map[][];
    private int maxCol, maxRow;
    private Tile[] tiles;

    public Level(String filePath) {
        initTile();
        setIndexMap(filePath);
        map = LoadSave.loadMap(filePath, maxCol, maxRow);
    }

    private void initTile() {
        tiles = new Tile[10];

        tiles[0] = new Tile("img/Tile/Blue.png");

        tiles[5] = new Tile("img/Tile/Blue.png");

        tiles[4] = new Tile("img/Tile/Blue.png");

        tiles[1] = new Tile("img/Tile/wood01.png");

        tiles[2] = new Tile("img/Tile/wood02.png");
    }

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
