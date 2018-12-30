package com.soonfor.measuremanager.view.tablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.view.tablayout.bean.TabItem;


/**
 * Created by DingYG on 2017/4/19.
 */

public class TabView extends LinearLayout implements View.OnClickListener {

    private ImageView mTabImage;
    private TextView mTabLable;

    public TabView(Context context) {
        super(context);
        initView(context);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.view_tabview, this, true);
        mTabImage = (ImageView) findViewById(R.id.tab_image);
        mTabLable = (TextView) findViewById(R.id.tab_lable);

    }

    public void initData(TabItem tabItem) {
        mTabImage.setImageResource(tabItem.imageResId);
        mTabLable.setText(tabItem.lableResId);
    }
    public void setSelectTabViews(int imgType,int i, int colorId){
        mTabLable.setTextColor(colorId);
        if(imgType==0) {//未选中
            switch (i) {
                case 0:
                    mTabImage.setImageResource(R.mipmap.iocn_home);
                    break;
                case 1:
                    mTabImage.setImageResource(R.mipmap.icon_cof);
                    break;
                case 2:
                    mTabImage.setImageResource(R.mipmap.icon_upload);
                    break;
                case 3:
                    mTabImage.setImageResource(R.mipmap.icon_list);
                    break;
                case 4:
                    mTabImage.setImageResource(R.mipmap.icon_my);
                    break;
            }
        }else if(imgType==1){
            switch (i) {
                case 0:
                    mTabImage.setImageResource(R.mipmap.iocn_home_active);
                    break;
                case 1:
                    mTabImage.setImageResource(R.mipmap.icon_cof_active);
                    break;
                case 2:
                    mTabImage.setImageResource(R.mipmap.icon_upload_active);
                    break;
                case 3:
                    mTabImage.setImageResource(R.mipmap.icon_list_active);
                    break;
                case 4:
                    mTabImage.setImageResource(R.mipmap.icon_my_active);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
    }
    public void onDataChanged(int badgeCount){

    }
}
