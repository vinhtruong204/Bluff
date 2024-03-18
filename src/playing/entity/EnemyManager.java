package playing.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import helpmethods.EnemyConstants;
import playing.camera.Camera;

public class EnemyManager {
    private ArrayList<Cucumber> cucumbers;

    private int[][] map;
    private Player player;

    public EnemyManager(int[][] map, Player player) {
        cucumbers = new ArrayList<>();
        this.map = map;
        this.player = player;
        addCucumber();
    }

    private void addCucumber() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 5) {
                    cucumbers.add(new Cucumber(EnemyConstants.CUCUMBER, i, j, map));
                }
            }
        }
    }

    private void eraseHeart() {
        for (Cucumber cucumber : cucumbers)
            if (cucumber.isHitPlayer()) {
                player.setDangerTouch(true);
            }
    }

    public void update() {
        for (Cucumber cucumber : cucumbers) {
            cucumber.update(player.getHitBox());
        }
        eraseHeart();
    }

    public void render(Graphics g, Camera camera) {
        for (Cucumber cucumber : cucumbers) {
            cucumber.render(g, camera);
        }
    }

    public ArrayList<Cucumber> getCucumbers() {
        return cucumbers;
    }

    public void setCucumbers(ArrayList<Cucumber> cucumbers) {
        this.cucumbers = cucumbers;
    }
}
