package game;

import java.awt.Graphics;

import gamestate.GameState;
import menu.Menu;
import playing.Playing;
import playing.pause.Pause;

public class Game {
    // Dimension of the window
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 480;

    // Game states
    private Playing playing;
    private Menu menu;
    private Pause pause;

    public Game() {
        // Create new playing and menu objects
        playing = new Playing();
        menu = new Menu();
        pause = new Pause();
    }

    public void update() {
        // Update depend on game state
        switch (GameState.gameState) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case PAUSE:
                pause.update();
                break;
            default:
                break;
        }
    }

    
    public void render(Graphics g) {
        // Render depend on game state
        switch (GameState.gameState) {
            case MENU:
                menu.render(g);
                break;
            case PLAYING:
                playing.render(g);
                break;
            case PAUSE:
                //playing.render(g);
                pause.render(g);
                break;
            default:
                break;
        }
    }

    public Playing getPlaying() {
        return playing;
    }
    
    public Menu getMenu() {
        return menu;
    }
    
    public Pause getPause() {
        return pause;
    }
}
