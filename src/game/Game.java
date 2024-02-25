package game;

import java.awt.Graphics;

import gamestate.GameState;
import menu.Menu;
import playing.Playing;

public class Game {
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 480;

    private Playing playing;
    private Menu menu;

    public Game() {
        playing = new Playing();
        menu = new Menu();
    }

    public void update() {
        switch (GameState.gameState) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            default:
                break;
        }
    }

    public void render(Graphics g) {
        switch (GameState.gameState) {
            case MENU:
                menu.render(g);
                break;
            case PLAYING:
                playing.render(g);
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
    
}
