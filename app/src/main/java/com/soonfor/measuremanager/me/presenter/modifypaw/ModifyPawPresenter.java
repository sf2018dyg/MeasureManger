package com.soonfor.measuremanager.me.presenter.modifypaw;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.ModifyPawActivity;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class ModifyPawPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private ModifyPawActivity mContext;
    private String newPassword;

    public void ModifyPaw(String... pawss){
        mContext.showLoadingDialog();
        newPassword = pawss[1];
        Request.changePassword(mContext, pawss[0], pawss[1],this);
    }

    public ModifyPawPresenter(ModifyPawActivity activity) {
        mContext = activity;
    }


    @Override
    public void success(int requestCode, JSONObject object) {
        //LogTools.showLog(mContext,object.toString());
        mContext.closeLoadingDialog();
        switch (requestCode){
            case Request.CHANGE_PASSWORD:
                Gson gson = new Gson();
                HeadBean bean = gson.fromJson(object.toString(),HeadBean.class);
                if (bean.isSuccess()){
                    PreferenceUtil.commitString(UserInfo.PASSWORD, newPassword);
                    MyToast.showToast(mContext,"密码修改成功！");
                    mContext.finish();
                }else
                    MyToast.showCaveatToast(mContext,bean.getFaileMsg());
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        mContext.closeLoadingDialog();
        MyToast.showToast(mContext,"密码修改不成功！");
    }
}
