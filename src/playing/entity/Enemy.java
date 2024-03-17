package playing.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Vector2D;
import helpmethods.EnemyConstants;
import helpmethods.WalkDirection;

public abstract class Enemy extends GameObject {
    protected final double MAX_DISTANCE_TRAVEL = 400.0d;;

    protected int enemyType;
    protected BufferedImage[][] animations;

    // Animation
    protected int aniType;
    protected int aniTick, aniIndex, aniSpeed = 4;

    // Motion
    protected Vector2D velocity;
    protected float enemySpeed;
    protected double leftBoundX, rightBoundX;
    protected double traveled; // distance traveled

    // Hit box
    protected Rectangle hitBox;

    // Walk direction
    protected WalkDirection direction;

    // Health of enemy
    protected int health;
    protected boolean hitPlayer;

    //
    protected int[][] map;

    public Enemy(int enemyType, int[][] map) {
        this.enemyType = enemyType;

        this.map = map;

        hitPlayer = false;

        enemySpeed = 1.0f;
        velocity = new Vector2D(enemySpeed, 0);
        traveled = 0.0d;

        // Initialize health depend on type of enemy
        initHealth();
    }

    private void initHealth() {
        switch (enemyType) {
            case EnemyConstants.CUCUMBER:
                health = 1;
                break;
            case EnemyConstants.CAPTAIN:
                health = 2;
                break;
            case EnemyConstants.BOLD_PIRATE:
                health = 3;
                break;
            case EnemyConstants.BIG_GUY:
                health = 4;
                break;
            case EnemyConstants.WHALE:
                health = 10;
                break;

            default:
                break;
        }
    }

    public int getHealth() {
        return health;
    }

    public boolean isHitPlayer() {
        return hitPlayer;
    }

    protected abstract void loadAni();

    // protected abstract void setAniType();

    protected abstract void updateAnimationTick();

    // protected abstract void upDatePosition();
}
