package playing.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import core.Position;
import core.Size;
import core.Vector2D;
import helpmethods.CheckCollision;
import helpmethods.LoadSave;
import helpmethods.PlayerAnimationType;
import playing.tile.Level;
import playing.tile.Tile;

public class Player extends GameObject {

    private final int PLAYER_WIDTH = 58;
    private final int PLAYER_HEIGHT = 58;

    private Vector2D velocity;
    private float speedX, speedY;

    private BufferedImage[][] animations;
    private Level level;
    private int aniType;
    private int aniTick, aniIndex, aniSpeed;
    private int mapStartX, mapStartY;
    // set status
    private boolean Up, Left, Right;
    private boolean moving;

    // private boolean moving;
    private Rectangle hitBox;
    private boolean onGround;

    private int startAni;

    public Player(Level level) {
        position = new Position(200f, 200f);
        size = new Size(PLAYER_WIDTH, PLAYER_HEIGHT);

        velocity = new Vector2D(0f, 0f);
        speedX = 5.0f;
        speedY = 7.0f;
        aniSpeed = 3;
        animations = new BufferedImage[6][26];
        this.level = level;
        // box of player
        hitBox = new Rectangle(200, 200, 48, 48);
        aniType = PlayerAnimationType.IDLE;
        onGround = false;
        moving = false;
        mapStartX = 0;
        mapStartY = 0;
        loadAnimations();
    }

    private void loadAnimations() {
        BufferedImage image = LoadSave.loadImage("img/Player/Player-Bomb Guy.png");
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
        StartAniOld();
        setAniType();
        // If start anitype is not equal to startAni reset aniTick and aniIndex
        if (aniType != startAni) {
            // Reset animation index and animation tick
            aniTick = 0;
            aniIndex = 0;
        }
    }

    private void StartAniOld() {
        startAni = aniType;
    }

    private void upDatePosition() {

        velocity = new Vector2D(0, 5f);
        moving = false;

        if (Up) {
            velocity.setY(-speedY);
        }

        if (Right && !Left) {
            moving = true;
            velocity.setX(speedX);
        }

        if (Left && !Right) {
            moving = true;
            velocity.setX(-speedX);
        }

        Position newPos = new Position(position.getX() + velocity.getX(), position.getY() + velocity.getY());
        Rectangle newHitbox = new Rectangle((int) newPos.getX(), (int) newPos.getY(), size.getWidth(),
                size.getHeight());

        // Move the character
        if (canMove(newHitbox)) {
            position = new Position(position.getX() + velocity.getX(), position.getY() + velocity.getY());
            hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        }
    }

    private boolean canMove(Rectangle newHitbox) {
        int[][] map = level.getMap();
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 42; j++) {
                if (map[i][j] == 1 && CheckCollision.isCollision(newHitbox,
                        new Rectangle(j * Tile.TILE_SIZE, i * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setAniType() {
        if (Up) {
            aniType = PlayerAnimationType.JUMP;
        }

        if (moving) {
            aniType = PlayerAnimationType.RUN;
        }

        if (!moving) {
            aniType = PlayerAnimationType.IDLE;
        }

    }

    public void setScreen(int mapStartX, int mapStartY) {
        this.mapStartX = mapStartX;
        this.mapStartY = mapStartY;
    }

    public void update(int mapStartX, int mapStartY) {
        // Change position if player is moving
        upDatePosition();

        // Update tick to render animation
        updateAnimationTick();

        // Set current type of animation
        setAnimationType();

        // set screen
        setScreen(mapStartX, mapStartY);

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animations[aniType][aniIndex],
                (int) position.getX() - mapStartX,
                (int) position.getY() - mapStartY,
                size.getWidth(),
                size.getHeight(),
                null);
    }

    @Override
    public void update() {

    }

    public void setAniType(int aniType) {
        this.aniType = aniType;
    }

    public BufferedImage[][] getAnimations() {
        return animations;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void inPos() {
        System.out.println(position.getX() + " " + position.getY());
    }

    public boolean isUp() {
        return Up;
    }

    public boolean isLeft() {
        return Left;
    }

    public boolean isRight() {
        return Right;
    }

    public void setUp(boolean up) {
        Up = up;
    }

    public void setLeft(boolean left) {
        Left = left;
    }

    public void setRight(boolean right) {
        Right = right;
    }
}
