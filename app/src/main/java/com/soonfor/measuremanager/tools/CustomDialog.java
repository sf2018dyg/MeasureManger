package com.soonfor.measuremanager.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;
import com.soonfor.repository.tools.ActivityUtils;


/**
 * Created by Administrator on 2017-12-30.
 */

public class CustomDialog {

    static CustomDialog custDialog;

    private CustomDialog() {
    }

    public static CustomDialog getInstance() {
        if (custDialog == null) {
            synchronized (CustomDialog.class) {
                if (custDialog == null) {
                    custDialog = new CustomDialog();
                }
            }
        }
        return custDialog;
    }
    /**
     * 对话框start
     **/
    public static NormalDialog getNormalDialog(Context mContext, String title, String msg, OnBtnClickL blistener) {
        if (mContext != null && ActivityUtils.isRunning((Activity) mContext)) {
            final NormalDialog dialog = new NormalDialog(mContext);
            dialog.content(msg)
                    .style(NormalDialog.STYLE_ONE)//
                    .title(title)
                    .titleTextSize(18)//
                    .show();
            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            dialog.dismiss();
                        }
                    },
                    blistener);
            return dialog;
        } else {
            return null;
        }
    }
    /**
     * 对话框start
     **/
    public static NormalDialog getNormalDialog_Red(Context mContext, String title, String msg, OnBtnClickL blistener) {
        if (mContext != null && ActivityUtils.isRunning((Activity) mContext)) {
            final NormalDialog dialog = new NormalDialog(mContext);
            dialog.setContentTextColor(Color.parseColor("#ed423a"));
            dialog.content(msg)
                    .style(NormalDialog.STYLE_ONE)//
                    .title(title)
                    .titleTextSize(18)//
                    .show();
            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            dialog.dismiss();
                        }
                    },
                    blistener);
            return dialog;
        } else {
            return null;
        }
    }

    public static NormalDialog getDoubleNormalDialog(Context mContext, String title, String msg, OnBtnClickL unFinlistener, OnBtnClickL Finlistener) {
        if (mContext != null && ActivityUtils.isRunning((Activity) mContext)) {
            final NormalDialog dialog = new NormalDialog(mContext);
            dialog.content(msg)
                    .style(NormalDialog.STYLE_TWO)//
                    .title(title)
                    .titleTextSize(18)//
                    .show();
            dialog.setCancelable(false);
            dialog.btnNum(2);
            dialog.btnText("未完成", "完成");
            dialog.setOnBtnClickL(unFinlistener, Finlistener);
            return dialog;
        } else {
            return null;
        }
    }

    public static NormalDialog getSingleNormalDialog2(Context mContext, String msg, String btnmsg, OnBtnClickL onBtnClickL) {
        if (mContext != null && ActivityUtils.isRunning((Activity) mContext)) {
            final NormalDialog dialog = new NormalDialog(mContext);
            dialog.content(msg)
                    .style(NormalDialog.STYLE_TWO)//
                    .titleTextSize(18)//
                    .show();
            dialog.btnNum(1);
            dialog.btnText(btnmsg);
            dialog.setOnBtnClickL(onBtnClickL);
            return dialog;
        } else {
            return null;
        }
    }

    /**
     * 无标题的选择提示框
     */
    public NormalDialog getNoTitleDialog(Context mContext, String msg, OnBtnClickL blistener) {
        if (mContext != null && ActivityUtils.isRunning((Activity) mContext)) {
            final NormalDialog dialog = new NormalDialog(mContext);
            dialog.content(msg)
                    .style(NormalDialog.STYLE_TWO)//
                    .isTitleShow(false)
                    .show();
            dialog.setOnBtnClickL(new OnBtnClickL() {
                @Override
                public void onBtnClick(View view) {
                    dialog.dismiss();
                }
            }, blistener);
            return dialog;
        } else {
            return null;
        }
    }

    /**
     * 输入提示框
     */
    public NormalDialog getInputDialog(Context mContext, String title, String hintText, OnBtnClickL blistener, int postion) {
        if (mContext != null && ActivityUtils.isRunning((Activity) mContext)) {
            final NormalDialog dialog = new NormalDialog(mContext, 2, postion, hintText, InputType.TYPE_CLASS_TEXT);
            dialog.style(NormalDialog.STYLE_ONE)//
                    .title(title)
                    .titleTextSize(18)//
                    .show();
            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            dialog.dismiss();
                        }
                    },
                    blistener);
            return dialog;
        } else {
            return null;
        }
    }
    /**
     * 输入提示框
     */
    public NormalDialog getInputDialog(Context mContext, String title, String hintText, OnBtnClickL blistener, int postion, int intputType) {
        if (mContext != null && ActivityUtils.isRunning((Activity) mContext)) {
            final NormalDialog dialog = new NormalDialog(mContext, 2, postion, hintText, intputType);
            dialog.style(NormalDialog.STYLE_ONE)//
                    .title(title)
                    .titleTextSize(18)//
                    .show();
            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            dialog.dismiss();
                        }
                    },
                    blistener);
            return dialog;
        } else {
            return null;
        }
    }

    /**
     * 作者：DC-ZhuSuiBo on 2018/2/8 11:12
     * 邮箱：suibozhu@139.com
     * 新建量尺的输入框
     */
    public static Dialog ShowLiangChiDialog(final Activity mActivity, String title, String loucengname, String loucenghint, String customname, View.OnClickListener confirmListener) {
        View view = LayoutInflater.from(mActivity).inflate(
                R.layout.view_dialog_createliangchi, null);
        final Dialog dialog = new Dialog(mActivity, R.style.my_style_dialog);
        WindowManager m = mActivity.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = mActivity.getWindow().getAttributes();
        p.width = d.getWidth() / 5 * 4; //设置dialog的宽度为当前手机屏幕的宽度
        dialog.setContentView(view, p);
        dialog.setCancelable(false);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView tcfcustom = (TextView) view.findViewById(R.id.tcfcustom);
        EditText tvfloulevel = (EditText) view.findViewById(R.id.tvfloulevel);
        RelativeLayout rlcancel = (RelativeLayout) view.findViewById(R.id.rlcancel);
        final RelativeLayout rlconfirm = (RelativeLayout) view.findViewById(R.id.rlconfirm);
        TextView txtlclc = (TextView) view.findViewById(R.id.txtlclc);

        txtlclc.setText(loucengname + "");
        tvfloulevel.setHint(loucenghint + "");
        txtTitle.setText(title + "");
        tcfcustom.setText(customname + "");
        tvfloulevel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rlconfirm.setTag(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rlcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rlconfirm.setOnClickListener(confirmListener);

        return dialog;
    }

    /**
     * 量尺复尺提交 保存模版的
     **/
    public static Dialog ShowSubmitSaveModel(Activity mActivity, String msghint, View.OnClickListener confirmListener, View.OnClickListener disSaveListener) {
        View view = LayoutInflater.from(mActivity).inflate(
                R.layout.view_dialog_submitsavemodel, null);
        final Dialog dialog = new Dialog(mActivity, R.style.my_style_dialog);
        WindowManager m = mActivity.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = mActivity.getWindow().getAttributes();
        p.width = d.getWidth() / 5 * 4; //设置dialog的宽度为当前手机屏幕的宽度
        dialog.setContentView(view, p);
        dialog.setCancelable(false);
        TextView tcfcustom = (TextView) view.findViewById(R.id.tcfcustom);
        RelativeLayout rlcancel = (RelativeLayout) view.findViewById(R.id.rlcancel);
        final RelativeLayout rlconfirm = (RelativeLayout) view.findViewById(R.id.rlconfirm);

        tcfcustom.setText(msghint + "");
        rlcancel.setOnClickListener(disSaveListener);

        rlconfirm.setOnClickListener(confirmListener);
        return dialog;
    }

}
