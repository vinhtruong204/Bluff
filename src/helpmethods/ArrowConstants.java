package helpmethods;

public class ArrowConstants {
    public static final int ARROW_SHOT = 0;
    public static final int ARROW_HIT = 1;

    public static final int TOTAL_TYPE = 2;
    public static final int TOTAL_FRAME = 1;

    public static int getSpriteAmount(int aniType) {
        switch (aniType) {
            case ARROW_SHOT:
                return 1;
            case ARROW_HIT:
                return 1;
            default:
                return 0;
        }
    }
}
