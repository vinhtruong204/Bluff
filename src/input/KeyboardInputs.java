package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Game;
import states.GameState;

public class KeyboardInputs implements KeyListener {
    // Store game to manage update and render game
    private Game game;

    public KeyboardInputs(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (GameState.gameState) {
            case MENU:

                break;
            case PLAYING:

                break;
            case PAUSE:

                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle keyboard event depend on game state
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().keyPressed(e);
                break;
            case PLAYING:
                game.getPlaying().keyPressed(e);
                break;
            case PAUSE:

                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle keyboard event depend on game state
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().keyReleased(e);
                break;
            case PLAYING:
                game.getPlaying().keyReleased(e);
                break;
            case PAUSE:

                break;
            default:
                break;
        }
    }

}