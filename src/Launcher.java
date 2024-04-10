import java.io.IOException;

import game.GamePanel;

public class Launcher {
    public static void main(String[] args) throws IOException {
        // Start new thread of the game
        new Thread(new GamePanel()).start();
    }
}
