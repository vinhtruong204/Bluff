package playing.entity.arrow;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import game.Game;
import helpmethods.ArrowConstants;
import helpmethods.CheckDirectionShot;
import helpmethods.DirectionShot;
import helpmethods.FilePath;
import helpmethods.LoadSave;
import playing.camera.Camera;
import playing.entity.GameObject;

public class Arrow extends GameObject {

    private static final int ARROW_WIDTH = 64;
    private static final int ARROW_HEIGHT = 15;
    private final float MAXSPEED = 8;

    private float speedX;
    private float speedY;

    private BufferedImage[][] animations;

    private Rectangle hitBox;
    // private Vector2D velocity;

    private DirectionShot direction;
    private Position positionStart;
    private Position positionEnd;

    protected int aniType;
    protected int aniIndex;

    public Arrow(Position positionStart, Position positionEnd) {
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        position = new Position(positionStart.getX(), positionStart.getY());
        // velocity = new Vector2D(0, 0);
        hitBox = new Rectangle((int) positionStart.getX(), (int) positionStart.getY(), ARROW_WIDTH, ARROW_HEIGHT);
        size = new Size(ARROW_WIDTH, ARROW_HEIGHT);
        aniType = ArrowConstants.ARROW_SHOT;
        aniIndex = 0;
        loadAnimations();
        setDirection();
        //setShootingPoint();
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

    private void setSpeed() {
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

    // private void setShootingPoint() {
    //     switch (direction) {
    //         case DirectionShot.RIGHT:
    //             position = new Position(position.getX(),
    //                     position.getY() + (float) ARROW_HEIGHT / 2);
    //                     System.out.println("right");
    //             break;
    //         case DirectionShot.LEFT:
    //             position = new Position(position.getX() - (float)ARROW_WIDTH, position.getY() + (float) ARROW_HEIGHT / 2);
    //             System.out.println("left");
    //             break;
    //         case DirectionShot.UP:
    //             position = new Position(position.getX() - (float) ARROW_WIDTH / 2, position.getY());
    //             System.out.println("up");
    //             break;
    //         case DirectionShot.DOWN:
    //             position = new Position(position.getX() + (float) ARROW_WIDTH / 2,
    //                     position.getY() + (float) ARROW_HEIGHT);
    //                     System.out.println("down");
    //             break;
    //         case DirectionShot.DIAGONAL_UP_LEFT:
    //         position = new Position(position.getX() - (float) ARROW_WIDTH / 2, position.getY());
    //         System.out.println("up left");
    //             break;
    //         case DirectionShot.DIAGONAL_UP_RIGHT:
    //             System.out.println("up right");
    //             break;
    //         case DirectionShot.DIAGONAL_DOWN_LEFT:
    //             position = new Position(position.getX() - (float)ARROW_WIDTH, position.getY() + (float) ARROW_HEIGHT);
    //             System.out.println("down left");
    //             break;
    //         case DirectionShot.DIAGONAL_DOWN_RIGHT:
    //             position = new Position(position.getX(), position.getY() + (float) ARROW_HEIGHT);
    //             System.out.println("down right");
    //             break;
    //         default:
    //             break;
    //     }
    // }

    private void setDirection() {
        direction = CheckDirectionShot.setDirection(positionStart, positionEnd);
    }

    private void updatePosition() {
        switch (direction) {
            case DirectionShot.RIGHT:
                position.setX(position.getX() + speedX);
                break;
            case DirectionShot.LEFT:
                position.setX(position.getX() - speedX);
                break;
            case DirectionShot.UP:
                position.setY(position.getY() - speedY);
                break;
            case DirectionShot.DOWN:
                position.setY(position.getY() + speedY);
                break;
            case DirectionShot.DIAGONAL_UP_LEFT:
                position.setX(position.getX() - speedX);
                position.setY(position.getY() - speedY);
                break;
            case DirectionShot.DIAGONAL_UP_RIGHT:
                position.setY(position.getY() - speedY);
                position.setX(position.getX() + speedX);
                break;
            case DirectionShot.DIAGONAL_DOWN_LEFT:
                position.setY(position.getY() + speedY);
                position.setX(position.getX() - speedX);
                break;
            case DirectionShot.DIAGONAL_DOWN_RIGHT:
                position.setY(position.getY() + speedY);
                position.setX(position.getX() + speedX);
                break;
            default:
                break;
        }

        hitBox = new Rectangle((int) position.getX(), (int) position.getY(), ARROW_WIDTH, ARROW_HEIGHT);
    }

    @Override
    public void update() {
        updatePosition();
    }

    @Override
    public void render(Graphics g, Camera camera) {
        // Image and position render
        BufferedImage temp = animations[aniType][aniIndex];

        if ((int) position.getX() - camera.getMapStartX() >= 0
                && (int) position.getX() - camera.getMapStartX() <= Game.SCREEN_WIDTH
                && (int) position.getY() - camera.getMapStartY() >= 0
                && (int) position.getY() - camera.getMapStartY() <= Game.SCREEN_HEIGHT) {

            Graphics2D g2d = (Graphics2D) g.create();

            // If player's direction is left

            int imageWidth = temp.getWidth();
            int imageHeight = temp.getHeight();

            // Calculate the coordinates of the center of the image
            int imageCenterX = (int) position.getX() - camera.getMapStartX() + imageWidth / 2;
            int imageCenterY = (int) position.getY() - camera.getMapStartY() + imageHeight / 2;

            // Move the coordinate axis to make the center of the image (0, 0)
            AffineTransform oldAT = g2d.getTransform();
            g2d.translate(imageCenterX, imageCenterY);
            //
            float doy = (float) Math.sqrt(Math.pow(positionEnd.getX() - positionStart.getX(), 2)
                    + Math.pow(positionEnd.getY() - positionStart.getY(), 2));
            float dyx = Math.abs(positionStart.getY() - positionEnd.getY());
            float angle = (float) Math.toDegrees((float) Math.asin(dyx / doy));
            //perform rotation
            if (direction == DirectionShot.LEFT) {
                g2d.rotate(Math.toRadians(180));
            } else if (direction == DirectionShot.RIGHT) {
                g2d.rotate(Math.toRadians(0));
            } else if (direction == DirectionShot.UP) {
                g2d.rotate(Math.toRadians(270));
            } else if (direction == DirectionShot.DOWN) {
                g2d.rotate(Math.toRadians(90));
            } else if (direction == DirectionShot.DIAGONAL_UP_RIGHT) {
                g2d.rotate(Math.toRadians(360 - (int) angle));
            } else if (direction == DirectionShot.DIAGONAL_UP_LEFT) {
                g2d.rotate(Math.toRadians(180 + (int) angle));
            } else if (direction == DirectionShot.DIAGONAL_DOWN_RIGHT) {
                g2d.rotate(Math.toRadians((int) angle));
            } else if (direction == DirectionShot.DIAGONAL_DOWN_LEFT) {
                g2d.rotate(Math.toRadians(180 - (int) angle));
            }

            // Render image
            g2d.drawImage(temp,
                    -imageWidth / 2,
                    -imageHeight / 2,
                    size.getWidth(),
                    size.getHeight(),
                    null);
            // Restore the state before moving the coordinate axis
            g2d.setTransform(oldAT);
        }
    }

    // getter and setter
    public DirectionShot getDirection() {
        return direction;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setAniType(int aniType) {
        this.aniType = aniType;
    }
}
