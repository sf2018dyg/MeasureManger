package com.soonfor.measuremanager.home.design.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.design.fragment.detail.DesignPlanFragment;
import com.soonfor.measuremanager.home.design.fragment.detail.CustomerInfoFragment;
import com.soonfor.measuremanager.home.design.fragment.detail.TaskInfoFragment;
import com.soonfor.measuremanager.home.design.model.DesignItemBean;
import com.soonfor.measuremanager.home.grab.model.bean.GrabOrderBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.home.lofting.fragment.detail.FuchiInfoFragment;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.tools.Tokens;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-01-29.
 * 放样任务信息
 */
public class DesignDetailActivity extends BaseActivity {

    public static DesignDetailActivity ddActivity;
    private String[] titles = new String[]{"任务信息", "设计方案", "测量信息", "客户信息"};
    private TaskInfoFragment bgFrament1;
    private DesignPlanFragment bgFrament2;
    private FuchiInfoFragment bgFrament3;
    private CustomerInfoFragment bgFragment4;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    //临时保存任务信息
    public static beanTotal sTaskInfo = null;

    @Override
    protected void initPresenter() {
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_tablayout_fixed;
    }

    @Override
    protected void initViews() {
        ((TextView)toolbar.findViewById(R.id.tvfTitile)).setText("设计任务详情");
        ddActivity = DesignDetailActivity.this;
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        int type = getIntent().getIntExtra(Tokens.Lofing.ITEMSKIPDETAIL_STATUS, 0);
        int position = getIntent().getIntExtra("POSITION",-1);
        DesignItemBean task = getIntent().getParcelableExtra(Tokens.Design.ITEMSKIPDETAIL_ITEM);

        mFragments = new ArrayList<>();
        bgFrament1 = new TaskInfoFragment();
        bgFrament2 = new DesignPlanFragment();
        bgFrament3 = new FuchiInfoFragment();
        bgFragment4 = new CustomerInfoFragment();
        Bundle b = new Bundle();
        b.putInt(Tokens.Lofing.ITEMSKIPDETAIL_STATUS,type);
        b.putInt("POSITION", position);
        b.putParcelable(Tokens.Design.ITEMSKIPDETAIL_ITEM, task);
        bgFrament1.setArguments(b);
        b = new Bundle();
        b.putInt(Tokens.Lofing.ITEMSKIPDETAIL_STATUS,type);
        b.putString("taskNo",task.getTaskNo());
        b.putString("orderNo",task.getOrderNo());
        bgFrament2.setArguments(b);
        b = new Bundle();
        b.putString("orderNo",task.getOrderNo());
        bgFrament3.setArguments(b);
        b = new Bundle();
        b.putParcelable(Tokens.Design.ITEMSKIPDETAIL_ITEM, task);
        bgFragment4.setArguments(b);

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);
        mFragments.add(bgFrament3);
        mFragments.add(bgFragment4);

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tablayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public static void FinishddActivity(){
        if(ddActivity!=null){
            ddActivity.finishRefresh();
            ddActivity.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sTaskInfo = null;
    }
}
