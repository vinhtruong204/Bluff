package states;

public enum GameState {
    MENU,
    PLAYING,
    PAUSE;

    // Set default start game state
    public static GameState gameState = MENU;
}
