//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.jzvd;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.view.Surface;
import java.lang.reflect.Method;
import java.util.Map;

public class JZMediaSystem extends JZMediaInterface implements OnPreparedListener, OnCompletionListener, OnBufferingUpdateListener, OnSeekCompleteListener, OnErrorListener, OnInfoListener, OnVideoSizeChangedListener {
    public MediaPlayer mediaPlayer;

    public JZMediaSystem() {
    }

    public void start() {
        this.mediaPlayer.start();
    }

    public void prepare() {
        try {
            this.mediaPlayer = new MediaPlayer();
            this.mediaPlayer.setAudioStreamType(3);
            if (this.dataSourceObjects.length > 1) {
                this.mediaPlayer.setLooping((Boolean)this.dataSourceObjects[1]);
            }

            this.mediaPlayer.setOnPreparedListener(this);
            this.mediaPlayer.setOnCompletionListener(this);
            this.mediaPlayer.setOnBufferingUpdateListener(this);
            this.mediaPlayer.setScreenOnWhilePlaying(true);
            this.mediaPlayer.setOnSeekCompleteListener(this);
            this.mediaPlayer.setOnErrorListener(this);
            this.mediaPlayer.setOnInfoListener(this);
            this.mediaPlayer.setOnVideoSizeChangedListener(this);
            Class<MediaPlayer> clazz = MediaPlayer.class;
            Method method = clazz.getDeclaredMethod("setDataSource", String.class, Map.class);
            if (this.dataSourceObjects.length > 2) {
                method.invoke(this.mediaPlayer, this.currentDataSource.toString(), this.dataSourceObjects[2]);
            } else {
                method.invoke(this.mediaPlayer, this.currentDataSource.toString(), null);
            }

            this.mediaPlayer.prepareAsync();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void pause() {
        this.mediaPlayer.pause();
    }

    public boolean isPlaying() {
        return this.mediaPlayer.isPlaying();
    }

    public void seekTo(long time) {
        try {
            this.mediaPlayer.seekTo((int)time);
        } catch (IllegalStateException var4) {
            var4.printStackTrace();
        }

    }

    public void release() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
        }

    }

    public long getCurrentPosition() {
        return this.mediaPlayer != null ? (long)this.mediaPlayer.getCurrentPosition() : 0L;
    }

    public long getDuration() {
        return this.mediaPlayer != null ? (long)this.mediaPlayer.getDuration() : 0L;
    }

    public void setSurface(Surface surface) {
        this.mediaPlayer.setSurface(surface);
    }

    public void setVolume(float leftVolume, float rightVolume) {
        this.mediaPlayer.setVolume(leftVolume, rightVolume);
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        if (this.currentDataSource.toString().toLowerCase().contains("mp3")) {
            JZMediaManager.instance().mainThreadHandler.post(new Runnable() {
                public void run() {
                    if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                        JZVideoPlayerManager.getCurrentJzvd().onPrepared();
                    }

                }
            });
        }

    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        JZMediaManager.instance().mainThreadHandler.post(new Runnable() {
            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().onAutoCompletion();
                }

            }
        });
    }

    public void onBufferingUpdate(MediaPlayer mediaPlayer, final int percent) {
        JZMediaManager.instance().mainThreadHandler.post(new Runnable() {
            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().setBufferProgress(percent);
                }

            }
        });
    }

    public void onSeekComplete(MediaPlayer mediaPlayer) {
        JZMediaManager.instance().mainThreadHandler.post(new Runnable() {
            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().onSeekComplete();
                }

            }
        });
    }

    public boolean onError(MediaPlayer mediaPlayer, final int what, final int extra) {
        JZMediaManager.instance().mainThreadHandler.post(new Runnable() {
            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().onError(what, extra);
                }

            }
        });
        return true;
    }

    public boolean onInfo(MediaPlayer mediaPlayer, final int what, final int extra) {
        JZMediaManager.instance().mainThreadHandler.post(new Runnable() {
            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    if (what == 3) {
                        if (JZVideoPlayerManager.getCurrentJzvd().currentState == 1) {
                            JZVideoPlayerManager.getCurrentJzvd().onPrepared();
                        }
                    } else {
                        JZVideoPlayerManager.getCurrentJzvd().onInfo(what, extra);
                    }
                }

            }
        });
        return false;
    }

    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
        JZMediaManager.instance().currentVideoWidth = width;
        JZMediaManager.instance().currentVideoHeight = height;
        JZMediaManager.instance().mainThreadHandler.post(new Runnable() {
            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().onVideoSizeChanged();
                }

            }
        });
    }
}
