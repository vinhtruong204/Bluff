package input;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

import game.Game;
import gamestate.GameState;

public class MouseInputs implements MouseInputListener {

    private Game game;

    public MouseInputs(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse is clicked");
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().mouseClicked(e);
                break;
            case PLAYING:
                game.getPlaying().mouseClicked(e);
            case PAUSE:

                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse is pressed");
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().mousePressed(e);
                break;
            case PLAYING:
                game.getPlaying().mousePressed(e);
            case PAUSE:

                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released");
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().mouseReleased(e);
                break;
            case PLAYING:
                game.getPlaying().mouseReleased(e);
                break;
            case PAUSE:

                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
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
    public void mouseExited(MouseEvent e) {
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
    public void mouseDragged(MouseEvent e) {

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
    public void mouseMoved(MouseEvent e) {
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

}
