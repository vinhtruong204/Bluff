package playing.tile;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gamestate.StateMethods;
import playing.camera.Camera;
import playing.entity.Player;

public class LevelManager implements StateMethods {

    private Level levels[];
    private int currentLevel = 0;
    private String[] nameFile;
    private Camera camera;
    private Player player;

    // constructor of TileManager
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

    // update Map
    public void update() {
        camera.update();
        player.update(camera.getMapStartX(), camera.getMapStartY());
    }

    // render Map
    public void render(Graphics g) {
        camera.render(g);
        player.render(g);
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
                player.setLeft(false);
                break;
                case KeyEvent.VK_SPACE:
                player.setUp(false);
            default:
                break;
        }
    }

}
