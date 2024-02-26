package menu;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import constants.Constants;
import core.Position;
import core.Size;
import game.Game;
import gamestate.GameState;
import helpmethods.LoadSave;

public class MenuButton {
    // Total button and dimension of each button
    public static final int TOTAL_BUTTON = 3;
    public static final int BUTTON_WIDTH = 32;
    public static final int BUTTON_HEIGHT = 16;
    public static final int BUTTON_SCALE = 3;

    private static final int STAND_STILL = 0;
    private static final int MOUSE_OVER = 1;
    private static final int MOUSE_CLICK = 2;

    private BufferedImage[] images;
    private Position position;
    private Size size;
    private int rowIndex, index;
    private Rectangle bounds;

    private boolean mouseOver, mousePressed;

    public MenuButton(int rowIndex) {
        this.rowIndex = rowIndex;
        size = new Size(BUTTON_WIDTH * BUTTON_SCALE, BUTTON_HEIGHT * BUTTON_SCALE);
        position = new Position((Game.SCREEN_WIDTH - size.getWidth()) / 2, 107 * (rowIndex + 1));
        bounds = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        loadImages();
    }

    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage temp = LoadSave.loadImage("img/Menu_Buttons.png");

        for (int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(i * BUTTON_WIDTH, rowIndex * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public void update() {
        index = STAND_STILL;
        if (mouseOver)
            index = MOUSE_OVER;
        if (mousePressed)
            index = MOUSE_CLICK;
    }

    public void render(Graphics g) {
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

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void applyGameState() {
        if (rowIndex == Constants.ButtonState.PLAY)
            GameState.gameState = GameState.PLAYING;
    }

    public void resetBoolean() {
        mouseOver = false;
        mousePressed = false;
    }

}
