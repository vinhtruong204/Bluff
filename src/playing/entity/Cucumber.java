package playing.entity;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import core.Vector2D;
import game.Game;
import helpmethods.*;
import helpmethods.EnemyConstants.CucumberConstants;
import playing.camera.Camera;
import playing.tile.Tile;

public class Cucumber extends Enemy {

    private boolean onGround = false;
    private int[][] map;

    public Cucumber(int enemyType, int i, int j, int[][] map) {
        super(enemyType);

        position = new Position(Tile.TILE_SIZE * j, Tile.TILE_SIZE * i);
        size = new Size(CucumberConstants.CUCUMBER_WIDTH, CucumberConstants.CUCUMBER_HEIGHT);
        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());

        velocity = new Vector2D(enemySpeed, 0);
        foresight = 500.0d;

        this.map = map;

        aniType = CucumberConstants.RUN;
        direction = WalkDirection.LEFT;
        loadAni();
    }

    @Override
    protected void loadAni() {
        // Max frame of all animation (10 type of animation and 36 frames max)
        animations = new BufferedImage[10][36];

        BufferedImage temp = LoadSave.loadImage("img/Enemy/Enemy-Cucumber.png");

        // Get all animation frames of enemy
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = temp.getSubimage(
                        j * CucumberConstants.CUCUMBER_WIDTH,
                        i * CucumberConstants.CUCUMBER_HEIGHT,
                        CucumberConstants.CUCUMBER_WIDTH,
                        CucumberConstants.CUCUMBER_HEIGHT);
            }
        }

    }

    @Override
    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= CucumberConstants.getSpriteAmount(aniType)) {
                aniIndex = 0;
            }
        }
    }

    private void setAniType(Rectangle playerHitbox) {
        // Initialize start animation type
        int startAni = aniType;

        // Set type of animation depend on current state
        aniType = CheckCollision.isCollision(hitBox, playerHitbox) ? CucumberConstants.ATTACK : CucumberConstants.RUN;

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
        Rectangle newHibox = new Rectangle((int) newPosition.getX(), (int) newPosition.getY(), size.getWidth(),
                size.getHeight());

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
        if (aniType == CucumberConstants.ATTACK) {
            // Change direction from position of player
            direction = playerHitbox.x <= hitBox.x ? WalkDirection.LEFT : WalkDirection.RIGHT;
            return;
        }

        // Set velocity depend on current direction
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

        // Calculate new position and hitbox of enemy
        Position newPosition = new Position(position.getX() + velocity.getX(),
                position.getY() + velocity.getY());
        Rectangle newHibox = new Rectangle(
                (int) newPosition.getX(),
                (int) newPosition.getY(),
                size.getWidth(),
                size.getHeight());

        // Update postion depend on direction
        switch (direction) {
            case LEFT:
                if (canMoveLeft(newHibox)) {
                    position = newPosition;
                    hitBox = newHibox;
                }

                // Change direction of enemy if can't move left
                else {
                    direction = WalkDirection.RIGHT;
                }

                break;
            case RIGHT:
                if (canMoveRight(newHibox)) {
                    position = newPosition;
                    hitBox = newHibox;
                }
                // Change direction of enemy if can't move right
                else {
                    direction = WalkDirection.LEFT;
                }

                break;
            default:
                break;
        }
    }

    private boolean canMoveLeft(Rectangle newHitbox) {
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

    private void updateHit(Rectangle playerHitbox) {
        hitPlayer = CheckCollision.isCollision(hitBox, playerHitbox) ? true : false;
    }

    public void update(Rectangle playerHitbox) {
        // Check hit the player
        updateHit(playerHitbox);

        // Set animation depend on current state
        setAniType(playerHitbox);

        // Set and update animation
        updateAnimationTick();

        // Update current position and hitBox
        upDatePosition(playerHitbox);
    }

    @Override
    public void render(Graphics g, Camera camera) {

        // Get current image rendrer
        BufferedImage temp = animations[aniType][aniIndex];

        // If enemy change move direction flip horizontal image
        if (direction == WalkDirection.RIGHT)
            temp = FlipImage.flipImage(temp);

        // Check cucumber if screen contain it and render
        if ((int) position.getX() - camera.getMapStartX() >= 0
                && (int) position.getX() - camera.getMapStartX() <= Game.SCREEN_WIDTH
                && (int) position.getY() - camera.getMapStartY() >= 0
                && (int) position.getY() - camera.getMapStartY() <= Game.SCREEN_HEIGHT) {
            // Draw hitbox
            g.setColor(Color.red);
            g.drawRect(hitBox.x - camera.getMapStartX(), hitBox.y - camera.getMapStartY(), hitBox.width, hitBox.height);

            // Draw cucumber
            g.drawImage(
                    temp,
                    (int) position.getX() - camera.getMapStartX(),
                    (int) position.getY() - camera.getMapStartY(),
                    size.getWidth(),
                    size.getHeight(),
                    null);
        }
    }

    @Override
    public void update() {
    }

}
