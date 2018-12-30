package com.soonfor.measuremanager.me.activity;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.presenter.modifypaw.IModifyPawView;
import com.soonfor.measuremanager.me.presenter.modifypaw.ModifyPawPresenter;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.RegularTool;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by DingYG on 2018/2/22.
 * 修改面
 */

public class ModifyPawActivity extends BaseActivity<ModifyPawPresenter> implements IModifyPawView {
    private Activity mActivity;
    private ModifyPawPresenter mpPresenter;
    @BindView(R.id.etfOldPaw)
    EditText etfOldPaw;
    @BindView(R.id.etfNewPaw)
    EditText etfNewPaw;
    @BindView(R.id.etfVerifyNewPaw)
    EditText etfVerifyNewPaw;
    String oldPWD, newPWD, newPWDA;
    CustomDialog cDialog;
    NormalDialog nDialog;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_modifypaw;
    }
    @Override
    protected void initPresenter() {
        presenter = new ModifyPawPresenter(this);
        cDialog = CustomDialog.getInstance();
    }


    @Override
    protected void initViews() {
        mActivity = ModifyPawActivity.this;
        initViewAndData();
    }
    private void initViewAndData() {
        etfOldPaw.addTextChangedListener(textWatcher);
        etfNewPaw.addTextChangedListener(textWatcher);
        etfVerifyNewPaw.addTextChangedListener(textWatcher);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            oldPWD = etfOldPaw.getText().toString().trim();
            newPWD = etfNewPaw.getText().toString().trim();
            newPWDA = etfVerifyNewPaw.getText().toString().trim();
        }
    };
    @OnClick({R.id.bt_save})
    void clickList(){
        //隐藏软键盘
        InputMethodManager imm2 = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm2.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (TextUtils.isEmpty(oldPWD)) {
            MyToast.showToast(mActivity, "请输入旧密码！");
            return;
        }
        if (TextUtils.isEmpty(newPWD)) {
            MyToast.showToast(mActivity, "请输入新密码！");
            return;
        }
        if (newPWD.length() < 6 || newPWD.length() > 30) {
            MyToast.showToast(mActivity, "新密码长度需在6~30位之间！");
            return;
        }
        if (!RegularTool.isPwd(newPWD)) {
            MyToast.showToast(mActivity,  "新密码必须包含英文字母和数字！");
            return;
        }
        if (oldPWD.equals(newPWD)) {
            MyToast.showToast(mActivity,  "新、旧密码不能相同，请重新设置新密码！");
            return;
        }
        if (TextUtils.isEmpty(newPWDA)) {
            MyToast.showToast(mActivity, "请再次输入新密码！");
            return;
        }
        if (!(newPWD.equals(newPWDA))) {
            MyToast.showToast(mActivity,  "两次输入的新密码不一致！");
            return;
        }
        /**
         * 请求后台进行修改
         */
        presenter.ModifyPaw(oldPWD,newPWDA);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (!TextUtils.isEmpty(oldPWD) && !TextUtils.isEmpty(newPWD)) {
               nDialog = cDialog.getNoTitleDialog(mActivity, "是否放弃修改?", new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View view) {
                        nDialog.dismiss();
                        finish();
                    }
                });
            } else {
                mActivity.finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }
}
