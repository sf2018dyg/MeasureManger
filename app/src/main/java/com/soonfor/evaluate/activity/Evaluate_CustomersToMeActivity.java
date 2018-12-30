package com.soonfor.evaluate.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.evaluate.activity.fragment.Evaluated_CustomersToMeFragment;
import com.soonfor.evaluate.activity.fragment.UnEvaluate_CustomersToMeFragment;
import com.soonfor.measuremanager.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-DingYG on 2018-10-17 15:20
 * 邮箱：dingyg012655@126.com
 * 客户对我的评价
 */
public class Evaluate_CustomersToMeActivity extends BaseActivity {
    private String[] titles = new String[]{"已评价", "待评价"};
    private Evaluated_CustomersToMeFragment bgFrament1;
    private UnEvaluate_CustomersToMeFragment bgFrament2;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @BindView(R.id.tvfLeftTilte)
    TextView tvfLeftTilte;
    @BindView(R.id.tvfRightTilte)
    TextView tvfRightTilte;
    @BindView(R.id.imgfNewNotify)
    ImageView imgfNewNotify;
    @BindView(R.id.imgfLeftVernier)
    ImageView imgfLeftVernier;
    @BindView(R.id.imgfRightVernier)
    ImageView imgfRightVernier;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    private int currPosition = 0;// 当前显示页面得位置

    @Override
    protected void initPresenter() {
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_evaluate_clients_to_me;
    }

    @Override
    protected void initViews() {
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("客户对我的评价");
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        bgFrament1 = new Evaluated_CustomersToMeFragment();
        bgFrament2 = new UnEvaluate_CustomersToMeFragment();

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);
        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        viewPager.setCurrentItem(0);
    }

    @OnClick({R.id.rlfYEvaluateToMe, R.id.rlfNEvaluateToMe})
    void thisOnClick(View v) {
        switch (v.getId()) {
            case R.id.rlfYEvaluateToMe:
                changeTab(0);
                break;
            case R.id.rlfNEvaluateToMe:
                changeTab(1);
                break;
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    private void changeTab(int index){
        try {
            switch (index){
                case 0:
                    if(imgfLeftVernier.getVisibility() != View.VISIBLE){
                        tvfLeftTilte.setTextColor(Color.parseColor("#ed423a"));
                        tvfRightTilte.setTextColor(Color.parseColor("#333333"));
                        imgfLeftVernier.setVisibility(View.VISIBLE);
                        imgfRightVernier.setVisibility(View.INVISIBLE);
                        viewPager.setCurrentItem(0);
                    }
                    break;
                case 1:
                    if(imgfRightVernier.getVisibility() != View.VISIBLE){
                        tvfRightTilte.setTextColor(Color.parseColor("#ed423a"));
                        tvfLeftTilte.setTextColor(Color.parseColor("#333333"));
                        imgfRightVernier.setVisibility(View.VISIBLE);
                        imgfLeftVernier.setVisibility(View.INVISIBLE);
                        viewPager.setCurrentItem(1);
                    }
                    break;
            }
        }catch (Exception e){
            NLogger.e("切换页签：" + e.getMessage());
        }
    }
}
