package helpmethods;

import gamestate.GameState;

public class CheckGame {

    public static boolean checkGameOver(int heartNumber, int bombNumber, int bombNumberOfList, int numberOfEnemy) {
        if (heartNumber == 0 || (bombNumber == 0 && bombNumberOfList == 0 && numberOfEnemy > 1)) {
            GameState.gameState = GameState.MENU;
            return true;
        }
        return false;
    }

    public static boolean checkGameWin(int heartNumber, int numberOfEnemy){
        if(heartNumber != 0 && numberOfEnemy == 0){
            return true;
        }
        return false;
    }
}
