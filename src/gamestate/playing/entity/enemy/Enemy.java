package gamestate.playing.entity.enemy;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Position;
import core.Vector2D;
import game.Game;
import game.GamePanel;
import gamestate.playing.camera.Camera;
import gamestate.playing.entity.GameObject;
import gamestate.playing.level.Tile;
import helpmethods.CheckCollision;
import helpmethods.EnemyConstants;
import helpmethods.EnemyConstants.CucumberConstants;
import helpmethods.FlipImage;
import helpmethods.WalkDirection;

public abstract class Enemy extends GameObject {
    // Visible distance
    protected final double MAX_DISTANCE_TRAVEL = 1000.0d;

    // Speed of enemy
    protected final float NORMAL_SPEED = 1.0f;
    protected final float MAX_SPEED = 3.5f;
    protected final float OFFSET_SPEED = 0.5f;

    // Offset of hitbox
    protected int offsetX;
    protected int offsetY;

    protected int enemyType;
    protected BufferedImage[][] animations;

    // Animation
    protected int aniType;
    protected int aniTick, aniIndex, aniSpeed;

    // Move
    protected Vector2D velocity;
    protected float enemySpeed;
    protected double leftBoundX, rightBoundX;
    protected double traveled; // distance traveled
    protected boolean onGround;

    // Hit box
    protected Rectangle hitBox;

    // Walk direction
    protected WalkDirection direction;

    // Health of enemy
    protected int health;

    // dead
    protected boolean dead;

    // Injured
    protected boolean injured;

    //
    protected boolean hitting;

    protected boolean hitPlayer;

    // Map
    protected int[][] map;

    // Position help render method
    protected Position oldPos;
    protected Position renderPos;

    public Enemy(int enemyType, int[][] map) {
        this.enemyType = enemyType;

        this.map = map;

        hitPlayer = false;
        hitting = false;

        enemySpeed = NORMAL_SPEED;
        velocity = new Vector2D(0.0f, 0.0f);
        traveled = 0.0d;
        position = new Position(0.0f, 0.0f);
        oldPos = new Position(position.getX(), position.getY());
        renderPos = new Position(position.getX(), position.getY());
        onGround = false;

        // Default animation speed
        aniSpeed = 3;

        // Initialize health depend on type of enemy
        initHealth();
    }

    private void initHealth() {
        switch (enemyType) {
            case EnemyConstants.CUCUMBER:
                health = 1;
                break;
            case EnemyConstants.CAPTAIN:
                health = 5;
                break;
            case EnemyConstants.BOLD_PIRATE:
                health = 3;
                break;
            case EnemyConstants.BIG_GUY:
                health = 4;
                break;
            case EnemyConstants.WHALE:
                health = 2;
                break;
            case EnemyConstants.ARCHER_BLUE:
                health = 5;
                break;
            case EnemyConstants.ARCHER_PURPLE:
                health = 5;
                break;
            case EnemyConstants.ARCHER_YELLOW:
                health = 5;
                break;
            case EnemyConstants.ARCHER_RED:
                health = 5;
                break;
            default:
                break;
        }
    }

    protected void initBounds() {
        // Expected bound
        leftBoundX = position.getX() - MAX_DISTANCE_TRAVEL;
        rightBoundX = position.getX() + MAX_DISTANCE_TRAVEL;
    }

    private void fall() {
        // Set gravity
        velocity.setY(enemySpeed);

        // Calculate new position and hibox
        Position newPosition = new Position(position.getX(), position.getY() + velocity.getY());
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

    protected void upDatePosition(Rectangle playerHitbox) {
        // Init old pos
        oldPos = position;

        // If the enemy in air
        if (!onGround) {
            // Move down
            fall();
            return;
        } 

        // If enemy colliding with player
        if (aniType == CucumberConstants.ATTACK) {
            // Change direction from position of player
            direction = playerHitbox.x <= hitBox.x ? WalkDirection.LEFT : WalkDirection.RIGHT;
            return;
        }

        // Set velocity depend on current direction
        setDirection(playerHitbox);

        // Calculate new position and hitbox of enemy
        Position newPosition = new Position(position.getX() + velocity.getX(),
                position.getY());
        Rectangle newHitbox = new Rectangle(
                (int) newPosition.getX(),
                (int) newPosition.getY(),
                size.getWidth() - offsetX * 2,
                size.getHeight() - offsetY);

        // Update postion depend on direction and in bounds
        switch (direction) {
            case LEFT:
                if (canMove(newHitbox.x, newHitbox.y + newHitbox.height)
                        && newPosition.getX() >= leftBoundX) {
                    position = newPosition;
                    hitBox = newHitbox;
                }

                // Change direction of enemy if can't move left
                else {
                    // Get actual max left bound
                    leftBoundX = hitBox.x;
                    direction = WalkDirection.RIGHT;
                }

                break;
            case RIGHT:
                if (canMove(newHitbox.x + newHitbox.width, newHitbox.y + newHitbox.height)
                        && newPosition.getX() <= rightBoundX) {
                    position = newPosition;
                    hitBox = newHitbox;
                }

                // Change direction of enemy if can't move right
                else {
                    // Get actual max right bound
                    rightBoundX = hitBox.x + hitBox.width;
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
            // Change direction and increase speed depend on position of the player
            direction = playerHitbox.x <= hitBox.x ? WalkDirection.LEFT : WalkDirection.RIGHT;

            // Increase speed if it not reach max speed
            if (enemySpeed <= MAX_SPEED)
                enemySpeed += OFFSET_SPEED;
        } else
            // Reset speed after player not visible
            enemySpeed = NORMAL_SPEED;

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
            if ((playerHitbox.x >= leftBoundX && playerHitbox.x <= rightBoundX)
                    || (playerHitbox.x + playerHitbox.width >= leftBoundX
                            && playerHitbox.x + playerHitbox.width <= rightBoundX)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMove(int x, int y) {
        int colIndex = x / Tile.TILE_SIZE;
        int rowIndex = y / Tile.TILE_SIZE;

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

    protected void updateHitting(Rectangle playerHitbox) {
        hitting = CheckCollision.isCollision(hitBox, playerHitbox) ? true : false;
    }

    @Override
    public void render(Graphics g, Camera camera) {

        // Get current image rendrer
        BufferedImage temp = animations[aniType][aniIndex];

        // If enemy change move direction flip horizontal image
        if (direction == WalkDirection.LEFT)
            temp = FlipImage.horizontalflip(temp);
            
        // If enemy isn't moving
        if (oldPos.compareTo(position) == 0) {
            renderPos = position;
        }
        // Calculate render position
        else {
            renderPos.setX(oldPos.getX() + velocity.getX() * GamePanel.interpolation);
            renderPos.setY(oldPos.getY() + velocity.getY() * GamePanel.interpolation);
        }

        // Render the bomb if it is in the screen
        if ((int) renderPos.getX() - camera.getMapStartX() >= 0
                && (int) renderPos.getX() - camera.getMapStartX() <= Game.SCREEN_WIDTH
                && (int) renderPos.getY() - camera.getMapStartY() >= 0
                && (int) renderPos.getY() - camera.getMapStartY() <= Game.SCREEN_HEIGHT)
            // Render image
            g.drawImage(temp,
                    (int) renderPos.getX() - camera.getMapStartX() - offsetX,
                    (int) renderPos.getY() - camera.getMapStartY() - offsetY,
                    size.getWidth(),
                    size.getHeight(),
                    null);
    }

    // Getter and Setter
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isHitPlayer() {
        return hitPlayer;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isInjured() {
        return injured;
    }

    public void setInjured(boolean injured) {
        this.injured = injured;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }
    protected abstract void loadAni();

    public abstract void update(Rectangle playerHitBox);

    protected abstract void setAniType();

    protected abstract void updateAnimationTick(Rectangle playerHitBox);

    // protected abstract void upDatePosition();
}
