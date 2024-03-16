package playing.entity.bomb;

import java.awt.Graphics;
import java.awt.Rectangle;
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

    private boolean exploded;
    private Rectangle hitBox;

    public Bomb(int i, int j) {
        animations = new BufferedImage[3][6];
        size = new Size(BOMB_WIDTH * 2, BOMB_HEIGHT * 2);
        position = new Position(i * Tile.TILE_SIZE - 30.0f, j * Tile.TILE_SIZE);
        hitBox = new Rectangle((int) position.getX(), (int) position.getX(), size.getWidth(), size.getHeight());
        aniSpeed = 3;
        currentTime = System.currentTimeMillis();
        aniType = BombConstants.PLACINGBOMB;
        afterTime = 0;
        exploded = false;
        loadAnimations();
    }

    private void loadAnimations() {
        BufferedImage image = LoadSave.loadImage("img/Player/Bomb.png");
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = image.getSubimage(j * BOMB_WIDTH, i * BOMB_HEIGHT, BOMB_WIDTH, BOMB_HEIGHT);
    }

    private void setAniType() {
        int startAni = aniType;

        afterTime = System.currentTimeMillis();
        if (afterTime - currentTime <= 500) {
            aniType = BombConstants.PLACINGBOMB;
        } else if (afterTime - currentTime > 500 && afterTime - currentTime <= 3000) {
            aniType = BombConstants.ACTIVATINGBOMB;
        } else if (afterTime - currentTime > 3000) {
            aniType = BombConstants.EXPLODINGBOMB;

        }

        if (startAni != aniType) {
            aniTick = 0;
            aniIndex = 0;
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
                if (aniType == BombConstants.EXPLODINGBOMB)
                    exploded = true;
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

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
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
