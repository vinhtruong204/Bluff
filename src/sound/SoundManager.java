package sound;

import java.util.Iterator;

import javax.sound.sampled.Clip;

import helpmethods.CheckCollision;
import helpmethods.FilePath;
import pause_music_background.PauseMusicBackGroundState;
import pause_sound.PauseSoundState;
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

    // stop all sound
    public void stopSound() {
        if (mBackground != null)
            mBackground.stop();
        if (sBomb != null)
            sBomb.stop();
        if (sHeart != null)
            sHeart.stop();
    }

    // close all sound
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
        // if status BackGroundMusic is On
        if (PauseMusicBackGroundState.pauseMusicBackGroundState == PauseMusicBackGroundState.ON) {
            SoundBackgroundMusic();
        }
        if (PauseSoundState.pauseSoundState == PauseSoundState.ON) {
            // Play a bomb sound if you press the e key
            SoundBomb();
            // Plays a heart-eating sound if the player collides with the heart
            SoundHeart();
        }
    }

    // If there are bombs left and the bomb is in explosive state
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

    // If the player collides with the heart
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

    // play background sound
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
