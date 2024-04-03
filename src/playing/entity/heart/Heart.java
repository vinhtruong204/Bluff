package playing.entity.heart;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import game.Game;
import helpmethods.HeartConstants;
import helpmethods.LoadSave;
import playing.camera.Camera;
import playing.entity.GameObject;
import playing.level.Tile;

public class Heart extends GameObject {
    private final int HEART_WIDTH = 32;
    private final int HEART_HEIGHT = 32;

    private Rectangle hitBox;
    private BufferedImage animations[][];

    private int aniTick, aniIndex, aniSpeed;
    private int aniType;
    public Heart(int anitype,int i, int j) {
        position = new Position(j * Tile.TILE_SIZE, i * Tile.TILE_SIZE);
        size = new Size(HEART_WIDTH, HEART_HEIGHT);
        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        animations = new BufferedImage[2][22];
        this.aniType=anitype;
        aniSpeed = 3;
        aniTick=0;
        aniIndex=0;
        loadAnimations();
    }

    private void loadAnimations() {
        BufferedImage image = LoadSave.loadImage("img/Object/Object-Heart.png");
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * HEART_WIDTH, i * HEART_HEIGHT, HEART_WIDTH, HEART_HEIGHT);
            }
    }

    private void updateAnimationTick() {
        // 60fps => 20 animation frames rendered
        aniTick++;

        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= HeartConstants.getSpriteAmount(aniType)) {
                aniIndex = 0;
            }
        }
    }

    //Getter and Setter
    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }


    @Override
    public void update() {
        updateAnimationTick();
    }

    //Render
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

    public void render(Graphics g) {
        g.drawImage(animations[aniType][aniIndex], (int) position.getX(),
                (int) position.getY(), size.getWidth(), size.getHeight(), null);
    }


}
