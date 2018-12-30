package com.soonfor.measuremanager.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/23 09:43
 * 邮箱：suibozhu@139.com
 * 倾斜 45度的textview x y 偏移中间
 */

public class LeanTextView extends AppCompatTextView {
    public LeanTextView(Context context) {
        super(context);
    }

    public LeanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //倾斜度45,上下左右居中  x 越大越下 y 越小越往右
        canvas.rotate(45, (float) (getMeasuredWidth() / 1.2), (float) (getMeasuredHeight() / 1.9));
        super.onDraw(canvas);
    }


}
