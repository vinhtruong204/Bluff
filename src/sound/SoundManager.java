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
    private SoundObject sBackground;
    // bomb explosion sound
    private SoundObject sBomb;
    // The sound of the player eats the heart
    private SoundObject sHeart;

    // Bombmanager
    private BombManager bombManager;
    // HeartManager
    private HeartManager heartManager;

    // constructor
    public SoundManager(BombManager bombManager, HeartManager heartManager) {
        this.bombManager = bombManager;
        this.heartManager = heartManager;
        sBackground = new SoundObject("sound/SoundBackground.wav");
    }

    public void resumeSound() {
        if (sBackground != null)
            sBackground.resume();
        if (sBomb != null)
            sBomb.resume();
        if (sHeart != null)
            sHeart.resume();
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
    private void SoundBomb() {
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
    private void SoundHeart() {
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
    private void SoundBackgroundMusic() {
        sBackground.start();
        sBackground.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // getter and setter
    public SoundObject getsBackground() {
        return sBackground;
    }

    public SoundObject getsBomb() {
        return sBomb;
    }

    public SoundObject getsHeart() {
        return sHeart;
    }

    public BombManager getBombManager() {
        return bombManager;
    }

    public HeartManager getHeartManager() {
        return heartManager;
    }

}
