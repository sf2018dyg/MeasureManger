package com.soonfor.repository.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.ninegrid.NineGridView;
import com.soonfor.repository.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：DC-DingYG on 2018-06-23 9:04
 * 邮箱：dingyg012655@126.com
 */
public class RepApp {

    public static Context AppContext;
    public static DisplayMetrics dm;
    public static String DOWNLOAD_FILE = "";

    public static Map<String, String> videopaths = new HashMap<>();//下载到本地的视频集合

    /**
     * Picasso 加载
     */
    public static class GlideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Picasso.with(context).load(url).config(Bitmap.Config.ARGB_4444).placeholder(R.mipmap.zanuw).error(R.mipmap.zanuw).into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

}