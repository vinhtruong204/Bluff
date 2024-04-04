package playing.entity.enemy;

import java.awt.Graphics;
import java.util.ArrayList;

import helpmethods.EnemyConstants;
import playing.camera.Camera;
import playing.entity.Player;

public class EnemyManager {
    private ArrayList<Enemy> enemies;

    private int[][] map;
    private Player player;

    public EnemyManager(int[][] map, Player player) {
        enemies = new ArrayList<Enemy>();
        this.map = map;
        this.player = player;
        addCucumber();
    }

    private void addCucumber() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 5) {
                    enemies.add(new Cucumber(EnemyConstants.CUCUMBER, i, j, map));
                }

                if(map[i][j] == 6){
                    enemies.add(new Captain(EnemyConstants.CAPTAIN, i, j, map));
                }

                if(map[i][j] == 7){
                    enemies.add(new Whale(EnemyConstants.WHALE, i, j, map));
                }

                if(map[i][j] == 8){
                    enemies.add(new BoldPirate(EnemyConstants.BOLD_PIRATE, i, j, map));
                }

                if(map[i][j] == 9)
                {
                    enemies.add(new BigGuy(EnemyConstants.BIG_GUY, i, j, map));
                }
            }
        }
    }

    public void update() {
        for (Enemy cucumber : enemies) {
            cucumber.update(player.getHitBox());
            if (cucumber.isHitPlayer()) {
                player.setDangerTouch(true);
            }
        }
    }

    public void render(Graphics g, Camera camera) {
        for (Enemy cucumber : enemies) {
            cucumber.render(g, camera);
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
