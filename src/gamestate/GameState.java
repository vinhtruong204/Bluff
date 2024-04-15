package gamestate;

public enum GameState {
    MENU,
    PLAYING,
    PAUSE,
    GAMEOVER;

    // Set default start game state
    public static GameState gameState = MENU;
}
