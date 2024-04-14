package helpmethods;

import java.awt.Rectangle;

import playing.level.Tile;

public class CheckCollision {

    public static boolean canMove(int[][] map, Rectangle newHitbox) {
        int offset = (newHitbox.y / Tile.TILE_SIZE + 1) * 48 - 10;

        // Special case if player is in the middle of the tile
        if (newHitbox.y > offset && newHitbox.y < offset + 10) {
            // left
            if (isSolid(map, newHitbox.x, newHitbox.y + 15))
                return false;
            // right
            if (isSolid(map, newHitbox.x + newHitbox.width, newHitbox.y + 15))
                return false;
        }

        // Left
        if (isSolid(map, newHitbox.x, newHitbox.y)
                || isSolid(map, newHitbox.x, newHitbox.y + newHitbox.height)) {
            return false;
        }

        // right
        if (isSolid(map, newHitbox.x + newHitbox.width + 1, newHitbox.y)
                || isSolid(map, newHitbox.x + newHitbox.width + 1, newHitbox.y + newHitbox.height)) {
            return false;
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
                return (colIndex + 2) * Tile.TILE_SIZE - hitbox.width - 1;

            default:
                return 0;
        }
    }

    public static int getVerticalOffset(Rectangle hitbox, boolean jumping) {
        int rowIndex = hitbox.y / Tile.TILE_SIZE;

        if (jumping)
            // jumping
            return rowIndex * Tile.TILE_SIZE;
        else
            // falling
            return (rowIndex + 2) * Tile.TILE_SIZE - hitbox.height - 1;

    }

    public static boolean isCollisionWithRoof(int[][] map, Rectangle newHitbox) {
        // Special case if player is in the middle of the tile
        return isSolid(map, newHitbox.x + 15, newHitbox.y - 1)
                || isSolid(map, newHitbox.x, newHitbox.y - 1)
                || isSolid(map, newHitbox.x + newHitbox.width, newHitbox.y - 1);
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

    public static boolean isEntityOnground(int[][] map, Rectangle hitbox) {

        // Check if collided with not solid tile
        if (isSolid(map, hitbox.x, hitbox.y + hitbox.height + 1)
                || isSolid(map, hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1)) {
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
            case 2:
                return true;
            default:
                return false;
        }
    }

}
