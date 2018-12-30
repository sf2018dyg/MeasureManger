package com.soonfor.measuremanager.home.complexruler.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.complexruler.fragment.detail.ComplexRulerFragment;
import com.soonfor.measuremanager.home.complexruler.fragment.detail.DesignPlanFragment;
import com.soonfor.measuremanager.home.complexruler.fragment.detail.TaskInfoFragment;
import com.soonfor.measuremanager.home.liangchi.fragment.detail.MeasurementFragment;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 10:51
 * 邮箱：suibozhu@139.com
 */
public class FuChiDetailActivity extends BaseActivity {

    static FuChiDetailActivity fcActivity;
    private String[] titles;
    private TaskInfoFragment bgFrament1;
    private MeasurementFragment bgFrament2;
    private DesignPlanFragment bgFrament3;
    private ComplexRulerFragment bgFrament4;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;
    LiangChiBean lb;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private int currPosition = 0;// 当前显示页面得位置

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fuchi_detail;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);
        fcActivity = FuChiDetailActivity.this;

        lb = getIntent().getParcelableExtra("LiangChiBean");
        int type = lb.getStatus();
        int isGrabIn = getIntent().getIntExtra("isGrabIn", -1);
        int position = getIntent().getIntExtra("POSITION", -1);
        titles = new String[]{"任务信息", "量尺信息", "设计方案", "复尺信息"};
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();

        bgFrament1 = new TaskInfoFragment();
        Bundle b = new Bundle();
        b.putInt("type", type);
        b.putInt("isGrabIn", isGrabIn);
        b.putParcelable("LiangChiBean", lb);
        b.putInt("POSITION", position);
        bgFrament1.setArguments(b);

        b = new Bundle();
        b.putString("taskNo", lb.getTaskNo());
        b.putString("orderNo", lb.getOrderNo());
        b.putInt("task_type",1);//任务类型 0量尺 1复尺
        b.putInt("LiangChiStatus", getIntent().getExtras().getInt("LiangChiStatus", 1));
        bgFrament2 = new MeasurementFragment();
        bgFrament2.setArguments(b);

        b = new Bundle();
        b.putString("taskNo", lb.getTaskNo());
        b.putString("orderNo", lb.getOrderNo());
        bgFrament3 = new DesignPlanFragment();
        bgFrament3.setArguments(b);

        b = new Bundle();
        b.putString("taskNo", lb.getTaskNo());
        b.putString("orderNo", lb.getOrderNo());
        b.putInt("LiangChiStatus", getIntent().getExtras().getInt("LiangChiStatus", 1));
        bgFrament4 = new ComplexRulerFragment();
        bgFrament4.setArguments(b);

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);
        mFragments.add(bgFrament3);
        mFragments.add(bgFrament4);

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tablayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
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

    public static void FinishFcDetialActivity() {
        if (fcActivity != null) {
            fcActivity.finishRefresh();
            fcActivity.finish();
        }
    }
}
