package com.soonfor.repository.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soonfor.repository.R;
import com.soonfor.repository.R;


/**
 * Created by Administrator on 2017/8/17 0017.
 */
public class MyToast {
    private Toast mToast;
    FrameLayout frameLayout;
    TextView textView;

    private View init(Context context) {
        if(context!=null) {
            View v = LayoutInflater.from(context).inflate(R.layout.view_eplay_toast, null);
            frameLayout = (FrameLayout) v.findViewById(R.id.background);
            textView = (TextView) v.findViewById(R.id.textView1);
            return v;
        }else {
            return null;
        }
    }

    private MyToast(Context context, CharSequence text, int duration) {
        View v = init(context);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
    }

    private MyToast(Context context, CharSequence text, int backgrounColor, int textViewColor, int duration) {
        View v = init(context);
        frameLayout.setBackgroundResource(backgrounColor);
        textView.setText(text);
        textView.setTextColor(context.getResources().getColor(textViewColor));
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
    }

    public static MyToast makeText(Context context, CharSequence text, int backgrounColor, int textViewColor, int duration) {
        return new MyToast(context, text, backgrounColor, textViewColor, duration);
    }

    public static MyToast makeText(Context context, CharSequence text, int duration) {
        return new MyToast(context, text, duration);
    }

    public static void showToast(Context context, CharSequence text) {
        MyToast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, CharSequence text, int backgrounColor, int textViewColor) {
        MyToast.makeText(context, text, backgrounColor, textViewColor, Toast.LENGTH_SHORT).show();
    }

    public static void showCaveatToast(Context context, CharSequence text) {
        MyToast.makeText(context, text, R.drawable.toast_caveat_bg, R.color.white, Toast.LENGTH_LONG).show();
    }

    public static void showFailToast(Context context, CharSequence text) {
        MyToast.makeText(context, text, R.drawable.toast_red_bg, R.color.white, Toast.LENGTH_SHORT).show();
    }

    public static void showSuccessToast(Context context, CharSequence text) {
        MyToast.makeText(context, text, R.drawable.toast_green_bg, R.color.white, Toast.LENGTH_SHORT).show();
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
