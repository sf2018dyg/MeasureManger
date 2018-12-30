package com.soonfor.measuremanager.home.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.login.presenter.LoginPresenter;
import com.soonfor.measuremanager.home.login.presenter.StartPresenter;
import com.soonfor.measuremanager.home.main.MainActivity;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.me.activity.SettingActivity;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * 作者：DC-DingYG on 2018-09-13 19:16
 * 邮箱：dingyg012655@126.com
 */
public class StartActivity extends BaseActivity<StartPresenter> {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_start;
    }

    @Override
    protected void initPresenter() {
        presenter = new StartPresenter(StartActivity.this);
    }

    @Override
    protected void initViews() {
        if(Hawk.get(SoonforApplication.ServerAdr, "").equals("")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startNewAct(LoginActivity.class);
                    finish();
                }
            },500);
        }else {
            String userName = PreferenceUtil.getString(UserInfo.USERNAME, "");
            String password = PreferenceUtil.getString(UserInfo.PASSWORD, "");
            if(!userName.equals("") && !password.equals("")){
                actionName = "自动登录中...";
                showLoadingDialog();
                presenter.getKey(userName, password, Hawk.get(SoonforApplication.MerchantCode, ""));
            }else {
                startNewAct(LoginActivity.class);
                finish();
            }
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {}


    //自动登录后
    public void afterAutoLogin(boolean isSuccess){
        if(isSuccess){
            presenter.getCurrentUserInfo(StartActivity.this);
        }else {
            closeLoadingDialog();
            startNewAct(LoginActivity.class);
            finish();
        }
    }
}
