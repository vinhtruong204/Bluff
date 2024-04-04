package playing.entity.heart;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import helpmethods.CheckCollision;
import helpmethods.HeartConstants;
import playing.camera.Camera;
import playing.entity.Player;

public class HeartManager {

    private ArrayList<Heart> hearts;
    private ArrayList<Heart> heartPlayer;

    private int[][] map;

    private Player player;

    public HeartManager(int[][] map, Player player) {
        this.map = map;
        this.player = player;
        hearts = new ArrayList<Heart>();
        heartPlayer = new ArrayList<Heart>();

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
        Iterator<Heart> itr = hearts.iterator();

        while (itr.hasNext()) {
            Heart heart = (Heart) itr.next();
            if (CheckCollision.isCollision(heart.getHitBox(), player.getHitBox())) {
                itr.remove();
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
        for (Heart heart : hearts) {
            heart.update();
        }
        for (Heart heart : heartPlayer) {
            heart.update();
        }
        CheckCollisionPlayerWithHeart();
        removeHeartPlayer();
    }

    public void render(Graphics g, Camera camera) {
        for (Heart heart : hearts) {
            heart.render(g, camera);
        }
        for (Heart heart : heartPlayer) {
            heart.render(g);
        }
    }

    // Getter and Setter
    public ArrayList<Heart> getHeartPlayer() {
        return heartPlayer;
    }

    public void setHeartPlayer(ArrayList<Heart> heartPlayer) {
        this.heartPlayer = heartPlayer;
    }

    public ArrayList<Heart> getHearts() {
        return hearts;
    }

    public Player getPlayer() {
        return player;
    }

}
