package com.soonfor.measuremanager.home.othertask.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.othertask.fragment.detail.DisposeDetailFragment;
import com.soonfor.measuremanager.home.othertask.fragment.detail.OtherTaskInfoFragment;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.NoScrollViewPager;
import com.soonfor.measuremanager.view.tablayout.TopTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by DingYg on 2018-01-29.
 * 其它任务信息
 */
public class OtherTaskDetailActivity extends BaseActivity {

    public static OtherTaskDetailActivity otActivity;
    private String[] titles = new String[]{"任务信息", "处理详情"};
    private OtherTaskInfoFragment bgFrament1;
    private DisposeDetailFragment bgFrament2;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @BindView(R.id.topTabLayout)
    TopTabLayout tablayout;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;

    @Override
    protected void initPresenter() {
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_tablayout_two;
    }

    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        OtherTaskMainBean otmBean = (OtherTaskMainBean) bundle.getSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM);
        if(otmBean!=null) {
            if (otmBean.getTaskType().equals("4")){//主动追踪任务
                ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("主动追踪任务详情");
            }else if (otmBean.getTaskType().equals("5")) {//人工回访任务
                ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("人工回访任务详情");
            }
            otActivity = OtherTaskDetailActivity.this;
            mTitles = new ArrayList<>();
            for (int i = 0; i < titles.length; i++) {
                mTitles.add(titles[i]);
            }
            int type = bundle.getInt(Tokens.OtherTask.OT_ITEMSKIPDETAIL_STATUS, -1);
            int position = bundle.getInt("POSITION", -1);
            int tabIndex = bundle.getInt("TABINDEX", 0);
            mFragments = new ArrayList<>();
            bgFrament1 = new OtherTaskInfoFragment();
            bgFrament2 = new DisposeDetailFragment();
            Bundle b = new Bundle();
            b.putInt(Tokens.OtherTask.OT_ITEMSKIPDETAIL_STATUS, type);
            b.putInt("POSITION", position);
            b.putSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM, otmBean);
            bgFrament1.setArguments(b);
            b = new Bundle();
            b.putInt(Tokens.OtherTask.OT_ITEMSKIPDETAIL_STATUS, type);
            b.putSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM, otmBean);
            bgFrament2.setArguments(b);

            mFragments.add(bgFrament1);
            mFragments.add(bgFrament2);
            adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
            tablayout.initTopTabLayout(this, mTitles, viewPager, adapter, false);
            viewPager.setCurrentItem(tabIndex);
        }
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
        if(otActivity!=null){
            otActivity.finishRefresh();
            otActivity.finish();
        }
    }
}
