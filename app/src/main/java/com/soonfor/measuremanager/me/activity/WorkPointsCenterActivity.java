package com.soonfor.measuremanager.me.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.bean.WorkPointsBean;
import com.soonfor.measuremanager.me.fragment.ExchangeFragment;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.me.fragment.WorkPointsGetFragment;
import com.soonfor.measuremanager.me.presenter.workpoints.IWorkPointsView;
import com.soonfor.measuremanager.me.presenter.workpoints.WorkPointsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/9 0009.
 * 工分中心
 */

public class WorkPointsCenterActivity extends BaseActivity<WorkPointsPresenter> implements IWorkPointsView {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tv_work_points)
    TextView tvWorkPoints;
    @BindView(R.id.sign_in_amount_left)
    TextView signInAmountLeft;
    @BindView(R.id.sign_in_amount)
    TextView signInAmount;
    private String[] titles = new String[]{"兑换", "工分记录"};
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
        return R.layout.activity_work_points_center;
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new WorkPointsPresenter(this);
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        tvWorkPoints.setText(getIntent().getStringExtra("points"));
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        mFragments.add(ExchangeFragment.newInstance());
        mFragments.add(WorkPointsGetFragment.newInstance());
        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
    }
    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    @Override
    protected void updateViews(boolean isRefresh) {

    }
    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void setData(WorkPointsBean bean) {

    }
}
