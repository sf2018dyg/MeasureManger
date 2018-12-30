package com.soonfor.repository.tools.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;

import com.soonfor.repository.R;

/**
 * 作者：DC-DingYG on 2018-07-26 20:24
 * 邮箱：dingyg012655@126.com
 */
public class TopicDialog  extends Dialog {

    private Context context;

    public TopicDialog(Context context) {
        //重写dialog默认的主题
        this(context, R.style.dialog);
        this.context=context;
    }

    public TopicDialog(Context context, int themeResId) {
        super(context, themeResId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setGravity(Gravity.BOTTOM); //显示在底部


    }
}

