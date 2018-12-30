package com.soonfor.measuremanager.home.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.afflatus.fragment.AfflatusFragment;
import com.soonfor.measuremanager.billboard.fragment.WindCloudsBillboardFragment;
import com.soonfor.measuremanager.home.homepage.fragment.HomePageFragment;
import com.soonfor.measuremanager.me.fragment.MeFragment;
import com.soonfor.repository.tools.ActivityUtils;
import com.soonfor.updateutil.CustomDialog;
import com.soonfor.updateutil.IntranetUpdateManager;
import com.jiamm.bluetooth.MeasureDevice;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.mjsdk.MJReqBean;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.NoScrollViewPager;
import com.soonfor.measuremanager.view.tablayout.TabLayout;
import com.soonfor.measuremanager.view.tablayout.bean.TabItem;
//import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;

import butterknife.BindView;
import cn.jesse.nativelogger.NLogger;
import cn.jiamm.lib.MJSdk;

/**
 * Created by Administrator on 2018/1/30.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, TabLayout.OnTabClickListener {

    static MainActivity mainActivity;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.container)
    FrameLayout container;

    private Fragment fragment;
    private ArrayList<TabItem> tabs;
    private HomePageFragment homepageFragment;
    private AfflatusFragment afflatusFragment;
    private WindCloudsBillboardFragment billBoardFragment;
    private MeFragment meFragment;
    //内网更新
    public IntranetUpdateManager intUpdateManager;
    FragAdapter adapter;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(this);
        homepageFragment = new HomePageFragment();
        afflatusFragment = new AfflatusFragment();
        billBoardFragment = new WindCloudsBillboardFragment();
        meFragment = new MeFragment();
        tabs = presenter.getTabItems(homepageFragment, afflatusFragment, billBoardFragment,meFragment);
    }

    @Override
    protected void initViews() {
        mainActivity = MainActivity.this;
        //加入bugly
        //CrashReport.initCrashReport(getApplicationContext());
        initMjSdk();
        String url[] = new String[]{"http://www.soonfor.com/appdownload/measuremanger/version.txt", "http://www.soonfor.com/appdownload/measuremanger/"};
        //内网 WarehousetManager.apk
        intUpdateManager = new IntranetUpdateManager(this,
                url, "com.soonfor.measuremanager.myfileprovider");
        new Thread(checkSelfUpdateInt).start();
        initData();
    }
    private void initData() {
        mTabLayout.initData(tabs, this);
        adapter = new FragAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setScanScroll(false);
        mTabLayout.setCurrentTab(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position); }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        switch (mViewPager.getCurrentItem()){
            case 0:
                if(homepageFragment!=null)
                homepageFragment.setUserVisibleHint(true);
                break;
            case 1:
                if(afflatusFragment!=null)
                    afflatusFragment.setUserVisibleHint(true);
                break;
            case 2:
                if(billBoardFragment!=null)
                    billBoardFragment.setUserVisibleHint(true);
                break;
            case 3:
                if(meFragment!=null)
                    meFragment.setUserVisibleHint(true);
                break;
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData(false);
    }


    @Override
    public void onTabClick(TabItem tabItem) {
        int item = tabs.indexOf(tabItem);
        mViewPager.setCurrentItem(item,false);
    }

    public class FragAdapter extends FragmentPagerAdapter {

        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            try {
                fragment = tabs.get(position).tagFragmentClz;
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabs.size();
        }
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            MyToast.showToast(MainActivity.this, "再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {//可以关闭
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    System.exit(0);
                }
            }, 500);
        }
    }
    public static void FinishMainActivity(){
        if(mainActivity!=null){
            mainActivity.finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        //在这个Activity中调用过MJSdk.getInstance().InitEnv(this);就需要在退出的时候
        //调用设置下测距仪的相关的activity为空，否则会在测距仪自动关闭时发生崩溃
        MeasureDevice.getInstance().setActivity(null);
    }


    /**
     * 初始化mjsdk
     */
    private void initMjSdk(){

        /**
         * 修改人：DC-ZhuSuiBo on 2018/7/3 0003 10:47
         * 邮箱：suibozhu@139.com
         * 修改目的：美家说最好在测量界面每次打开都初始化一次,先放着，可以重复初始化
         */
        //第一步：初始化环境
        MJSdk.getInstance().InitEnv(this);

        //第二步：初始化配置
        MJReqBean.SdkAppConfig config = new MJReqBean.SdkAppConfig();
        config.packageName = getPackageName();
        config.apiUrl = "http://api.jiamm.cn/test";
        //String storagePath = Environment.getExternalStorageDirectory().getPath() + "/jmm/";
        String storagePath = getFilesDir().getPath() + "/test/";
        config.storagePath = storagePath;
        //先获取时间戳，再调用generateSign生成签名信息
        long tm = System.currentTimeMillis();
        config.timeStamp = String.valueOf(tm);
        config.generateSign();
        String sret = MJSdk.getInstance().Execute(config.getString());
        NLogger.w("demo", "config ret:" + sret);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntranetUpdateManager.INSTALL_PACKAGES_REQUESTCODE) {
            intUpdateManager.checkIsAndroid();
        }
    }
    Dialog dialog;
    //内网更新
    Runnable checkSelfUpdateInt = new Runnable() {
        @Override
        public void run() {
            // TODO 这里写上传逻辑
            try {
                int localVerCode = intUpdateManager.getLocalVerCode();
                int serverVerCode = intUpdateManager.getServerVerCode();
                // 如果服务器版本高于本地版本则提示更新
                if (serverVerCode > localVerCode) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (ActivityUtils.isRunning(MainActivity.this)) {
                                if (System.currentTimeMillis() - SoonforApplication.lastUpdateTime > 0) {
                                    SoonforApplication.lastUpdateTime = System.currentTimeMillis();
                                    dialog = CustomDialog.createUpdateDialog(MainActivity.this,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    dialog.setCancelable(true);
                                                    intUpdateManager.checkIsAndroid();
                                                }
                                            });
                                    dialog.show();
                                }
                            }
                        }
                    });
                }
            } catch (Exception e) {
            }
        }
    };
}
