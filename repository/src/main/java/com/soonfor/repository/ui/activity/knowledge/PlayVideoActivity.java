package com.soonfor.repository.ui.activity.knowledge;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.soonfor.repository.R;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 作者：DC-DingYG on 2018-09-19 14:15
 * 邮箱：dingyg012655@126.com
 */
public class PlayVideoActivity extends Activity {

    protected Toolbar toolbar;
    JZVideoPlayerStandard jzVideoPlayer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideo);
        findViewById();
        ((TextView)toolbar.findViewById(R.id.tvfTitile)).setText("播放视频");
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView imgfRight = toolbar.findViewById(R.id.imgfRight);
        imgfRight.setVisibility(View.GONE);
        String fameAtPath = getIntent().getStringExtra("FAMEATPATH");
        String videoPath = getIntent().getStringExtra("VIDEOPATH");
        jzVideoPlayer.setUp(
                videoPath, JZVideoPlayer.SCREEN_WINDOW_LIST,
                "");
        RequestOptions options = new RequestOptions()
                .fitCenter() //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .skipMemoryCache(true) //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //只缓存最终的图片
        Glide.with(PlayVideoActivity.this).load(fameAtPath).apply(options).into(jzVideoPlayer.thumbImageView);
    }
    private void findViewById() {
        toolbar = this.findViewById(R.id.toolbar);
        jzVideoPlayer = this.findViewById(R.id.videoplayer);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
