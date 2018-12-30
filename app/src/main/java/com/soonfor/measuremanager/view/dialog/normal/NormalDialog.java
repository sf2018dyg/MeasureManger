package com.soonfor.measuremanager.view.dialog.normal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.soonfor.measuremanager.R;


@SuppressWarnings("deprecation")
public class NormalDialog extends BaseAlertDialog<NormalDialog> {
    /** title underline */
    private View mVLineTitle;
    /** vertical line between btns */
    private View mVLineVertical;
    /** vertical line between btns */
    private View mVLineVertical2;
    /** horizontal line above btns */
    private View mVLineHorizontal;

    /** title underline color(标题下划线颜色) */
    private int mTitleLineColor = Color.parseColor("#f2f2f2");
    /** title underline height(标题下划线高度) */
    private float mTitleLineHeight = 1f;
    /** btn divider line color(对话框之间的分割线颜色(水平+垂直)) */
    private int mDividerColor = Color.parseColor("#DCDCDC");

    public static final int STYLE_ONE = 0;
    public static final int STYLE_TWO = 1;
    private int mStyle = STYLE_ONE;

    private EditText mEditText;
    private int type;
    private int positon = 0;
    private String hintText = "";
    private int intputType;

    public NormalDialog(Context context) {
        super(context);
        intputType = InputType.TYPE_CLASS_TEXT;
        /** default value*/
        mTitleTextColor = Color.parseColor("#333333");
        mTitleTextSize = 18f;
        mContentTextColor = Color.parseColor("#383838");
        mContentTextSize = 15f;
        mLeftBtnTextColor = Color.parseColor("#ff0000");
        mRightBtnTextColor = Color.parseColor("#ff0000");
        mMiddleBtnTextColor = Color.parseColor("#ff0000");
        /** default value*/
    }
    public NormalDialog(Context context, int type, int position, String hintText, int intputType) {
        super(context);
        this.type = type;
        this.positon = position;
        this.hintText = hintText;
        this.intputType = intputType;
        /** default value*/
        mTitleTextColor = Color.parseColor("#333333");
        mTitleTextSize = 18f;
        mContentTextColor = Color.parseColor("#383838");
        mContentTextSize = 14f;
        mLeftBtnTextColor = Color.parseColor("#ff0000");
        mRightBtnTextColor = Color.parseColor("#ff0000");
        mMiddleBtnTextColor = Color.parseColor("#ff0000");
        /** default value*/
    }

    public void setContentTextColor(int newColor){
        mContentTextColor = newColor;
    }

    @Override
    public View onCreateView() {
        /** title */
        mTvTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mLlContainer.addView(mTvTitle);

//        /** title underline */
        mVLineTitle = new View(mContext);
        mLlContainer.addView(mVLineTitle);

        if(type==2){//输入
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setPadding(15,15,15,15);
            linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            LinearLayout llE = new LinearLayout(mContext);
            mEditText = CreateCEditText(llE, positon, hintText, intputType);
            linearLayout.addView(mEditText,layoutParams);
            mLlContainer.addView(linearLayout,layoutParams);
        }else{//显示
            /** content */
            mTvContent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            mLlContainer.addView(mTvContent);
        }


        mVLineHorizontal = new View(mContext);
        mVLineHorizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        mLlContainer.addView(mVLineHorizontal);

        /** btns */
        mTvBtnLeft.setLayoutParams(new LinearLayout.LayoutParams(0, dp2px(45), 1));
        mLlBtns.addView(mTvBtnLeft);

        mVLineVertical = new View(mContext);
        mVLineVertical.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
        mLlBtns.addView(mVLineVertical);

        mTvBtnMiddle.setLayoutParams(new LinearLayout.LayoutParams(0, dp2px(45), 1));
        mLlBtns.addView(mTvBtnMiddle);

        mVLineVertical2 = new View(mContext);
        mVLineVertical2.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
        mLlBtns.addView(mVLineVertical2);

        mTvBtnRight.setLayoutParams(new LinearLayout.LayoutParams(0, dp2px(45), 1));
        mLlBtns.addView(mTvBtnRight);
        if(type==2){
            mTvBtnRight.setTag(mEditText);
        }
        mLlContainer.addView(mLlBtns);

        return mLlContainer;
    }

    @Override
    public void setUiBeforShow() {
        super.setUiBeforShow();

        /** title */
        if (mStyle == STYLE_ONE) {
            mTvTitle.setMinHeight(dp2px(48));
            mTvTitle.setGravity(Gravity.CENTER);
            mTvTitle.setPadding(dp2px(15), dp2px(5), dp2px(0), dp2px(5));
            mTvTitle.setVisibility(mIsTitleShow ? View.VISIBLE : View.GONE);
        } else if (mStyle == STYLE_TWO) {
            mTvTitle.setGravity(Gravity.CENTER);
            mTvTitle.setPadding(dp2px(0), dp2px(15), dp2px(0), dp2px(0));
            mTvTitle.setVisibility(mIsTitleShow ? View.VISIBLE : View.GONE);
        }

        /** title underline */
        mVLineTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                dp2px(mTitleLineHeight)));
        mVLineTitle.setBackgroundColor(mTitleLineColor);
        mVLineTitle.setVisibility(mIsTitleShow && mStyle == STYLE_ONE ? View.VISIBLE : View.GONE);

        /** content */
        if (mStyle == STYLE_ONE) {
            mTvContent.setPadding(dp2px(15), dp2px(10), dp2px(15), dp2px(10));
            mTvContent.setMinHeight(dp2px(68));
            mTvContent.setGravity(mContentGravity);
        } else if (mStyle == STYLE_TWO) {
            mTvContent.setPadding(dp2px(15), dp2px(10), dp2px(15), dp2px(10));
            mTvContent.setMinHeight(dp2px(56));
            mTvContent.setGravity(Gravity.CENTER);
        }

        /** btns */
        mVLineHorizontal.setBackgroundColor(mDividerColor);
        mVLineVertical.setBackgroundColor(mDividerColor);
        mVLineVertical2.setBackgroundColor(mDividerColor);

        if (mBtnNum == 1) {
            mTvBtnLeft.setVisibility(View.GONE);
            mTvBtnRight.setVisibility(View.GONE);
            mVLineVertical.setVisibility(View.GONE);
            mVLineVertical2.setVisibility(View.GONE);
        } else if (mBtnNum == 2) {
            mTvBtnMiddle.setVisibility(View.GONE);
            mVLineVertical.setVisibility(View.GONE);
        }

        /**set background color and corner radius */
        float radius = dp2px(mCornerRadius);
        mLlContainer.setBackgroundDrawable(CornerUtils.cornerDrawable(mBgColor, radius));
        mTvBtnLeft.setBackgroundDrawable(CornerUtils.btnSelector(radius, mBgColor, mBtnPressColor, 0));
        mTvBtnRight.setBackgroundDrawable(CornerUtils.btnSelector(radius, mBgColor, mBtnPressColor, 1));
        mTvBtnMiddle.setBackgroundDrawable(CornerUtils.btnSelector(mBtnNum == 1 ? radius : 0, mBgColor, mBtnPressColor, -1));
    }

    // --->属性设置

    /** set style(设置style) */
    public NormalDialog style(int style) {
        this.mStyle = style;
        return this;
    }

    /** set title underline color(设置标题下划线颜色) */
    public NormalDialog titleLineColor(int titleLineColor) {
        this.mTitleLineColor = titleLineColor;
        return this;
    }

    /** set title underline height(设置标题下划线高度) */
    public NormalDialog titleLineHeight(float titleLineHeight_DP) {
        this.mTitleLineHeight = titleLineHeight_DP;
        return this;
    }

    /** set divider color between btns(设置btn分割线的颜色) */
    public NormalDialog dividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
        return this;
    }


    /**
     * 创建一个EditText
     **/
    /**
     * 修改人：DC-ZhuSuiBo on 2018/4/6 0006 14:30
     * 邮箱：suibozhu@139.com
     * 修改目的：修改输入字体大小
     */
    private EditText CreateCEditText(LinearLayout contextLayout, int positon, String text, int intputType) {
        final EditText et = new CEditText(contextLayout.getContext());
        et.setInputType(intputType);
        if(intputType==InputType.TYPE_CLASS_PHONE){
           et.setFilters(new InputFilter[]{ new  InputFilter.LengthFilter(11)});
        }
        et.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
        et.setHint(text);
        et.setTextSize(15f);//(px2dip(26));
        et.setTextColor(Color.parseColor("#333333"));
        et.setTag(positon);
        et.setPadding(dp2px(10), dp2px(10), dp2px(10), dp2px(10));
        et.setBackgroundResource(R.color.bg_color);
        et.setMinHeight(dp2px(50));
        et.setMaxLines(5);
        //returnColor(et);
        return et;
    }
    /**
     * 重写的EditText
     */
    @SuppressLint("AppCompatCustomView")
    class CEditText extends EditText {

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }

        public CEditText(Context context) {
            super(context);
        }
    }
}
