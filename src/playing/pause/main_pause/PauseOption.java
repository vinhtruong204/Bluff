package playing.pause.main_pause;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import button.Button;
import core.Position;
import core.Size;
import game.Game;
import gamestate.GameState;
import helpmethods.LoadSave;
import playing.pause.PauseState;

public class PauseOption extends Button {

    public PauseOption(int rowIndex) {
        // Initialize type of button
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
            case PauseOptionState.CONTINUE:
                position = new Position((Game.SCREEN_WIDTH - size.getWidth()) / 2, 150);
                break;
            case PauseOptionState.RESTART:
                position = new Position((Game.SCREEN_WIDTH - size.getWidth()) / 2, 150 + 70);
                break;
            case PauseOptionState.EXIT:
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
        BufferedImage temp = LoadSave.loadImage("img/Pause/Pause_Buttons.png");

        // Load all state of the button
        for (int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(i * BUTTON_WIDTH, rowIndex * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    @Override
    public void applyGameState() {
        // Apply game state depend on type of pause button
        switch (rowIndex) {
            case PauseOptionState.CONTINUE:
                GameState.gameState = GameState.PLAYING;
                break;
            case PauseOptionState.RESTART:
                GameState.gameState = GameState.PLAYING;
                break;
            case PauseOptionState.EXIT:
                PauseState.pauseState = PauseState.EXIT_POPUP;
                break;
            default:
                break;
        }
    }

}
