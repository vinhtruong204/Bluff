package playing.camera;

import java.awt.Graphics;

import game.Game;
import playing.entity.Player;
import playing.tile.Level;
import playing.tile.Tile;

public class Camera {

    private int maxMapX;
    private int maxMapY;

    private int mapStartX,mapStartY;

    private Level level;
    private Player player;
    // x1 va y1 la vi tri bat dau de tai gach 
    // x2 va y2 la vi tri dai nhat rong nhat trong camera 
    private int x1, x2, y1, y2;
    // chi so hang va cot cua ma tran map 
    private int map_x, map_y;

    public Camera(Level level, Player player) {
        this.level = level;
        this.player = player;
        maxMapX = Tile.TILE_SIZE * level.getMaxCol();
        maxMapY = Tile.TILE_SIZE * level.getMaxRow();
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
        x1 = 0;
        x2 = 0;

        y1 = 0;
        y2 = 0;

        map_x = 0;
        map_y = 0;

        map_x = mapStartX / Tile.TILE_SIZE;
        x1 = (mapStartX % Tile.TILE_SIZE) * -1;
        x2 = x1 + Game.SCREEN_WIDTH + (x1 == 0 ? 0 : Tile.TILE_SIZE);

        map_y = mapStartY / Tile.TILE_SIZE;
        y1 = (mapStartY % Tile.TILE_SIZE) * -1;
        y2 = y1 + Game.SCREEN_HEIGHT + (y1 == 0 ? 0 : Tile.TILE_SIZE);
    }

    public void update() {
        checkCenterToMap();
        UpdatePositionRenderToMap();
    }

    public void render(Graphics g) {
        for (int i = y1; i < y2; i += Tile.TILE_SIZE) {
            map_x = (mapStartX / Tile.TILE_SIZE);
            for (int j = x1; j < x2; j += Tile.TILE_SIZE) {
                if (map_x < level.getMaxCol() && map_y < level.getMaxRow()) {
                    int tileNum = level.getMap()[map_x][map_y];
                    g.drawImage(level.getTiles()[tileNum].getImage(), j, i, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
                }
                map_x++;
            }
            map_y++;
        }
    }

}
