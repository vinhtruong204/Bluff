package gamestate.playing.level;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import core.Position;
import gamestate.GameState;
import gamestate.StateMethods;
import gamestate.playing.Playing;
import gamestate.playing.camera.Camera;
import gamestate.playing.entity.Player;
import gamestate.playing.entity.arrow.Arrow;
import gamestate.playing.entity.arrow.ArrowManager;
import gamestate.playing.entity.bomb.Bomb;
import gamestate.playing.entity.bomb.BombManager;
import gamestate.playing.entity.door.DoorManager;
import gamestate.playing.entity.enemy.EnemyManager;
import gamestate.playing.entity.heart.HeartManager;
import helpmethods.CheckCollision;
import helpmethods.CheckGame;
import helpmethods.FilePath;
import sound.SoundManager;
import sound.pause_music_background.PauseMusicBackGroundState;
import sound.pause_sound.PauseSoundState;

public class LevelManager implements StateMethods {

    private Level levels[];
    private int currentLevel;
    private String[] nameFile;
    private Camera camera;
    private Player player;
    private EnemyManager enemyManager;
    private HeartManager heartManager;
    private BombManager bombManager;
    private ArrowManager arrowManager;
    private DoorManager doorManager;
    private SoundManager soundManager;

    private Playing playing;

    // Constructor
    public LevelManager(Playing playing) {
        currentLevel = 0; // Set default level
        initPathMap();
        initMap();
        this.playing = playing;
        player = new Player(levels[currentLevel].getMap(), false, true);
        camera = new Camera(levels[currentLevel], player);
        enemyManager = new EnemyManager(levels[currentLevel].getMap(), player);
        heartManager = new HeartManager(levels[currentLevel].getMap(), player);
        bombManager = new BombManager(currentLevel);
        doorManager = new DoorManager(levels[currentLevel].getMap(), player);
        soundManager = new SoundManager(bombManager, heartManager, enemyManager, doorManager, player);
        if (currentLevel == levels.length - 1)
            arrowManager = new ArrowManager(player, enemyManager, levels[currentLevel].getMap());

    }

    private void initPathMap() {
        nameFile = new String[7];
        nameFile[0] = new String(FilePath.Map.MAP_1);
        nameFile[1] = new String(FilePath.Map.MAP_2);
        nameFile[2] = new String(FilePath.Map.MAP_3);
        nameFile[3] = new String(FilePath.Map.MAP_4);
        nameFile[4] = new String(FilePath.Map.MAP_5);
        nameFile[5] = new String(FilePath.Map.MAP_6);
        nameFile[6] = new String(FilePath.Map.MAP_7);
    }

    private void initMap() {
        levels = new Level[7];

        // Initialize first map
        levels[currentLevel] = new Level(nameFile[currentLevel]);

    }

    private void setNewMap() {
        if (enemyManager.getEnemies().size() == 0 && doorManager.getDoor().isClosed()) {
            soundManager.closeSound();
            currentLevel++;
            // Load new map
            levels[currentLevel] = new Level(nameFile[currentLevel]);

            // Set new player, camera, enemy, heart
            player = new Player(levels[currentLevel].getMap(), true, false);
            camera = new Camera(levels[currentLevel], player);
            enemyManager = new EnemyManager(levels[currentLevel].getMap(), player);
            heartManager = new HeartManager(levels[currentLevel].getMap(), player);
            bombManager = new BombManager(currentLevel);
            doorManager = new DoorManager(levels[currentLevel].getMap(), player);
            soundManager = new SoundManager(bombManager, heartManager, enemyManager, doorManager, player);
            if (currentLevel == levels.length - 1)
                arrowManager = new ArrowManager(player, enemyManager, levels[currentLevel].getMap());
        }
    }

    // collision bomb with enemy
    private void handleBombCollision() {
        for (int i = 0; i < bombManager.getBombs().size(); i++) {
            if (bombManager.getBombs().get(i).isExploded()) {
                // Collision with enemy
                for (int j = 0; j < enemyManager.getEnemies().size(); j++) {
                    if (CheckCollision.isCollision(bombManager.getBombs().get(i).getHitBox(),
                            enemyManager.getEnemies().get(j).getHitBox())) {
                        if (enemyManager.getEnemies().get(j).getHealth() > 0) {
                            enemyManager.getEnemies().get(j)
                                    .setHealth(enemyManager.getEnemies().get(j).getHealth() - 1);
                            enemyManager.getEnemies().get(j).setInjured(true);
                        }
                    }
                }
                // Collision with player
                if (CheckCollision.isCollision(player.getHitBox(), bombManager.getBombs().get(i).getHitBox())) {
                    // If player's heart larger than 0
                    if (player.getHeartPlayer() > 0) {
                        player.setHeartPlayer(player.getHeartPlayer() - 1);

                        // Decrease heart of player
                        heartManager.getHeartPlayer().remove(heartManager.getHeartPlayer().size() - 1);
                    }
                }

                // Delete if bomb exploded
                bombManager.getBombs().remove(i);
            }
        }
    }

    // Delete enemy dead
    private void deleteEnemy() {
        for (int i = 0; i < enemyManager.getEnemies().size(); i++) {
            if (enemyManager.getEnemies().get(i).isDead()) {
                enemyManager.getEnemies().remove(i);
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

    private void checkWinGame() {
        if (currentLevel == levels.length - 1 && doorManager.getDoor().isClosed()) {
            soundManager.closeSound();
            playing.resetAll();
            GameState.gameState = GameState.WIN;
        }
    }

    // Update Map
    public void update() {
        checkWinGame();
        if (GameState.gameState == GameState.PLAYING) {
            setNewMap();
            camera.update();
            player.update();
            Iterator<Bomb> itrBomb = bombManager.getBombs().iterator();
            while (itrBomb.hasNext()) {
                Bomb bomb = (Bomb) itrBomb.next();
                bomb.update();
            }

            soundManager.update();

            handleBombCollision();
            deleteEnemy();

            if (currentLevel == levels.length - 1)
                arrowManager.update();
            enemyManager.update();
            heartManager.update();
            doorManager.update();

            checkGameOver();
            checkNewScreen();
        }
    }

    // Render Map
    @Override
    public void render(Graphics g) {
        camera.render(g);
        doorManager.render(g, camera);
        player.render(g, camera);
        Iterator<Bomb> itrBomb = bombManager.getBombs().iterator();
        while (itrBomb.hasNext()) {
            Bomb bomb = (Bomb) itrBomb.next();
            bomb.render(g, camera);
        }

        // If is the last level
        if (currentLevel == levels.length - 1)
            arrowManager.render(g, camera);
        enemyManager.render(g, camera);
        heartManager.render(g, camera);
        bombManager.render(g);
        if (GameState.gameState == GameState.PAUSE) {
            soundManager.stopSound();
        }

        if (PauseSoundState.pauseSoundState == PauseSoundState.OFF) {
            if (soundManager.getsBomb() != null)
                soundManager.getsBomb().stop();
            if (soundManager.getsHeart() != null)
                soundManager.getsHeart().stop();
            if (soundManager.getsEnemyAttack() != null)
                soundManager.getsEnemyAttack().stop();
            if (soundManager.getsNewMap() != null)
                soundManager.getsNewMap().stop();
            if (soundManager.getsJumpPlayer() != null)
                soundManager.getsJumpPlayer().stop();
        }

        if (PauseMusicBackGroundState.pauseMusicBackGroundState == PauseMusicBackGroundState.OFF) {
            if (soundManager.getmBackground() != null)
                soundManager.getmBackground().stop();
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
            case KeyEvent.VK_Q:
                arrowManager.getArrows().add(new Arrow(new Position(100, 500), new Position(300, 100)));
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

    // getter and setter

    public SoundManager getSoundManager() {
        return soundManager;
    }

}
