package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Game;
import gamestate.GameState;

public class KeyboardInputs implements KeyListener {
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
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().keyPressed(e);
                break;
            case PLAYING:
                game.getPlaying().keyPressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().keyReleased(e);
                break;
            case PLAYING:
                game.getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }

}