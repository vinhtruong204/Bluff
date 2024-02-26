import display.GamePanel;

public class Launcher {
    public static void main(String[] args) {
        // Start new thread of the game
        new Thread(new GamePanel()).start();
    }
}
