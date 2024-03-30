package core;

public class Position implements Comparable<Position> {
    private float x;
    private float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public int compareTo(Position that) {
        if (this.x == that.x && this.y == that.y)
            return 0;
        else if (this.x > that.x)
            return 1;
        else
            return -1;
    }
}
