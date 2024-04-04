package helpmethods;

public class HeartConstants {
    public static final int STAND_STILL = 0;
    public static final int COLLECTED = 1;

    // Total frame and type
    public static final int TOTAL_TYPE = 2;
    public static final int TOTAL_FRAME = 22;

    public static int getSpriteAmount(int aniType) {
        switch (aniType) {
            case STAND_STILL:
                return 22;
            case COLLECTED:
                return 4;
            default:
                return 0;
        }
    }
}
