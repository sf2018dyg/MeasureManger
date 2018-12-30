package com.soonfor.evaluate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.evaluate.activity.fragment.Evaluated_IToCustomersFragment;
import com.soonfor.evaluate.activity.fragment.UnEvaluate_IToCustomersFragment;
import com.soonfor.evaluate.presenter.Evaluate_IToCustomersPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * 作者：DC-DingYG on 2018-10-19 9:20
 * 邮箱：dingyg012655@126.com
 * 我对客户的评价
 */
public class Evaluate_IToCustomersActivity extends BaseActivity<Evaluate_IToCustomersPresenter>{

    public static final int REQUEST_CODE_UEITC = 1012;
    private String[] titles = new String[]{"待评价", "已评价"};
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    UnEvaluate_IToCustomersFragment bgFrament1;
    Evaluated_IToCustomersFragment bgFrament2;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_evaluate_i_to_clients;
    }

    @Override
    protected void initViews() {
        ((TextView)toolbar.findViewById(R.id.tvfTitile)).setText("我对客户的评价");
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        bgFrament1 = new UnEvaluate_IToCustomersFragment();
        bgFrament2 = new Evaluated_IToCustomersFragment();

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tablayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void initPresenter() {}

    @Override
    protected void updateViews(boolean isRefresh) {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_UEITC:
                    Refreshhandler refreshHandler = new Refreshhandler(this);
                    Message msg = new Message();
                    refreshHandler.sendMessage(msg);
                    break;
            }
        }
    }

    public class Refreshhandler extends Handler {
        Activity mContext;
        public Refreshhandler(Activity mContext) {
            this.mContext = mContext;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bgFrament1.showLoadingDialog();
                        bgFrament1.RefreshData(true);
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
