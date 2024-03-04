package playing.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import core.Position;
import core.Size;
import core.Vector2D;
import helpmethods.CheckKeyPress;
import helpmethods.LoadSave;
import helpmethods.PlayerAnimationType;

public class Player extends GameObject {

    private final int PLAYER_WIDTH = 58;
    private final int PLAYER_HEIGHT = 58;

    private Vector2D velocity;
    private int speed;

    private BufferedImage[][] animations;
    private int aniType;
    private int aniTick, aniIndex, aniSpeed;

    private boolean moving;

    private Rectangle hitBox;
    private boolean onGround;

    private int keyPressed;

    public Player() {
        position = new Position(100f, 100f);
        size = new Size(PLAYER_WIDTH, PLAYER_HEIGHT);

        velocity = new Vector2D(0, 0);
        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        aniType = speed = 5;
        aniSpeed = 3;
        animations = new BufferedImage[6][26];
        onGround = false;
        loadAnimations();
    }

    private void loadAnimations() {
        image = LoadSave.loadImage("img/Player-Bomb Guy.png");
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = image.getSubimage(j * PLAYER_WIDTH, i * PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    private void updateAnimationTick() {
        // 60fps => 20 animation frames rendered
        aniTick++;

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;

            if (aniIndex >= PlayerAnimationType.getSpriteAmount(aniType))
                aniIndex = 0;
        }
    }

    private void setAnimationType() {
        // Store begin ani type
        int startAni = aniType;

        // Set type of animation if player is moving
        aniType = moving ? PlayerAnimationType.RUN : PlayerAnimationType.IDLE;

        // If start anitype is not equal to startAni reset aniTick and aniIndex
        if (aniType != startAni) {
            // Reset animation index and animation tick
            aniTick = 0;
            aniIndex = 0;
        }
    }

    private void upDatePosition() {
        moving = false;
        velocity = new Vector2D(0, 0);

        if (keyPressed == CheckKeyPress.Up) {
            onGround = false;
            velocity.setY(-speed);
        }

        if (keyPressed == CheckKeyPress.Right && keyPressed != CheckKeyPress.Left) {
            velocity.setX(speed);
            velocity.setY(0);
            moving = true;

        }

        if (keyPressed == CheckKeyPress.Left && keyPressed != CheckKeyPress.Right) {
            velocity.setX(-speed);
            velocity.setY(0);
            moving = true;
        }

        position = new Position(position.getX() + velocity.getX(), position.getY() + velocity.getY());
    }

    @Override
    public void update() {
        // Set current type of animation
        setAnimationType();

        // Update tick to render animation
        updateAnimationTick();

        // Change position if player is moving
        upDatePosition();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animations[aniType][aniIndex],
                (int) position.getX(),
                (int) position.getY(),
                size.getWidth(),
                size.getHeight(),
                null);
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setAniType(int aniType) {
        this.aniType = aniType;
    }

    public void setKeyPress(int keyPressed) {
        this.keyPressed = keyPressed;
    }

    public BufferedImage[][] getAnimations() {
        return animations;
    }

}
