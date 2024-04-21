package gamestate.playing.entity.heart;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import gamestate.playing.camera.Camera;
import gamestate.playing.entity.Player;
import helpmethods.CheckCollision;
import helpmethods.HeartConstants;

public class HeartManager {

    private List<Heart> hearts;
    private List<Heart> heartPlayer;

    private int[][] map;

    private Player player;

    public HeartManager(int[][] map, Player player) {
        this.map = map;
        this.player = player;
        hearts = new CopyOnWriteArrayList<Heart>();
        heartPlayer = new CopyOnWriteArrayList<Heart>();

        // Add hearts into map
        addHeart();

        setHeartPlayer();
    }

    private void addHeart() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 4) {
                    hearts.add(new Heart(HeartConstants.RED_HEART, i, j));
                }
            }
        }
    }

    private void setHeartPlayer() {
        for (int i = 0; i < Player.MAX_HEART; i++) {
            heartPlayer.add(new Heart(HeartConstants.RED_HEART, 0, (1 * i)));
        }
    }

    private void CheckCollisionPlayerWithHeart() {
        for (int i = 0; i < hearts.size(); i++) {
            if (CheckCollision.isCollision(hearts.get(i).getHitBox(), player.getHitBox())) {
                hearts.remove(i);
                if (player.getHeartPlayer() < Player.MAX_HEART) {
                    player.setHeartPlayer(player.getHeartPlayer() + 1);
                }
            }
        }
    }

    private void removeHeartPlayer() {
        if (player.getHeartPlayer() < heartPlayer.size()) {
            heartPlayer.remove(heartPlayer.size() - 1);
        } else if (player.getHeartPlayer() > heartPlayer.size()) {
            heartPlayer.add(new Heart(HeartConstants.RED_HEART, 0, 1 * (heartPlayer.size())));
        }
    }

    public void update() {
        Iterator<Heart> itrHeart = hearts.iterator();
        while (itrHeart.hasNext()) {
            Heart heart = (Heart) itrHeart.next();
            heart.update();
        }

        Iterator<Heart> itrHeartPlayer = heartPlayer.iterator();
        while (itrHeartPlayer.hasNext()) {
            Heart heartPlayer = (Heart) itrHeartPlayer.next();
            heartPlayer.update();
        }

        CheckCollisionPlayerWithHeart();
        removeHeartPlayer();
    }

    public void render(Graphics g, Camera camera) {
        Iterator<Heart> itrHeart = hearts.iterator();
        while (itrHeart.hasNext()) {
            Heart heart = (Heart) itrHeart.next();
            heart.render(g, camera);
        }
        Iterator<Heart> itrHeartPlayer = heartPlayer.iterator();
        while (itrHeartPlayer.hasNext()) {
            Heart heartPlayer = (Heart) itrHeartPlayer.next();
            heartPlayer.render(g);
        }
    }

    // Getter and Setter
    public List<Heart> getHeartPlayer() {
        return heartPlayer;
    }

    public void setHeartPlayer(List<Heart> heartPlayer) {
        this.heartPlayer = heartPlayer;
    }

    public List<Heart> getHearts() {
        return hearts;
    }

    public Player getPlayer() {
        return player;
    }

}
