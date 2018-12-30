package com.soonfor.repository.tools;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soonfor.repository.R;


/**
 * 作者：DC-DingYG on 2018-04-27 9:03
 * 邮箱：dingyg012655@126.com
 */
public class LoadingDailog extends Dialog {
    public LoadingDailog(Context context) {
        super(context);
    }

    public LoadingDailog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String message;
        private boolean isShowMessage = true;
        private boolean isCancelable = false;
        private boolean isCancelOutside = false;

        public Builder(Context context) {
            this.context = context;
        }

        public LoadingDailog.Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public LoadingDailog.Builder setShowMessage(boolean isShowMessage) {
            this.isShowMessage = isShowMessage;
            return this;
        }

        public LoadingDailog.Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public LoadingDailog.Builder setCancelOutside(boolean isCancelOutside) {
            this.isCancelOutside = isCancelOutside;
            return this;
        }

        public LoadingDailog create() {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            View view = null;
            try {
                view = inflater.inflate(R.layout.dialog_loading, (ViewGroup) null);
            }catch (Exception e){
                e.fillInStackTrace();
            }
            LoadingDailog loadingDailog = new LoadingDailog(this.context, R.style.MyDialogStyle);
            if(view!=null) {
//                TextView msgText = (TextView) view.findViewById(R.id.tipTextView);
//                if (this.isShowMessage) {
//                    msgText.setText(this.message);
//                } else {
//                    msgText.setVisibility(8);
//                }
                loadingDailog.setContentView(view);
            }
            loadingDailog.setCancelable(this.isCancelable);
            loadingDailog.setCanceledOnTouchOutside(this.isCancelOutside);
            return loadingDailog;
        }
    }
}
