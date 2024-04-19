package playing.entity.arrow;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import playing.camera.Camera;
import playing.entity.Player;
import playing.entity.enemy.Enemy;
import playing.entity.enemy.EnemyManager;
import playing.level.Tile;

public class ArrowManager {
    private List<Arrow> arrows;

    private Player player;
    private EnemyManager enemyManager;
    private int aniTick = 0;

    public ArrowManager(Player player, EnemyManager enemyManager) {
        this.player = player;
        this.enemyManager = enemyManager;
        arrows = new CopyOnWriteArrayList<>();
    }

    public void enterBossAttackRange() {
        aniTick++;
        if (aniTick >= 50) {
            Iterator<Enemy> itrBoss = enemyManager.getEnemies().iterator();
            while (itrBoss.hasNext()) {
                Enemy boss = (Enemy) itrBoss.next();
                if (Math.abs(boss.getPosition().getX() - player.getPosition().getX()) < 6 * Tile.TILE_SIZE
                        && Math.abs(boss.getPosition().getY() - player.getPosition().getY()) < 6 * Tile.TILE_SIZE) {
                        arrows.add(new Arrow(boss.getPosition(), player.getPosition()));
                }
            }
            aniTick = 0;
        }
    }

    public void update() {
        Iterator<Arrow> itrArrow = arrows.iterator();
        while (itrArrow.hasNext()) {
            Arrow arrow = (Arrow) itrArrow.next();
            arrow.update();
        }
        enterBossAttackRange();
    }

    public void render(Graphics g, Camera camera) {
        Iterator<Arrow> itrArrow = arrows.iterator();
        while (itrArrow.hasNext()) {
            Arrow arrow = (Arrow) itrArrow.next();
            arrow.render(g, camera);
        }
    }

    // getter and setter
    public List<Arrow> getArrows() {
        return arrows;
    }
}
