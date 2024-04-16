package playing.entity.enemy;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import helpmethods.EnemyConstants;
import playing.camera.Camera;
import playing.entity.Player;
import playing.entity.enemy.boss.Boss;

public class EnemyManager {
    private List<Enemy> enemies;

    private int[][] map;
    private Player player;

    public EnemyManager(int[][] map, Player player) {
        enemies = new CopyOnWriteArrayList<Enemy>();
        this.map = map;
        this.player = player;

        addEnemies();
    }

    private void addEnemies() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case EnemyConstants.CUCUMBER:
                        enemies.add(new Cucumber(EnemyConstants.CUCUMBER, i, j, map));
                        break;
                    case EnemyConstants.CAPTAIN:
                        enemies.add(new Captain(EnemyConstants.CAPTAIN, i, j, map));
                        break;
                    case EnemyConstants.WHALE:
                        enemies.add(new Whale(EnemyConstants.WHALE, i, j, map));
                        break;
                    case EnemyConstants.BOLD_PIRATE:
                        enemies.add(new BoldPirate(EnemyConstants.BOLD_PIRATE, i, j, map));
                        break;
                    case EnemyConstants.BIG_GUY:
                        enemies.add(new BigGuy(EnemyConstants.BIG_GUY, i, j, map));
                        break;
                    case EnemyConstants.ARCHER_BLUE:
                        enemies.add(new Boss(EnemyConstants.ARCHER_BLUE, i, j, map));
                        break;
                    case EnemyConstants.ARCHER_PURPLE:
                        enemies.add(new Boss(EnemyConstants.ARCHER_PURPLE, i, j, map));
                        break;
                    case EnemyConstants.ARCHER_YELLOW:
                        enemies.add(new Boss(EnemyConstants.ARCHER_YELLOW, i, j, map));
                        break;
                    case EnemyConstants.ARCHER_RED:
                        enemies.add(new Boss(EnemyConstants.ARCHER_RED, i, j, map));
                        break;

                    default:
                        break;
                }
            }
        }
    }

    public void update() {
        Iterator<Enemy> itrEnemy = enemies.iterator();
        while (itrEnemy.hasNext()) {
            Enemy enemy = (Enemy) itrEnemy.next();
            enemy.update(player.getHitBox());
            if (enemy.isHitPlayer()) {
                player.setDangerTouch(true);
            }
        }
    }

    public void render(Graphics g, Camera camera) {
        Iterator<Enemy> itrEnemy = enemies.iterator();
        while (itrEnemy.hasNext()) {
            Enemy enemy = (Enemy) itrEnemy.next();
            enemy.render(g, camera);
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
