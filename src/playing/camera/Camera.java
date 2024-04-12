package playing.camera;

import java.awt.Graphics;

import game.Game;
import playing.entity.Player;
import playing.level.Level;
import playing.level.Tile;

public class Camera {

    // The largest location of the map
    private int maxMapX;
    private int maxMapY;

    // Camera's starting position in x and y axes
    private int mapStartX, mapStartY;

    private Level level;
    private Player player;
    // Starting and ending position of image loading
    private int x1, x2, y1, y2;
    // Row index and column index of the map matrix
    private int map_x, map_y;

    // Constructor
    public Camera(Level level, Player player) {
        this.level = level;
        this.player = player;
        maxMapX = Tile.TILE_SIZE * level.getMaxCol();
        maxMapY = Tile.TILE_SIZE * level.getMaxRow();
    }

    // Getter and setter of camera
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

    // Set the player always in the camera
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

    // Start and end location for image loading
    private void UpdatePositionRenderToMap() {

        x1 = (mapStartX % Tile.TILE_SIZE) * -1;
        x2 = x1 + Game.SCREEN_WIDTH + (x1 == 0 ? 0 : Tile.TILE_SIZE);

        y1 = (mapStartY % Tile.TILE_SIZE) * -1;
        y2 = y1 + Game.SCREEN_HEIGHT + (y1 == 0 ? 0 : Tile.TILE_SIZE);
    }

    // Update
    public void update() {
        checkCenterToMap();
        UpdatePositionRenderToMap();
    }

    // Render
    public void render(Graphics g) {
        // Calculate begin row and column 
        map_x = mapStartX / Tile.TILE_SIZE;
        map_y = mapStartY / Tile.TILE_SIZE;

        // Get all tiles
        Tile[] tiles = level.getTiles();

        // Render map
        for (int i = y1; i < y2; i += Tile.TILE_SIZE) {
            map_x = (mapStartX / Tile.TILE_SIZE);
            for (int j = x1; j < x2; j += Tile.TILE_SIZE) {
                if (map_x >= 0 && map_x < level.getMaxCol() && map_y >= 0 && map_y < level.getMaxRow()) {
                    // Get current tile number
                    int tileNum = level.getMap()[map_y][map_x];

                    switch (tileNum) {
                        // If tile number is brick
                        case Tile.ELEVATION:
                            g.drawImage(tiles[Tile.ELEVATION].getImage(), j, i, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
                            break;

                        // Enemy, background, heart,.... -> render background
                        default:
                            g.drawImage(tiles[Tile.BLUE].getImage(), j, i, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
                            break;
                    }

                }
                map_x++;
            }
            map_y++;
        }
    }

}
