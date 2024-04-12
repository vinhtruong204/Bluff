package menu;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import button.Button;
import core.Position;
import core.Size;
import game.Game;
import gamestate.GameState;
import helpmethods.FilePath;
import helpmethods.LoadSave;

public class MenuButton extends Button {

    public MenuButton(int rowIndex) {
        // Initialize index indicate for type of button
        this.rowIndex = rowIndex;

        // Set size for the button
        size = new Size(BUTTON_WIDTH * BUTTON_SCALE, BUTTON_HEIGHT * BUTTON_SCALE);

        // Set position for the button depend on index of type
        setPosition();

        // Set box check contain when mouse move
        bounds = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());

        // Load all button image state
        loadImages();
    }

    @Override
    protected void setPosition() {
        // Set posision for each type of button
        // Distance among each button is 70 - size.getHeight() = 70 - 48 = 22(pixel)
        switch (rowIndex) {
            case MenuButtonState.PLAY:
                position = new Position((Game.SCREEN_WIDTH - size.getWidth()) / 2, 150);
                break;
            case MenuButtonState.HELP:
                position = new Position((Game.SCREEN_WIDTH - size.getWidth()) / 2, 150 + 70);
                break;
            case MenuButtonState.EXIT:
                position = new Position((Game.SCREEN_WIDTH - size.getWidth()) / 2, (150 + 70) + 70);
                break;
            default:
                break;
        }
    }

    @Override
    protected void loadImages() {
        // Allocate memory
        images = new BufferedImage[3];

        // Load image contain all state of the button
        BufferedImage temp = LoadSave.loadImage(FilePath.Menu.MENU_BUTTONS);

        // Load all state of the button
        for (int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(i * BUTTON_WIDTH, rowIndex * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    @Override
    public void applyGameState() {
        // Change game state depend on type of menu buttons
        switch (rowIndex) {
            case MenuButtonState.PLAY:
                // Change game state
                GameState.gameState = GameState.PLAYING;
                break;
            case MenuButtonState.HELP:
                // Change menu state
                MenuState.menuState = MenuState.HELP;
                break;
            case MenuButtonState.EXIT:
                // Terminates the application
                System.exit(0);
                break;

            default:
                break;
        }
    }

}
