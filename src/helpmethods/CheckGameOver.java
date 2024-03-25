package helpmethods;

import gamestate.GameState;

public class CheckGameOver {

    public static boolean checkGameOver(int heartNumber, int bombNumber, int bombNumberOfList, int numberOfEnemy) {
        if (heartNumber == 0 || (bombNumber == 0 && bombNumberOfList == 0 && numberOfEnemy > 1)) {
            GameState.gameState = GameState.MENU;
            return true;
        }
        return false;
    }
}
