//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.jzvd;

import android.view.Surface;

public abstract class JZMediaInterface {
    public Object currentDataSource;
    public Object[] dataSourceObjects;

    public JZMediaInterface() {
    }

    public abstract void start();

    public abstract void prepare();

    public abstract void pause();

    public abstract boolean isPlaying();

    public abstract void seekTo(long var1);

    public abstract void release();

    public abstract long getCurrentPosition();

    public abstract long getDuration();

    public abstract void setSurface(Surface var1);

    public abstract void setVolume(float var1, float var2);
}
