package com.soonfor.measuremanager.home.login.activity;

import android.graphics.Paint;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.login.presenter.LoginPresenter;
import com.soonfor.measuremanager.home.login.view.ILoginView;
import com.soonfor.measuremanager.home.main.MainActivity;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.me.activity.SettingActivity;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.repository.tools.ActivityUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/11 0011.
 */
@Route(path = "/com/soonfor/measuremanager/LoginActivity")
public class LoginActivity extends BaseActivity<LoginPresenter> implements CompoundButton.OnCheckedChangeListener, ILoginView {

    public static LoginActivity loginActivity;
    @BindView(R.id.img_login_logo)
    ImageView imgLoginLogo;
    @BindView(R.id.tv_login_username)
    EditText tvLoginUsername;
    @BindView(R.id.tv_login_password)
    EditText tvLoginPassword;
    @BindView(R.id.check_is_remember)
    CheckBox checkIsRemember;
    @BindView(R.id.tv_forget)
    TextView tvForgot;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_login_merchantCode)
    EditText tvLoginMerchantCode;
    public boolean is_Remember = false;

    /**
     * 是否显示验证码
     */
    private boolean is_validate_show = false;

    @BindView(R.id.tv_validate)
    EditText tvValidate;
    @BindView(R.id.iv_validate)
    ImageView ivValidate;
    @BindView(R.id.ll_validate)
    LinearLayout llValidate;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        actionName = "登录中...";
        return R.layout.activity_login;
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        is_Remember = PreferenceUtil.getBoolean(UserInfo.ISREMEMBER, false);
        presenter = new LoginPresenter(this);
        loginActivity = LoginActivity.this;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        mLoadingDialog.setCancelable(false);
        tvReset.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvReset.getPaint().setAntiAlias(true);//抗锯齿
        checkIsRemember.setOnCheckedChangeListener(this);
        checkIsRemember.setSelected(is_Remember);
        if(Hawk.get(SoonforApplication.ServerAdr, "").equals("")){
            startNewAct(ServerSettingsActivity.class);
        }
    }

    /**
     * 验证码显示
     */
    public void showValidate(boolean bl) {
        if (bl) {
            llValidate.setVisibility(View.VISIBLE);
            Picasso.with(this).load("http://" + Hawk.get(SoonforApplication.ServerAdr) + "/captcha")
                    .placeholder(R.drawable.ic_album).memoryPolicy(MemoryPolicy.NO_CACHE).into(ivValidate);
            this.is_validate_show = true;
        } else {
            llValidate.setVisibility(View.GONE);
            this.is_validate_show = false;
        }
        presenter.getKey(tvLoginUsername, tvLoginPassword, tvValidate, is_validate_show, "mt");
    }

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    @Override
    protected void updateViews(boolean isRefresh) {
    }

    @OnClick({R.id.tv_forget, R.id.bt_login, R.id.tv_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                startNewAct(ForgetPasswordActivity.class);
                break;
            case R.id.bt_login:
                presenter.needCaptcha();
                break;
            case R.id.tv_reset:
                startNewAct(ServerSettingsActivity.class);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.check_is_remember:
                is_Remember = isChecked;
                PreferenceUtil.commitBoolean(UserInfo.ISREMEMBER, is_Remember);
                checkIsRemember.setSelected(is_Remember);
                break;
        }
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing())
            mLoadingDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ActivityUtils.isRunning(LoginActivity.this) && mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    closeLoadingDialog();
                }
            }
        }, 30000);//30秒后关闭
    }

    @Override
    public void closeLoadingDialog() {
        if (ActivityUtils.isRunning(LoginActivity.this)) {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) mLoadingDialog.dismiss();
        }
    }

    @Override
    public void setPassword(String password) {
        tvLoginPassword.setText(password);
    }

    @Override
    public void userName(String userName) {
        tvLoginUsername.setText(userName);
        tvLoginUsername.setSelection(userName.length());//将光标移至文字末尾
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.FinishMainActivity();
        finish();
    }

    public static void FinishLoginActivity(boolean mainIsExist) {
        if (loginActivity != null) {
            loginActivity.closeLoadingDialog();
            if (mainIsExist) {
                loginActivity.finish();
            }
        }
    }
}
