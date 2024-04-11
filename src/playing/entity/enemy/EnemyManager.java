package playing.entity.enemy;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import helpmethods.EnemyConstants;
import playing.camera.Camera;
import playing.entity.Player;

public class EnemyManager {
    private List<Enemy> enemies;

    private int[][] map;
    private Player player;

    public EnemyManager(int[][] map, Player player) {
        enemies = new CopyOnWriteArrayList<Enemy>();
        this.map = map;
        this.player = player;
        addEnemy();
    }

    private void addEnemy() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 5) {
                    enemies.add(new Cucumber(EnemyConstants.CUCUMBER, i, j, map));
                }

                if (map[i][j] == 6) {
                    enemies.add(new Captain(EnemyConstants.CAPTAIN, i, j, map));
                }

                if (map[i][j] == 7) {
                    enemies.add(new Whale(EnemyConstants.WHALE, i, j, map));
                }

                if (map[i][j] == 8) {
                    enemies.add(new BoldPirate(EnemyConstants.BOLD_PIRATE, i, j, map));
                }

                if (map[i][j] == 9) {
                    enemies.add(new BigGuy(EnemyConstants.BIG_GUY, i, j, map));
                }
            }
        }
    }

    public void update() {
        Iterator<Enemy> itrEnemy = enemies.iterator();
        while (itrEnemy.hasNext()) {
            Enemy enemy = (Enemy)itrEnemy.next();
            enemy.update(player.getHitBox());
            if (enemy.isHitPlayer()) {
                player.setDangerTouch(true);
            }
        }
    }

    public void render(Graphics g, Camera camera) {
        Iterator<Enemy> itrEnemy = enemies.iterator();
        while (itrEnemy.hasNext()) {
            Enemy enemy = (Enemy)itrEnemy.next();
            enemy.render(g, camera);
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
