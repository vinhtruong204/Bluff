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
import playing.tile.Tile;

public class Player extends GameObject {

    private final int PLAYER_WIDTH = 58;
    private final int PLAYER_HEIGHT = 58;

    private Vector2D velocity;
    private float speedX, speedY;

    private BufferedImage[][] animations;
    private int[][] TileMapNum;

    private int aniType;
    private int aniTick, aniIndex, aniSpeed;
    private int mapStartX, mapStartY;

    // private boolean moving;

    private Rectangle hitBox;
    private boolean onGround;

    private int keyPressed;
    private int startAni;

    public Player(int[][] TileMapNum) {
        position = new Position(60f, 60f);
        size = new Size(PLAYER_WIDTH, PLAYER_HEIGHT);

        velocity = new Vector2D(0f, 0f);
        speedX = 5f;
        speedY = 7f;
        aniSpeed = 3;
        animations = new BufferedImage[6][26];
        // box of player
        hitBox = new Rectangle(8, 8, 48, 48);
        keyPressed = CheckKeyPress.keyDefault;
        startAni = PlayerAnimationType.IDLE;
        this.TileMapNum = TileMapNum;
        onGround = false;
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

        // if (!checkTile()) {
        // if (keyPressed == CheckKeyPress.Up) {
        //     velocity.setY(-speedY);
        //     velocity.setX(0f);
        //     onGround = false;
        // } else if (keyPressed == CheckKeyPress.Down) {
        //     velocity.setY(speedY);
        //     velocity.setX(0f);
        if (keyPressed == CheckKeyPress.Right && keyPressed != CheckKeyPress.Left) {
            if (onGround) {
                velocity.setX(speedX);
                velocity.setY(0f);
            } else {
                // velocity.setX(speedX);
                // velocity.setY(speedY);
            }
            velocity.setX(speedX);

        } else if (keyPressed == CheckKeyPress.Left && keyPressed != CheckKeyPress.Right) {
            if (onGround) {
                velocity.setX(-speedX);
                velocity.setY(0f);
            } else {
                // velocity.setX(-speedX);
                // velocity.setY(speedY);
            }
        }
        position.setY(position.getY() + velocity.getY());
        position.setX(position.getX() + velocity.getX());
        // }
    }

    private boolean checkTile() {
    int entityLeftWorldX = (int) position.getX() + hitBox.x;
    int entityRightWorldX = (int) position.getX() + hitBox.x + hitBox.width;
    int entityTopWorldY = (int) position.getY() + hitBox.y;
    int entityBottomWorldY = (int) position.getY() + hitBox.y + hitBox.height;

    int entityLeftCol = entityLeftWorldX / Tile.TILE_SIZE;
    int entityRightCol = entityRightWorldX / Tile.TILE_SIZE;
    int entityTopRow = entityTopWorldY / Tile.TILE_SIZE;
    int entityBottomRow = entityBottomWorldY / Tile.TILE_SIZE;

    int tileNum1, tileNum2;
    boolean flag = false;

    switch (keyPressed) {
    case CheckKeyPress.Up: {
    entityTopRow = (entityTopWorldY - (int) speedY) / Tile.TILE_SIZE;
    tileNum1 = TileMapNum[entityLeftCol][entityTopRow];
    tileNum2 = TileMapNum[entityRightCol][entityTopRow];
    if (tile[tileNum1].getCollition() == true || tile[tileNum2].getCollition() ==
    true) {
    flag = true;
    }
    break;
    }
    case CheckKeyPress.Down: {
    entityBottomRow = (entityBottomWorldY + (int) speedY) / Tile.TILE_SIZE;
    tileNum1 = TileMapNum[entityLeftCol][entityBottomRow];
    tileNum2 = TileMapNum[entityRightCol][entityBottomRow];
    if (tile[tileNum1].getCollition() == true || tile[tileNum2].getCollition() ==
    true) {
    flag = true;
    onGround = true;
    }
    break;
    }
    case CheckKeyPress.Left: {
    entityLeftCol = (entityLeftWorldX - (int) speedX) / Tile.TILE_SIZE;
    tileNum1 = TileMapNum[entityLeftCol][entityTopRow];
    tileNum2 = TileMapNum[entityLeftCol][entityBottomRow];
    if (tile[tileNum1].getCollition() == true || tile[tileNum2].getCollition() ==
    true) {
    flag = true;
    }
    break;
    }
    case CheckKeyPress.Right: {
    entityRightCol = (entityRightWorldX + (int) speedX) / Tile.TILE_SIZE;
    tileNum1 = TileMapNum[entityRightCol][entityTopRow];
    tileNum2 = TileMapNum[entityRightCol][entityBottomRow];
    if (tile[tileNum1].getCollition() == true || tile[tileNum2].getCollition() ==
    true) {
    flag = true;
    }
    break;
    }
    }
    return flag;
    }

    public void setAniType() {
        switch (keyPressed) {
            case CheckKeyPress.Up: {
                aniType = PlayerAnimationType.JUMP;
                break;
            }
            case CheckKeyPress.Down: {
                if (!onGround) {
                    aniType = PlayerAnimationType.FALL;
                } else {
                    aniType = PlayerAnimationType.IDLE;
                }
                break;
            }
            case CheckKeyPress.Left: {
                if (!onGround) {
                    aniType = PlayerAnimationType.FALL;
                } else {
                    aniType = PlayerAnimationType.RUN;
                }
                break;
            }
            case CheckKeyPress.Right: {
                if (!onGround) {
                    aniType = PlayerAnimationType.FALL;
                } else {
                    aniType = PlayerAnimationType.RUN;
                }
                break;
            }
        }
    }

    public void setScreen(int mapStartX, int mapStartY) {
        this.mapStartX = mapStartX;
        this.mapStartY = mapStartY;
    }

    public void update(int mapStartX, int mapStartY) {

        // Update tick to render animation
        updateAnimationTick();

        // take AniType Old
        StartAniOld();

        // set Anitype new
        setAniType();

        // Set current type of animation
        setAnimationType();

        // set screen
        setScreen(mapStartX, mapStartY);

        // Change position if player is moving
        upDatePosition();
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

    public void setKeyPress(int keyPressed) {
        this.keyPressed = keyPressed;
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
}
