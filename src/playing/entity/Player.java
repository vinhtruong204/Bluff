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
import playing.tile.TileManager;

public class Player extends GameObject {

    private final int PLAYER_WIDTH = 58;
    private final int PLAYER_HEIGHT = 58;

    private Vector2D velocity;
    private int speed;

    private BufferedImage[][] animations;
    private Tile tile[];
    private int[][] TileMapNum;
    private int aniType;
    private int aniTick, aniIndex, aniSpeed;

    private boolean moving;

    private Rectangle hitBox;
    private boolean onGround;

    private int keyPressed;

    public Player(Tile[] tile, int[][] TileMapNum) {
        position = new Position(100f, 300f);
        size = new Size(PLAYER_WIDTH, PLAYER_HEIGHT);

        velocity = new Vector2D(0, 0);
        speed = 5;
        aniSpeed = 3;
        animations = new BufferedImage[6][26];
        hitBox = new Rectangle(8, 8, 48, 48);
        this.tile = tile;
        this.TileMapNum = TileMapNum;
        onGround = false;
        loadAnimations();
    }

    private void loadAnimations() {
        image = LoadSave.loadImage("img/Player/Player-Bomb Guy.png");
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

        if (keyPressed == CheckKeyPress.Down) {
            onGround = false;
            velocity.setY(speed);
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

        if (checkTile() == false) {
            position = new Position(position.getX() + velocity.getX(), position.getY() + velocity.getY());
        }
    }

    private boolean checkTile() {
        int entityLeftWorldX = (int) position.getX() + hitBox.x;
        int entityRightWorldX = (int) position.getX() + hitBox.x + hitBox.width;
        int entityTopWorldY = (int) position.getY() + hitBox.y;
        int entityBottomWorldY = (int) position.getY() + hitBox.y + hitBox.height;

        int entityLeftCol = entityLeftWorldX / TileManager.TILE_SIZE;
        int entityRightCol = entityRightWorldX / TileManager.TILE_SIZE;
        int entityTopRow = entityTopWorldY / TileManager.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / TileManager.TILE_SIZE;

        int tileNum1, tileNum2;
        boolean flag = false;

        switch (keyPressed) {
            case CheckKeyPress.Up: {
                entityTopRow = (entityTopWorldY - speed) / TileManager.TILE_SIZE;
                tileNum1 = TileMapNum[entityLeftCol][entityTopRow];
                tileNum2 = TileMapNum[entityRightCol][entityTopRow];
                if (tile[tileNum1].getCollition() == true || tile[tileNum2].getCollition() == true) {
                    System.out.println("Up");
                    flag = true;
                }
                break;
            }
            case CheckKeyPress.Down: {
                entityBottomRow = (entityBottomWorldY + speed) / TileManager.TILE_SIZE;
                tileNum1 = TileMapNum[entityLeftCol][entityBottomRow];
                tileNum2 = TileMapNum[entityRightCol][entityBottomRow];
                if (tile[tileNum1].getCollition() == true || tile[tileNum2].getCollition() == true) {
                    System.out.println("Down");
                    flag = true;
                }
                break;
            }
            case CheckKeyPress.Left: {
                entityLeftCol = (entityLeftWorldX - speed) / TileManager.TILE_SIZE;
                tileNum1 = TileMapNum[entityLeftCol][entityTopRow];
                tileNum2 = TileMapNum[entityLeftCol][entityBottomRow];
                if (tile[tileNum1].getCollition() == true || tile[tileNum2].getCollition() == true) {
                    System.out.println("Left");
                    flag = true;
                }
                break;
            }
            case CheckKeyPress.Right: {
                entityRightCol = (entityRightWorldX + speed) / TileManager.TILE_SIZE;
                tileNum1 = TileMapNum[entityRightCol][entityTopRow];
                tileNum2 = TileMapNum[entityRightCol][entityBottomRow];
                if (tile[tileNum1].getCollition() == true || tile[tileNum2].getCollition() == true) {
                    System.out.println("right");
                    flag = true;
                }
                break;
            }
            default: {
                System.out.println("Default");
                flag = false;
                break;
            }
        }
        return flag;
    }

    @Override
    public void update() {
        // Set current type of animation
        setAnimationType();

        // Update tick to render animation
        updateAnimationTick();

        checkTile();

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
