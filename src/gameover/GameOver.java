package gameover;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamestate.GameState;
import helpmethods.FilePath;
import helpmethods.LoadSave;
import playing.sound.SoundObject;
import playing.sound.pause_sound.PauseSoundState;

public class GameOver {
    //method
    private BufferedImage image;
    private SoundObject gameOver;
    private long currentTime;

    //GameOver WIDTH
    private final int GAME_OVER_WIDTH = 490;
    //GameOver HEIGHT
    private final int GAME_OVER_HEIGHT = 160;

    //contructor of GameOver
    public GameOver() {
        image = LoadSave.loadImage(FilePath.GameOver.GAME_OVER);
        gameOver = new SoundObject(FilePath.Sound.GAMEOVER_SOUND);
    }

    //update
    public void update() {
        //if PauseSoundState = ON
        if (PauseSoundState.pauseSoundState == PauseSoundState.ON) {
            gameOver.start();
        }
        // after 5s
        if (System.currentTimeMillis() - currentTime >= 5000) {
            System.out.println(System.currentTimeMillis() - currentTime);
            GameState.gameState = GameState.MENU;
        }
    }

    //render
    public void render(Graphics g) {
        g.drawImage(image, 100, 150, GAME_OVER_WIDTH, GAME_OVER_HEIGHT, null);
    }

    //getter abd setter

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}
