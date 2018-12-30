package com.soonfor.measuremanager;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.ninegrid.NineGridView;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.logging.SimpleFormatter;

import cn.jesse.nativelogger.NLogger;
import cn.jesse.nativelogger.logger.LoggerLevel;
import cn.jesse.nativelogger.util.CrashWatcher;
import cn.jiguang.share.android.api.JShareInterface;

/**
 * 修改人：DC-ZhuSuiBo on 2018/2/28 14:35
 * 邮箱：suibozhu@139.com
 */
public class SoonforApplication extends android.support.multidex.MultiDexApplication {

    public static long lastUpdateTime;//上一次次更新dialog弹出时间
    public static Context AppContext;
    public static String ServerAdr = "ServerAdr";//接口服务器
    public static String MerchantCode = "MerchantCode";//商家服务号
    public static String ServerAdr_fj = "ServerAdr_fj";//附件服务器
    public static String ServerAdr_Upload = "ServerAdr_Upload";//上传下载服务器
    public static Bitmap bitmap;
    public static String isNewsHouseMap = "ISNEWSHOUSEMAP";//用来把合同号是否新建的状态存本地，map集合方式
    public static int pathIndex_liangchi = -1;//预览多张图片的位置
    public static int pathIndex_fuchi = -1;//预览多张图片的位置

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.bg_normal_color, R.color.text);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();

        //ARouter 路由框架sdk初始化
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        NineGridView.setImageLoader(new GLideImageLoader());
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        //初始化Hawk
        Hawk.init(getApplicationContext()).build();
        //日志
        String loggerpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MeasureManger/logs";
        File dir = new File(loggerpath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        NLogger.getInstance()
                .builder()
                .tag("MeasureManger")
                .loggerLevel(LoggerLevel.DEBUG)
                .fileLogger(true)
                .fileDirectory(loggerpath)
                .fileFormatter(new SimpleFormatter())
                .expiredPeriod(3)
                .catchException(true, new CrashWatcher.UncaughtExceptionListener() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable ex) {
                        NLogger.e("uncaughtException", ex);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .build();

        //
        JShareInterface.setDebugMode(true);
        JShareInterface.init(this);

/*        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        NLogger.i("CrashHandler.init ok");*/

    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }
    /**
     * GLide 加载
     */
    private class GLideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            if (url != null) {
                Picasso.with(context).load(url)
                        .placeholder(R.mipmap.zanuw)
                        .error(R.mipmap.zanuw)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.mipmap.zanuw);
            }
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

}
