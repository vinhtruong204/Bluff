package gamestate.playing.pause;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import game.Game;
import gamestate.StateMethods;
import gamestate.playing.Playing;
import gamestate.playing.pause.exit.ExitButtons;
import gamestate.playing.pause.exit.ExitOption;
import gamestate.playing.pause.main_pause.PauseOption;
import gamestate.playing.pause.main_pause.PauseOptionState;
import helpmethods.FilePath;
import helpmethods.LoadSave;

public class Pause implements StateMethods {
    // Background image of the pause
    private BufferedImage background;

    // Array button of game pause state
    private PauseOption[] pauseOptions;

    // Background exit popup
    private BufferedImage exitBackground;

    // Array button of pop up exit
    private ExitButtons[] exitOptions;

    // Current playing
    private Playing playing;

    public Pause(Playing playing) {
        this.playing = playing;

        // Load background of game pause
        background = LoadSave.loadImage(FilePath.Pause.PAUSE_BACKGROUND);

        // Load exit popup background
        exitBackground = LoadSave.loadImage(FilePath.Pause.POPUP_BACKGROUND);

        // Load all pause buttons
        loadPauseOption();

        // Load all exit popup buttons
        loadExitOption();
    }

    private void loadExitOption() {
        // Allocate memory
        exitOptions = new ExitButtons[3];

        for (int i = 0; i < exitOptions.length; i++) {
            exitOptions[i] = new ExitButtons(i);
        }
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
        switch (PauseState.pauseState) {
            case MAIN:
                for (PauseOption option : pauseOptions)
                    option.update();
                break;
            case EXIT_POPUP:
                for (ExitButtons exitOption : exitOptions)
                    exitOption.update();
                break;

            default:
                break;
        }

    }

    @Override
    public void render(Graphics g) {
        switch (PauseState.pauseState) {
            case MAIN:
                // Draw pause background
                g.drawImage(background,
                        (Game.SCREEN_WIDTH - background.getWidth()) / 2,
                        (Game.SCREEN_HEIGHT - background.getHeight()) / 2,
                        null);

                for (PauseOption option : pauseOptions)
                    option.render(g);

                break;
            case EXIT_POPUP:
                // Draw pause background
                g.drawImage(exitBackground,
                        (Game.SCREEN_WIDTH - exitBackground.getWidth()) / 2,
                        (Game.SCREEN_HEIGHT - exitBackground.getHeight()) / 2,
                        null);

                for (ExitButtons exitOption : exitOptions)
                    exitOption.render(g);

                break;

            default:
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (PauseState.pauseState) {
            case MAIN:
                for (PauseOption button : pauseOptions)
                    // If mouse press over button
                    if (button.isIn(e))
                        button.setMousePressed(true);
                break;
            case EXIT_POPUP:
                for (ExitButtons exitOption : exitOptions)
                    // If mouse press over button
                    if (exitOption.isIn(e))
                        exitOption.setMousePressed(true);
                break;

            default:
                break;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (PauseState.pauseState) {
            case MAIN:
                for (PauseOption button : pauseOptions) {
                    if (button.isIn(e) && button.isMousePressed()) {
                        // Apply game state when mouse released on button
                        if (button.getRowIndex() == PauseOptionState.RESTART) {
                            // Start play over
                            playing.resetAll();
                        }

                        // Apply game state depend on type button
                        button.applyGameState();
                    }
                }

                // Reset boolean of all button if mouse released
                for (PauseOption button : pauseOptions)
                    button.resetBoolean();
                break;
            case EXIT_POPUP:
                for (ExitButtons exitOption : exitOptions) {
                    if (exitOption.isIn(e) && exitOption.isMousePressed()) {
                        // Apply game state when mouse released on exitOption
                        if (exitOption.getRowIndex() == ExitOption.NO) {
                            // Start play over
                            playing.resetAll();
                        }

                        // Apply game state depend on type exitOption
                        exitOption.applyGameState();
                    }
                }

                // Reset boolean of all button if mouse released
                for (ExitButtons exitOption : exitOptions)
                    exitOption.resetBoolean();
                break;

            default:
                break;
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (PauseState.pauseState) {
            case MAIN:
                // Reset mouse over when mouse moved
                for (PauseOption button : pauseOptions)
                    button.setMouseOver(false);

                // Check and set mouse over
                for (PauseOption button : pauseOptions)
                    if (button.isIn(e)) {
                        button.setMouseOver(true);
                        break;
                    }
                break;
            case EXIT_POPUP:
                // Reset mouse over when mouse moved
                for (ExitButtons exitOption : exitOptions)
                    exitOption.setMouseOver(false);

                // Check and set mouse over
                for (ExitButtons exitOption : exitOptions)
                    if (exitOption.isIn(e)) {
                        exitOption.setMouseOver(true);
                        break;
                    }
                break;

            default:
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
