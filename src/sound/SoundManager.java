package sound;

import java.util.Iterator;

import javax.sound.sampled.Clip;

import helpmethods.CheckCollision;
import playing.entity.bomb.Bomb;
import playing.entity.bomb.BombManager;
import playing.entity.heart.Heart;
import playing.entity.heart.HeartManager;

public class SoundManager {
    // background music
    SoundObject sBackground;
    // bomb explosion sound
    SoundObject sBomb;
    // The sound of the player eats the heart
    SoundObject sHeart;

    // Bombmanager
    BombManager bombManager;
    // HeartManager
    HeartManager heartManager;

    // constructor
    public SoundManager(BombManager bombManager, HeartManager heartManager) {
        this.bombManager = bombManager;
        this.heartManager = heartManager;
        sBackground = new SoundObject("sound/SoundBackground.wav");
    }

    public void stopSound() {
        if (sBackground != null)
            sBackground.stop();
        if (sBomb != null)
            sBomb.stop();
        if (sHeart != null)
            sHeart.stop();
    }

    public void closeSound() {
        if (sBackground != null)
            sBackground.close();
        if (sBomb != null)
            sBomb.close();
        if (sHeart != null)
            sHeart.close();
    }

    // update
    public void update() {
        SoundBackgroundMusic();
        SoundBomb();
        SoundHeart();
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
    public void SoundHeart() {
        Iterator<Heart> itr = heartManager.getHearts().iterator();

        while (itr.hasNext()) {
            Heart heart = (Heart) itr.next();
            if (CheckCollision.isCollision(heart.getHitBox(), heartManager.getPlayer().getHitBox())) {
                sHeart = new SoundObject("sound/collect.wav");
                sHeart.start();
            }
        }
    }

    //
    public void SoundBackgroundMusic() {
        sBackground.start();
        sBackground.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
