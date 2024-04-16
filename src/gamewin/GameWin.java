package gamewin;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import gamestate.GameState;
import helpmethods.FilePath;
import helpmethods.LoadSave;
import pause_sound.PauseSoundState;
import sound.SoundObject;

public class GameWin {
    //method
    private BufferedImage image;
    private SoundObject gameOver;
    private long currentTime;

    //GameWin WIDTH
    private final int GAME_WIN_WIDTH = 490;
    //GameWin HEIGHT
    private final int GAME_WIN_HEIGHT = 160;

    //contructor of gameWin
    public GameWin() {
        image = LoadSave.loadImage(FilePath.GameWin.GAME_WIN);
        gameOver = new SoundObject(FilePath.Sound.GAMEWIN_SOUND);
    }

    //render
    public void update() {
        //if PauseSoundState = ON
        if (PauseSoundState.pauseSoundState == PauseSoundState.ON) {
            gameOver.start();
        }
        // after 6s
        if (System.currentTimeMillis() - currentTime >= 6000) {
            GameState.gameState = GameState.MENU;
        }
    }

    //render
    public void render(Graphics g) {
        g.drawImage(image, 100, 150, GAME_WIN_WIDTH, GAME_WIN_HEIGHT, null);
    }

    //getter and setter
    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}
