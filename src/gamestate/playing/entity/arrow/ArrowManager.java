package gamestate.playing.entity.arrow;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import gamestate.playing.camera.Camera;
import gamestate.playing.entity.Player;
import gamestate.playing.entity.enemy.Enemy;
import gamestate.playing.entity.enemy.EnemyManager;
import gamestate.playing.entity.enemy.boss.Boss;
import gamestate.playing.level.Tile;
import helpmethods.CheckCollision;
import helpmethods.CheckDirectionShot;

public class ArrowManager {
    private List<Arrow> arrows;

    private Player player;
    private EnemyManager enemyManager;
    private int[][] map;
    private int aniTick;

    public ArrowManager(Player player, EnemyManager enemyManager, int[][] map) {
        this.player = player;
        this.enemyManager = enemyManager;
        this.map = map;
        this.aniTick = 0;
        arrows = new CopyOnWriteArrayList<Arrow>();
    }

    private void enterBossAttackRange() {
        aniTick++;
        if (aniTick >= 50) {
            Iterator<Enemy> itrBoss = enemyManager.getEnemies().iterator();
            while (itrBoss.hasNext()) {
                Boss boss = (Boss) itrBoss.next();
                if (Math.abs(boss.getPosition().getX() - player.getPosition().getX()) < 6 * Tile.TILE_SIZE
                        && Math.abs(boss.getPosition().getY() - player.getPosition().getY()) < 6 * Tile.TILE_SIZE) {
                    boss.setDirectionAttack(CheckDirectionShot.setDirection(boss.getPosition(), player.getPosition()));
                    if (boss.isAttacked()) {
                        arrows.add(new Arrow(boss.getPosition(), player.getPosition()));
                    }
                }
            }
            aniTick = 0;
        }
    }

    private void arrowCollidesWithTheplayer() {
        for (int i = 0; i < arrows.size(); i++) {
            if (CheckCollision.isCollision(player.getHitBox(), arrows.get(i).getHitBox())) {
                arrows.remove(i);
                player.setDangerTouch(true);
            }
        }
    }

    private void arrowCollidesWithTheMap() {
        for (int i = 0; i < arrows.size(); i++) {
            if (CheckCollision.isSolid(map, arrows.get(i).getPosition().getX(), arrows.get(i).getPosition().getY())) {
                arrows.remove(i);
            }
        }
    }

    public void update() {
        Iterator<Arrow> itrArrow = arrows.iterator();
        while (itrArrow.hasNext()) {
            Arrow arrow = (Arrow) itrArrow.next();
            arrow.update();
        }
        enterBossAttackRange();
        arrowCollidesWithTheplayer();
        arrowCollidesWithTheMap();
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
