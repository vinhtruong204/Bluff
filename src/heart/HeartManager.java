package heart;

import java.awt.Graphics;
import java.util.ArrayList;

import playing.camera.Camera;

public class HeartManager {
    private ArrayList<Heart> hearts;
    private int[][] map;

    public HeartManager(int[][] map) {
        this.map = map;
        hearts = new ArrayList<>();
        addHeart();
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

    public void update() {
        for (Heart heart : hearts) {
            heart.update();
        }
    }

    public void render(Graphics g, Camera camera) {
        for (Heart heart : hearts) {
            heart.render(g, camera);
        }
    }

}
