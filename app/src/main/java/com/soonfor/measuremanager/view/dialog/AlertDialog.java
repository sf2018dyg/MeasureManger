package com.soonfor.measuremanager.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;


/**
 * 作者：DC-ZhuSuiBo on 2018/8/30 0030 08:26
 * 邮箱：suibozhu@139.com
 * 类用途：自定义画像用
 */
public class AlertDialog {
    /**
     * ApprovalAlertDialog.build(mAcitvity, edt_spopinion.getText().toString(), edt_spopinion).show();
     *
     * @param activity 上下文
     * @param content  上文中的内容
     * @param etView   上文中文本框
     **/
    public static Dialog build(final Activity activity, final String content, final TextView etView) {

        View view = LayoutInflater.from(activity).inflate(
                R.layout.view_huaxiang_messagedialog, null);
        final Dialog dialog = new Dialog(activity, R.style.dialog);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        // 内容
        final EditText tView = (EditText) view.findViewById(R.id.edt_spopinion_detial);
        RelativeLayout okButton = (RelativeLayout) view.findViewById(R.id.rl_spconfirm);
        RelativeLayout cancelButton = (RelativeLayout) view.findViewById(R.id.rl_spcancl);
        if (!content.equals("")) {
            tView.setText(content + "");
        }
        tView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etView.setText(tView.getText().toString() + "");
//                App.isUpdateOpinion = true;
//                App.Opinion = tView.getText().toString();
                dialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                App.isUpdateOpinion = false;
//                App.Opinion = "";
                dialog.dismiss();
            }
        });
        return dialog;
    }

}
