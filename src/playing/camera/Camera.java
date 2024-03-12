package playing.camera;

import java.awt.Graphics;

import game.Game;
import playing.entity.Player;
import playing.tile.Level;
import playing.tile.LevelManager;

public class Camera {

    private int maxMapX;
    private int maxMapY;
    private int mapStartX;
    private int mapStartY;

    private int[][] mapTileNum;
    private Level[] tile;
    private Player player;

    private int x1, x2, y1, y2, map_x, map_y;

    public Camera(int[][] mapTileNum, Level[] tile, Player player) {
        this.mapTileNum = mapTileNum;
        this.tile = tile;
        this.player = player;
        maxMapX = LevelManager.TILE_SIZE * (LevelManager.MAX_WORLD_COL);
        maxMapY = LevelManager.TILE_SIZE * (LevelManager.MAX_WORLD_ROW);
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
        } else if (mapStartX + Game.SCREEN_WIDTH >= maxMapX) {
            mapStartX = maxMapX - Game.SCREEN_WIDTH;
        }
        mapStartY = (int) player.getPosition().getY() - Game.SCREEN_HEIGHT / 2;
        if (mapStartY < 0) {
            mapStartY = 0;
        } else if (mapStartY + Game.SCREEN_HEIGHT >= maxMapY) {
            mapStartY = maxMapY - Game.SCREEN_HEIGHT;
        }
    }

    private void UpdatePositionRenderToMap() {

    }

    public void update() {
        checkCenterToMap();
        UpdatePositionRenderToMap();
    }

    public void render(Graphics g) {
        x1 = 0;
        x2 = 0;

        y1 = 0;
        y2 = 0;

        map_x = 0;
        map_y = 0;

        map_x = mapStartX / LevelManager.TILE_SIZE;
        x1 = (mapStartX % LevelManager.TILE_SIZE) * -1;
        x2 = x1 + Game.SCREEN_WIDTH + (x1 == 0 ? 0 : LevelManager.TILE_SIZE);

        map_y = mapStartY / LevelManager.TILE_SIZE;
        y1 = (mapStartY % LevelManager.TILE_SIZE) * -1;
        y2 = y1 + Game.SCREEN_HEIGHT + (y1 == 0 ? 0 : LevelManager.TILE_SIZE);
        for (int i = y1; i < y2; i += LevelManager.TILE_SIZE) {
            map_x = (mapStartX / LevelManager.TILE_SIZE);
            for (int j = x1; j < x2; j += LevelManager.TILE_SIZE) {
                if (map_x < LevelManager.MAX_WORLD_COL && map_y < LevelManager.MAX_WORLD_ROW) {
                    int tileNum = mapTileNum[map_x][map_y];
                    g.drawImage(tile[tileNum].getImage(), j, i, LevelManager.TILE_SIZE, LevelManager.TILE_SIZE, null);
                }
                map_x++;
            }
            map_y++;
        }
    }

    public void inPos() {
        System.out.println(player.getPosition().getX() + " " + player.getPosition().getY());
    }

}
