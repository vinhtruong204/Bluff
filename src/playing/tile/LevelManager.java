package playing.tile;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import gamestate.StateMethods;
import helpmethods.CheckCollision;
import playing.camera.Camera;
import playing.entity.Cucumber;
import playing.entity.EnemyManager;
import playing.entity.Player;
import playing.entity.bomb.Bomb;
import playing.entity.heart.HeartManager;

public class LevelManager implements StateMethods {

    private Level levels[];
    private int currentLevel = 0;
    private String[] nameFile;
    private Camera camera;
    private Player player;
    private ArrayList<Bomb> bombs;
    private EnemyManager enemyManager;
    private HeartManager heartManager;

    // Constructor
    public LevelManager() {
        initPathMap();
        initMap();
        player = new Player(levels[currentLevel]);
        camera = new Camera(levels[currentLevel], player);
        enemyManager = new EnemyManager(levels[currentLevel].getMap(), player);
        heartManager = new HeartManager(levels[currentLevel].getMap(), player);
        bombs = new ArrayList<>();

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
        for (int i = 0; i < levels.length; i++) {
            levels[i] = new Level(nameFile[i]);
        }
    }

    // collision bomb with cucumber
    public void collisionBombWithCucumber() {
        for (Bomb bomb : bombs) {
            Iterator<Cucumber> itr = enemyManager.getCucumbers().iterator();
            while (itr.hasNext()) {
                Cucumber cucumber = (Cucumber) itr.next();
                if (bomb.isExploded() && CheckCollision.isCollision(bomb.getHitBox(), cucumber.getHitBox())) {
                    cucumber.setDead(true);
                }
            }
        }
    }

    // collision bomb with Player
    public void collisionBombWithPlayer() {
        Iterator<Bomb> itr = bombs.iterator();
        while (itr.hasNext()) {
            Bomb bomb = (Bomb) itr.next();
            if (bomb.isExploded() && CheckCollision.isCollision(player.getHitBox(), bomb.getHitBox())) {
                if (player.getMaxHeart() > 0) {
                    player.setMaxHeart(player.getMaxHeart() - 1);
                    heartManager.getHeartPlayer().remove(heartManager.getHeartPlayer().size() - 1);
                }
            }
        }
    }

    // Delete cucumber dead
    public void deleteCucumber() {
        Iterator<Cucumber> itr = enemyManager.getCucumbers().iterator();
        while (itr.hasNext()) {
            Cucumber cucumber = (Cucumber) itr.next();
            if (cucumber.isDead()) {
                itr.remove();
            }
        }
    }

    // Delete bomb Exploded
    public void deleteBomb() {
        Iterator<Bomb> itr = bombs.iterator();
        while (itr.hasNext()) {
            Bomb bomb = (Bomb) itr.next();
            if (bomb.isExploded()) {
                itr.remove();
            }
        }
    }

    // Update Map
    public void update() {
        camera.update();
        player.update();
        for (Bomb bomb : bombs) {
            bomb.update();
        }
        collisionBombWithCucumber();
        collisionBombWithPlayer();
        deleteCucumber();
        deleteBomb();
        enemyManager.update();
        heartManager.update();
    }

    // Render Map
    public void render(Graphics g) {
        camera.render(g);
        player.render(g, camera);
        for (Bomb bomb : bombs) {
            bomb.render(g, camera);
        }
        enemyManager.render(g, camera);
        heartManager.render(g, camera);
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
                player.setDown(true);
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
        int indexX = (int) (player.getPosition().getX()) / Tile.TILE_SIZE;
        int indexY = (int) (player.getPosition().getY()) / Tile.TILE_SIZE;
        bombs.add(new Bomb(indexX, indexY));
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
                player.setDown(false);
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
