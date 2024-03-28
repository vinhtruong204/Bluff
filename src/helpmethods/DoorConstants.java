package helpmethods;

public class DoorConstants {
    //Width and height of door
    public static final int DOOR_WIDTH = 176;
    public static final int DOOR_HEIGHT = 160;

    //numberType and ToTalFrame 
    public static final int TOTAL_TYPE = 3;
    public static final int TOTAL_MAX_FRAME = 11;

    //status
    public static final int DEFAULT = 0;
    public static final int OPEN = 1;
    public static final int CLOSE = 2;



    //
    public static int getSpriteAmount(int aniType) {
        switch (aniType) {
            case DEFAULT:
                return 1;
            case OPEN:
                return 7;
            case CLOSE:
                return 8;
            default:
                return 0;
        }
    }
}
