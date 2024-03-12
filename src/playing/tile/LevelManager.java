package playing.tile;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gamestate.StateMethods;
import helpmethods.CheckKeyPress;
import playing.camera.Camera;
import playing.entity.Player;

public class LevelManager implements StateMethods {

    private Level levels[];
    private int currentLevel = 0;
    private Camera camera;
    private Player player;

    // constructor of TileManager
    public LevelManager() {
        levels = new Level[5];
        initMap();
        player = new Player(levels[currentLevel].getMap());
        camera = new Camera(levels[currentLevel], player);
    }

    private void initMap() {
        for (int i = 0; i < levels.length; i++) {
            levels[i] = new Level("Map/Map01.txt");
        }
    }

    // update Map
    public void update() {
        //camera.update();
        player.update(camera.getMapStartX(), camera.getMapStartY());
    }

    // render Map
    public void render(Graphics g) {
        //camera.render(g);
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
                player.setKeyPress(CheckKeyPress.Left);
                break;
            case KeyEvent.VK_W:
                player.setKeyPress(CheckKeyPress.Up);
                break;
            case KeyEvent.VK_D:
                player.setKeyPress(CheckKeyPress.Right);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setKeyPress(CheckKeyPress.Down);
                break;
            case KeyEvent.VK_W:
                player.setKeyPress(CheckKeyPress.Down);
                break;
            case KeyEvent.VK_D:
                player.setKeyPress(CheckKeyPress.Down);
                break;
            default:
                break;
        }
    }

}
