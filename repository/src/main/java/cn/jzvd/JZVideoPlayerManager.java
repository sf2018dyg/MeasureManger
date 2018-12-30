//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.jzvd;

public class JZVideoPlayerManager {
    public static JZVideoPlayer FIRST_FLOOR_JZVD;
    public static JZVideoPlayer SECOND_FLOOR_JZVD;

    public JZVideoPlayerManager() {
    }

    public static JZVideoPlayer getFirstFloor() {
        return FIRST_FLOOR_JZVD;
    }

    public static void setFirstFloor(JZVideoPlayer jzVideoPlayer) {
        FIRST_FLOOR_JZVD = jzVideoPlayer;
    }

    public static JZVideoPlayer getSecondFloor() {
        return SECOND_FLOOR_JZVD;
    }

    public static void setSecondFloor(JZVideoPlayer jzVideoPlayer) {
        SECOND_FLOOR_JZVD = jzVideoPlayer;
    }

    public static JZVideoPlayer getCurrentJzvd() {
        return getSecondFloor() != null ? getSecondFloor() : getFirstFloor();
    }

    public static void completeAll() {
        if (SECOND_FLOOR_JZVD != null) {
            SECOND_FLOOR_JZVD.onCompletion();
            SECOND_FLOOR_JZVD = null;
        }

        if (FIRST_FLOOR_JZVD != null) {
            FIRST_FLOOR_JZVD.onCompletion();
            FIRST_FLOOR_JZVD = null;
        }

    }
}
