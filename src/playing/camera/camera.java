package playing.camera;

import java.awt.Graphics;

import game.Game;
import playing.entity.Player;
import playing.tile.Tile;
import playing.tile.TileManager;

public class camera {

    private int maxMapX;
    private int maxMapY;
    private int mapStartX;
    private int mapStartY;

    private int[][] mapTileNum;
    private Tile[] tile;
    private Player player;

    public camera(int[][] mapTileNum, Tile[] tile, Player player) {
        this.mapTileNum = mapTileNum;
        this.tile = tile;
        this.player = player;
        maxMapX = TileManager.TILE_SIZE * (TileManager.MAX_WORLD_COL);
        maxMapY = TileManager.TILE_SIZE * (TileManager.MAX_WORLD_ROW);
    }

    public int getMaxMapX() {
        return maxMapX;
    }

    public void setMaxMapX(int maxMapX) {
        this.maxMapX = maxMapX;
    }

    public int getMaxMapY() {
        return maxMapY;
    }

    public void setMaxMapY(int maxMapY) {
        this.maxMapY = maxMapY;
    }

    public int getMapStartX() {
        return mapStartX;
    }

    public void setMapStartX(int mapStartX) {
        this.mapStartX = mapStartX;
    }

    public int getMapStartY() {
        return mapStartY;
    }

    public void setMapStartY(int mapStartY) {
        this.mapStartY = mapStartY;
    }

    private void checkCenterToMap() {
        mapStartX = (int) player.getPosition().getX() - Game.SCREEN_WIDTH / 2;
        if (mapStartX < 0) {
            mapStartX = 0;
        }
        else if (mapStartX + Game.SCREEN_WIDTH >= maxMapX) {
            mapStartX = maxMapX - Game.SCREEN_WIDTH;
        }
        mapStartY = (int) player.getPosition().getY() - Game.SCREEN_HEIGHT / 2;
        if (mapStartY < 0) {
            mapStartY = 0;
        }
        else if (mapStartY + Game.SCREEN_HEIGHT >= maxMapY) {
            mapStartY = maxMapY - Game.SCREEN_HEIGHT;
        }
    }

    public void update() {
        checkCenterToMap();
    }

    public void render(Graphics g) {
        int x1 = 0;
        int x2 = 0;

        int y1 = 0;
        int y2 = 0;

        int map_x = 0;
        int map_y = 0;

       // System.out.println(mapStartX + " " + mapStartY);

        map_x = mapStartX / TileManager.TILE_SIZE;
        x1 = (mapStartX % TileManager.TILE_SIZE) * -1;
        x2 = x1 + Game.SCREEN_WIDTH + (x1 == 0 ? 0 : TileManager.TILE_SIZE);

        map_y = mapStartY / TileManager.TILE_SIZE;
        y1 = (mapStartY % TileManager.TILE_SIZE) * -1;
        y2 = y1 + Game.SCREEN_HEIGHT + (y1 == 0 ? 0 : TileManager.TILE_SIZE);

        for (int i = y1; i < y2; i += TileManager.TILE_SIZE) {
            map_x = (mapStartX / TileManager.TILE_SIZE);
            for (int j = x1; j < x2; j += TileManager.TILE_SIZE) {
                int tileNum = mapTileNum[map_x][map_y];
                g.drawImage(tile[tileNum].getImage(), j, i, TileManager.TILE_SIZE, TileManager.TILE_SIZE, null);
                map_x++;
            }
            map_y++;
        }
    }

    public void inPos()
    {
        System.out.println(player.getPosition().getX() + " " + player.getPosition().getY());
    }

}
