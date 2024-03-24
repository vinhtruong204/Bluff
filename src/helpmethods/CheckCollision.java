package helpmethods;

import java.awt.Rectangle;

import playing.tile.Tile;

public class CheckCollision {

    public static boolean isCollisionWithFloor(int[][] map, Rectangle newHitbox) {
        return isSolid(map, newHitbox.x, newHitbox.y) || isSolid(map, newHitbox.x + newHitbox.width, newHitbox.y);
    }

    private static boolean isSolid(int[][] map, float x, float y) {

        int colIndex = (int) (x / Tile.TILE_SIZE);
        int rowIndex = (int) (y / Tile.TILE_SIZE);

        return isTileSolid(map[rowIndex][colIndex]);
    }

    public static boolean isCollision(Rectangle a, Rectangle b) {
        // Hit box of tile
        int topA, topB;
        int leftA, leftB;
        int rightA, rightB;
        int bottomA, bottomB;

        // All side of player after move
        topA = a.y;
        leftA = a.x;
        rightA = a.x + a.width;
        bottomA = a.y + a.height;

        // All side of tile
        topB = b.y;
        leftB = b.x;
        rightB = b.x + b.width;
        bottomB = b.y + b.height;

        if (leftA > rightB || rightA < leftB) {
            return false;
        }
        if (topA > bottomB || bottomA < topB) {
            return false;
        }
        return true;
    }

    public static boolean isEntityOnground(int[][] map, Rectangle newHitbox) {
        // Get current row and column index of entity
        int rowIndex = (newHitbox.y + newHitbox.height) / Tile.TILE_SIZE;
        int colIndexLeft = newHitbox.x / Tile.TILE_SIZE;
        int colIndexRight = (newHitbox.x + newHitbox.width) / Tile.TILE_SIZE;

        // Check if collided with not solid tile
        if (!isTileSolid(map[rowIndex][colIndexLeft]) && !isTileSolid(map[rowIndex][colIndexRight])) {
            // Return not on ground
            return false;
        }

        // Return not on ground
        return true;
    }

    public static boolean isTileSolid(int tileType) {
        switch (tileType) {
            // Is brick
            case 1:
                return true;
            // Isn't brick
            default:
                return false;
        }
    }

}
