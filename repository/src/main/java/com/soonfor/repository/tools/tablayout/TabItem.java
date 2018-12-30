package com.soonfor.repository.tools.tablayout;


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

    public int rightImageId;

    public TabItem(int imageResId, int lableResId, String title) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.title = title;
    }
    public TabItem(int imageResId, int lableResId, String title, Fragment tagFragmentClz){
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.title = title;
        this.tagFragmentClz = tagFragmentClz;
    }
    public TabItem(int imageResId, int rightImageId, int lableResId, String title, Fragment tagFragmentClz){
        this.imageResId = imageResId;
        this.rightImageId = rightImageId;
        this.lableResId = lableResId;
        this.title = title;
        this.tagFragmentClz = tagFragmentClz;
    }
}
