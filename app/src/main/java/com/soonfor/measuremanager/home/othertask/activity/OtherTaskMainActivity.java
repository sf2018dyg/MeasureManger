package com.soonfor.measuremanager.home.othertask.activity;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.othertask.fragment.BaseOtherTaskMainFragment;
import com.soonfor.measuremanager.home.othertask.fragment.OtherTaskMainFragment1;
import com.soonfor.measuremanager.home.othertask.fragment.OtherTaskMainFragment2;
import com.soonfor.measuremanager.home.othertask.fragment.OtherTaskMainFragment3;
import com.soonfor.measuremanager.home.othertask.presenter.OtherTaskMainPresenter;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.Tokens;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by DingYg on 2018-01-29.
 * 其它任务
 */

public class OtherTaskMainActivity extends BaseActivity<OtherTaskMainPresenter> {

    private String[] titles = new String[]{"进行中", "已逾期", "已完成"};
    private BaseOtherTaskMainFragment bgFrament1, bgFrament2, bgFrament3;
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
        presenter = new OtherTaskMainPresenter(this);
    }

    @Override
    protected void initViews() {
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("其它任务");
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        bgFrament1 = new OtherTaskMainFragment1();
        bgFrament2 = new OtherTaskMainFragment2();
        bgFrament3 = new OtherTaskMainFragment3();

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);
        mFragments.add(bgFrament3);

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tablayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
        fragmentPositon = getIntent().getIntExtra(Tokens.OtherTask.SKIPTOFRAGMENT_STATUS, 0);
        viewPager.setCurrentItem(fragmentPositon,false);
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void updateViews(boolean isRefresh) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
