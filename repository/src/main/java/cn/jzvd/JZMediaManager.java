//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.jzvd;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;

public class JZMediaManager implements SurfaceTextureListener {
    public static final String TAG = "JiaoZiVideoPlayer";
    public static final int HANDLER_PREPARE = 0;
    public static final int HANDLER_RELEASE = 2;
    public static JZResizeTextureView textureView;
    public static SurfaceTexture savedSurfaceTexture;
    public static Surface surface;
    public static JZMediaManager jzMediaManager;
    public int positionInList = -1;
    public JZMediaInterface jzMediaInterface;
    public int currentVideoWidth = 0;
    public int currentVideoHeight = 0;
    public HandlerThread mMediaHandlerThread = new HandlerThread("JiaoZiVideoPlayer");
    public JZMediaManager.MediaHandler mMediaHandler;
    public Handler mainThreadHandler;

    public JZMediaManager() {
        this.mMediaHandlerThread.start();
        this.mMediaHandler = new JZMediaManager.MediaHandler(this.mMediaHandlerThread.getLooper());
        this.mainThreadHandler = new Handler();
        if (this.jzMediaInterface == null) {
            this.jzMediaInterface = new JZMediaSystem();
        }

    }

    public static JZMediaManager instance() {
        if (jzMediaManager == null) {
            jzMediaManager = new JZMediaManager();
        }

        return jzMediaManager;
    }

    public static Object[] getDataSource() {
        return instance().jzMediaInterface.dataSourceObjects;
    }

    public static void setDataSource(Object[] dataSourceObjects) {
        instance().jzMediaInterface.dataSourceObjects = dataSourceObjects;
    }

    public static Object getCurrentDataSource() {
        return instance().jzMediaInterface.currentDataSource;
    }

    public static void setCurrentDataSource(Object currentDataSource) {
        instance().jzMediaInterface.currentDataSource = currentDataSource;
    }

    public static long getCurrentPosition() {
        return instance().jzMediaInterface.getCurrentPosition();
    }

    public static long getDuration() {
        return instance().jzMediaInterface.getDuration();
    }

    public static void seekTo(long time) {
        instance().jzMediaInterface.seekTo(time);
    }

    public static void pause() {
        instance().jzMediaInterface.pause();
    }

    public static void start() {
        instance().jzMediaInterface.start();
    }

    public static boolean isPlaying() {
        return instance().jzMediaInterface.isPlaying();
    }

    public void releaseMediaPlayer() {
        this.mMediaHandler.removeCallbacksAndMessages((Object)null);
        Message msg = new Message();
        msg.what = 2;
        this.mMediaHandler.sendMessage(msg);
    }

    public void prepare() {
        this.releaseMediaPlayer();
        Message msg = new Message();
        msg.what = 0;
        this.mMediaHandler.sendMessage(msg);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Log.i("JiaoZiVideoPlayer", "onSurfaceTextureAvailable [" + JZVideoPlayerManager.getCurrentJzvd().hashCode() + "] ");
        if (savedSurfaceTexture == null) {
            savedSurfaceTexture = surfaceTexture;
            this.prepare();
        } else {
            textureView.setSurfaceTexture(savedSurfaceTexture);
        }

    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return savedSurfaceTexture == null;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public class MediaHandler extends Handler {
        public MediaHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case 0:
                    JZMediaManager.this.currentVideoWidth = 0;
                    JZMediaManager.this.currentVideoHeight = 0;
                    JZMediaManager.this.jzMediaInterface.prepare();
                    if (JZMediaManager.savedSurfaceTexture != null) {
                        if (JZMediaManager.surface != null) {
                            JZMediaManager.surface.release();
                        }

                        JZMediaManager.surface = new Surface(JZMediaManager.savedSurfaceTexture);
                        JZMediaManager.this.jzMediaInterface.setSurface(JZMediaManager.surface);
                    }
                    break;
                case 2:
                    JZMediaManager.this.jzMediaInterface.release();
            }

        }
    }
}
