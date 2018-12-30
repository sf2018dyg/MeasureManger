package com.soonfor.measuremanager.view.ninegrid;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by ljc on 2018/1/23.
 */

public class SoonForNineGird extends NineGridLayout {
    public SoonForNineGird(Context context) {
        super(context);
    }

    public SoonForNineGird(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(RatioImageView imageView, String url, int parentWidth) {
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {

    }

    @Override
    protected void onClickImage(int position, String url, List<String> urlList) {

    }
}
