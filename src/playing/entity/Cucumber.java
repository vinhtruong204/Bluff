package playing.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import core.Vector2D;
import helpmethods.*;
import helpmethods.EnemyConstants.CucumberConstants;
import playing.tile.Tile;

public class Cucumber extends Enemy {
    private float attackDistance;
    private Vector2D velocity;
    private float traveled; // distance traveled
    private int mapStartX, mapStartY;

    public Cucumber(int enemyType, int i, int j) {
        super(enemyType);
        position = new Position(Tile.TILE_SIZE * i, Tile.TILE_SIZE * j);
        size = new Size(CucumberConstants.CUCUMBER_WIDTH, CucumberConstants.CUCUMBER_HEIGHT);
        aniType = CucumberConstants.IDLE;
        attackDistance = 100.0f;
        velocity = new Vector2D(-1.0f, 0);
        loadAni();
    }

    private void setMapStartXY(int mapStartX, int mapStartY) {
        this.mapStartX = mapStartX;
        this.mapStartY = mapStartY;
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
            // flip image
            for (int i = 0; i < animations.length; i++) {
                for (int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = FlipImage.flipImage(animations[i][j]);
                }
            }
        }
    }

    public void update(int mapStartX, int mapStartY) {
        setMapStartXY(mapStartX, mapStartY);
        updateAnimationTick();
        upDatePosition();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(
                animations[aniType][aniIndex],
                (int) position.getX() - mapStartX,
                (int) position.getY() - mapStartY,
                size.getWidth(),
                size.getHeight(),
                null);
    }

    @Override
    public void update() {

    }

}
