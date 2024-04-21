package playing.entity.bomb;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import core.Vector2D;
import game.Game;
import game.GamePanel;
import gamestate.GameState;
import helpmethods.BombConstants;
import helpmethods.CheckCollision;
import helpmethods.FilePath;
import helpmethods.LoadSave;
import playing.camera.Camera;
import playing.entity.GameObject;
import playing.level.Tile;

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
    private long timePassed, lastTime;

    // Boolean check exploded
    private boolean exploded;

    // Box of Bomb
    private Rectangle hitBox;

    // Position help render
    private Position oldPos;
    private Position renderPos;

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
        oldPos = new Position(position.getX(), position.getY());
        renderPos = new Position(position.getX(), position.getY());

        // Speed of animation
        aniSpeed = 3;

        // Start timer
        timePassed = 0;
        lastTime = System.currentTimeMillis();

        // Init
        aniType = BombConstants.PLACINGBOMB;

        exploded = false;

        onGround = false;

        // Set velocity gravity for bomb
        velocity = new Vector2D(0.0f, MIN_GRAVITY);

        // Load Animations
        loadAnimations();
    }

    private void loadAnimations() {
        // Allocate memory
        animations = new BufferedImage[BombConstants.TOTAL_TYPE][BombConstants.TOTAL_FRAME]; // 3 bomb states, max 6
                                                                                             // frames for each type

        // Load all image
        BufferedImage image = LoadSave.loadImage(FilePath.Object.BOMB);
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = image.getSubimage(j * BOMB_WIDTH, i * BOMB_HEIGHT, BOMB_WIDTH, BOMB_HEIGHT);
    }

    // set Type Bomb
    private void setAniType() {
        // Init begin animation type
        int startAni = aniType;

        // Set new type of animation depend on time has passed
        if (timePassed   <= 500) {
            aniType = BombConstants.PLACINGBOMB;
        } else if (timePassed > 500 && timePassed <= 3000) {
            aniType = BombConstants.ACTIVATINGBOMB;
        } else if (timePassed > 3000) {
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
        // Save old position
        oldPos = position;

        // If the bomb not on ground
        if (!onGround)
            // Falling
            fall();

        // Set animation type depend on time passed
        setAniType();

        // Update ani tick
        updateAnimationTick();
    }

    // Render
    @Override
    public void render(Graphics g, Camera camera) {
        // If game state is not pause
        if (GameState.gameState == GameState.PLAYING)
            timePassed += System.currentTimeMillis() - lastTime;

        // Calculate render position
        if (onGround)
            // The bomb isn't falling
            renderPos = position;
        else {
            renderPos.setX(oldPos.getX() + velocity.getX() * GamePanel.interpolation);
            renderPos.setY(oldPos.getY() + velocity.getY() * GamePanel.interpolation);
        }

        // Render the bomb if it is in the screen
        if ((int) renderPos.getX() - camera.getMapStartX() >= 0
                && (int) renderPos.getX() - camera.getMapStartX() <= Game.SCREEN_WIDTH
                && (int) renderPos.getY() - camera.getMapStartY() >= 0
                && (int) renderPos.getY() - camera.getMapStartY() <= Game.SCREEN_HEIGHT) {
            // Draw current position
            g.drawImage(animations[aniType][aniIndex],
                    (int) renderPos.getX() - camera.getMapStartX(),
                    (int) renderPos.getY() - camera.getMapStartY(),
                    size.getWidth(),
                    size.getHeight(),
                    null);
        }

        // Update last time
        lastTime = System.currentTimeMillis();

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
