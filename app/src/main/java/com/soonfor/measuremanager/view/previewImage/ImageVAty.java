package com.soonfor.measuremanager.view.previewImage;


import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lzy.ninegrid.preview.HackyViewPager;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ImageVAty extends BaseActivity implements ViewPager.OnPageChangeListener {

    ImageViewPagerAdapter adapter;
    HackyViewPager pager;
    private ImageView[] tips;
    private LinearLayout linearLayout;
    private Activity mActivity;
    private int currentPageIndex;//当前图片所在的页码
    ArrayList<String> urls;
    private Activity FirstActivity;
    String FromBaseHouseType = "";

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_imagevaty;
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initViews() {
        mActivity = ImageVAty.this;
        ((ImageView) toolbar.findViewById(R.id.ivfLeft)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urls.size()>1){
                    if(FromBaseHouseType.equals("量尺清单")){
                        SoonforApplication.pathIndex_liangchi = currentPageIndex;
                    }else if(FromBaseHouseType.equals("复尺清单")) {
                        SoonforApplication.pathIndex_fuchi = currentPageIndex;
                    }else {
                        SoonforApplication.pathIndex_liangchi = -1;
                        SoonforApplication.pathIndex_fuchi = -1;
                    }
                }else{
                    SoonforApplication.pathIndex_liangchi = -1;
                    SoonforApplication.pathIndex_fuchi = -1;
                }
                finish();
            }
        });
        pager = (HackyViewPager) findViewById(R.id.pager);
        linearLayout = (LinearLayout) findViewById(R.id.viewGroup);
        int position = getIntent().getIntExtra("position", 0);
        urls = getIntent().getStringArrayListExtra("images");
        try{
            FromBaseHouseType = getIntent().getStringExtra("ISBASEHOUSETYPE");
            if(FromBaseHouseType==null)FromBaseHouseType = "";
        }catch (Exception e){}
        if (urls != null && urls.size() > 0) {
            setDatas(position);
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    //根据数据显示fragment和page相关
    private void setDatas(int position) {
        List list = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            list.add(urls.get(i));
        }
        int defaultId = -1;
        try{
            defaultId = getIntent().getIntExtra("DEFAULTIMAGEID",-1);
        }catch (Exception e){}
        if(defaultId==-1){
            adapter = new ImageViewPagerAdapter(getSupportFragmentManager(),list);
        }else{
            adapter = new ImageViewPagerAdapter(getSupportFragmentManager(), list, defaultId);
        }
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(this);

        /*下面那几个点*/
        tips = new ImageView[urls.size()];
        linearLayout.removeAllViews();
        for (int i = 0; i < urls.size(); i++) {
            ImageView mImageView = new ImageView(this);
            tips[i] = mImageView;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.rightMargin = 5;
            layoutParams.leftMargin = 5;

            layoutParams.bottomMargin = 35;

            mImageView.setBackgroundResource(R.drawable.page_indicator_unfocused);
            linearLayout.addView(mImageView, layoutParams);
        }
        pager.setCurrentItem(position);
        if(tips.length == 1)
            tips[0].setBackgroundResource(R.drawable.page_indicator_focused);
        else
            setImageBackground(position);
    }

    /*
            * 设置选中的tip的背景
            * @param selectItems
            */
    public void setImageBackground(int selectItems) {
       for (int i = 0; i < tips.length; i++) {
                if (i == selectItems) {
                    tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
                } else {
                    tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                }
            }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPageIndex = position;
        setImageBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(urls.size()>1){
                if(FromBaseHouseType.equals("量尺清单")){
                    SoonforApplication.pathIndex_liangchi = currentPageIndex;
                }else if(FromBaseHouseType.equals("复尺清单")) {
                    SoonforApplication.pathIndex_fuchi = currentPageIndex;
                }else {
                    SoonforApplication.pathIndex_liangchi = -1;
                    SoonforApplication.pathIndex_fuchi = -1;
                }
            }else{
                SoonforApplication.pathIndex_liangchi = -1;
                SoonforApplication.pathIndex_fuchi = -1;
            }
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
