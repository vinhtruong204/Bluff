package game;

import java.awt.Graphics;

import gameover.GameOver;
import gameover.GameOverState;
import gamestate.GameState;
import gamewin.GameWin;
import gamewin.GameWinState;
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
    private GameOver gameOver;
    private GameWin gameWin;

    public Game() {
        // Create new playing and menu objects
        playing = new Playing();
        menu = new Menu();
        pause = new Pause(playing);
        gameOver = new GameOver();
        gameWin = new GameWin();
    }

    public void update() {
        // Update depend on game state
        switch (GameState.gameState) {
            case MENU:
                if(GameOverState.gameOverState == GameOverState.NOSTART) GameOverState.gameOverState = GameOverState.START;
                if(GameWinState.gameWinState == GameWinState.NOSTART) GameWinState.gameWinState = GameWinState.START;
                gameOver = new GameOver();
                gameWin = new GameWin();
                playing = new Playing();
                pause = new Pause(playing);
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case PAUSE:
                pause.update();
                break;
            case GAMEOVER:
                if(GameOverState.gameOverState == GameOverState.START){
                    gameOver.setCurrentTime(System.currentTimeMillis());
                    GameOverState.gameOverState = GameOverState.NOSTART;
                }
                gameOver.update();
                break;
            case WIN:
                if(GameWinState.gameWinState == GameWinState.START){
                    gameWin.setCurrentTime(System.currentTimeMillis());
                    GameWinState.gameWinState = GameWinState.NOSTART;
                }
                gameWin.update();
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
                playing.render(g);
                pause.render(g);
                break;
            case GAMEOVER:
                gameOver.render(g);
                break;
            case WIN:
                gameWin.render(g);
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
