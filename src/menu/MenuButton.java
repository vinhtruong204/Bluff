package menu;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import constants.Constants;
import core.Position;
import core.Size;
import game.Game;
import helpmethods.LoadSave;
import states.GameState;
import states.MenuState;

public class MenuButton {
    // Total button and dimension of each button
    public static final int TOTAL_BUTTON = 3;

    // Actual size
    public static final int BUTTON_WIDTH = 32;
    public static final int BUTTON_HEIGHT = 16;

    //
    public static final int BUTTON_SCALE = 3;

    // Mouse states
    private static final int STAND_STILL = 0;
    private static final int MOUSE_OVER = 1;
    private static final int MOUSE_CLICK = 2;

    // Button image
    private BufferedImage[] images;

    // Dimension on screen
    private Position position;
    private Size size;

    // Index of type button(rowIndex) and index state of button(index);
    private int rowIndex, index;

    // Box check contain when mouse move
    private Rectangle bounds;

    // Check mouse state on button
    private boolean mouseOver, mousePressed;

    public MenuButton(int rowIndex) {
        // Initialize index indicate for type of button
        this.rowIndex = rowIndex;

        // Set size for the button
        size = new Size(BUTTON_WIDTH * BUTTON_SCALE, BUTTON_HEIGHT * BUTTON_SCALE);

        // Set position for the button depend on index of type
        setPosition();

        // Set box check contain when mouse move
        bounds = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        loadImages();
    }

    private void setPosition() {
        // Set posision for each type of button
        // Distance among each button is 70 - size.getHeight() = 70 - 48 = 22(pixel)
        switch (rowIndex) {
            case Constants.ButtonState.PLAY:
                position = new Position((Game.SCREEN_WIDTH - size.getWidth()) / 2, 150);
                break;
            case Constants.ButtonState.HELP:
                position = new Position((Game.SCREEN_WIDTH - size.getWidth()) / 2, 150 + 70);
                break;
            case Constants.ButtonState.EXIT:
                position = new Position((Game.SCREEN_WIDTH - size.getWidth()) / 2, (150 + 70) + 70);
                break;
            default:
                break;
        }
    }

    private void loadImages() {
        // Allocate memory
        images = new BufferedImage[3];

        // Load image contain all state of the button
        BufferedImage temp = LoadSave.loadImage("img/Menu_Buttons.png");

        // Load all state of the button
        for (int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(i * BUTTON_WIDTH, rowIndex * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public void update() {
        // Reset state of button
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

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void applyGameState() {
        // Change game state depend on type of menu buttons
        switch (rowIndex) {
            case Constants.ButtonState.PLAY:
                GameState.gameState = GameState.PLAYING;
                break;
            case Constants.ButtonState.HELP:
                MenuState.menuState = MenuState.HELP;
                break;
            case Constants.ButtonState.EXIT:
                // Terminates the application
                System.exit(0);
                break;

            default:
                break;
        }
    }

    public void resetBoolean() {
        // Reset mouse bool to false when mouse released
        mouseOver = false;
        mousePressed = false;
    }

}
