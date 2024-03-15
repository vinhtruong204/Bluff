package playing.tile;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import bomb.Bomb;
import gamestate.StateMethods;
import helpmethods.BombConstants;
import playing.camera.Camera;
import playing.entity.Player;

public class LevelManager implements StateMethods {

    private Level levels[];
    private int currentLevel = 0;
    private String[] nameFile;
    private Camera camera;
    private Player player;
    private Bomb bomb;

    // Constructor
    public LevelManager() {
        initPathMap();
        initMap();
        player = new Player(levels[currentLevel]);
        camera = new Camera(levels[currentLevel], player);
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

    // Update Map
    public void update() {
        camera.update();
        player.update();
        if (bomb != null) {
            bomb.update();
        }
    }

    // Render Map
    public void render(Graphics g) {
        camera.render(g);
        player.render(g, camera);
        if (bomb != null) {
            bomb.render(g, camera);
        }
    }

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
            case KeyEvent.VK_SPACE:
                player.setUp(true);
                break;
            case KeyEvent.VK_W:
                player.setUp(true);
                break;
            case KeyEvent.VK_E:
                bomb = new Bomb();
                break;
            default:
                break;
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
            case KeyEvent.VK_SPACE:
                player.setUp(false);
                break;
            case KeyEvent.VK_W:
                player.setUp(false);
                break;
            case KeyEvent.VK_E:
                break;
            default:
                break;
        }
    }

}
