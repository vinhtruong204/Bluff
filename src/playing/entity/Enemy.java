package playing.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Vector2D;

public abstract class Enemy extends GameObject {

    protected int enemyType;
    protected BufferedImage[][] animations;

    // Animation
    protected int aniType;
    protected int aniTick, aniIndex, aniSpeed = 4;
    protected boolean changeDirection;

    // Motion
    protected float attackDistance;
    protected Vector2D velocity;
    protected float traveled; // distance traveled\

    // Hit box
    protected Rectangle hitBox;

    public Enemy(int enemyType) {
        this.enemyType = enemyType;
    }

    protected abstract void loadAni();

    // protected abstract void setAniType();

    protected abstract void updateAnimationTick();

    protected abstract void upDatePosition();
}
