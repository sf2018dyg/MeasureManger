package com.soonfor.measuremanager.home.lofting.activity;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.lofting.fragment.BaseLoftingMainFragment;
import com.soonfor.measuremanager.home.lofting.fragment.LoftingMainFragment1;
import com.soonfor.measuremanager.home.lofting.fragment.LoftingMainFragment2;
import com.soonfor.measuremanager.home.lofting.fragment.LoftingMainFragment3;
import com.soonfor.measuremanager.home.lofting.fragment.LoftingMainFragment4;
import com.soonfor.measuremanager.home.lofting.presenter.LoftingMainPresenter;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.Tokens;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by DingYg on 2018-01-29.
 * 我的放样客户
 */

public class LoftingMainActivity extends BaseActivity<LoftingMainPresenter> {

    private String[] titles = new String[]{"待接收", "待上门确认", "待放样", "放样完成"};
    private BaseLoftingMainFragment bgFrament1, bgFrament2, bgFrament3, bgFrament4;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    public static int fragmentPositon = 0;// 第一次进入的是哪个Fragment
    public static int ItemPosition = -1;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_tablayout_fixed;
    }

    @Override
    protected void initPresenter() {
        presenter = new LoftingMainPresenter(this);
    }

    @Override
    protected void initViews() {
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("我的放样客户");
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        bgFrament1 = new LoftingMainFragment1();
        bgFrament2 = new LoftingMainFragment2();
        bgFrament3 = new LoftingMainFragment3();
        bgFrament4 = new LoftingMainFragment4();

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);
        mFragments.add(bgFrament3);
        mFragments.add(bgFrament4);

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tablayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
        fragmentPositon = getIntent().getIntExtra(Tokens.Lofing.SKIPTOFRAGMENT_STATUS, 0);
        viewPager.setCurrentItem(fragmentPositon,false);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Refreshhandler refreshHandler;
                if (!DataTools.ListMap.containsKey(position + 1) ||
                        DataTools.ListMap.get(position + 1) == null || DataTools.ListMap.get(position + 1).size() == 0) {
                    refreshHandler = new Refreshhandler(LoftingMainActivity.this);
                    Message msg = new Message();
                    msg.arg1 = 0;
                    msg.arg2 = position;
                    refreshHandler.sendMessage(msg);
                } else {
                    refreshHandler = new Refreshhandler(LoftingMainActivity.this);
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

    @Override
    protected void updateViews(boolean isRefresh) {}
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
                                ((BaseLoftingMainFragment) adapter.getItem(msg.arg2)).RefreshData(true);
                                break;
                            case 1:
                                ((BaseLoftingMainFragment) adapter.getItem(msg.arg2)).showDataToView(null);
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
        if(DataTools.pageMap!=null){
            DataTools.pageMap.clear();
        }
        if(DataTools.ListMap!=null){
            DataTools.ListMap.clear();
        }
        if(DataTools.loftDetailInfos!=null){
            DataTools.loftDetailInfos.clear();
        }
    }

}
