package com.soonfor.measuremanager.home.lofting.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.complexruler.fragment.detail.DesignPlanFragment;
import com.soonfor.measuremanager.home.lofting.fragment.detail.FuchiInfoFragment;
import com.soonfor.measuremanager.home.lofting.fragment.detail.LiangchiInfoFragment;
import com.soonfor.measuremanager.home.lofting.fragment.detail.LoftingInfoFragment;
import com.soonfor.measuremanager.home.lofting.fragment.detail.TaskInfoFragment;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.tools.Tokens;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by DingYg on 2018-01-29.
 * 放样任务信息
 */
public class LoftingDetailActivity extends BaseActivity{

    public static LoftingDetailActivity ldActivity;
    private String[] titles;
    private TaskInfoFragment bgFrament1;
    private LiangchiInfoFragment bgFrament2;
    private DesignPlanFragment bgFrament3;
    private FuchiInfoFragment bgFragment4;
    private LoftingInfoFragment bgFragment5;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void initPresenter() {
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_tablayout_slip;
    }

    @Override
    protected void initViews() {
        ((TextView)toolbar.findViewById(R.id.tvfTitile)).setText("放样任务详情");
        ldActivity = LoftingDetailActivity.this;
        int type = getIntent().getIntExtra(Tokens.Lofing.ITEMSKIPDETAIL_STATUS, 0);
        int position = getIntent().getIntExtra("POSITION",-1);
        LoftingMainBean task = getIntent().getParcelableExtra(Tokens.Lofing.ITEMSKIPDETAIL_ITEM);

        mFragments = new ArrayList<>();
        bgFrament1 = new TaskInfoFragment();
        bgFrament2 = new LiangchiInfoFragment();
        bgFrament3 = new DesignPlanFragment();
        bgFragment4 = new FuchiInfoFragment();
        bgFragment5 = new LoftingInfoFragment();
        Bundle b = new Bundle();
        b.putInt(Tokens.Lofing.ITEMSKIPDETAIL_STATUS,type);
        b.putInt("POSITION", position);
        b.putParcelable(Tokens.Lofing.ITEMSKIPDETAIL_ITEM, task);
        bgFrament1.setArguments(b);
        b = new Bundle();
        b.putString("orderNo",task.getOrderNo());
        bgFrament2.setArguments(b);
        b = new Bundle();
        b.putString("taskNo",task.getTaskNo());
        b.putString("orderNo",task.getOrderNo());
        bgFrament3.setArguments(b);
        b = new Bundle();
        b.putString("orderNo",task.getOrderNo());
        bgFragment4.setArguments(b);
        b = new Bundle();
        b.putParcelable(Tokens.Lofing.ITEMSKIPDETAIL_ITEM, task);
        bgFragment5.setArguments(b);

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);
        mFragments.add(bgFrament3);
        mFragments.add(bgFragment4);
        if(type == -1) {//抢单
            titles = new String[]{"任务信息", "量尺信息", "设计方案", "复尺信息"};
        }else {
            titles = new String[]{"任务信息", "量尺信息", "设计方案", "复尺信息", "放样完成"};
            mFragments.add(bgFragment5);
        }
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }

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

    public static void FinishLdActivity(){
        if(ldActivity!=null){
            ldActivity.finishRefresh();
            ldActivity.finish();
        }
    }
}
