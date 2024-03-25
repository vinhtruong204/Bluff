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
import playing.tile.Tile;

public class Player extends GameObject {

    // Constants for jump and fall
    private final float MAX_GRAVITY = 9.0f;
    private final float MIN_JUMP_SPEED = 1.0f;
    private final float MIN_GRAVITY = 0.5f;
    private final float MAX_JUMP_SPEED = 9.0f;
    private final float OFFSET_VERTICAL_MOVE = 0.2f; // Minus or add offset when player fall or jump
    private final float MAX_JUMP_HEIGHT = 200.0f;

    // Constant max heart for every level
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
    private float maxHeightJump; // Calculate max height when player jumping

    // Animations of Player
    private BufferedImage[][] animations;
    private int aniType;
    private int aniTick, aniIndex, aniSpeed;

    // Current maxtrix map
    private int[][] map;

    // Box of player
    private Rectangle hitBox;

    // Heart hlayer
    private static int heartPlayer;

    // Boolean danger
    private Boolean dangerTouch;

    // Player direction
    private WalkDirection currentDirection;

    // Jump and gravity
    private float gravity;
    private float jumpSpeed;
    private boolean onGround;

    // Contructor
    public Player(int[][] map) {
        // Set first position, size
        position = new Position(2 * Tile.TILE_SIZE, 1 * Tile.TILE_SIZE); // Column 2, row 1 TileSize = 48
        size = new Size(PLAYER_WIDTH, PLAYER_HEIGHT);

        // Init hitbox depend on position and size
        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());

        // Init velocity
        velocity = new Vector2D(0.0f, 0.0f);

        // Set current matrix map
        this.map = map;

        // Place a heart on the player if this is the first level
        Player.heartPlayer = Player.heartPlayer > 0 ? Player.heartPlayer : 5;

        // Init danger hurt
        dangerTouch = false;

        // Initialize variable for vertical move
        initMoveVertical();

        // Initialize variable for horizontal move
        initMoveHorizontal();

        // Load image animation
        loadAnimations();

        // Initialize animation and direction
        aniType = PlayerAnimationType.IDLE;
        aniSpeed = 3; // 20 animation frames per second
    }

    private void initMoveHorizontal() {
        currentDirection = WalkDirection.RIGHT;
        moving = false;
    }

    private void initMoveVertical() {
        gravity = MIN_GRAVITY; // Init begin gravity
        jumpSpeed = MAX_JUMP_SPEED; // Init begin jumpspeed
        onGround = false; // Init on ground false for first frame
    }

    // Load animations
    private void loadAnimations() {
        // Allocate memory
        animations = new BufferedImage[8][26];

        // Load image from file
        BufferedImage image = LoadSave.loadImage("img/Player/Player-Bomb Guy.png");

        // Put animation into matrix
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

            // If can move the character
            if (CheckCollision.canMoveLeftOrRight(map, newHitbox, currentDirection)) {
                hitBox = newHitbox;
                position = new Position(hitBox.x, hitBox.y);

                // Check on ground when move left or right
                if (!CheckCollision.isEntityOnground(map, newHitbox))
                    onGround = false;

            } else {
                //hitBox.x = CheckCollision.getHorizontalOffset(newHitbox, currentDirection);
                position = new Position(hitBox.x, hitBox.y);
            }
        }

        // Reset velocity
        velocity.setX(0.0f);
    }

    private void updateVerticalPos() {

        // If player is falling and not on ground
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

        // If the jump speed does not reach the minimum jump speed
        if (jumpSpeed >= MIN_JUMP_SPEED)
            // Decrease jump speed
            jumpSpeed -= OFFSET_VERTICAL_MOVE;

        // Calculate new hibox
        Rectangle newHitBox = new Rectangle(
                (int) position.getX(),
                (int) (position.getY() + velocity.getY()),
                size.getWidth(),
                size.getHeight());

        // If player is not reach max jump height
        if (!CheckCollision.isCollisionWithFloor(map, newHitBox)
                && newHitBox.y >= maxHeightJump) {
            hitBox = newHitBox;
            position = new Position(hitBox.x, hitBox.y);
        } else {
            // falling = true;
            jumping = false;

            // Reset the maximum value for jump speed
            jumpSpeed = MAX_JUMP_SPEED;
        }

        // Reset coordinate X of velocity
        velocity.setY(0.0f);
    }

    private void fall() {
        // Set vector gravity
        velocity.setY(gravity);

        // Gravity is not reach to max gravity
        if (gravity <= MAX_GRAVITY)
            // Increase gravity
            gravity += OFFSET_VERTICAL_MOVE;

        // Calculate hitbox
        Rectangle newHitbox = new Rectangle(
                (int) position.getX(),
                (int) (position.getY() + velocity.getY()),
                size.getWidth(),
                size.getHeight());

        // Move down player if player in the air
        if (!CheckCollision.isEntityOnground(map, newHitbox)) {
            hitBox = newHitbox;
            position = new Position(hitBox.x, hitBox.y);
        } else {
            onGround = true;
            // Reset gravity if player on ground
            gravity = MIN_GRAVITY;
        }

        // Reset coordinate X of velocity
        velocity.setY(0.0f);
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
