package playing.entity.enemy.boss;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

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
    private int[] dx = { 0, -1, 1, -1, 1, 0, -1, 1 }; // row
    private int[] dy = { -1, -1, -1, 0, 0, 1, 1, 1 }; // col
    private boolean[][] visited;

    public Boss(int enemyType, int i, int j, int[][] map) {
        super(enemyType, map);

        // Allocate memory
        visited = new boolean[map.length][map[0].length];

        resetBooleanVisited();

        offsetX = 1;
        offsetY = 1;

        // Init position
        position = new Position(j * Tile.TILE_SIZE, Tile.TILE_SIZE * i);
        oldPos = position;
        size = new Size(BossConstants.BOSS_WIDTH, BossConstants.BOSS_HEIGHT);
        hitBox = new Rectangle(
                (int) position.getX(),
                (int) position.getY(),
                size.getWidth(),
                size.getHeight());

        // Initialize boolean dea
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
                visited = false;
            }
        }
    }

    private void initVisited() {
        
        for (int i = )
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
        updateAnimationTick(playerHitBox);
        System.out.println(seePlayer());
    }

    private boolean seePlayer() {

        return true;
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

}
