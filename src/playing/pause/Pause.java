package playing.pause;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import game.Game;
import gamestate.StateMethods;
import helpmethods.LoadSave;

public class Pause implements StateMethods {
    // Background image of the pause
    BufferedImage background;

    // Array button of the menu
    PauseOption[] pauseOptions;

    public Pause() {
        // Load background of game pause
        background = LoadSave.loadImage("img/Pause/Pause_Background.png");

        // Load all buttons
        loadPauseOption();
    }

    private void loadPauseOption() {
        // Allocate memory
        pauseOptions = new PauseOption[3];
        for (int i = 0; i < pauseOptions.length; i++) {
            pauseOptions[i] = new PauseOption(i);
        }
    }

    @Override
    public void update() {
        for (PauseOption option : pauseOptions) {
            option.update();
        }

    }

    @Override
    public void render(Graphics g) {
        // Draw pause background
        g.drawImage(background,
                (Game.SCREEN_WIDTH - background.getWidth()) / 2,
                (Game.SCREEN_HEIGHT - background.getHeight()) / 2,
                null);

        for (PauseOption option : pauseOptions) {
            option.render(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (PauseOption button : pauseOptions) {
            // If mouse press over button
            if (button.isIn(e)) {
                button.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (PauseOption button : pauseOptions) {
            if (button.isIn(e)) {
                if (button.isMousePressed()) {
                    // Apply game state when mouse released on button
                    button.applyGameState();
                }
            }
        }

        // Reset boolean of all button if mouse released
        for (PauseOption button : pauseOptions) {
            button.resetBoolean();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Reset mouse over when mouse moved
        for (PauseOption button : pauseOptions)
            button.setMouseOver(false);

        // Check and set mouse over
        for (PauseOption button : pauseOptions)
            if (button.isIn(e)) {
                button.setMouseOver(true);
                break;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
