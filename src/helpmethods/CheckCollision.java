package helpmethods;

import java.awt.Rectangle;

public class CheckCollision {
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
}
