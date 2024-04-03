package playing.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import core.Position;
import core.Size;
import core.Vector2D;
import game.GamePanel;
import helpmethods.CheckCollision;
import helpmethods.FlipImage;
import helpmethods.LoadSave;
import helpmethods.PlayerAnimationType;
import helpmethods.WalkDirection;
import playing.camera.Camera;
import playing.level.Tile;

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
    private float horizontalSpeed;
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

    // Heart player
    private static int heartPlayer;

    // Boolean danger
    private Boolean dangerTouch;

    // Player direction
    private WalkDirection currentDirection;

    // Jump and gravity
    private float gravity;
    private float jumpSpeed;
    private boolean onGround;

    //
    private boolean doorIn;
    private boolean enteredDoor;

    //
    private boolean doorOut;

    //

    private boolean locked;

    // Old posisition
    private Position oldPos;

    // Contructor
    public Player(int[][] map, boolean doorOut, boolean reset) {
        // Set first position, size
        position = new Position(2 * Tile.TILE_SIZE, 1 * Tile.TILE_SIZE); // Column 2, row 1 TileSize = 48
        oldPos = position;
        size = new Size(PLAYER_WIDTH, PLAYER_HEIGHT);

        // Init hitbox depend on position and size
        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());

        // Init velocity
        velocity = new Vector2D(0.0f, 0.0f);

        // Set current matrix map
        this.map = map;

        // Place a heart on the player if this is the first level
        Player.heartPlayer = reset ? 5 : Player.heartPlayer;

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

        //

        doorIn = false;
        this.doorOut = doorOut;
        enteredDoor = false;
        locked = doorOut == true ? true : false;
    }

    private void initMoveHorizontal() {
        horizontalSpeed = 5.0f;
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
        animations = new BufferedImage[10][26];

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

                // Reset ani index depend on type of animation
                switch (aniType) {
                    case PlayerAnimationType.JUMP:
                        aniIndex = PlayerAnimationType.getSpriteAmount(aniType) - 1;
                        break;
                    case PlayerAnimationType.FALL:
                        aniIndex = PlayerAnimationType.getSpriteAmount(aniType) - 1;
                        break;
                    case PlayerAnimationType.DOOR_IN:
                        aniIndex = PlayerAnimationType.getSpriteAmount(aniType) - 1;
                        enteredDoor = true;
                        break;
                    case PlayerAnimationType.DOOR_OUT:
                        doorOut = false;
                        locked =false;
                        break;
                    default:
                        aniIndex = 0;
                        break;
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
            aniTick = 0;
            aniIndex = 0;

            // If player is dying
            if (aniType == PlayerAnimationType.DEAD_HIT || aniType == PlayerAnimationType.HIT
                    || aniType == PlayerAnimationType.DEAD_GROUND) {
                // Reset position to the last checkpoint
                position.setX(2 * Tile.TILE_SIZE - 20f);
                position.setY(2 * Tile.TILE_SIZE);
                hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
            }
        }
    }

    // set type animations
    public void setAniType() {

        // If jumping
        if (jumping) {
            aniType = PlayerAnimationType.JUMP;
        }

        // If falling
        else if (!onGround) {
            aniType = PlayerAnimationType.FALL;
        }
        // Moving
        else if (moving) {
            aniType = PlayerAnimationType.RUN;
        }

        // If on ground and not move
        else if (onGround) {
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

        if (doorIn && !doorOut) {
            aniType = PlayerAnimationType.DOOR_IN;
        }

        if (doorOut) {
            aniType = PlayerAnimationType.DOOR_OUT;
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
        velocity.setX(0.0f);
        moving = false;

        // Move right
        if (right && !left) {
            moving = onGround ? true : false;
            velocity.setX(horizontalSpeed);
            currentDirection = WalkDirection.RIGHT;
        }

        // Move left
        if (left && !right) {
            moving = onGround ? true : false;
            velocity.setX(-horizontalSpeed);
            currentDirection = WalkDirection.LEFT;
        }

        // Caculate new hit box
        Rectangle newHitbox = new Rectangle(
                (int) (position.getX() + velocity.getX()),
                (int) position.getY(),
                size.getWidth(),
                size.getHeight());

        // If can move the character
        if (CheckCollision.canMove(map, newHitbox)) {
            hitBox = newHitbox;

            // Check on ground when move left or right
            onGround = CheckCollision.isEntityOnground(map, hitBox) ? true : false;

        } else {
            hitBox.x = CheckCollision.getHorizontalOffset(hitBox, currentDirection);
        }

        position = new Position(hitBox.x, hitBox.y);

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
        // Reset coordinate X of velocity
        velocity.setY(0.0f);

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
        if (!CheckCollision.isCollisionWithRoof(map, newHitBox)
                && newHitBox.y >= maxHeightJump) {
                hitBox = newHitBox;
        } else {
            if (CheckCollision.isCollisionWithRoof(map, newHitBox))
                hitBox.y = CheckCollision.getVerticalOffset(hitBox, true);

            // falling = true;
            jumping = false;

            // Reset the maximum value for jump speed
            jumpSpeed = MAX_JUMP_SPEED;
        }

        position = new Position(hitBox.x, hitBox.y);

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
        } else {
            hitBox.y = CheckCollision.getVerticalOffset(hitBox, false);
            onGround = true;
            // Reset gravity if player on ground
            gravity = MIN_GRAVITY;

            // Reset coordinate X of velocity
            velocity.setY(0.0f);
        }

        position = new Position(hitBox.x, hitBox.y);

    }

    // update
    @Override
    public void update() {
        // Change position if player is moving
        if  (!locked)  {
            oldPos = position;
            updatePosition();
        }

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
        if (oldPos.compareTo(position) == 0) {
            g.drawImage(temp,
                    (int) position.getX() - camera.getMapStartX(),
                    (int) position.getY() - camera.getMapStartY(),
                    size.getWidth(),
                    size.getHeight(),
                    null);
        } else
            g.drawImage(temp,
                    (int) (oldPos.getX() + velocity.getX() * GamePanel.interpolation) - camera.getMapStartX(),
                    (int) (oldPos.getY() + velocity.getY() * GamePanel.interpolation) - camera.getMapStartY(),
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

    public boolean isDoorIn() {
        return doorIn;
    }

    public void setDoorIn(boolean doorIn) {
        this.doorIn = doorIn;
    }

    public boolean isDoorOut() {
        return doorOut;
    }

    public void setDoorOut(boolean doorOut) {
        this.doorOut = doorOut;
    }

    public boolean isEnteredDoor() {
        return enteredDoor;
    }

    public void setEnteredDoor(boolean enteredDoor) {
        this.enteredDoor = enteredDoor;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

}
