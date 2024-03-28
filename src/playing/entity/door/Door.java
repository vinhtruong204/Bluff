package playing.entity.door;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import game.Game;
import helpmethods.DoorConstants;
import helpmethods.Draw;
import helpmethods.LoadSave;
import playing.camera.Camera;
import playing.entity.GameObject;
import playing.tile.Tile;

public class Door extends GameObject {

    private int offsetX = 48;
    private int offsetY = 32;

    //
    private BufferedImage[][] animations;

    //
    private Rectangle hitBox;

    //
    private int aniTick, aniIndex, aniSpeed;
    private int aniType;

    //
    private boolean close;
    private boolean closed;

    private boolean open;
    private boolean opend;

    public Door(int i, int j) {
        position = new Position(j * Tile.TILE_SIZE + offsetX , i * Tile.TILE_SIZE - offsetY);
        size = new Size(DoorConstants.DOOR_WIDTH, DoorConstants.DOOR_HEIGHT);
        hitBox = new Rectangle((int) position.getX() + offsetX * 2 , (int) position.getY() + offsetY * 2 , DoorConstants.DOOR_WIDTH - offsetX * 9 / 3,
                DoorConstants.DOOR_HEIGHT - offsetY * 3);
        animations = new BufferedImage[DoorConstants.TOTAL_TYPE][DoorConstants.TOTAL_MAX_FRAME];
        aniTick = 0;
        aniIndex = 0;
        aniType = DoorConstants.DEFAULT;
        aniSpeed = 15;

        close = false;
        closed = false;
        open = false;
        opend = false;
        loadAnimations();
    }

    private void loadAnimations() {
        BufferedImage image = LoadSave.loadImage("img/Door/Door.png");
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * DoorConstants.DOOR_WIDTH, i * DoorConstants.DOOR_HEIGHT,
                        DoorConstants.DOOR_WIDTH, DoorConstants.DOOR_HEIGHT);
            }
    }

    private void updateAnimationTick() {
        // 60fps => 20 animation frames rendered
        aniTick++;

        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= DoorConstants.getSpriteAmount(aniType)) {
                if (aniType == DoorConstants.OPEN) {
                    opend = true;
                    aniIndex = DoorConstants.getSpriteAmount(aniType) - 1;
                }

                if (aniType == DoorConstants.CLOSE) {
                    closed = true;
                    aniIndex = DoorConstants.getSpriteAmount(aniType) - 1;
                }
            }
         }
    }

      // Set type Animation
      private void setAnimationType() {
        // Initialize start animation type
        int startAni = aniType;

        // Set type of animation depend on current state
        setAniType();

        // If start anitype is not equal to startAni reset aniTick and aniIndex
        if (aniType != startAni) {
            // Reset animation index and animation tick
            if(aniType == DoorConstants.CLOSE){
                aniTick = 1;
            }
            else aniIndex = 0;
            aniIndex = 0;
        }
    }

    // set type animations
    public void setAniType() {
        if(open && ! close){
            aniType = DoorConstants.OPEN;
        }

        if(close){
            aniType = DoorConstants.CLOSE;
        }
    }


    @Override
    public void update() {
        if (aniType != DoorConstants.DEFAULT && !closed) {
            updateAnimationTick();
        }
        setAnimationType();
    }

    @Override
    public void render(Graphics g, Camera camera) {
        if ((int) position.getX() - camera.getMapStartX() >= 0
                && (int) position.getX() - camera.getMapStartX() <= Game.SCREEN_WIDTH
                && (int) position.getY() - camera.getMapStartY() >= 0
                && (int) position.getY() - camera.getMapStartY() <= Game.SCREEN_HEIGHT) {
            if (aniType == DoorConstants.DEFAULT) {
                Draw.drawImage(g, animations[1][0], (int) position.getX() - camera.getMapStartX(),
                        (int) position.getY() - camera.getMapStartY(), size.getWidth(),
                        size.getHeight());
            } else {
                g.drawImage(animations[aniType][aniIndex], (int) position.getX() - camera.getMapStartX(),
                        (int) position.getY() - camera.getMapStartY(),
                        size.getWidth(), size.getHeight(), null);
            }
        }
    }

    // getter and setter;

    public int getAnitype() {
        return aniType;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isOpend() {
        return opend;
    }

    public void setOpend(boolean opend) {
        this.opend = opend;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }
}
