package com.soonfor.measuremanager.me.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiImmediatelyActivity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.me.fragment.my_favorite.DesignCaseFragment;
import com.soonfor.measuremanager.me.fragment.my_favorite.DesignHotpostFragment;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.view.NoScrollViewPager;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyFavoriteActivity extends BaseActivity{
    private String[] titles = new String[]{"设计案例", "设计热帖"};
    private DesignCaseFragment bgFrament1;
    private DesignHotpostFragment bgFrament2;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    private int currPosition = 0;// 当前显示页面得位置
    @Override
    protected void initPresenter() {

    }
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_tablayout_fixed;
    }

    @Override
    protected void initViews() {
        ((TextView)toolbar.findViewById(R.id.tvfTitile)).setText("我的收藏");
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        bgFrament1 = new DesignCaseFragment();
        bgFrament2 = new DesignHotpostFragment();

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tablayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
        viewPager.setCurrentItem(0);
        viewPager.setScanScroll(false);
    }

    @Override
    protected void updateViews(boolean isRefresh) {}
}
