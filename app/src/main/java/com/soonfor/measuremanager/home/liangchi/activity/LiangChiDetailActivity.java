package com.soonfor.measuremanager.home.liangchi.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.liangchi.fragment.detail.IntentionalFragment;
import com.soonfor.measuremanager.home.liangchi.fragment.detail.MeasurementFragment;
import com.soonfor.measuremanager.home.liangchi.fragment.detail.PortraitFragment;
import com.soonfor.measuremanager.home.liangchi.fragment.detail.TaskInfoFragment;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 10:51
 * 邮箱：suibozhu@139.com
 */
public class LiangChiDetailActivity extends BaseActivity {

    public static LiangChiDetailActivity lcActivity;
    private String[] titles;
    private TaskInfoFragment bgFrament1;
    private IntentionalFragment bgFrament2;
    private PortraitFragment bgFrament3;
    private MeasurementFragment bgFrament4;
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
    protected void initPresenter() {}

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_liangchi_detail;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);
        lcActivity = LiangChiDetailActivity.this;
        lb = getIntent().getParcelableExtra("LiangChiBean");
        int itemPositon = getIntent().getExtras().getInt("LC_POSITION", -1);
        int type = lb.getStatus();//任务状态
        int isGrabIn = getIntent().getIntExtra("isGrabIn", -1);
        if (type == 2) {
            titles = new String[]{"任务信息", "意向产品", "客户画像", "测量信息"};
        } else {
            titles = new String[]{"任务信息", "意向产品", "测量信息"};
        }
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        //任务信息
        bgFrament1 = new TaskInfoFragment();
        Bundle b = new Bundle();
        b.putInt("type", type);//任务状态
        b.putInt("isGrabIn", isGrabIn);
        b.putParcelable("LiangChiBean", lb);
        b.putInt("LC_POSITION", itemPositon);
        //b.putParcelable("LiangChiBean",getIntent().getParcelableExtra("LiangChiBean"));
        bgFrament1.setArguments(b);

        //意向产品
        bgFrament2 = new IntentionalFragment();
        b = new Bundle();
        b.putString("orderNo", lb.getOrderNo());
        bgFrament2.setArguments(b);

        //用户画像
        bgFrament3 = new PortraitFragment();
        b = new Bundle();
        b.putString("customerId", lb.getCustomerId() + "");
        bgFrament3.setArguments(b);

        //测量信息
        bgFrament4 = new MeasurementFragment();
        b = new Bundle();
        b.putString("taskNo", lb.getTaskNo());
        b.putString("orderNo", lb.getOrderNo());
        b.putInt("task_type", 0);//任务类型 0量尺 1复尺
        b.putInt("LiangChiStatus", getIntent().getExtras().getInt("LiangChiStatus", 1));
        bgFrament4.setArguments(b);

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);
        if (type == 2) {
            mFragments.add(bgFrament3);
        }
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

    public static void FinishLcDetialActivity() {
        if (lcActivity != null) {
            lcActivity.finishRefresh();
            lcActivity.finish();
        }
    }
}
