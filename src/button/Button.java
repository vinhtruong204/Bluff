package button;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;

import core.Position;
import core.Size;

public abstract class Button {
    // Total button and dimension of each button
    protected final int TOTAL_BUTTON = 3;

    // Actual size
    protected final int BUTTON_WIDTH = 32;
    protected final int BUTTON_HEIGHT = 16;

    //
    protected final int BUTTON_SCALE = 3;

    // Mouse states
    protected final int STAND_STILL = 0;
    protected final int MOUSE_OVER = 1;
    protected final int MOUSE_CLICK = 2;

    // Dimension on screen
    protected Position position;
    protected Size size;

    // Button image
    protected BufferedImage[] images;

    // Box check contain when mouse move
    protected Rectangle bounds;

    // Check mouse state on button
    protected boolean mouseOver, mousePressed;

    // Index of type button(rowIndex) and index state of button(index);
    protected int rowIndex, index;

    public void resetBoolean() {
        // Reset mouse bool to false when mouse released
        mouseOver = false;
        mousePressed = false;
    }

    // Check mouse over a button
    public boolean isIn(MouseEvent e) {
        return bounds.contains(e.getX(), e.getY());
    }

    public void update() {
        // Reset mouse state on button
        index = STAND_STILL;

        // Change state if mouse over or mouse pressed
        if (mouseOver)
            index = MOUSE_OVER;
        if (mousePressed)
            index = MOUSE_CLICK;
    }

    public void render(Graphics g) {
        // Draw button depend on state of button
        g.drawImage(images[index],
                (int) position.getX(),
                (int) position.getY(),
                size.getWidth(),
                size.getHeight(),
                null);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    protected abstract void setPosition();

    protected abstract void loadImages();

    public abstract void applyGameState();

}
