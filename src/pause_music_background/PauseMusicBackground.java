package pause_music_background;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import button.Button;
import core.Position;
import core.Size;
import game.Game;
import helpmethods.FilePath;
import helpmethods.LoadSave;

public class PauseMusicBackground extends Button {
    private final int PAUSE_SOUND_BACKGROUND_BUTTON_WIDTH = 35;
    private final int PAUSE_SOUND_BACKGROUND_BUTTON_HEIGHT = 35;

    public PauseMusicBackground(int rowIndex) {
        // Initialize index indicate for type of button
        this.rowIndex = rowIndex;

        // Set size for the button
        size = new Size(PAUSE_SOUND_BACKGROUND_BUTTON_WIDTH, PAUSE_SOUND_BACKGROUND_BUTTON_HEIGHT);

        // Set position for the button depend on index of type
        setPosition();

        // Set box check contain when mouse move
        bounds = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());

        // Load all button image state
        loadImages();
    }

    @Override
    public void applyGameState() {
    }

    @Override
    protected void loadImages() {
        images = new BufferedImage[3];
        // Load image contain all state of the button
        BufferedImage temp = LoadSave.loadImage(FilePath.Sound.PAUSE_SOUND_BACKGROUND_BUTTON);
        // Load all state of the button
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * BUTTON_WIDTH, rowIndex * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
    }

    @Override
    protected void setPosition() {
        // Set position of pause button to top left of window
        position = new Position(Game.SCREEN_WIDTH - 3 * size.getWidth() - 40, 0);
    }
}
