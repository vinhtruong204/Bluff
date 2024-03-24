package playing.entity;

import java.awt.Color;
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
import helpmethods.WalkDirection;
import playing.camera.Camera;
import playing.tile.Level;
import playing.tile.Tile;

public class Player extends GameObject {
    private final float MAX_GRAVITY = 9.0f;
    private final float MIN_JUMP_SPEED = 1.0f;

    private final float MIN_GRAVITY = 0.5f;
    private final float MAX_JUMP_SPEED = 9.0f;
    private final float MAX_JUMP_HEIGHT = 200.0f;

    public static final int MAX_HEART = 10;

    // Player dimension
    private final int PLAYER_WIDTH = 58;
    private final int PLAYER_HEIGHT = 58;

    // Move
    private float horizontalSpeed = 5.0f;
    private Vector2D velocity;
    private boolean up, left, right;
    private boolean moving;
    private boolean jumping;
    private float maxHeightJump;

    // animations of Player
    private BufferedImage[][] animations;
    private Level level;
    private int aniType;
    private int aniTick, aniIndex, aniSpeed;

    // Box of player
    private Rectangle hitBox;

    // heart Player
    private static int heartPlayer;

    // danger
    private Boolean dangerTouch;

    private WalkDirection currentDirection;

    // Jump and gravity
    private float offsetVertical = 0.2f;
    private float gravity = MIN_GRAVITY; // Init begin gravity
    private float jumpSpeed = MAX_JUMP_SPEED; // Init begin jumpspeed
    private boolean onGround = false;

    // Contructor
    public Player(Level level, int maxHeart) {
        position = new Position(2 * Tile.TILE_SIZE, 1 * Tile.TILE_SIZE + 6.0f);
        size = new Size(PLAYER_WIDTH, PLAYER_HEIGHT);
        velocity = new Vector2D(0f, 0f);
        aniSpeed = 3;
        animations = new BufferedImage[8][26];
        this.level = level;
        // box of player
        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        // this.maxHeart = maxHeart;
        Player.heartPlayer = maxHeart;
        dangerTouch = false;
        aniType = PlayerAnimationType.IDLE;
        currentDirection = WalkDirection.RIGHT;
        moving = false;
        loadAnimations();
    }

    // Load animations
    private void loadAnimations() {
        BufferedImage image = LoadSave.loadImage("img/Player/Player-Bomb Guy.png");
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = image.getSubimage(j * PLAYER_WIDTH, i * PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    // Align Time
    private void updateAnimationTick() {
        // 60fps => 20 animation frames rendered
        aniTick++;

        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= PlayerAnimationType.getSpriteAmount(aniType)) {
                if (aniType == PlayerAnimationType.HIT || aniType == PlayerAnimationType.DEAD_HIT
                        || aniType == PlayerAnimationType.DEAD_GROUND) {
                    if (heartPlayer > 0) {
                        heartPlayer = heartPlayer - 1;
                    }
                    dangerTouch = false;
                }
                // System.out.println(aniIndex);
                aniIndex = 0;
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
            aniTick = 0;
            aniIndex = 0;
            if (aniType == PlayerAnimationType.DEAD_HIT || aniType == PlayerAnimationType.HIT
                    || aniType == PlayerAnimationType.DEAD_GROUND) {
                position.setX(2 * Tile.TILE_SIZE - 20f);
                position.setY(2 * Tile.TILE_SIZE);
                hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
            }
        }
    }

    // update possition
    private void updatePosition() {

        // Update vertical position
        updateVerticalPos();

        // Update horizontal position
        updateHorizontalPos();

    }

    private void updateHorizontalPos() {
        // Reset vetor velocity and gravity
        moving = false;

        // Move right
        if (right && !left) {
            moving = true;
            velocity.setX(horizontalSpeed);
            currentDirection = WalkDirection.RIGHT;
        }

        // Move left
        if (left && !right) {
            moving = true;
            velocity.setX(-horizontalSpeed);
            currentDirection = WalkDirection.LEFT;
        }

        {
            // Caculate new hit box
            Rectangle newHitbox = new Rectangle(
                    (int) (position.getX() + velocity.getX()),
                    (int) position.getY(),
                    size.getWidth(),
                    size.getHeight());

            // Move the character
            if (canMove(newHitbox)) {
                hitBox = newHitbox;
                position = new Position(hitBox.x, hitBox.y);
                if (!CheckCollision.isEntityOnground(level.getMap(), newHitbox)) {
                    onGround = false;
                }
            }
        }

        // Reset velocity
        velocity.setX(0.0f);
    }

    private void updateVerticalPos() {

        // If player in the air
        if (!jumping && !onGround) {
            // Move down
            fall(); // Reset onground in here
        }

        // Move up
        if (up && !jumping && onGround) {
            // Set onground to false
            onGround = false;

            // Jumping is true
            jumping = true;

            // Calculate max height jump
            maxHeightJump = position.getY() - MAX_JUMP_HEIGHT;
        }

        // If player is jumping
        if (jumping)
            jump();

    }

    private void jump() {

        // Set velocity jump
        velocity.setY(-jumpSpeed);

        if (jumpSpeed >= MIN_JUMP_SPEED)
            jumpSpeed -= offsetVertical;

        // If player is not reach max jump height
        if (!CheckCollision.isCollisionWithFloor(level.getMap(), hitBox) && position.getY() >= maxHeightJump) {
            position = new Position(position.getX(), position.getY() + velocity.getY());
            hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        } else {
            // falling = true;
            jumping = false;

            jumpSpeed = 9.0f;
        }

    }

    private void fall() {
        // Set vector gravity
        velocity.setY(gravity);

        if (gravity <= MAX_GRAVITY) {
            gravity += offsetVertical;
        }

        // Calculate hitbox
        Rectangle newHitbox = new Rectangle(
                (int) position.getX(),
                (int) (position.getY() + velocity.getY()),
                size.getWidth(),
                size.getHeight());

        // Move down player if player in the air
        if (!CheckCollision.isEntityOnground(level.getMap(), newHitbox)) {
            hitBox = newHitbox;
            position = new Position(hitBox.x, hitBox.y);
        } else {
            onGround = true;
            // Reset gravity if player on ground
            gravity = 0.5f;
        }

    }

    // check collition with Map
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

    // set type animations
    public void setAniType() {

        if (moving && !up) {
            aniType = PlayerAnimationType.RUN;
        }

        if (!moving) {
            aniType = PlayerAnimationType.IDLE;
        }

        if (dangerTouch && heartPlayer > 1) {
            aniType = PlayerAnimationType.HIT;
        }

        if (dangerTouch && heartPlayer == 1) {
            aniType = PlayerAnimationType.DEAD_HIT;
        }

        if (dangerTouch && heartPlayer == 0) {
            aniType = PlayerAnimationType.DEAD_GROUND;
        }

    }

    // update
    @Override
    public void update() {
        // Change position if player is moving
        updatePosition();

        // Set current type of animation
        setAnimationType();

        // update tick to render animation
        updateAnimationTick();

    }

    // Render
    @Override
    public void render(Graphics g, Camera camera) {
        g.setColor(Color.pink);
        g.drawRect(hitBox.x - camera.getMapStartX(), hitBox.y - camera.getMapStartY(), hitBox.width, hitBox.height);
        BufferedImage temp = animations[aniType][aniIndex];
        if (currentDirection == WalkDirection.LEFT)
            temp = FlipImage.flipImage(temp);
        g.drawImage(temp,
                (int) position.getX() - camera.getMapStartX(),
                (int) position.getY() - camera.getMapStartY(),
                size.getWidth(),
                size.getHeight(),
                null);
    }

    // Getter and Setter

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setDangerTouch(Boolean dangerTouch) {
        this.dangerTouch = dangerTouch;
    }

    public int getHeartPlayer() {
        return heartPlayer;
    }

    public void setHeartPlayer(int heartPlayer) {
        Player.heartPlayer = heartPlayer;
    }

}
