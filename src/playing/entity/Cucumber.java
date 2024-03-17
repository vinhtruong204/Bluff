package playing.entity;

import java.awt.Graphics;
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

    public Cucumber(int enemyType, int i, int j) {
        super(enemyType);
        position = new Position(Tile.TILE_SIZE * j, Tile.TILE_SIZE * i + 30.0f);
        size = new Size(CucumberConstants.CUCUMBER_WIDTH, CucumberConstants.CUCUMBER_HEIGHT);
        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        aniType = CucumberConstants.RUN;
        foresight = 200.0d;
        direction = WalkDirection.LEFT;
        velocity = new Vector2D(enemySpeed, 0);
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

    private void upDatePosition(Rectangle playerHitbox) {
        // System.out.println(seePlayer(playerHitbox));
        if (seePlayer(playerHitbox)) {

        }

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

        // If moved to a limited position
        if (traveled <= foresight) {
            traveled += enemySpeed;
        } else {
            traveled = 0.0f;
            direction = direction == WalkDirection.LEFT ? WalkDirection.RIGHT : WalkDirection.LEFT;
        }

        position = new Position(position.getX() + velocity.getX(), position.getY() + velocity.getY());
        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
    }

    private boolean seePlayer(Rectangle playerHitbox) {
        if (Math.abs(playerHitbox.y - hitBox.y) <= Tile.TILE_SIZE) {
            double distance = Math.pow(playerHitbox.y - hitBox.y, 2) +
                    Math.pow(playerHitbox.x - hitBox.x, 2);
            // System.out.println(distance);
            if (Math.sqrt(distance) <= foresight)
                return true;
        }
        return false;
    }

    public void update(Rectangle playerHitbox) {
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
