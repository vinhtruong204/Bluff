package playing.entity;

import java.awt.Graphics;

import core.Position;
import core.Size;

public abstract class GameObject {
    // Default attributes of a game object
    protected Position position;
    protected Size size;

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }

    // The objects have to provide update and render functions
    public abstract void update();

    public abstract void render(Graphics g);
}
