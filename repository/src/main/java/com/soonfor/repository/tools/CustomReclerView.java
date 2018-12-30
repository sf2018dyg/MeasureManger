package com.soonfor.repository.tools;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 作者：DC-DingYG on 2018-09-09 15:27
 * 邮箱：dingyg012655@126.com
 */
public class CustomReclerView extends RecyclerView {

    public CustomReclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutManager(new LinearLayoutManager(context));
        setNestedScrollingEnabled(false);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int newHeightSpec=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthSpec, newHeightSpec);
    }
}
