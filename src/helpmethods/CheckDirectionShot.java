package helpmethods;

import core.Position;

public class CheckDirectionShot {
    public static DirectionShot setDirection(Position positionStart, Position positionEnd) {
        // If the y coordinates are the same, check the left or right direction
        if (positionStart.getY() == positionEnd.getY()) {
            if (positionStart.getX() < positionEnd.getX()) {
                return DirectionShot.RIGHT;
            } else {
                return DirectionShot.LEFT;
            }
        }
        // If the y coordinates are different, check the remaining directions
        else {
            if (positionStart.getY() > positionEnd.getY() && positionStart.getX() < positionEnd.getX()) {
                return DirectionShot.DIAGONAL_UP_RIGHT;
            } else if (positionStart.getY() > positionEnd.getY() && positionStart.getX() > positionEnd.getX()) {
                return DirectionShot.DIAGONAL_UP_LEFT;
            } else if (positionStart.getY() > positionEnd.getY() && positionStart.getX() == positionEnd.getX()) {
                return DirectionShot.UP;
            } else if (positionStart.getY() < positionEnd.getY() && positionStart.getX() < positionEnd.getX()) {
                return DirectionShot.DIAGONAL_DOWN_RIGHT;
            } else if (positionStart.getY() < positionEnd.getY() && positionStart.getX() > positionEnd.getX()) {
                return DirectionShot.DIAGONAL_DOWN_LEFT;
            } else if (positionStart.getY() < positionEnd.getY() && positionStart.getX() == positionEnd.getX()) {
                return DirectionShot.DOWN;
            }
        }
        return null;
    }
}
