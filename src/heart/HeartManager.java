package heart;

import java.awt.Graphics;
import java.util.ArrayList;

import playing.camera.Camera;

public class HeartManager {
    private ArrayList<Heart> hearts;
    private ArrayList<Heart> heartPlayer;
    private int[][] map;

    public HeartManager(int[][] map) {
        this.map = map;
        hearts = new ArrayList<>();
        heartPlayer = new ArrayList<>();
        addHeart();
        setHeartPlayer();
    }

    public void addHeart() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 4) {
                    hearts.add(new Heart(i, j));
                }
            }
        }
    }

    public void setHeartPlayer() {
        for (int i = 0; i < 5; i++) {
            heartPlayer.add(new Heart(0, (1 * i)));
        }
    }

    public void update() {
        for (Heart heart : hearts) {
            heart.update();
        }
        for (Heart heart : heartPlayer) {
            heart.update();
        }
    }

    public void render(Graphics g, Camera camera) {
        for (Heart heart : hearts) {
            heart.render(g, camera);
        }
        for (Heart heart : heartPlayer) {
            heart.render(g);
        }
    }

}
