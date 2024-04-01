package sound;

import java.util.Iterator;

import javax.sound.sampled.Clip;

import playing.entity.bomb.Bomb;
import playing.entity.bomb.BombManager;

public class SoundManager {
    // background music
    SoundObject sBackground;
    // bomb explosion sound
    SoundObject sBomb;
    // The sound of the player eats the heart
    SoundObject sHeart;

    // Bombmanager
    BombManager bombManager;

    // constructor
    public SoundManager(BombManager bombManager) {
        this.bombManager = bombManager;
        sBackground = new SoundObject("sound/SoundBackground.wav");
    }

    // update
    public void update() {
        SoundBackgroundMusic();
        SoundBomb();
    }

    //
    public void SoundBomb() {
        Iterator<Bomb> itrBomb = bombManager.getBombs().iterator();
        while (itrBomb.hasNext()) {
            Bomb bomb = (Bomb) itrBomb.next();
            if (bomb.isExploded()) {
                sBomb = new SoundObject("sound/SoundBomb.wav");
                sBomb.start();
            }
        }
    }

    //
    public void SoundBackgroundMusic() {
        sBackground.start();
        sBackground.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
