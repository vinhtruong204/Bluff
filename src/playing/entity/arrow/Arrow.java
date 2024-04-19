package playing.entity.arrow;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import helpmethods.ArrowConstants;
import helpmethods.FilePath;
import helpmethods.LoadSave;
import playing.camera.Camera;
import playing.entity.GameObject;

public class Arrow extends GameObject {

    private static final int ARROW_WIDTH = 64;
    private static final int ARROW_HEIGHT = 15;
    private final int MAXSPEED = 10;

    private float speedX;
    private float speedY;

    private BufferedImage[][] animations;
    private Rectangle hitBox;
    private ArrowDirection direction;
    private Position positionStart;
    private Position positionEnd;

    protected int aniType;
    protected int aniTick, aniIndex, aniSpeed;

    public Arrow(Position positionStart, Position positionEnd) {
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        position = new Position(positionStart.getX(), positionStart.getY());
        hitBox = new Rectangle((int) positionStart.getX(), (int) positionStart.getY(), ARROW_WIDTH, ARROW_HEIGHT);
        size = new Size(ARROW_WIDTH, ARROW_HEIGHT);
        aniType = ArrowConstants.ARROW_SHOT;
        aniIndex = 0;
        aniSpeed = 3;
        loadAnimations();
        setDirection();
        setSpeed();
    }

    private void loadAnimations() {
        // Allocate memory
        animations = new BufferedImage[ArrowConstants.TOTAL_TYPE][ArrowConstants.TOTAL_FRAME];

        // Load image from file
        BufferedImage image = LoadSave.loadImage(FilePath.Arrow.ARROW);

        // Put animation into matrix
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = image.getSubimage(j * ARROW_WIDTH, i * ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT);
    }

    public void setSpeed() {
        float distanceX = Math.abs(positionStart.getX() - positionEnd.getX());
        float distanceY = Math.abs(positionStart.getY() - positionEnd.getY());
        if (distanceX >= distanceY) {
            speedX = MAXSPEED;
            speedY = (distanceY / (distanceX / speedX));
        } else {
            speedY = MAXSPEED;
            speedX = (distanceX / (distanceY / speedY));
        }
    }

    // Align Time
    private void updateAnimationTick() {
        // 60fps => 20 animation frames rendered
        aniTick++;

        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= ArrowConstants.getSpriteAmount(aniType)) {
                aniIndex = 0;
            }
        }
    }

    private void setDirection() {
        if (positionStart.getY() == positionEnd.getY()) {
            if (positionStart.getX() < positionEnd.getX()) {
                direction = ArrowDirection.RIGHT;
            } else {
                direction = ArrowDirection.LEFT;
            }
        } else {
            if (positionStart.getY() > positionEnd.getY() && positionStart.getX() < positionEnd.getX()) {
                direction = ArrowDirection.DIAGONAL_UP_RIGHT;
            } else if (positionStart.getY() > positionEnd.getY() && positionStart.getX() > positionEnd.getX()) {
                direction = ArrowDirection.DIAGONAL_UP_LEFT;
            } else if (positionStart.getY() > positionEnd.getY() && positionStart.getX() == positionEnd.getX()) {
                direction = ArrowDirection.UP;
            } else if (positionStart.getY() < positionEnd.getY() && positionStart.getX() < positionEnd.getX()) {
                direction = ArrowDirection.DIAGONAL_DOWN_RIGHT;
            } else if (positionStart.getY() < positionEnd.getY() && positionStart.getX() > positionEnd.getX()) {
                direction = ArrowDirection.DIAGONAL_DOWN_LEFT;
            } else if (positionStart.getY() < positionEnd.getY() && positionStart.getX() == positionEnd.getX()) {
                direction = ArrowDirection.DOWN;
            }
        }
    }

    private void updatePosition() {
        switch (direction) {
            case ArrowDirection.RIGHT:
                position.setX(position.getX() + speedX);
                break;
            case ArrowDirection.LEFT:
                position.setX(position.getX() - speedX);
                break;
            case ArrowDirection.UP:
                position.setY(position.getY() - speedY);
                break;
            case ArrowDirection.DOWN:
                position.setY(position.getY() + speedY);
                break;
            case ArrowDirection.DIAGONAL_UP_LEFT:
                position.setX(position.getX() - speedX);
                position.setY(position.getY() - speedY);
                break;
            case ArrowDirection.DIAGONAL_UP_RIGHT:
                position.setY(position.getY() - speedY);
                position.setX(position.getX() + speedX);
                break;
            case ArrowDirection.DIAGONAL_DOWN_LEFT:
                position.setY(position.getY() + speedY);
                position.setX(position.getX() - speedX);
                break;
            case ArrowDirection.DIAGONAL_DOWN_RIGHT:
                position.setY(position.getY() + speedY);
                position.setX(position.getX() + speedX);
                break;
            default:
                break;
        }

        hitBox = new Rectangle((int) positionStart.getX(), (int) positionStart.getY(), ARROW_WIDTH, ARROW_HEIGHT);
    }

    @Override
    public void update() {
        updatePosition();
        updateAnimationTick();
    }

    @Override
    public void render(Graphics g, Camera camera) {
        // Image and position render
        BufferedImage temp = animations[aniType][aniIndex];

        Graphics2D g2d = (Graphics2D) g.create();

        // If player's direction is left

        int imageWidth = temp.getWidth();
        int imageHeight = temp.getHeight();

        // Tính toán tọa độ của tâm của ảnh
        int imageCenterX = (int) position.getX() - camera.getMapStartX() + imageWidth / 2;
        int imageCenterY = (int) position.getY() - camera.getMapStartY() + imageHeight / 2;

        // Di chuyển trục tọa độ để làm cho tâm của ảnh là (0, 0)
        AffineTransform oldAT = g2d.getTransform();
        g2d.translate(imageCenterX, imageCenterY);
        //
        float doy = (float) Math.sqrt(Math.pow(positionEnd.getX() - positionStart.getX(), 2)
                + Math.pow(positionEnd.getY() - positionStart.getY(), 2));
        float dyx = Math.abs(positionStart.getY() - positionEnd.getY());
        float angle = (float) Math.toDegrees((float) Math.asin(dyx / doy));
        //
        if (direction == ArrowDirection.LEFT) {
            // Thực hiện xoay
            g2d.rotate(Math.toRadians(180)); // Đổi giá trị góc xoay ở đây nếu cần
        } else if (direction == ArrowDirection.RIGHT) {
            g2d.rotate(Math.toRadians(0));
        } else if (direction == ArrowDirection.UP) {
            g2d.rotate(Math.toRadians(270));
        } else if (direction == ArrowDirection.DOWN) {
            g2d.rotate(Math.toRadians(90));
        } else if (direction == ArrowDirection.DIAGONAL_UP_RIGHT) {
            g2d.rotate(Math.toRadians(360 - (int) angle));
        } else if (direction == ArrowDirection.DIAGONAL_UP_LEFT) {
            g2d.rotate(Math.toRadians(180 + (int) angle));
        } else if (direction == ArrowDirection.DIAGONAL_DOWN_RIGHT) {
            g2d.rotate(Math.toRadians((int) angle));
        } else if (direction == ArrowDirection.DIAGONAL_DOWN_LEFT) {
            g2d.rotate(Math.toRadians(180 - (int) angle));
        }

        // Render image
        g2d.drawImage(temp,
                -imageWidth / 2,
                -imageHeight / 2,
                size.getWidth(),
                size.getHeight(),
                null);
        // Khôi phục lại trạng thái trước khi di chuyển trục tọa độ
        g2d.setTransform(oldAT);
    }
}
