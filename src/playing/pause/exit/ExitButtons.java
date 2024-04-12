package playing.pause.exit;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import button.Button;
import core.Position;
import core.Size;
import game.Game;
import gamestate.GameState;
import helpmethods.FilePath;
import helpmethods.LoadSave;
import playing.pause.PauseState;

public class ExitButtons extends Button {

    public ExitButtons(int rowIndex) {
        // Initialize type of button
        this.rowIndex = rowIndex;

        // Set size for the button
        setSize();

        // Set position for the button depend on index of type
        setPosition();

        // Set box check contain when mouse move
        bounds = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());

        // Load all button image state
        loadImages();
    }

    private void setSize() {
        switch (rowIndex) {
            case ExitOption.YES, ExitOption.NO:
                size = new Size(BUTTON_WIDTH * BUTTON_SCALE, BUTTON_HEIGHT * BUTTON_SCALE);
                break;
            case ExitOption.CLOSE:
                size = new Size(BUTTON_WIDTH, BUTTON_HEIGHT * 2);
                break;

            default:
                break;
        }
    }

    @Override
    protected void setPosition() {
        // Set posision for each type of button
        // Distance among each button is 70 - size.getHeight() = 70 - 48 = 22(pixel)
        switch (rowIndex) {
            case ExitOption.YES:
                position = new Position(Game.SCREEN_WIDTH / 2 - (size.getWidth() + 22), 250);
                break;
            case ExitOption.NO:
                position = new Position(Game.SCREEN_WIDTH / 2 + 22, 250);
                break;
            case ExitOption.CLOSE:
                // Exit background posX = 155
                // Exit background posY = 140
                // Exit background width = 400
                // Exit background width = 200
                position = new Position(((155 + 400) - size.getWidth()), 140);
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
        BufferedImage temp = LoadSave.loadImage(FilePath.Pause.POPUP_BUTTONS);

        // Load all state of the button
        for (int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(i * BUTTON_WIDTH, rowIndex * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    @Override
    public void applyGameState() {
        // Apply game state depend on type of pause button
        switch (rowIndex) {
            case ExitOption.YES:
                // Change game state and reset pause state
                PauseState.pauseState = PauseState.MAIN;
                GameState.gameState = GameState.MENU;
                break;
            case ExitOption.NO:
                // Change game state and reset pause state
                PauseState.pauseState = PauseState.MAIN;
                GameState.gameState = GameState.MENU;
                break;
            case ExitOption.CLOSE:
                // Go back to game pause panel
                PauseState.pauseState = PauseState.MAIN;
                break;
            default:
                break;
        }
    }

}
