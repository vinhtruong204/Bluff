package playing.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import helpmethods.EnemyConstants;

public class EnemyManager {
    private ArrayList<Cucumber> cucumbers;
    private int[][] map;

    public EnemyManager(int[][]  map) {
        cucumbers = new ArrayList<>();
        this.map = map;
        addCucumber();
    }

    private void addCucumber() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 5) {
                    System.out.println(i + " " + j );
                    cucumbers.add(new Cucumber(EnemyConstants.CUCUMBER, i, j));
                }
            }
        }
        for (Cucumber cucumber : cucumbers) {
            System.out.println(cucumber.getPosition().getX() + "\t" + cucumber.getPosition().getY());
        }
    }

    public void update() {
        for (Cucumber cucumber : cucumbers) {
            cucumber.update();
        }
    }

    public void render(Graphics g) {
        for (Cucumber cucumber : cucumbers) {
            cucumber.render(g);
        }
    }
}
