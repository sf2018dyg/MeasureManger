package com.soonfor.measuremanager.home.login.presenter;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.login.activity.LoginActivity;
import com.soonfor.measuremanager.home.login.activity.StartActivity;
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

import static com.soonfor.measuremanager.http.api.Request.sendGetKey;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class StartPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private StartActivity sActivity;
    private String userName;
    private String password;
    private String merchantCode;

    public StartPresenter(StartActivity sActivity) {
        this.sActivity = sActivity;
    }

    public void getKey(String userName, String password, String merchantCode) {
        this.userName = userName;
        this.password = password;
        this.merchantCode = merchantCode;
        sendGetKey(sActivity, merchantCode, userName, this);
    }
    //请求当前登录用户的信息
    public void getCurrentUserInfo(Context mContext){
        Request.getCurrentUser(mContext, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
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
                    Request.sendLogin(sActivity, merchantCode, userName, "", afterPassword, 2, 1, this);
                } else {
                    sActivity.afterAutoLogin(false);
                }
                break;
            case Request.LOGIN:
                LoginBean loginBean = gson.fromJson(object.toString(), LoginBean.class);
                if (loginBean.isSuccess()) {
                    PreferenceUtil.commitString(UserInfo.UUID, loginBean.getData());
                    PreferenceUtil.commitString(UserInfo.USERNAME, userName);
                    if(PreferenceUtil.getBoolean(UserInfo.ISREMEMBER, false)){
                        PreferenceUtil.commitString(UserInfo.PASSWORD, password);
                    }else {
                        PreferenceUtil.removeKey(UserInfo.PASSWORD);
                    }
                    sActivity.afterAutoLogin(true);
                } else {
                    sActivity.afterAutoLogin(false);
                }
                break;
            case Request.GET_CURRENT_USER:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        sActivity.afterAutoLogin(false);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            CurrentUserBean cUserBean = gson.fromJson(data, new TypeToken<CurrentUserBean>() {
                            }.getType());
                            PreferenceUtil.commitCurrentUserBean(cUserBean);
                        } catch (Exception e) {}
                        if(PreferenceUtil.getCurrentUserBean()!=null){
                            sActivity.closeLoadingDialog();
                            //登录
                            sActivity.startNewAct(MainActivity.class);
                            sActivity.finish();
                        }else {
                            sActivity.afterAutoLogin(false);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        MyToast.showFailToast(sActivity, errorMsg);
       sActivity.afterAutoLogin(false);
    }
}
