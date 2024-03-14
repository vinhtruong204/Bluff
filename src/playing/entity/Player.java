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
import playing.camera.Camera;
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
        speedY = 100.0f;
        aniSpeed = 3;
        animations = new BufferedImage[6][26];
        this.level = level;
        // box of player
        hitBox = new Rectangle(200, 200, 48, 48);
        aniType = PlayerAnimationType.IDLE;
        onGround = false;
        moving = false;
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
        // Reset vetor velocity and gravity
        velocity = onGround ? new Vector2D(0.0f, 0.0f) : new Vector2D(0.0f, 5.0f);
        moving = false;

        // If player onground and request jump
        if (Up && onGround) {
            velocity.setY(-speedY);
            aniType = PlayerAnimationType.JUMP;
            onGround = false;
        }

        // Move right
        if (Right && !Left) {
            moving = true;
            velocity.setX(speedX);
        }

        // Move left
        if (Left && !Right) {
            moving = true;
            velocity.setX(-speedX);
        }

        // Caculate new position and hit box
        Position newPos = new Position(position.getX() + velocity.getX(), position.getY() + velocity.getY());
        Rectangle newHitbox = new Rectangle(
                (int) newPos.getX(),
                (int) newPos.getY(),
                size.getWidth(),
                size.getHeight());

        // Move the character
        if (canMove(newHitbox)) {
            System.out.println(onGround);
            position = new Position(position.getX() + velocity.getX(), position.getY() + velocity.getY());
            hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        }
    }

    private boolean canMove(Rectangle newHitbox) {
        // Get matrix map from current level
        int[][] map = level.getMap();

        // Check collision
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // If tile is a brick
                if (map[i][j] == 1) {
                    // Create new rectangle for brick
                    Rectangle tileRect = new Rectangle(
                            j * Tile.TILE_SIZE,
                            i * Tile.TILE_SIZE,
                            Tile.TILE_SIZE,
                            Tile.TILE_SIZE);
                    // Colliding with a brick
                    if (CheckCollision.isCollision(newHitbox, tileRect)) {
                        // If brick is ground set onground to true
                        if (newHitbox.y + newHitbox.height > tileRect.y) {
                            onGround = true;
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void setAniType() {
        if (Up) {
            aniType = PlayerAnimationType.JUMP;
        }

        if (moving && !Up) {
            aniType = PlayerAnimationType.RUN;
        }

        if (!moving) {
            aniType = PlayerAnimationType.IDLE;
        }

    }

    @Override
    public void update() {
        // Change position if player is moving
        upDatePosition();

        // Update tick to render animation
        updateAnimationTick();

        // Set current type of animation
        setAnimationType();
    }

    @Override
    public void render(Graphics g, Camera camera) {
        g.drawImage(animations[aniType][aniIndex],
                (int) position.getX() - camera.getMapStartX(),
                (int) position.getY() - camera.getMapStartY(),
                size.getWidth(),
                size.getHeight(),
                null);
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
        this.Up = up;
    }

    public void setLeft(boolean left) {
        this.Left = left;
    }

    public void setRight(boolean right) {
        this.Right = right;
    }
}
