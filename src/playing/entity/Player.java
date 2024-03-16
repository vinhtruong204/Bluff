package playing.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import core.Position;
import core.Size;
import core.Vector2D;
import helpmethods.CheckCollision;
import helpmethods.FlipImage;
import helpmethods.LoadSave;
import helpmethods.PlayerAnimationType;
import helpmethods.PlayerDirection;
import playing.camera.Camera;
import playing.tile.Level;
import playing.tile.Tile;

public class Player extends GameObject {
    // Player dimension
    private final int PLAYER_WIDTH = 58;
    private final int PLAYER_HEIGHT = 58;

    // private final float MAX_JUMP_HEIGHT = 100.0f;

    private Vector2D velocity;
    private float speedX, speedY;

    private BufferedImage[][] animations;
    private Level level;
    private int aniType;
    private int aniTick, aniIndex, aniSpeed;

    // set status
    private boolean Up, Left, Right, Down;
    private boolean moving;

    // Current jump distance
    // private float currJumpHeight;

    // private boolean moving;
    private Rectangle hitBox;

    private PlayerDirection currentDirection;

    public Player(Level level) {
        position = new Position(2 * Tile.TILE_SIZE, 1 * Tile.TILE_SIZE);
        size = new Size(PLAYER_WIDTH, PLAYER_HEIGHT);

        velocity = new Vector2D(0f, 0f);
        speedX = 5.0f;
        speedY = 10.0f;
        aniSpeed = 3;
        animations = new BufferedImage[6][26];
        this.level = level;
        // box of player
        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        aniType = PlayerAnimationType.IDLE;

        currentDirection = PlayerDirection.RIGHT;
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

        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= PlayerAnimationType.getSpriteAmount(aniType))
                aniIndex = 0;
        }
    }

    private void setAnimationType() {
        // Initialize start animation type
        int startAni = aniType;

        // Set type of animation depend on current state
        setAniType();

        // If start anitype is not equal to startAni reset aniTick and aniIndex
        if (aniType != startAni) {
            // Reset animation index and animation tick
            aniTick = 0;
            aniIndex = 0;
        }
    }

    private void upDatePosition() {
        // "\t" + "jumping " + jumping);
        // Reset vetor velocity and gravity
        velocity = new Vector2D(0.0f, 0.0f);
        moving = false;

        // If player onground and request jump
        if (Up) {
            velocity.setY(-speedY);
        }

        if (Down) {
            velocity.setY(speedY);
        }

        // Move right
        if (Right && !Left) {
            moving = true;
            velocity.setX(speedX);
            currentDirection = PlayerDirection.RIGHT;
        }

        // Move left
        if (Left && !Right) {
            moving = true;
            velocity.setX(-speedX);
            currentDirection = PlayerDirection.LEFT;
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
            position = newPos;
            hitBox = newHitbox;
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
                        // if ((newHitbox.y + newHitbox.height > tileRect.y)) {
                        // onGround = true;
                        // }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void setAniType() {

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

        // Set current type of animation
        setAnimationType();

        // Update tick to render animation
        updateAnimationTick();

    }

    @Override
    public void render(Graphics g, Camera camera) {
        BufferedImage temp = animations[aniType][aniIndex];
        if (currentDirection == PlayerDirection.LEFT)
            temp = FlipImage.flipImage(temp);
        g.drawImage(temp,
                (int) position.getX() - camera.getMapStartX(),
                (int) position.getY() - camera.getMapStartY(),
                size.getWidth(),
                size.getHeight(),
                null);
    }

    public void setAniType(int aniType) {
        this.aniType = aniType;
    }

    public boolean isDown() {
        return Down;
    }

    public void setDown(boolean down) {
        Down = down;
    }

    public BufferedImage[][] getAnimations() {
        return animations;
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
