package com.soonfor.measuremanager.home.complexruler.activity;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.complexruler.fragment.BaseFuChiFragment;
import com.soonfor.measuremanager.home.complexruler.fragment.FuChiFragment1;
import com.soonfor.measuremanager.home.complexruler.fragment.FuChiFragment2;
import com.soonfor.measuremanager.home.complexruler.fragment.FuChiFragment3;
import com.soonfor.measuremanager.home.complexruler.fragment.FuChiFragment4;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.entity.TabEntity;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.listener.CustomTabEntity;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.listener.OnTabSelectListener;
import com.soonfor.measuremanager.view.NoPreloadViewPager;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:27
 * 邮箱：suibozhu@139.com
 */
public class FuChiMainActivity extends BaseActivity{

    private String[] titles = new String[]{"待接收", "待上门确认", "待复尺", "复尺完成"};
    private BaseFuChiFragment bgFrament1, bgFrament2, bgFrament3, bgFrament4;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    CommonTabLayout tablayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @BindView(R.id.viewPager)
    NoPreloadViewPager viewPager;
    public static int currPosition = 0;// 当前显示页面得位置

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fuchi;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);
        mFragments = new ArrayList<>();
        bgFrament1 = new FuChiFragment1();
        bgFrament2 = new FuChiFragment2();
        bgFrament3 = new FuChiFragment3();
        bgFrament4 = new FuChiFragment4();

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);
        mFragments.add(bgFrament3);
        mFragments.add(bgFrament4);

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], R.mipmap.icon_quanjing, R.mipmap.icon_quanjing));
        }
        currPosition = getIntent().getIntExtra("posi", 0);
        tablayout.setTabData(mTabEntities);
        tablayout.setCurrentTab(currPosition);
        tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.setCurrentItem(currPosition, false);
        viewPager.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tablayout.setCurrentTab(position);
                Refreshhandler refreshHandler;
                if (!DataTools.ListMapFuChi.containsKey(position + 1) ||
                        DataTools.ListMapFuChi.get(position + 1) == null || DataTools.ListMapFuChi.get(position + 1).size() == 0) {
                    refreshHandler = new Refreshhandler(FuChiMainActivity.this);
                    Message msg = new Message();
                    msg.arg1 = 0;
                    msg.arg2 = position;
                    refreshHandler.sendMessage(msg);
                } else {
                    refreshHandler = new Refreshhandler(FuChiMainActivity.this);
                    Message msg = new Message();
                    msg.arg1 = 1;
                    msg.arg2 = position;
                    refreshHandler.sendMessage(msg);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class Refreshhandler extends Handler {

        Activity mContext;

        public Refreshhandler(Activity mContext) {
            this.mContext = mContext;
        }

        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            try {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (msg.arg1) {
                            case 0:
                                ((BaseFuChiFragment) adapter.getItem(msg.arg2)).RefreshData(true);
                                break;
                            case 1:
                                ((BaseFuChiFragment) adapter.getItem(msg.arg2)).showDataToView(null);
                                break;
                        }
                    }
                });

            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DataTools.pageMap != null) {
            DataTools.pageMap.clear();
        }
        if (DataTools.ListMapFuChi != null) {
            DataTools.ListMapFuChi.clear();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
