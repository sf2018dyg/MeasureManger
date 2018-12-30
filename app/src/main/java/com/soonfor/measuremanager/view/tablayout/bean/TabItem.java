package com.soonfor.measuremanager.view.tablayout.bean;


import android.support.v4.app.Fragment;

/**
 * Created by DingYG on 2017/4/19.
 */

public class TabItem {
    /**
     * icon
     */
    public int imageResId;
    /**
     * 文本
     */
    public int lableResId;
    public Fragment tagFragmentClz;

    public String title;
    public TabItem(int imageResId,int lableResId) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
    }
    public TabItem(int imageResId, int lableResId, Fragment tagFragmentClz){
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragmentClz;
    }

    public Fragment getTagFragmentClz() {
        return tagFragmentClz;
    }
}
