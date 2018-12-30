package com.soonfor.measuremanager.home.login.activity;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.tools.Countdown;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.ibt_back)
    ImageButton ibtBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.tv_forget_phone_number)
    EditText tvForgetPhoneNumber;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.bt_get_code)
    Button btGetCode;
    @BindView(R.id.edit_new_password)
    EditText editNewPassword;
    @BindView(R.id.edit_again_new_password)
    EditText editAgainNewPassword;
    @BindView(R.id.bt_change_password)
    Button btChangePassword;

    private Countdown mCountdown;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_forget_password;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        setHead(true, "忘记密码");
        btGetCode.setText(Html.fromHtml("<font color='#ff0000' size='29px'>获取验证码</font>"));
        mCountdown = new Countdown(60, btGetCode, this);
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {

    }

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    @Override
    protected void updateViews(boolean isRefresh) {

    }


    @OnClick({R.id.bt_get_code, R.id.bt_change_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get_code:
                mCountdown.setCountDown();
                break;
            case R.id.bt_change_password:
                break;
        }
    }
}
