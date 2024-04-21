package gamestate.playing.entity.door;

import java.awt.Graphics;

import gamestate.playing.camera.Camera;
import gamestate.playing.entity.Player;
import helpmethods.CheckCollision;

public class DoorManager {
    // Map
    private int[][] map;
    // Player
    private Player player;

    // Door
    private Door door;

    // check open with Door
    private boolean open;

    // Contructor
    public DoorManager(int[][] map, Player player) {
        this.map = map;
        this.player = player;
        open = false;
        addDoor();
    }

    // add door to map
    public void addDoor() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 3) {
                    door = (new Door(i, j));
                }
            }
        }
    }

    // check collision Door with Player
    public void checkCollisionDoorWithPlayer() {
        // if it collides with the door and is qualified to open the door
        if (CheckCollision.isCollision(door.getHitBox(), player.getHitBox()) && open) {
            // if the door is closed
            if (!door.isClose()) {
                // open door
                door.setOpen(true);
                // if the door is opend
                if (door.isOpend()) {
                    // set animation DoorIn
                    player.setDoorIn(true);
                }
                // locked Player
                player.setLocked(true);
            }

            // if the door is opend and player is EnteredDoor
            if (door.isOpend() && player.isEnteredDoor()) {
                // set the door is close
                door.setClose(true);
            }
        }
    }

    // Update
    public void update() {
        door.update();
        checkCollisionDoorWithPlayer();
    }

    // Render
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
