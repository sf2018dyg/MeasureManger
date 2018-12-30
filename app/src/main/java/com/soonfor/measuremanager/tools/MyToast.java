package com.soonfor.measuremanager.tools;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soonfor.measuremanager.R;


/**
 * Created by Administrator on 2017/8/17 0017.
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/2/2 10:22
 * 邮箱：suibozhu@139.com
 */
public class MyToast {
    private Toast mToast;
    FrameLayout frameLayout;
    TextView textView;

    private View init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_eplay_toast, null);
        frameLayout = (FrameLayout) v.findViewById(R.id.background);
        textView = (TextView) v.findViewById(R.id.textView1);
        return v;
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
        try {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, CharSequence text, int backgrounColor, int textViewColor) {
        try {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        MyToast.makeText(context, text, backgrounColor, textViewColor, Toast.LENGTH_SHORT).show();
    }

    public static void showCaveatToast(Context context, CharSequence text) {
        try {
            if (!((Activity) context).isFinishing()) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        MyToast.makeText(context, text, R.drawable.toast_caveat_bg, R.color.white, Toast.LENGTH_SHORT).show();
    }

    public static void showFailToast(Context context, CharSequence text) {
        try {
            if (!((Activity) context).isFinishing()) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        MyToast.makeText(context, text, R.drawable.toast_red_bg, R.color.white, Toast.LENGTH_SHORT).show();
    }

    public static void showSuccessToast(Context context, CharSequence text) {

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//        MyToast.makeText(context, text, R.drawable.toast_green_bg, R.color.white, Toast.LENGTH_SHORT).show();
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