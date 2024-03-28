package playing.entity.door;

import java.awt.Graphics;

import helpmethods.CheckCollision;
import playing.camera.Camera;
import playing.entity.Player;

public class DoorManager {
    private int[][] map;
    private Player player;

    private Door door;

    private boolean open;

    public DoorManager(int[][] map, Player player) {
        this.map = map;
        this.player = player;
        open = false;
        addDoor();
    }

    public void addDoor() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 3) {
                    door = new Door(i, j);
                }
            }
        }
    }

    public void checkCollisionDoorWithPlayer() {
        if (CheckCollision.isCollision(door.getHitBox(), player.getHitBox()) && open) {
            if (!door.isClose()) {
                door.setOpen(true);
                player.setDoorIn(true);
                player.setLocked(true);
            }

            if (door.isOpend() && player.isEnteredDoor()) {
                door.setClose(true);
            }

            if (door.isClosed()) {
                player.setDoorOut(true);
            }
        }
    }

    public void update() {
        door.update();
        checkCollisionDoorWithPlayer();
    }

    public void render(Graphics g, Camera camera) {
        door.render(g, camera);
    }

    // getter and setter

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Door getDoor() {
        return door;
    }

    public boolean isOpen() {
        return open;
    }

}
