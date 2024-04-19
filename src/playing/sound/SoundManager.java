package playing.sound;

import java.util.Iterator;

import javax.sound.sampled.Clip;

import helpmethods.CheckCollision;
import helpmethods.FilePath;
import playing.entity.Player;
import playing.entity.bomb.Bomb;
import playing.entity.bomb.BombManager;
import playing.entity.door.DoorManager;
import playing.entity.enemy.Enemy;
import playing.entity.enemy.EnemyManager;
import playing.entity.heart.Heart;
import playing.entity.heart.HeartManager;
import playing.sound.pause_music_background.PauseMusicBackGroundState;
import playing.sound.pause_sound.PauseSoundState;

public class SoundManager {
    // background music
    private SoundObject mBackground;
    // bomb explosion sound
    private SoundObject sBomb;
    // The sound of the player eats the heart
    private SoundObject sHeart;
    // The Sound of enemy attack
    private SoundObject sEnemyAttack;
    // The Sound of new Map
    private SoundObject sNewMap;
    // The Sound of jumping
    private SoundObject sJumpPlayer;

    // Bombmanager
    private BombManager bombManager;
    // HeartManager
    private HeartManager heartManager;
    // EnemyManager
    private EnemyManager enemyManager;
    //DoorManager
    private DoorManager doorManager;
    //Player
    private Player player;

    // constructor
    public SoundManager(BombManager bombManager, HeartManager heartManager, EnemyManager enemyManager,DoorManager doorManager,Player player) {
        this.bombManager = bombManager;
        this.heartManager = heartManager;
        this.enemyManager = enemyManager;
        this.doorManager = doorManager;
        this.player = player;
        mBackground = new SoundObject(FilePath.Sound.MUSIC_BACKGROUND);
        sNewMap = new SoundObject(FilePath.Sound.SOUND_NEW_MAP);
    }

    // stop all sound
    public void stopSound() {
        if(mBackground != null)
            mBackground.stop();
        if (sBomb != null)
            sBomb.stop();
        if (sHeart != null)
            sHeart.stop();
        if (sEnemyAttack != null)
            sEnemyAttack.stop();
        if (sNewMap != null)
            sNewMap.stop();
        if(sJumpPlayer != null)
            sJumpPlayer.stop();
    }

    // close all sound
    public void closeSound() {
        if (mBackground != null)
            mBackground.close();
        if (sBomb != null)
            sBomb.close();
        if (sHeart != null)
            sHeart.close();
        if (sEnemyAttack != null)
            sEnemyAttack.close();
        if (sNewMap != null)
            sNewMap.close();
        if(sJumpPlayer != null)
            sJumpPlayer.close();
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
            // Enemy attack
            SoundEnemyAttack();

            SoundNewMap();

            SoundJumpPlayer();
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

    // If the enemy Attack
    private void SoundEnemyAttack() {
        Iterator<Enemy> itr = enemyManager.getEnemies().iterator();

        while (itr.hasNext()) {
            Enemy enemy = (Enemy) itr.next();
            if (enemy.isHitPlayer()) {
                sEnemyAttack = new SoundObject(FilePath.Sound.ENEMY_ATTACK);
                sEnemyAttack.start();
            }
        }
    }

    // if over new Map
    private void SoundNewMap(){
        if(doorManager.getDoor().isOpen()){
            sNewMap.start();
        }
    }

    // if Player is jumping
    private void SoundJumpPlayer(){
        if(player.isJumping() && player.isOnGround()){
            sJumpPlayer = new SoundObject(FilePath.Sound.SOUND_JUMP_PLAYER);
            sJumpPlayer.start();
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

    public SoundObject getsEnemyAttack() {
        return sEnemyAttack;
    }

    public SoundObject getsNewMap() {
        return sNewMap;
    }

    public SoundObject getsJumpPlayer() {
        return sJumpPlayer;
    }

}
