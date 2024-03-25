package helpmethods;

import java.awt.Rectangle;

import playing.tile.Tile;

public class CheckCollision {

    public static boolean canMoveLeftOrRight(int[][] map, Rectangle newHitbox, WalkDirection playDirection) {

        switch (playDirection) {
            case LEFT:
                if (isSolid(map, newHitbox.x, newHitbox.y)
                        || isSolid(map, newHitbox.x, newHitbox.y + newHitbox.height))
                    return false;
                break;
            case RIGHT:
                if (isSolid(map, newHitbox.x + newHitbox.width, newHitbox.y + newHitbox.height)
                        || isSolid(map, newHitbox.x + newHitbox.width, newHitbox.y))
                    return false;
                break;

            default:
                break;
        }

        // Return can move
        return true;
    }

    public static int getHorizontalOffset(Rectangle hitbox, WalkDirection playDirection) {

        // Get current column index
        int colIndex = hitbox.x / Tile.TILE_SIZE;

        switch (playDirection) {
            case LEFT:
                return colIndex * Tile.TILE_SIZE;
            case RIGHT:
                return (colIndex + 1) * Tile.TILE_SIZE - hitbox.width - 1;

            default:
                return 0;
        }
    }

    public static boolean isCollisionWithFloor(int[][] map, Rectangle newHitbox) {
        return isSolid(map, newHitbox.x, newHitbox.y) || isSolid(map, newHitbox.x + newHitbox.width, newHitbox.y);
    }

    private static boolean isSolid(int[][] map, float x, float y) {
        // Get current row and column index of entity
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

        // Check if collided with not solid tile
        if (isSolid(map, newHitbox.x, newHitbox.y + newHitbox.height)
                || isSolid(map, newHitbox.x + newHitbox.width - 15, newHitbox.y + newHitbox.height)) {
            // Return not on ground
            return true;
        }

        // Return on ground
        return false;
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
