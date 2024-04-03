package playing.entity.enemy;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import helpmethods.CheckCollision;
import helpmethods.EnemyConstants.WhaleConstants;
import helpmethods.LoadSave;
import helpmethods.WalkDirection;
import playing.tile.Tile;

public class Whale extends Enemy {

    public Whale(int enemyType, int i, int j, int[][] map) {
        super(enemyType, map);

        // Init offset
        offsetX = 3;
        offsetY = 5;

        // Init position
        position = new Position(Tile.TILE_SIZE * j + offsetX, Tile.TILE_SIZE * i + offsetY);
        size = new Size(WhaleConstants.WHALE_WIDTH, WhaleConstants.WHALE_HEIGHT);
        hitBox = new Rectangle(
                (int) position.getX(),
                (int) position.getY(),
                size.getWidth() - offsetX * 2,
                size.getHeight() - offsetY);

        // Initialize boolean dea
        dead = false;

        // Init left and right bounds
        initBounds();

        // Init animation and direction
        aniType = WhaleConstants.RUN;
        direction = WalkDirection.LEFT;

        // Load all animation of the enemy
        loadAni();
    }

    @Override
    protected void loadAni() {
        // Max frame of all animation (10 type of animation and 36 frames max)
        animations = new BufferedImage[WhaleConstants.TOTAL_TYPE][WhaleConstants.TOTAL_FRAME];

        BufferedImage temp = LoadSave.loadImage("img/Enemy/Enemy-Whale.png");

        // Get all animation frames of enemy
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = temp.getSubimage(
                        j * WhaleConstants.WHALE_WIDTH,
                        i * WhaleConstants.WHALE_HEIGHT,
                        WhaleConstants.WHALE_WIDTH,
                        WhaleConstants.WHALE_HEIGHT);
            }
        }

    }

    @Override
    protected void updateAnimationTick(Rectangle playerHitbox) {
        aniTick++;
        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= WhaleConstants.getSpriteAmount(aniType)) {

                // Set animation type and dead
                switch (aniType) {
                    case WhaleConstants.DEAD_GROUND:
                        // Set dead to true when play all animation dead
                        dead = true;
                        break;
                    case WhaleConstants.ATTACK:
                        // Check if the attack hits the player
                        hitPlayer = CheckCollision.isCollision(hitBox, playerHitbox) ? true : false;
                        aniType = WhaleConstants.RUN;
                        break;
                    case WhaleConstants.DEAD_HIT:
                        // Set next animation is dead ground
                        aniType = WhaleConstants.DEAD_GROUND;
                        break;

                    default:
                        break;
                }

                // Reset index of an animation type
                aniIndex = 0;
            }
        }
    }

    @Override
    protected void setAniType() {
        // Initialize start animation type
        int startAni = aniType;

        // Set type of animation and speed ani depend on current state
        if (hitting) {
            aniType = WhaleConstants.ATTACK;
            aniSpeed = 1;
        } else if (health == 0) {
            aniType = WhaleConstants.DEAD_HIT;
            aniSpeed = 3;
        } else {
            aniType = WhaleConstants.RUN;
            aniSpeed = 3;
        }

        // If start anitype is not equal to startAni reset aniTick and aniIndex
        if (aniType != startAni) {
            // Reset animation index and animation tick
            aniTick = 0;
            aniIndex = 0;
        }
    }

    @Override
    public void update(Rectangle playerHitbox) {
        // Check hitting the player and reset hitplayer
        updateHitting(playerHitbox);
        hitPlayer = false;

        if (aniType != WhaleConstants.DEAD_HIT && aniType != WhaleConstants.DEAD_GROUND) {

            // Update current position and hitBox
            upDatePosition(playerHitbox);

            // Set animation depend on current state
            setAniType();
        }

        // Set and update animation
        updateAnimationTick(playerHitbox);

    }

    @Override
    public void update() {
    }
}
