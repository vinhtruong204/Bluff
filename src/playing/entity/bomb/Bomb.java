package playing.entity.bomb;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import core.Vector2D;
import game.Game;
import helpmethods.BombConstants;
import helpmethods.CheckCollision;
import helpmethods.LoadSave;
import playing.camera.Camera;
import playing.entity.GameObject;
import playing.tile.Tile;

public class Bomb extends GameObject {
    // Gravity for bomb
    private final float MAX_GRAVITY = 6.0f;
    private final float MIN_GRAVITY = 1.0f;
    private final float OFFSET_VERTICAL_MOVE = 0.5f;

    // Size of bomb
    private final int BOMB_WIDTH = 80;
    private final int BOMB_HEIGHT = 80;

    // Animations
    private BufferedImage animations[][];

    // Animation Type
    private int aniType;

    // Align the speed and position of the bomb
    private int aniTick, aniIndex, aniSpeed;

    // Align the time
    private long currentTime, afterTime;

    // Check exploded
    private boolean exploded;

    // Box of Bomb
    private Rectangle hitBox;

    // Move
    private Vector2D velocity;
    private boolean onGround;
    private int[][] map;

    // Contructor
    public Bomb(int i, int j, int[][] map) {
        // Init map
        this.map = map;

        // Set size, position and hitbox
        size = new Size(BOMB_WIDTH * 2, BOMB_HEIGHT * 2);
        position = new Position(i * Tile.TILE_SIZE - 30.0f, j * Tile.TILE_SIZE);
        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());

        // Speed of animation
        aniSpeed = 3;

        // Start timer
        currentTime = System.currentTimeMillis();

        // Init
        aniType = BombConstants.PLACINGBOMB;
        afterTime = 0;

        exploded = false;

        onGround = false;

        // Set velocity gravity for bomb
        velocity = new Vector2D(0.0f, MIN_GRAVITY);

        // Load Animations
        loadAnimations();
    }

    private void loadAnimations() {
        // Allocate memory
        animations = new BufferedImage[3][6]; // 3 bomb states, max 6 frames for each type

        // Load all image
        BufferedImage image = LoadSave.loadImage("img/Player/Bomb.png");
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = image.getSubimage(j * BOMB_WIDTH, i * BOMB_HEIGHT, BOMB_WIDTH, BOMB_HEIGHT);
    }

    // set Type Bomb
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

    // Align Time frames
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

    private void fall() {
        // Increase gravity if it isn't reach max gravity
        if (velocity.getY() <= MAX_GRAVITY) {
            velocity.setY(velocity.getY() + OFFSET_VERTICAL_MOVE);
        }

        // Calculate new pos
        Position newPos = new Position(position.getX() + velocity.getX(), position.getY() + velocity.getY());
        Rectangle newMoveBox = new Rectangle(
                (int) newPos.getX() + 64,
                (int) newPos.getY() + 64,
                32,
                32); // Offset x, y: 64. Image bomb dimension: 32x32

        if (!CheckCollision.isEntityOnground(map, newMoveBox)) {
            // Set new position and hit box of bomb
            position = newPos;
            hitBox = new Rectangle((int) position.getX(), (int) position.getY(), size.getWidth(), size.getHeight());
        } else
            onGround = true;
    }

    // Update
    @Override
    public void update() {
        if (!onGround)
            fall();
        setAniType();
        updateAnimationTick();

    }

    // Render
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

    // Getter and setter
    public int getAniType() {
        return aniType;
    }

    public boolean isExploded() {
        return exploded;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

}
