package playing.entity.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import game.Game;
import helpmethods.CheckCollision;
import helpmethods.EnemyConstants.BoldPirateConstants;
import helpmethods.FlipImage;
import helpmethods.LoadSave;
import helpmethods.WalkDirection;
import playing.camera.Camera;
import playing.tile.Tile;

public class BoldPirate extends Enemy {

    public BoldPirate(int enemyType, int i, int j, int[][] map) {
        super(enemyType, map);

        // Init offset
        offsetX = 9;
        offsetY = 9;

        // Init position
        position = new Position(Tile.TILE_SIZE * j + offsetX, Tile.TILE_SIZE * i + offsetY);
        size = new Size(BoldPirateConstants.BOLD_PIRATE_WIDTH, BoldPirateConstants.BOLD_PIRATE_HEIGHT);
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
        aniType = BoldPirateConstants.RUN;
        direction = WalkDirection.LEFT;

        // Load all animation of the enemy
        loadAni();
    }

    @Override
    protected void loadAni() {
        // Max frame of all animation (10 type of animation and 36 frames max)
        animations = new BufferedImage[BoldPirateConstants.TOTAL_TYPE][BoldPirateConstants.TOTAL_FRAME];

        BufferedImage temp = LoadSave.loadImage("img/Enemy/Enemy-Bold Pirate.png");

        // Get all animation frames of enemy
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = temp.getSubimage(
                        j * BoldPirateConstants.BOLD_PIRATE_WIDTH,
                        i * BoldPirateConstants.BOLD_PIRATE_HEIGHT,
                        BoldPirateConstants.BOLD_PIRATE_WIDTH,
                        BoldPirateConstants.BOLD_PIRATE_HEIGHT);
            }
        }

    }

    @Override
    protected void updateAnimationTick(Rectangle playerHitbox) {
        aniTick++;
        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= BoldPirateConstants.getSpriteAmount(aniType)) {

                // Set animation type and dead
                switch (aniType) {
                    case BoldPirateConstants.DEAD_GROUND:
                        // Set dead to true when play all animation dead
                        dead = true;
                        break;
                    case BoldPirateConstants.ATTACK:
                        // Check if the attack hits the player
                        hitPlayer = CheckCollision.isCollision(hitBox, playerHitbox) ? true : false;
                        aniType = BoldPirateConstants.RUN;
                        break;
                    case BoldPirateConstants.DEAD_HIT:
                        // Set next animation is dead ground
                        aniType = BoldPirateConstants.DEAD_GROUND;
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
            aniType = BoldPirateConstants.ATTACK;
            aniSpeed = 1;
        } else if (health == 0) {
            aniType = BoldPirateConstants.DEAD_HIT;
            aniSpeed = 3;
        } else {
            aniType = BoldPirateConstants.RUN;
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

        if (aniType != BoldPirateConstants.DEAD_HIT && aniType != BoldPirateConstants.DEAD_GROUND) {

            // Update current position and hitBox
            upDatePosition(playerHitbox);

            // Set animation depend on current state
            setAniType();
        }

        // Set and update animation
        updateAnimationTick(playerHitbox);

    }

    @Override
    public void render(Graphics g, Camera camera) {

        // Get current image rendrer
        BufferedImage temp = animations[aniType][aniIndex];

        // If enemy change move direction flip horizontal image
        if (direction == WalkDirection.LEFT)
            temp = FlipImage.flipImage(temp);

        // Check cucumber if screen contain it and render
        if ((int) position.getX() - camera.getMapStartX() >= 0
                && (int) position.getX() - camera.getMapStartX() <= Game.SCREEN_WIDTH
                && (int) position.getY() - camera.getMapStartY() >= 0
                && (int) position.getY() - camera.getMapStartY() <= Game.SCREEN_HEIGHT) {
            // Draw hitbox
            g.setColor(Color.red);
            g.drawRect(hitBox.x - camera.getMapStartX(), hitBox.y - camera.getMapStartY(), hitBox.width, hitBox.height);

            // Draw cucumber minus offset
            g.drawImage(
                    temp,
                    (int) position.getX() - offsetX - camera.getMapStartX(),
                    (int) position.getY() - offsetY - camera.getMapStartY(),
                    size.getWidth(),
                    size.getHeight(),
                    null);
        }
    }

    @Override
    public void update() {
    }
}
