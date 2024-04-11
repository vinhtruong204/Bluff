package helpmethods;

public class EnemyConstants {
    // All types of enemies and corresponding enemy value on the map
    public static final int CUCUMBER = 5;
    public static final int CAPTAIN = 6;
    public static final int WHALE = 7;
    public static final int BOLD_PIRATE = 8;
    public static final int BIG_GUY = 9;

    public class CucumberConstants {
        //
        public static final int CUCUMBER_WIDTH = 64;
        public static final int CUCUMBER_HEIGHT = 68;

        //
        public static final int TOTAL_TYPE = 10;
        public static final int TOTAL_FRAME = 36;

        // All types animation of the Cucumber enemy
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int DEAD_HIT = 6;
        public static final int DEAD_GROUND = 7;
        public static final int ATTACK = 8;
        public static final int BLOW_THE_WICK = 9;

        // Get total animations depend on type
        public static int getSpriteAmount(int aniType) {
            switch (aniType) {
                case IDLE:
                    return 36;
                case RUN:
                    return 12;
                case JUMP:
                    return 4;
                case FALL:
                    return 2;
                case GROUND:
                    return 3;
                case HIT:
                    return 8;
                case DEAD_HIT:
                    return 6;
                case DEAD_GROUND:
                    return 4;
                case ATTACK:
                    return 11;
                case BLOW_THE_WICK:
                    return 11;
                default:
                    return 0;
            }
        }
    }

    public class WhaleConstants {
        public static final int WHALE_WIDTH = 68;
        public static final int WHALE_HEIGHT = 46;

        //
        public static final int TOTAL_TYPE = 10;
        public static final int TOTAL_FRAME = 44;

        // All types animation of the Whale enemy
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int GROUND = 4;
        public static final int ATTACK = 5;
        public static final int SWALLOW_BOMB = 6;
        public static final int HIT = 7;
        public static final int DEAD_HIT = 8;
        public static final int DEAD_GROUND = 9;

        // Get total animation frames depend on type
        public static int getSpriteAmount(int aniType) {
            switch (aniType) {
                case IDLE:
                    return 44;
                case RUN:
                    return 14;
                case JUMP:
                    return 4;
                case FALL:
                    return 2;
                case GROUND:
                    return 3;
                case ATTACK:
                    return 11;
                case SWALLOW_BOMB:
                    return 10;
                case HIT:
                    return 7;
                case DEAD_HIT:
                    return 6;
                case DEAD_GROUND:
                    return 4;
                default:
                    return 0;
            }
        }
    }

    public class CaptainConstants {
        public static final int CAPTAIN_WIDTH = 80;
        public static final int CAPTAIN_HEIGHT = 72;

        //
        public static final int TOTAL_TYPE = 10;
        public static final int TOTAL_FRAME = 32;

        // All types animation of the Captain enemy
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int GROUND = 4;
        public static final int ATTACK = 5;
        public static final int SCARE_RUN = 6;
        public static final int HIT = 7;
        public static final int DEAD_HIT = 8;
        public static final int DEAD_GROUND = 9;

        // Get total animation frames depend on type
        public static int getSpriteAmount(int aniType) {
            switch (aniType) {
                case IDLE:
                    return 32;
                case RUN:
                    return 14;
                case JUMP:
                    return 4;
                case FALL:
                    return 2;
                case GROUND:
                    return 3;
                case ATTACK:
                    return 7;
                case SCARE_RUN:
                    return 12;
                case HIT:
                    return 8;
                case DEAD_HIT:
                    return 6;
                case DEAD_GROUND:
                    return 4;
                default:
                    return 0;
            }
        }
    }

    public class BoldPirateConstants {
        public static final int BOLD_PIRATE_WIDTH = 63;
        public static final int BOLD_PIRATE_HEIGHT = 67;

        //
        public static final int TOTAL_TYPE = 9;
        public static final int TOTAL_FRAME = 34;

        // All types animation of the Bold Pirate enemy
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int GROUND = 4;
        public static final int ATTACK = 5;
        public static final int HIT = 6;
        public static final int DEAD_HIT = 7;
        public static final int DEAD_GROUND = 8;

        // Get total animation frames depend on type
        public static int getSpriteAmount(int aniType) {
            switch (aniType) {
                case IDLE:
                    return 34;
                case RUN:
                    return 14;
                case JUMP:
                    return 4;
                case FALL:
                    return 2;
                case GROUND:
                    return 3;
                case ATTACK:
                    return 11;
                case HIT:
                    return 7;
                case DEAD_HIT:
                    return 6;
                case DEAD_GROUND:
                    return 4;
                default:
                    return 0;
            }
        }
    }

    public class BigGuyConstants {
        public static final int BIG_GUY_WIDTH = 77;
        public static final int BIG_GUY_HEIGHT = 74;

        //
        public static final int TOTAL_TYPE = 11;
        public static final int TOTAL_FRAME = 38;

        // All types animation of the Big Guy enemy
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int ATTACK = 2;
        public static final int RUN_BOMB = 3;
        public static final int THROW_BOMB = 4;
        public static final int JUMP = 5;
        public static final int FALL = 6;
        public static final int GROUND = 7;
        public static final int HIT = 8;
        public static final int DEAD_HIT = 9;
        public static final int DEAD_GROUND = 10;

        // Get total animation frames depend on type
        public static int getSpriteAmount(int aniType) {
            switch (aniType) {
                case IDLE:
                    return 38;
                case RUN:
                    return 16;
                case ATTACK:
                    return 11;
                case RUN_BOMB:
                    return 16;
                case THROW_BOMB:
                    return 13;
                case JUMP:
                    return 4;
                case FALL:
                    return 2;
                case GROUND:
                    return 3;
                case HIT:
                    return 8;
                case DEAD_HIT:
                    return 6;
                case DEAD_GROUND:
                    return 4;
                default:
                    return 0;
            }
        }
    }
}
