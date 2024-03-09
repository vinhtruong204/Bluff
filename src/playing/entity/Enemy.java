package playing.entity;

import java.awt.image.BufferedImage;

public abstract class Enemy extends GameObject {

    protected int enemyType;
    protected BufferedImage[][] animations;

    // Animation 
    protected int aniType;
    protected int aniTick, aniIndex, aniSpeed = 4;

    public Enemy(int enemyType) {
        this.enemyType = enemyType;
    }

    protected abstract void loadAni();

    protected abstract void updateAnimationTick();

    protected abstract void upDatePosition();
}
