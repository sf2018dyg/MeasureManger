//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.soonfor.measuremanager.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jesse.nativelogger.NLogger;

public class ViewTools {
    public ViewTools() {
    }

    public static ProgressBar setProgress(Activity activity, int id, int progress) {
        ProgressBar progressBar = (ProgressBar) activity.findViewById(id);
        progressBar.setProgress(progress);
        progressBar.setMax(100);
        return progressBar;
    }

    public static TextView setStringToTextView(Activity activity, int textId, CharSequence text) {
        TextView textView = (TextView) activity.findViewById(textId);
        textView.setText(text);
        return textView;
    }

    public static TextView setStringToTextView(View view, int textId, CharSequence text) {
        TextView textView = (TextView) view.findViewById(textId);
        textView.setText(text);
        return textView;
    }

    public static Button setStringToButton(View view, int textId, CharSequence text) {
        Button button = (Button) view.findViewById(textId);
        button.setText(text);
        return button;
    }

    public static Button setStringToButton(Activity activity, int textId, CharSequence text) {
        Button button = (Button) activity.findViewById(textId);
        button.setText(text);
        return button;
    }

    public static void setVisible(Activity activity, int id) {
        activity.findViewById(id).setVisibility(View.VISIBLE);
    }

    public static void setVisible(View view, int id) {
        view.findViewById(id).setVisibility(View.VISIBLE);
    }

    public static void setInvisible(Activity activity, int id) {
        activity.findViewById(id).setVisibility(View.INVISIBLE);
    }

    public static void setInvisible(View view, int id) {
        view.findViewById(id).setVisibility(View.INVISIBLE);
    }

    public static void setGone(Activity activity, int id) {
        activity.findViewById(id).setVisibility(View.GONE);
    }

    public static void setGone(View view, int id) {
        view.findViewById(id).setVisibility(View.GONE);
    }

    public static void setAlpha(Activity activity, int id, float alpha) {
        activity.findViewById(id).setAlpha(alpha);
    }

    public static void setAlpha(View view, int id, float alpha) {
        view.findViewById(id).setAlpha(alpha);
    }

    public static boolean isShow(Activity activity, int id) {
        return activity.findViewById(id).isShown();
    }

    public static boolean isShow(View view, int id) {
        return view.findViewById(id).isShown();
    }

    public static TextView setTextViewBackground(Activity activity, int textId, int resid) {
        TextView textView = (TextView) activity.findViewById(textId);
        textView.setBackgroundResource(resid);
        return textView;
    }

    public static TextView setTextViewBackground(View view, int textId, int resid) {
        TextView textView = (TextView) view.findViewById(textId);
        textView.setBackgroundResource(resid);
        return textView;
    }

    public static EditText setStringToEditText(Activity activity, int editId, CharSequence text) {
        EditText editText = (EditText) activity.findViewById(editId);
        editText.setText(text);
        editText.setSelection(editText.getText().toString().length());
        return editText;
    }

    public static EditText setStringToEditText(View view, int editId, CharSequence text) {
        EditText editText = (EditText) view.findViewById(editId);
        editText.setText(text);
        editText.setSelection(editText.getText().toString().length());
        return editText;
    }

    public static EditText setEditTextEnable(View view, int editId, boolean enabled) {
        EditText editText = (EditText) view.findViewById(editId);
        editText.setEnabled(enabled);
        return editText;
    }

    public static String getStringFromTextView(Activity activity, int textId) {
        TextView textView = (TextView) activity.findViewById(textId);
        String content = textView.getText().toString().trim();
        return content;
    }

    public static String getStringFromTextView(View view, int textId) {
        TextView textView = (TextView) view.findViewById(textId);
        String content = textView.getText().toString().trim();
        return content;
    }

    public static String getStringFromButton(Activity activity, int textId) {
        Button textView = (Button) activity.findViewById(textId);
        String content = textView.getText().toString().trim();
        return content;
    }

    public static String getStringFromEdittext(Activity activity, int editId) {
        EditText editText = (EditText) activity.findViewById(editId);
        String content = editText.getText().toString().trim();
        return content;
    }

    public static String getStringFromEdittext(View view, int editId) {
        EditText editText = (EditText) view.findViewById(editId);
        String content = editText.getText().toString().trim();
        return content;
    }

    public static RelativeLayout setRelativeLayoutListener(Activity activity, int textId, OnClickListener listener) {
        RelativeLayout relativeLayout = (RelativeLayout) activity.findViewById(textId);
        relativeLayout.setOnClickListener(listener);
        return relativeLayout;
    }

    public static RelativeLayout setRelativeLayoutListener(View parentView, int textId, OnClickListener listener) {
        RelativeLayout relativeLayout = (RelativeLayout) parentView.findViewById(textId);
        relativeLayout.setOnClickListener(listener);
        return relativeLayout;
    }

    public static LinearLayout setLinearLayoutListener(Activity activity, int textId, OnClickListener listener) {
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(textId);
        linearLayout.setOnClickListener(listener);
        return linearLayout;
    }

    public static LinearLayout setLinearLayoutListener(View parentView, int textId, OnClickListener listener) {
        LinearLayout linearLayout = (LinearLayout) parentView.findViewById(textId);
        linearLayout.setOnClickListener(listener);
        return linearLayout;
    }

    public static TextView setTextViewListener(Activity activity, int textId, OnClickListener listener) {
        TextView textView = (TextView) activity.findViewById(textId);
        textView.setOnClickListener(listener);
        return textView;
    }

    public static ImageView setImageViewListener(Activity activity, int imageId, OnClickListener listener) {
        ImageView imageView = (ImageView) activity.findViewById(imageId);
        imageView.setOnClickListener(listener);
        return imageView;
    }

    public static ImageView setImageViewListener(View view, int imageId, OnClickListener listener) {
        ImageView imageView = (ImageView) view.findViewById(imageId);
        imageView.setOnClickListener(listener);
        return imageView;
    }

    public static ImageView setImageButtonListener(Activity activity, int imageId, OnClickListener listener) {
        ImageButton imageView = (ImageButton) activity.findViewById(imageId);
        imageView.setOnClickListener(listener);
        return imageView;
    }

    public static ImageView setImageButtonListener(View view, int imageId, OnClickListener listener) {
        ImageButton imageView = (ImageButton) view.findViewById(imageId);
        imageView.setOnClickListener(listener);
        return imageView;
    }

//    public static ImageView setImageViewBackround(Activity activity, int imageId, int resid) {
//        ImageView imageView = (ImageView)activity.findViewById(imageId);
//        imageView.setImageBitmap(PictureUtil.readBitMap(activity, resid));
//        return imageView;
//    }
//
//    public static ImageView setImageViewBackround(Context context, View view, int imageId, int resid) {
//        ImageView imageView = (ImageView)view.findViewById(imageId);
//        imageView.setImageBitmap(PictureUtil.readBitMap(context, resid));
//        return imageView;
//    }

    public static void setTextViewHint(View parentView, int textId, String hint) {
        TextView textView = (TextView) parentView.findViewById(textId);
        textView.setHint(hint);
    }

    public static void setTextViewHint(Activity activity, int textId, String hint) {
        TextView textView = (TextView) activity.findViewById(textId);
        textView.setHint(hint);
    }

    public static void setEditTextHint(View parentView, int textId, String hint) {
        TextView textView = (TextView) parentView.findViewById(textId);
        textView.setHint(hint);
    }

    public static void setEditTextHint(Activity activity, int textId, String hint) {
        TextView textView = (TextView) activity.findViewById(textId);
        textView.setHint(hint);
    }

    public static TextView setTextViewListener(View parentView, int textId, OnClickListener listener) {
        TextView textView = (TextView) parentView.findViewById(textId);
        textView.setOnClickListener(listener);
        return textView;
    }

    public static TextView setTextViewTextColor(Activity activity, int textId, String color) {
        TextView textView = (TextView) activity.findViewById(textId);
        textView.setTextColor(Color.parseColor(color));
        return textView;
    }

    public static TextView setTextViewTextColor(Activity activity, int textId, int color) {
        TextView textView = (TextView) activity.findViewById(textId);
        textView.setTextColor(color);
        return textView;
    }

    public static TextView setTextViewTextColor(View view, int textId, String color) {
        TextView textView = (TextView) view.findViewById(textId);
        textView.setTextColor(Color.parseColor(color));
        return textView;
    }

    public static TextView setTextViewTextColor(View view, int textId, int color) {
        TextView textView = (TextView) view.findViewById(textId);
        textView.setTextColor(color);
        return textView;
    }

    public static Button setButtonListener(Activity activity, int btnId, OnClickListener listener) {
        Button button = (Button) activity.findViewById(btnId);
        button.setOnClickListener(listener);
        return button;
    }

    public static Button setButtonListener(View view, int btnId, OnClickListener listener) {
        Button button = (Button) view.findViewById(btnId);
        button.setOnClickListener(listener);
        return button;
    }

    public static void setButtonTextColor(Activity activity, int btnId, int color) {
        Button button = (Button) activity.findViewById(btnId);
        button.setTextColor(activity.getResources().getColor(color));
    }

    public static void setButtonTextColor(View view, int btnId, int color) {
        Button button = (Button) view.findViewById(btnId);
        button.setTextColor(view.getResources().getColor(color));
    }

    public static void addListViewBottomView(Context context, int id, ListView listView) {
        View view = LayoutInflater.from(context).inflate(id, (ViewGroup) null);
        listView.addFooterView(view);
    }

    public static TextView setBelowLine(TextView textView) {
        textView.getPaint().setFlags(View.GONE);
        return textView;
    }

    public static void setTextCuTi(TextView textview) {
        TextPaint tp = textview.getPaint();
        tp.setFakeBoldText(true);
    }

    public static void setWebView(WebView webView, String url) {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setFocusable(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.requestFocus();
        webView.setFocusableInTouchMode(true);
    }

    public static void setBackgroundResource(Activity activity, int id, int drawId) {
        activity.findViewById(id).setBackgroundResource(drawId);
    }

    public static void setBackgroundResource(View view, int id, int drawId) {
        view.findViewById(id).setBackgroundResource(drawId);
    }

    public static void setBackgroundColor(Activity activity, int id, int colorId) {
        activity.findViewById(id).setBackgroundColor(colorId);
    }

    public static void setBackgroundColor(View view, int id, int colorId) {
        view.findViewById(id).setBackgroundColor(colorId);
    }

    public static void setEditTextFocusChangeListener(Activity activity, int id, OnFocusChangeListener l) {
        EditText editText = (EditText) activity.findViewById(id);
        editText.setOnFocusChangeListener(l);
    }

    public static void setEditTextChangedListener(Activity activity, int id, TextWatcher textWatcher) {
        EditText editText = (EditText) activity.findViewById(id);
        editText.addTextChangedListener(textWatcher);
    }

    /**
     * @param activity 禁止输入表情
     * @param id
     */
    public static void setEditTextForbiddenEmoji(Activity activity, int id) {
        final EditText editText = (EditText) activity.findViewById(id);
        final String[] before = {""};
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                before[0] = editText.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (hasEmoji(charSequence.toString())) {
                    editText.setText(before[0]);
                    editText.setSelection(i);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * @param view 禁止输入表情
     * @param id
     */
    public static void setEditTextForbiddenEmoji(View view, int id) {
        final EditText editText = (EditText) view.findViewById(id);
        final String[] before = {""};
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                before[0] = editText.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (hasEmoji(charSequence.toString())) {
                    editText.setText(before[0]);
                    editText.setSelection(i);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * @param string 判断是否有表情
     * @return
     */
    public static boolean hasEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }


    public static void setEditTextInputLength(Activity activity, int id, int length) {
        EditText editText = (EditText) activity.findViewById(id);
        editText.setFilters(new InputFilter[]{new LengthFilter(length)});
    }

    public static void setEditTextInputLength(View view, int id, int length) {
        EditText editText = (EditText) view.findViewById(id);
        editText.setFilters(new InputFilter[]{new LengthFilter(length)});
    }

    public static void setEditTextInputType(Activity activity, int id, int type) {
        EditText editText = (EditText) activity.findViewById(id);
        editText.setInputType(type);
    }

    public static void setEditTextInputType(View view, int id, int type) {
        EditText editText = (EditText) view.findViewById(id);
        editText.setInputType(type);
    }

    public static void setImageViewBitmap(Activity activity, int id, Bitmap bitmap) {
        ImageView imageView = (ImageView) activity.findViewById(id);
        imageView.setImageBitmap(bitmap);
    }

    public static void setImageViewBitmap(View view, int id, Bitmap bitmap) {
        ImageView imageView = (ImageView) view.findViewById(id);
        imageView.setImageBitmap(bitmap);
    }

    public static boolean checkBoxIsSelect(Activity activity, int id) {
        CheckBox checkBox = (CheckBox) activity.findViewById(id);
        return checkBox.isChecked();
    }

    public static boolean checkBoxIsSelect(View view, int id) {
        CheckBox checkBox = (CheckBox) view.findViewById(id);
        return checkBox.isChecked();
    }

    public static void setOnCheckedChangeListener(Activity activity, int id, RadioGroup.OnCheckedChangeListener listener) {
        RadioGroup radioGroup = (RadioGroup) activity.findViewById(id);
        radioGroup.setOnCheckedChangeListener(listener);
    }

    public static void setEnabled(Activity activity, int id, boolean enabled) {
        View view = (View) activity.findViewById(id);
        view.setEnabled(enabled);
    }

    public static void setFocusable(Activity activity, int id, boolean focusable) {
        View view = (View) activity.findViewById(id);
        view.setFocusable(focusable);
    }

    public static void setViewEnabled(Activity activity, int id, boolean colorId) {
        activity.findViewById(id).setEnabled(colorId);
    }

    public static void setViewEnabled(View view, int id, boolean colorId) {
        view.findViewById(id).setEnabled(colorId);
    }


//    public static void imgDisplay(Context context, String url, ImageView imageView) {
//        if (url == null || url.equals("") || url.equals("null")) {
//            Picasso.with(context).load(R.drawable.bitmap)
//                    .placeholder(R.drawable.bitmap)
//                    .error(R.drawable.bitmap)
//                    .into(imageView);
//            return;
//        }
//        Picasso.with(context).load(url)
//                .placeholder(R.drawable.bitmap)
//                .error(R.drawable.bitmap)
//                .into(imageView);
//    }
//
//    public static void headDisplay(Context context, String url, ImageView imageView, int headType) {
//        int ic_default = 0;
//        if (headType == 1) {
//            ic_default = R.drawable.avatar_client;
//        } else if (headType == 2) {
//            ic_default = R.drawable.avatar_merchant;
//        }
//        if (headType == 3) {
//            ic_default = R.drawable.avatar_merchant_delivery;
//        }
//        if (url == null || url.equals("") || url.equals("null")) {
//            Picasso.with(context).load(ic_default)
//                    .placeholder(ic_default)
//                    .error(ic_default)
//                    .into(imageView);
//            return;
//        }
//        Picasso.with(context).load(url)
//                .placeholder(ic_default)
//                .error(ic_default)
//                .into(imageView);
//    }

    /**
     * @param context 获取版本号
     * @return
     */
    public static String getVersion(Context context) {
        String versionCode = "";
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 判断软键盘是否弹出
     */
    public static boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    /**
     * @return 判断照相机是否存在
     */
    public static boolean isCameraUseable() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5MX5通过Camera.open()拿到的Camera对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    /**
     * 修改人：DC-ZhuSuiBo on 2018/2/3 8:35
     * 邮箱：suibozhu@139.com
     * 返回文字右边图标的位置大小  居左，居上，宽 ，长
     * 左 1 上 2 右  3  下  4
     */
    public static void returnDrawable(Context context, TextView tv, int posi, int resid, int[] bound) {
        Drawable drawable = context.getResources().getDrawable(resid);
        drawable.setBounds(bound[0], bound[1], bound[2], bound[3]);
        switch (posi) {
            case 1:
                tv.setCompoundDrawables(drawable, null, null, null);
                break;
            case 2:
                tv.setCompoundDrawables(null, drawable, null, null);
                break;
            case 3:
                tv.setCompoundDrawables(null, null, drawable, null);
                break;
            case 4:
                tv.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }
}
