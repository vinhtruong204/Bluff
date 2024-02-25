package playing.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;

public abstract class GameObject {
    protected Position position;
    protected Size size;
    protected BufferedImage image;

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }

    public BufferedImage getImage() {
        return image;
    }

    public abstract void update();
    public abstract void render(Graphics g);
}
