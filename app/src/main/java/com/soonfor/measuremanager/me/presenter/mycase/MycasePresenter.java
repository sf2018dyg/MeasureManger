package com.soonfor.measuremanager.me.presenter.mycase;

import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.MycaseActivity;
import com.soonfor.measuremanager.tools.PreferenceUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MycasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private MycaseActivity mcActivity;

    public MycasePresenter(MycaseActivity mcActivity) {
        this.mcActivity = mcActivity;
    }


    public void getCaseDatas(MycaseActivity mcActivity, int pageIndex, String inputText, boolean isRefresh){
        if(!isRefresh) this.mcActivity = mcActivity;
//        mcActivity.showLoadingDialog();
//        Request.Me.getMyCases(mcActivity, this, PreferenceUtil.getString(UserInfo.USERNAME, ""), inputText);
        mcActivity.setListView(null,inputText);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode){
            case Request.GET_MYCASE:
                mcActivity.closeLoadingDialog();
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode){
            case Request.GET_MYCASE:
                mcActivity.closeLoadingDialog();
                break;
        }
    }
}
