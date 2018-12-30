//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.jzvd;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.MeasureSpec;

public class JZResizeTextureView extends TextureView {
    protected static final String TAG = "JZResizeTextureView";
    public int currentVideoWidth = 0;
    public int currentVideoHeight = 0;

    public JZResizeTextureView(Context context) {
        super(context);
        this.currentVideoWidth = 0;
        this.currentVideoHeight = 0;
    }

    public JZResizeTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.currentVideoWidth = 0;
        this.currentVideoHeight = 0;
    }

    public void setVideoSize(int currentVideoWidth, int currentVideoHeight) {
        if (this.currentVideoWidth != currentVideoWidth || this.currentVideoHeight != currentVideoHeight) {
            this.currentVideoWidth = currentVideoWidth;
            this.currentVideoHeight = currentVideoHeight;
            this.requestLayout();
        }

    }

    public void setRotation(float rotation) {
        if (rotation != this.getRotation()) {
            super.setRotation(rotation);
            this.requestLayout();
        }

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("JZResizeTextureView", "onMeasure  [" + this.hashCode() + "] ");
        int viewRotation = (int)this.getRotation();
        int videoWidth = this.currentVideoWidth;
        int videoHeight = this.currentVideoHeight;
        int parentHeight = ((View)this.getParent()).getMeasuredHeight();
        int parentWidth = ((View)this.getParent()).getMeasuredWidth();
        int width;
        if (parentWidth != 0 && parentHeight != 0 && videoWidth != 0 && videoHeight != 0 && JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE == 1) {
            if (viewRotation == 90 || viewRotation == 270) {
                width = parentWidth;
                parentWidth = parentHeight;
                parentHeight = width;
            }

            videoHeight = videoWidth * parentHeight / parentWidth;
        }

        if (viewRotation == 90 || viewRotation == 270) {
            width = widthMeasureSpec;
            widthMeasureSpec = heightMeasureSpec;
            heightMeasureSpec = width;
        }

        width = getDefaultSize(videoWidth, widthMeasureSpec);
        int height = getDefaultSize(videoHeight, heightMeasureSpec);
        int widthSpecMode;
        if (videoWidth > 0 && videoHeight > 0) {
            widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
            Log.i("JZResizeTextureView", "widthMeasureSpec  [" + MeasureSpec.toString(widthMeasureSpec) + "]");
            Log.i("JZResizeTextureView", "heightMeasureSpec [" + MeasureSpec.toString(heightMeasureSpec) + "]");
            if (widthSpecMode == 1073741824 && heightSpecMode == 1073741824) {
                width = widthSpecSize;
                height = heightSpecSize;
                if (videoWidth * heightSpecSize < widthSpecSize * videoHeight) {
                    width = heightSpecSize * videoWidth / videoHeight;
                } else if (videoWidth * heightSpecSize > widthSpecSize * videoHeight) {
                    height = widthSpecSize * videoHeight / videoWidth;
                }
            } else if (widthSpecMode == 1073741824) {
                width = widthSpecSize;
                height = widthSpecSize * videoHeight / videoWidth;
                if (heightSpecMode == -2147483648 && height > heightSpecSize) {
                    height = heightSpecSize;
                    width = heightSpecSize * videoWidth / videoHeight;
                }
            } else if (heightSpecMode == 1073741824) {
                height = heightSpecSize;
                width = heightSpecSize * videoWidth / videoHeight;
                if (widthSpecMode == -2147483648 && width > widthSpecSize) {
                    width = widthSpecSize;
                    height = widthSpecSize * videoHeight / videoWidth;
                }
            } else {
                width = videoWidth;
                height = videoHeight;
                if (heightSpecMode == -2147483648 && videoHeight > heightSpecSize) {
                    height = heightSpecSize;
                    width = heightSpecSize * videoWidth / videoHeight;
                }

                if (widthSpecMode == -2147483648 && width > widthSpecSize) {
                    width = widthSpecSize;
                    height = widthSpecSize * videoHeight / videoWidth;
                }
            }
        }

        if (parentWidth != 0 && parentHeight != 0 && videoWidth != 0 && videoHeight != 0) {
            if (JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE == 3) {
                height = videoHeight;
                width = videoWidth;
            } else if (JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE == 2) {
                if (viewRotation == 90 || viewRotation == 270) {
                    widthSpecMode = parentWidth;
                    parentWidth = parentHeight;
                    parentHeight = widthSpecMode;
                }

                if ((double)videoHeight / (double)videoWidth > (double)parentHeight / (double)parentWidth) {
                    height = (int)((double)parentWidth / (double)width * (double)height);
                    width = parentWidth;
                } else if ((double)videoHeight / (double)videoWidth < (double)parentHeight / (double)parentWidth) {
                    width = (int)((double)parentHeight / (double)height * (double)width);
                    height = parentHeight;
                }
            }
        }

        this.setMeasuredDimension(width, height);
    }
}
