package playing.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import core.Vector2D;
import game.Game;
import helpmethods.*;
import helpmethods.EnemyConstants.CucumberConstants;
import playing.camera.Camera;
import playing.tile.Tile;
import playing.entity.Player;

public class Cucumber extends Enemy {
    private float attackDistance;
    private Vector2D velocity;
    private float traveled; // distance traveled
    private boolean changeDirection;

    public Cucumber(int enemyType, int i, int j) {
        super(enemyType);
        position = new Position(Tile.TILE_SIZE * j, Tile.TILE_SIZE * i + 30.0f);
        size = new Size(CucumberConstants.CUCUMBER_WIDTH, CucumberConstants.CUCUMBER_HEIGHT);
        aniType = CucumberConstants.RUN;
        attackDistance = 200.0f;
        changeDirection = false;
        velocity = new Vector2D(-1.0f, 0);
        loadAni();
    }

    @Override
    protected void loadAni() {
        // Max frame of all animation (10 type of animation and 36 frames max)
        animations = new BufferedImage[10][36];

        BufferedImage temp = LoadSave.loadImage("img/Enemy/Enemy-Cucumber.png");

        //
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

    @Override
    protected void upDatePosition() {
        if (traveled <= attackDistance) {
            traveled += Math.abs(velocity.getX());
            position.setX(position.getX() + velocity.getX());
        } else {
            traveled = 0.0f;
            velocity.setX(-velocity.getX());
            changeDirection = !changeDirection;
        }
    }

    @Override
    public void update() {
        updateAnimationTick();
        upDatePosition();
    }

    @Override
    public void render(Graphics g, Camera camera) {
        BufferedImage temp = animations[aniType][aniIndex];
        if (changeDirection)
            temp = FlipImage.flipImage(temp);
        //System.out.println(changeDirection);
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

}
