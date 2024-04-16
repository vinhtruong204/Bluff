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
    private long lastTime, totalTime;

    private final int GAME_OVER_WIDTH = 490;
    private final int GAME_OVER_HEIGHT = 160;

    public GameOver() {
        image = LoadSave.loadImage(FilePath.GameOver.GAME_OVER);
        gameOver = new SoundObject(FilePath.Sound.GAMEOVER_SOUND);
    }

    public void update() {
        lastTime = System.currentTimeMillis();
        if (PauseSoundState.pauseSoundState == PauseSoundState.ON) {
            gameOver.start();
        }
        if (totalTime >= 10) {
            GameState.gameState = GameState.MENU;
        }
        totalTime += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
    }

    public void render(Graphics g) {
        g.drawImage(image, 100, 150, GAME_OVER_WIDTH, GAME_OVER_HEIGHT, null);
    }
}
