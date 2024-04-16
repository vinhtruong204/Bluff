package gamestate;

public enum GameState {
    MENU,
    PLAYING,
    PAUSE,
    GAMEOVER,
    WIN;

    // Set default start game state
    public static GameState gameState = MENU;
}
