package helpmethods;

public class PlayerAnimationType {
    public static final int IDLE = 0;
    public static final int RUN = 1;
    public static final int JUMP = 2;
    public static final int FALL = 3;
    public static final int DEAD_HIT = 4;
    public static final int DEAD_GROUND = 5;

    public static int getSpriteAmount(int aniType) {
        switch (aniType) {
            case IDLE:
                return 26;
            case RUN:
                return 14;
            case JUMP:
                return 4;
            case FALL:
                return 2;
            case DEAD_HIT:
                return 6;
            case DEAD_GROUND:
                return 4;
            default:
                return 0;
        }
    }
}

