package playing.level;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import gamestate.GameState;
import gamestate.StateMethods;
import helpmethods.CheckCollision;
import helpmethods.CheckGame;
import playing.Playing;
import playing.camera.Camera;
import playing.entity.Player;
import playing.entity.bomb.Bomb;
import playing.entity.bomb.BombManager;
import playing.entity.door.DoorManager;
import playing.entity.enemy.Enemy;
import playing.entity.enemy.EnemyManager;
import playing.entity.heart.HeartManager;

public class LevelManager implements StateMethods {

    private Level levels[];
    private int currentLevel = 0;
    private String[] nameFile;
    private Camera camera;
    private Player player;
    private EnemyManager enemyManager;
    private HeartManager heartManager;
    private BombManager bombManager;
    private DoorManager doorManager;
    //private SoundManager soundManager;
    private Playing playing;

    // Constructor
    public LevelManager(Playing playing) {
        initPathMap();
        initMap();
        this.playing = playing;
        player = new Player(levels[currentLevel].getMap(), false, true);
        camera = new Camera(levels[currentLevel], player);
        enemyManager = new EnemyManager(levels[currentLevel].getMap(), player);
        heartManager = new HeartManager(levels[currentLevel].getMap(), player);
        bombManager = new BombManager(40, 0);
        doorManager = new DoorManager(levels[currentLevel].getMap(), player);
        //soundManager = new SoundManager(bombManager, heartManager);
    }

    private void initPathMap() {
        nameFile = new String[5];
        nameFile[0] = new String("Map/Map01.txt");
        nameFile[1] = new String("Map/Map02.txt");
        nameFile[2] = new String("Map/Map03.txt");
        nameFile[3] = new String("Map/Map04.txt");
        nameFile[4] = new String("Map/Map05.txt");
    }

    private void initMap() {
        levels = new Level[5];

        // Initialize first map
        levels[currentLevel] = new Level(nameFile[currentLevel]);

    }

    private void setNewMap() {
        if (enemyManager.getEnemies().size() == 0 && doorManager.getDoor().isClosed()) {

            currentLevel++;

            // Load new map
            levels[currentLevel] = new Level(nameFile[currentLevel]);

            // Set new player, camera, enemy, heart
            player = new Player(levels[currentLevel].getMap(), true, false);
            camera = new Camera(levels[currentLevel], player);
            enemyManager = new EnemyManager(levels[currentLevel].getMap(), player);
            heartManager = new HeartManager(levels[currentLevel].getMap(), player);
            bombManager = new BombManager(40, 0);
            doorManager = new DoorManager(levels[currentLevel].getMap(), player);
            //soundManager = new SoundManager(bombManager, heartManager);
        }
    }

    // collision bomb with enemy
    private void handleBombCollision() {
        Iterator<Bomb> itrBomb = bombManager.getBombs().iterator();
        while (itrBomb.hasNext()) {
            Bomb bomb = (Bomb) itrBomb.next();
            if (bomb.isExploded()) {

                // Collision with enemy
                Iterator<Enemy> itr = enemyManager.getEnemies().iterator();

                while (itr.hasNext()) {
                    Enemy enemy = (Enemy) itr.next();
                    if (CheckCollision.isCollision(bomb.getHitBox(), enemy.getHitBox())) {
                        enemy.setHealth(enemy.getHealth() - 1);
                    }
                }

                // Collision with player
                if (CheckCollision.isCollision(player.getHitBox(), bomb.getHitBox())) {
                    // If player's heart larger than 0
                    if (player.getHeartPlayer() > 0) {
                        player.setHeartPlayer(player.getHeartPlayer() - 1);

                        //
                        heartManager.getHeartPlayer().remove(heartManager.getHeartPlayer().size() - 1);
                    }
                }

                // Delete if bomb exploded
                itrBomb.remove();
            }
        }
    }

    // Delete enemy dead
    private void deleteenemy() {
        Iterator<Enemy> itr = enemyManager.getEnemies().iterator();

        while (itr.hasNext()) {
            Enemy enemy = (Enemy) itr.next();
            if (enemy.isDead()) {
                itr.remove();
            }
        }
    }

    // Check Game over
    private void checkGameOver() {
        if (CheckGame.checkGameOver(heartManager.getHeartPlayer().size(),
                bombManager.getMaxBomb() - bombManager.getNumberOfBombsExploded(), bombManager.getBombs().size(),
                enemyManager.getEnemies().size())) {
            playing.resetAll();
            soundManager.closeSound();
        }
    }

    private void checkNewScreen() {
        if (CheckGame.checkGameWin(heartManager.getHeartPlayer().size(), enemyManager.getEnemies().size())) {
            doorManager.setOpen(true);
        }
    }

    // Update Map
    public void update() {
        setNewMap();
        camera.update();
        player.update();
        for (Bomb bomb : bombManager.getBombs()) {
            bomb.update();
        }

        //
        //soundManager.update();
        //

        handleBombCollision();
        deleteenemy();

        enemyManager.update();
        heartManager.update();
        doorManager.update();

        checkGameOver();
        checkNewScreen();
    }

    // Render Map
    @Override
    public void render(Graphics g) {
        camera.render(g);
        doorManager.render(g, camera);
        player.render(g, camera);
        for (Bomb bomb : bombManager.getBombs()) {
            bomb.render(g, camera);
        }
        enemyManager.render(g, camera);
        heartManager.render(g, camera);
        bombManager.render(g);
        if(GameState.gameState == GameState.PAUSE){
            soundManager.stopSound();
        }
    }

    // Event
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_S:
                break;
            case KeyEvent.VK_W:
                player.setUp(true);
                break;
            case KeyEvent.VK_E:
                addBomb();
                break;
            case KeyEvent.VK_SPACE:
                player.setUp(true);
                break;
            default:
                break;
        }
    }

    private void addBomb() {
        if (bombManager.getNumberOfBombsExploded() < bombManager.getMaxBomb()) {
            int indexX = (int) (player.getPosition().getX()) / Tile.TILE_SIZE;
            int indexY = (int) (player.getPosition().getY()) / Tile.TILE_SIZE;
            bombManager.getBombs().add(new Bomb(indexX, indexY, levels[currentLevel].getMap()));
            bombManager.setNumberOfBombsExploded(bombManager.getNumberOfBombsExploded() + 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_S:
                break;
            case KeyEvent.VK_W:
                player.setUp(false);
                break;
            case KeyEvent.VK_E:
                break;
            case KeyEvent.VK_SPACE:
                player.setUp(false);
                break;
            default:
                break;
        }
    }

}
