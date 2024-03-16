package playing.entity.bomb;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import game.Game;
import helpmethods.BombConstants;
import helpmethods.LoadSave;
import playing.camera.Camera;
import playing.entity.GameObject;
import playing.tile.Tile;

public class Bomb extends GameObject {
    private BufferedImage animations[][];
    private final int BOMB_WIDTH = 80;
    private final int BOMB_HEIGHT = 80;
    private int aniType;

    private int aniTick, aniIndex, aniSpeed;
    private long currentTime, afterTime;

    public Bomb(int i, int j) {
        animations = new BufferedImage[3][6];
        size = new Size(BOMB_WIDTH * 2, BOMB_HEIGHT * 2);
        position = new Position(i * Tile.TILE_SIZE - 30.0f, j * Tile.TILE_SIZE);
        aniSpeed = 1;
        currentTime = System.currentTimeMillis() / 1000;
        afterTime = 0;
        loadAnimations();
    }

    private void loadAnimations() {
        BufferedImage image = LoadSave.loadImage("img/Player/Bomb.png");
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = image.getSubimage(j * BOMB_WIDTH, i * BOMB_HEIGHT, BOMB_WIDTH, BOMB_HEIGHT);
    }

    private void setAniType() {
        afterTime = System.currentTimeMillis() / 1000;
        if (afterTime - currentTime <= 1) {
            aniType = BombConstants.PLACINGBOMB;
            aniTick = 0;
            aniIndex = 0;
        } else if (afterTime - currentTime > 1 && afterTime - currentTime <= 2) {
            aniType = BombConstants.ACTIVATINGBOMB;
            aniTick = 0;
            aniIndex = 1;
        } else if (afterTime - currentTime > 2) {
            aniType = BombConstants.EXPLODINGBOMB;
            aniTick = 0;
            aniIndex = 2;
        }
    }

    private void updateAnimationTick() {
        // 60fps => 20 animation frames rendered
        aniTick++;

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= BombConstants.getSpriteAmount(aniType)) {
                aniIndex = 0;
            }
        }
    }

    public int getAniType() {
        return aniType;
    }

    public void setAniType(int aniType) {
        this.aniType = aniType;
    }

    public long getAfterTime() {
        return afterTime;
    }

    public void setAfterTime(long afterTime) {
        this.afterTime = afterTime;
    }

    @Override
    public void update() {
        setAniType();
        updateAnimationTick();
    }

    @Override
    public void render(Graphics g, Camera camera) {
        if ((int) position.getX() - camera.getMapStartX() >= 0
                && (int) position.getX() - camera.getMapStartX() <= Game.SCREEN_WIDTH
                && (int) position.getY() - camera.getMapStartY() >= 0
                && (int) position.getY() - camera.getMapStartY() <= Game.SCREEN_HEIGHT) {
            g.drawImage(animations[aniType][aniIndex], (int) position.getX() - camera.getMapStartX(),
                    (int) position.getY() - camera.getMapStartY(), size.getWidth(), size.getHeight(), null);
        }
    }

}
