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
    private int currentLevel=0;
    private String[] nameFile;
    private Camera camera;
    private Player player;

    // constructor of TileManager
    public LevelManager() {
        // nameFile = {"Map/Map01.txt", "Map/Map02.txt", "Map/Map03.txt", "Map/Map04.txt", "Map/Map05.txt"};
        nameFile = new String[5];
        nameFile[0]=new String("Map/Map01.txt");
        nameFile[1]=new String("Map/Map02.txt");
        nameFile[2]=new String("Map/Map03.txt");
        nameFile[3]=new String("Map/Map04.txt");
        nameFile[4]=new String("Map/Map05.txt");
        initMap();
        player = new Player(levels[currentLevel].getMap());
        camera = new Camera(levels[currentLevel], player);
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
