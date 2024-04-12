package sound;

import java.util.Iterator;

import javax.sound.sampled.Clip;

import helpmethods.CheckCollision;
import helpmethods.FilePath;
import playing.entity.bomb.Bomb;
import playing.entity.bomb.BombManager;
import playing.entity.heart.Heart;
import playing.entity.heart.HeartManager;

public class SoundManager {
    // background music
    private SoundObject mBackground;
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
        mBackground = new SoundObject(FilePath.Sound.MUSIC_BACKGROUND);
    }

    public void resumeSound() {
        if (mBackground != null)
            mBackground.resume();
        if (sBomb != null)
            sBomb.resume();
        if (sHeart != null)
            sHeart.resume();
    }

    public void stopSound() {
        if (mBackground != null)
            mBackground.stop();
        if (sBomb != null)
            sBomb.stop();
        if (sHeart != null)
            sHeart.stop();
    }

    public void closeSound() {
        if (mBackground != null)
            mBackground.close();
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
                sBomb = new SoundObject(FilePath.Sound.BOMB_SOUND);
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
                sHeart = new SoundObject(FilePath.Sound.COLLECTED_HEART_SOUND);
                sHeart.start();
            }
        }
    }

    //
    private void SoundBackgroundMusic() {
        mBackground.start();
        mBackground.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // getter and setter
    public SoundObject getmBackground() {
        return mBackground;
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
