package gameover;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamestate.GameState;
import helpmethods.FilePath;
import helpmethods.LoadSave;
import pause_sound.PauseSoundState;
import sound.SoundObject;

public class GameOver {
    private BufferedImage image;
    private SoundObject gameOver;
    private long currentTime;

    private final int GAME_OVER_WIDTH = 490;
    private final int GAME_OVER_HEIGHT = 160;

    public GameOver() {
        image = LoadSave.loadImage(FilePath.GameOver.GAME_OVER);
        gameOver = new SoundObject(FilePath.Sound.GAMEOVER_SOUND);
        currentTime = System.currentTimeMillis();
    }

    public void update() {
        if(PauseSoundState.pauseSoundState == PauseSoundState.ON){
            gameOver.start();
        }
        if ((System.currentTimeMillis() - currentTime) / 1000 >= 15) {
            GameState.gameState = GameState.MENU;
        }
    }

    public void render(Graphics g) {
        g.drawImage(image, 100, 150, GAME_OVER_WIDTH, GAME_OVER_HEIGHT, null);
    }
}
