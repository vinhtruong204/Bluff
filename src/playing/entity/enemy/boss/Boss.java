package playing.entity.enemy.boss;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Stack;

import core.PointMatrix;
import core.Position;
import core.Size;
import helpmethods.EnemyConstants;
import helpmethods.EnemyConstants.BossConstants;
import helpmethods.FilePath;
import helpmethods.LoadSave;
import helpmethods.WalkDirection;
import playing.entity.enemy.Enemy;
import playing.level.Tile;

public class Boss extends Enemy {

    /*
     * left,
     * diagonal up left,
     * diagonal down left,
     * up,
     * down,
     * right,
     * diagonal up right,
     * diagonal down right
     */
    // private int[] dx = { 0, -1, 1, -1, 1, 0, -1, 1 }; // row
    // private int[] dy = { -1, -1, -1, 0, 0, 1, 1, 1 }; // col
    private int[] dx = { 0, -1, 1, 0 };
    private int[] dy = { -1, 0, 0, 1 };
    private boolean[][] visited;
    private Stack<PointMatrix> points;

    public Boss(int enemyType, int i, int j, int[][] map) {
        super(enemyType, map);

        // Allocate memory
        visited = new boolean[map.length][map[0].length];

        resetBooleanVisited();
        points = new Stack<>();

        offsetX = 45;
        offsetY = 45;

        // Init position
        position = new Position(j * Tile.TILE_SIZE + offsetX, Tile.TILE_SIZE * i + offsetY);
        oldPos = position;
        size = new Size(BossConstants.BOSS_WIDTH, BossConstants.BOSS_HEIGHT);
        hitBox = new Rectangle(
                (int) position.getX(),
                (int) position.getY(),
                size.getWidth() - 2 * offsetX,
                size.getHeight() - 2 * offsetY);

        // Initialize boolean dead
        dead = false;

        // Initialize boolean injured
        injured = false;

        // Init left and right bounds
        initBounds();

        // Init animation and direction
        aniType = BossConstants.IDLE;
        direction = WalkDirection.RIGHT;

        // Set animation speed for boss
        aniSpeed = 4;
        loadAni();
    }

    private void resetBooleanVisited() {
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                visited[i][j] = false;
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && col >= 0 && row < 25 && col < 30;
    }

    private void bfs(PointMatrix src, PointMatrix des) {
        points.clear();
        resetBooleanVisited();

        ArrayDeque<PointMatrix> q = new ArrayDeque<>();

        visited[src.getRow()][src.getCol()] = true;
        q.addLast(src);

        while (!q.isEmpty()) {
            PointMatrix current = q.removeFirst();

            points.add(current);
            if (current.getRow() == des.getRow() && current.getCol() == des.getCol())
                break;

            for (int i = 0; i < dx.length; i++) {
                PointMatrix nextPoint = new PointMatrix(current.getRow() + dx[i], current.getCol() + dy[i],
                        current.getRow(), current.getCol());
                if (isValid(nextPoint.getRow(), nextPoint.getCol()) && !visited[nextPoint.getRow()][nextPoint.getCol()]
                        && map[nextPoint.getRow()][nextPoint.getCol()] != 1) {
                    q.addLast(nextPoint);
                    visited[nextPoint.getRow()][nextPoint.getCol()] = true;
                }
            }
        }
    }

    @Override
    protected void loadAni() {
        // Allocate memory
        animations = new BufferedImage[BossConstants.TOTAL_TYPE][BossConstants.TOTAL_FRAME];

        // Initialize image animation
        BufferedImage temp = new BufferedImage(BossConstants.BOSS_WIDTH, BossConstants.BOSS_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        // Load image depend on type of boss
        switch (enemyType) {
            case EnemyConstants.ARCHER_BLUE:
                temp = LoadSave.loadImage(FilePath.Enemy.ARCHER_BLUE);
                break;
            case EnemyConstants.ARCHER_PURPLE:
                temp = LoadSave.loadImage(FilePath.Enemy.ARCHER_PURPLE);
                break;
            case EnemyConstants.ARCHER_YELLOW:
                temp = LoadSave.loadImage(FilePath.Enemy.ARCHER_YELLOW);
                break;
            case EnemyConstants.ARCHER_RED:
                temp = LoadSave.loadImage(FilePath.Enemy.ARCHER_RED);
                break;
            default:
                break;
        }

        // Get all animation frames of enemy
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = temp.getSubimage(
                        j * BossConstants.BOSS_WIDTH,
                        i * BossConstants.BOSS_HEIGHT,
                        BossConstants.BOSS_WIDTH,
                        BossConstants.BOSS_HEIGHT);
            }
        }
    }

    @Override
    public void update(Rectangle playerHitBox) {
        //chasePlayer(playerHitBox);
        updateAnimationTick(playerHitBox);
    }

    private void chasePlayer(Rectangle playerHitBox) {
        // Calculate point of player, boss in map matrix
        PointMatrix bossPosMatrix = new PointMatrix((int) hitBox.y / Tile.TILE_SIZE,
                (int) hitBox.x / Tile.TILE_SIZE, -1, -1);
        PointMatrix playerPosMatrix = new PointMatrix((int) playerHitBox.y / Tile.TILE_SIZE,
                (int) playerHitBox.x / Tile.TILE_SIZE, -1, -1);

        if (bossPosMatrix.getCol() == playerPosMatrix.getCol()
                && bossPosMatrix.getRow() == playerPosMatrix.getRow())
            return;

        bfs(playerPosMatrix, bossPosMatrix);

        PointMatrix nextPoint = points.pop();
        nextPoint = points.pop();
        // System.out.println(currPoint.getRow() + "\t" + currPoint.getCol());
        // int prevRow = currPoint.getPrevRow();
        // int prevCol = currPoint.getPrevCol();

        setVelocity(bossPosMatrix, nextPoint);
        moveBoss();

        // while (!points.isEmpty()) {
        // currPoint = points.pop();
        // if (currPoint.getRow() == prevRow && currPoint.getCol() == prevCol) {
        // System.out.println(currPoint.getRow() + "\t" + currPoint.getCol());
        // prevRow = currPoint.getPrevRow();
        // prevCol = currPoint.getPrevCol();
        // }
        // }

        // System.out.println("Complete");
    }

    private void setVelocity(PointMatrix bossPosMatrix, PointMatrix nextPoint) {
        velocity.setX((nextPoint.getCol() - bossPosMatrix.getCol()));
        velocity.setY((nextPoint.getRow() - bossPosMatrix.getRow()));
    }

    private void moveBoss() {
        position.setX(position.getX() + velocity.getX());
        position.setY(position.getY() + velocity.getY());
        hitBox = new Rectangle((int) position.getX(),
                (int) position.getY(),
                size.getWidth() - 2 * offsetX,
                size.getHeight() - 2 * offsetY);
    }

    @Override
    protected void setAniType() {

    }

    @Override
    protected void updateAnimationTick(Rectangle playerHitBox) {
        aniTick++;
        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= BossConstants.getSpriteAmount(aniType)) {
                aniIndex = 0;
            }
        }
    }

    @Override
    public void update() {
    }

    // getter and setter
}
