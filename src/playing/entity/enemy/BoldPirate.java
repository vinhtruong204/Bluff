package playing.entity.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import core.Vector2D;
import game.Game;
import helpmethods.CheckCollision;
import helpmethods.EnemyConstants.BoldPirateConstants;
import helpmethods.FlipImage;
import helpmethods.LoadSave;
import helpmethods.WalkDirection;
import playing.camera.Camera;
import playing.tile.Tile;

public class BoldPirate extends Enemy {
    private int offsetX = 9;
    private int offsetY = 9;
    private boolean onGround = false;

    public BoldPirate(int enemyType, int i, int j, int[][] map) {
        super(enemyType, map);

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

    private void initBounds() {
        // Expected bound
        leftBoundX = position.getX() - MAX_DISTANCE_TRAVEL;
        rightBoundX = position.getX() + MAX_DISTANCE_TRAVEL;
    }

    @Override
    protected void loadAni() {
        // Max frame of all animation (10 type of animation and 36 frames max)
        animations = new BufferedImage[BoldPirateConstants.TOTAL_TYPE][BoldPirateConstants.TOTAL_MAX_FRAME];

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

        // Set type of animation depend on current state
        if (hitting)
            aniType = BoldPirateConstants.ATTACK;
        else if (health == 0)
            aniType = BoldPirateConstants.DEAD_HIT;
        else
            aniType = BoldPirateConstants.RUN;

        // If start anitype is not equal to startAni reset aniTick and aniIndex
        if (aniType != startAni) {
            // Reset animation index and animation tick
            aniTick = 0;
            aniIndex = 0;
        }
    }

    private void fall() {
        // Set gravity
        velocity = new Vector2D(0.0f, enemySpeed);

        // Calculate new position and hibox
        Position newPosition = new Position(position.getX() + velocity.getX(), position.getY() + velocity.getY());
        Rectangle newHibox = new Rectangle(
                (int) newPosition.getX(),
                (int) newPosition.getY(),
                size.getWidth() - offsetX * 2,
                size.getHeight() - offsetY);

        // If enemy is onground
        if (CheckCollision.isEntityOnground(map, newHibox)) {

            // Set onground is true
            onGround = true;

            // Reset velocity
            velocity = new Vector2D(enemySpeed, 0.0f);

        } else {
            // Update position and hit box if enemy continue in air
            position = newPosition;
            hitBox = newHibox;
        }
    }

    private void upDatePosition(Rectangle playerHitbox) {
        // If the enemy in air
        if (!onGround) {
            // Move down
            fall();
            return;
        }

        // If enemy colliding with player
        if (aniType == BoldPirateConstants.ATTACK) {
            // Change direction from position of player
            direction = playerHitbox.x <= hitBox.x ? WalkDirection.LEFT : WalkDirection.RIGHT;
            return;
        }

        // Set velocity depend on current direction
        setDirection(playerHitbox);

        // Calculate new position and hitbox of enemy
        Position newPosition = new Position(position.getX() + velocity.getX(),
                position.getY() + velocity.getY());
        Rectangle newHibox = new Rectangle(
                (int) newPosition.getX(),
                (int) newPosition.getY(),
                size.getWidth() - offsetX * 2,
                size.getHeight() - offsetY);

        // Update postion depend on direction and in bounds
        switch (direction) {
            case LEFT:
                if (canMoveLeft(newHibox) && newPosition.getX() >= leftBoundX) {
                    position = newPosition;
                    hitBox = newHibox;
                }

                // Change direction of enemy if can't move left
                else {
                    // Get actual max left bound
                    leftBoundX = position.getX();
                    direction = WalkDirection.RIGHT;
                }

                break;
            case RIGHT:
                if (canMoveRight(newHibox) && newPosition.getX() <= rightBoundX) {
                    position = newPosition;
                    hitBox = newHibox;
                }
                // Change direction of enemy if can't move right
                else {
                    // Get actual max right bound
                    rightBoundX = position.getX();
                    direction = WalkDirection.LEFT;
                }

                break;
            default:
                break;
        }
    }

    private void setDirection(Rectangle playerHitbox) {
        // If enemy saw the player
        if (seePlayer(playerHitbox)) {
            // Change direction depend on position of the player
            direction = playerHitbox.x <= hitBox.x ? WalkDirection.LEFT : WalkDirection.RIGHT;
        }

        // Set velocity depend on direction of enemy
        switch (direction) {
            case LEFT:
                velocity.setX(-enemySpeed);
                break;
            case RIGHT:
                velocity.setX(enemySpeed);
                break;
            default:
                break;
        }
    }

    private boolean seePlayer(Rectangle playerHitbox) {
        // If within the enemy's line of sight and the distance is less than 1 tile
        if (Math.abs((playerHitbox.y + playerHitbox.height) - (hitBox.y + hitBox.height)) <= Tile.TILE_SIZE) {
            if (playerHitbox.x >= leftBoundX && playerHitbox.x <= rightBoundX) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveLeft(Rectangle newHitbox) {
        // Get current index
        int colIndex = newHitbox.x / Tile.TILE_SIZE;
        int rowIndex = (newHitbox.y + newHitbox.height) / Tile.TILE_SIZE;

        /*
         * 1: solid tile; 5: enemy; 0: background;
         * 1 1 1
         * 0 5 0
         * 0 1 1
         */

        // Ahead is a wall or abyss
        if (CheckCollision.isTileSolid(map[rowIndex][colIndex])
                || !CheckCollision.isTileSolid(map[rowIndex + 1][colIndex])) {
            return false;
        }

        return true;
    }

    private boolean canMoveRight(Rectangle newHitbox) {
        int colIndex = (newHitbox.x + newHitbox.width) / Tile.TILE_SIZE;
        int rowIndex = (newHitbox.y + newHitbox.height) / Tile.TILE_SIZE;

        /*
         * 1: solid tile; 5: enemy; 0: background;
         * 1 1 1
         * 0 5 0
         * 0 1 1
         */

        // Ahead is a wall or abyss
        if (CheckCollision.isTileSolid(map[rowIndex][colIndex])
                || !CheckCollision.isTileSolid(map[rowIndex + 1][colIndex])) {
            return false;
        }

        return true;
    }

    private void updateHitting(Rectangle playerHitbox) {
        hitting = CheckCollision.isCollision(hitBox, playerHitbox) ? true : false;
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
