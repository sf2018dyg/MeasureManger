package com.soonfor.measuremanager.view.tablayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.view.NoScrollViewPager;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-DingYG on 2018-11-05 10:20
 * 邮箱：dingyg012655@126.com
 */
public class TopTabLayout extends LinearLayout {
    private Context mContext;
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
    NoScrollViewPager viewPager;
    boolean isNeedRedPoint;

    public TopTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_top_tablayout, this);
        ButterKnife.bind(this, view);
    }

    //初始化
    public void initTopTabLayout(final Activity mContext, List<String> mTitles, NoScrollViewPager viewPager, FragmentAdapter adapter, boolean isNeedRedPoint) {
        this.mContext = mContext;
        tvfLeftTilte.setText(mTitles.get(0));
        tvfRightTilte.setText(mTitles.get(1));
        this.viewPager = viewPager;
        this.isNeedRedPoint = isNeedRedPoint;
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        viewPager.setOffscreenPageLimit(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @OnClick({R.id.rlfLeft, R.id.rlfRight})
    void clickListener(View v){
        switch (v.getId()){
            case R.id.rlfLeft:
                changeTab(0);
                break;
            case R.id.rlfRight:
                changeTab(1);
                break;
        }
    }


    private void changeTab(int index) {
        try {
            switch (index) {
                case 0:
                    if (imgfLeftVernier.getVisibility() != View.VISIBLE) {
                        tvfLeftTilte.setTextColor(Color.parseColor("#ed423a"));
                        tvfRightTilte.setTextColor(Color.parseColor("#333333"));
                        imgfLeftVernier.setVisibility(View.VISIBLE);
                        imgfRightVernier.setVisibility(View.INVISIBLE);
                        viewPager.setCurrentItem(0);
                    }
                    break;
                case 1:
                    if (imgfRightVernier.getVisibility() != View.VISIBLE) {
                        tvfRightTilte.setTextColor(Color.parseColor("#ed423a"));
                        tvfLeftTilte.setTextColor(Color.parseColor("#333333"));
                        imgfRightVernier.setVisibility(View.VISIBLE);
                        imgfLeftVernier.setVisibility(View.INVISIBLE);
                        viewPager.setCurrentItem(1);
                    }
                    break;
            }
        } catch (Exception e) {
            NLogger.e("切换页签：" + e.getMessage());
        }
    }
}
