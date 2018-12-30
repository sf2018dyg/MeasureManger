package com.soonfor.measuremanager.me.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.me.fragment.SalesTargetFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 销售目标
 * Created by Administrator on 2018/1/10 0010.
 */

public class SalesTargetActivity extends BaseActivity {

    private String[] titles = new String[]{"月度目标", "季度目标","年度目标"};
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sales_target;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.size(); i++) {
            mFragments.add(SalesTargetFragment.newInstance(i));
        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {

    }

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    @Override
    protected void updateViews(boolean isRefresh) {

    }
}
