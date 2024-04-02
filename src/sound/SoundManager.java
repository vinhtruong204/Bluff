package sound;

import java.util.Iterator;

import playing.entity.bomb.Bomb;
import playing.entity.bomb.BombManager;

public class SoundManager {
    SoundBackground sBackground;
    SoundItem sBomb;
    SoundItem sHeart;

    BombManager bombManager;

    public SoundManager(BombManager bombManager) {
        this.bombManager = bombManager;
        sBackground = new SoundBackground("sound/SoundBackground.wav");
    }

    public void update() {
        SoundBackground();
        SoundBomb();
    }

    public void SoundBomb() {
        Iterator<Bomb> itrBomb = bombManager.getBombs().iterator();
        while (itrBomb.hasNext()) {
            Bomb bomb = (Bomb) itrBomb.next();
            if (bomb.isExploded()) {
                sBomb = new SoundItem("sound/SoundBomb.wav");
                sBomb.start();
            }
        }
    }

    public void SoundBackground() {
        sBackground.start();
        sBackground.loop();
    }

}
