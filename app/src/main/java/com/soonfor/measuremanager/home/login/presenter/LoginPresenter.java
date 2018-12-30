package com.soonfor.measuremanager.home.login.presenter;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.homepage.presenter.HomePagePresenter;
import com.soonfor.measuremanager.home.login.activity.LoginActivity;
import com.soonfor.measuremanager.home.login.bean.CurrentUserBean;
import com.soonfor.measuremanager.home.login.bean.LoginBean;
import com.soonfor.measuremanager.home.login.bean.PublicKeyBean;
import com.soonfor.measuremanager.home.main.MainActivity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.Base64Utils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.tools.RSAUtils;
import com.soonfor.repository.tools.ActivityUtils;

import org.json.JSONObject;

import java.security.PublicKey;

import cn.jesse.nativelogger.NLogger;

import static com.soonfor.measuremanager.http.api.Request.sendGetKey;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class LoginPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private LoginActivity mLoginActivity;
    private String userName;
    private String password;
    private String merchantCode;
    private String validateCode;

    public LoginPresenter(LoginActivity loginActivity) {
        mLoginActivity = loginActivity;
        mLoginActivity.userName(PreferenceUtil.getString(UserInfo.USERNAME, ""));
        String password = PreferenceUtil.getString(UserInfo.PASSWORD, "");
        if(password.equals("")){
            mLoginActivity.is_Remember = false;
            PreferenceUtil.commitBoolean(UserInfo.ISREMEMBER, false);
        }else if(mLoginActivity.is_Remember){
            mLoginActivity.setPassword(password);
        }else {
            PreferenceUtil.removeKey(UserInfo.PASSWORD);
            mLoginActivity.setPassword("");
        }
    }


    public void needCaptcha() {
        mLoginActivity.showLoadingDialog();
        Request.getNeedCaptcha(mLoginActivity, this);
    }

    public void getKey(EditText tvLoginUsername, EditText tvLoginPassword, EditText tvValdate, boolean isValidateShow, String loginMerchantCode) {
        userName = tvLoginUsername.getText().toString();
        password = tvLoginPassword.getText().toString();
        merchantCode = loginMerchantCode;
        validateCode = tvValdate.getText().toString();
        if (userName.isEmpty()) {
            MyToast.showCaveatToast(mLoginActivity, "请输入用户名");
            mLoginActivity.closeLoadingDialog();
            return;
        }
        if (password.isEmpty()) {
            MyToast.showCaveatToast(mLoginActivity, "请输入用密码");
            mLoginActivity.closeLoadingDialog();
            return;
        }
        if (merchantCode.isEmpty()) {
            MyToast.showCaveatToast(mLoginActivity, "请输入商户号");
            mLoginActivity.closeLoadingDialog();
            return;
        }
        if (isValidateShow && validateCode.isEmpty()) {
            Toast.makeText(mLoginActivity, "请输入验证码", Toast.LENGTH_SHORT).show();
            mLoginActivity.closeLoadingDialog();
            return;
        }
        sendGetKey(mLoginActivity, merchantCode, userName, this);
    }

    //请求当前登录用户的信息
    public void getCurrentUserInfo(Context mContext){
        Request.getCurrentUser(mContext, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        //LogTools.showLog(mLoginActivity,object.toString());
        final Gson gson = new Gson();
        switch (requestCode) {
            case Request.GET_KEY:
                PublicKeyBean keyBean = gson.fromJson(object.toString(), PublicKeyBean.class);
                if (keyBean.isSuccess()) {
                    PublicKey publicKey = null;
                    try {
                        publicKey = RSAUtils.loadPublicKey(keyBean.getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 加密
                    byte[] encryptByte = RSAUtils.encryptData(password.getBytes(), publicKey);
                    // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
                    String afterPassword = Base64Utils.encode(encryptByte);
                    Request.sendLogin(mLoginActivity, merchantCode, userName, validateCode, afterPassword, 2, 1, this);
                } else {
                    mLoginActivity.closeLoadingDialog();
                    MyToast.showCaveatToast(mLoginActivity, keyBean.getData().toString());
                }
                break;
            case Request.LOGIN:
                LoginBean loginBean = gson.fromJson(object.toString(), LoginBean.class);
                if (loginBean.isSuccess()) {
                    PreferenceUtil.commitString(UserInfo.UUID, loginBean.getData());
                    PreferenceUtil.commitString(UserInfo.USERNAME, userName);
                    if (mLoginActivity.is_Remember) {
                        PreferenceUtil.commitString(UserInfo.PASSWORD, password);
                    } else {
                        PreferenceUtil.removeKey(UserInfo.PASSWORD);
                    }
                    //请求当前用户信息数据
                    getCurrentUserInfo(mLoginActivity);
                } else {
                    MyToast.showCaveatToast(mLoginActivity, loginBean.getData());
                    mLoginActivity.closeLoadingDialog();
                    if (loginBean.getData().equals("验证码已过期，请重新获取") || loginBean.getData().equals("验证码错误")) {
                        mLoginActivity.showValidate(true);
                    }
                }
                break;
            case Request.NEEDCAPTCHA:
                try {
                    HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                    if (headBean != null && headBean.getMsgcode() == 0 && headBean.getData().toLowerCase().equals("true")) {
                        mLoginActivity.showValidate(true);
                    } else {
                        mLoginActivity.showValidate(false);
                    }
                } catch (Exception e) {
                    mLoginActivity.showValidate(false);
                }
                break;
            case Request.GET_CURRENT_USER:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        MyToast.showFailToast(mLoginActivity, msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            CurrentUserBean cUserBean = gson.fromJson(data, new TypeToken<CurrentUserBean>() {
                            }.getType());
                            PreferenceUtil.commitCurrentUserBean(cUserBean);
                        } catch (Exception e) {}
                        if(PreferenceUtil.getCurrentUserBean()!=null){
                            //登录
                            mLoginActivity.startNewAct(MainActivity.class);
                        }else {
                            MyToast.showFailToast(mLoginActivity, "获取当前用户信息失败");
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        mLoginActivity.closeLoadingDialog();
//        if(requestCode == Request.GET_CURRENT_USER){
//            view.afterPostCurrentUser(false, "无法查询当前用户信息");
//        }else
        if (ActivityUtils.isRunning(mLoginActivity)) {
            MyToast.showFailToast(mLoginActivity, errorMsg);
        }
    }
}
