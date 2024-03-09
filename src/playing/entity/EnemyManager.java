package playing.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import helpmethods.EnemyConstants;

public class EnemyManager {
    ArrayList<Cucumber> cucumbers;

    public EnemyManager() {
        cucumbers = new ArrayList<>();
        addCucumber();
    }

    private void addCucumber() {
        Cucumber cb = new Cucumber(EnemyConstants.CUCUMBER);
        cucumbers.add(cb);
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
