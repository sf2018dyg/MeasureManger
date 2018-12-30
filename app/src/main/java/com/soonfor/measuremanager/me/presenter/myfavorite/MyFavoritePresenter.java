package com.soonfor.measuremanager.me.presenter.myfavorite;

import android.app.Activity;

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

public class MyFavoritePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private IMyFavoriteView mfView;
    private Activity mActivity;

    public MyFavoritePresenter(IMyFavoriteView mfView, Activity mActivity) {
        this.mfView = mfView;
        this.mActivity = mActivity;
    }
    /**
     * 获取案例库列表
     *
     * @param pageIndex
     * @param isNeedLoadDialog 是否需要进度条
     */
    public void getFavroiteDatas(int type, int pageIndex, int pageSize, String inputText, boolean isNeedLoadDialog) {
        if(isNeedLoadDialog)
        mfView.showLoadingDialog();
        Request.Me.getMyFavroite(type, mActivity, pageIndex, pageSize, this);
    }

    /**
     * 获取关注列表
     */
    public void getAttentionDatas(int pageIndex, String inputText, boolean isRefresh) {
//        mfView.showLoadingDialog();
//        Request.Me.getMyAttentions(mActivity, this, PreferenceUtil.getString(UserInfo.USERNAME, ""), inputText);

        mfView.setListView(getGzjsj());
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.GET_MYFAVROITE_CASE://设计案例
                mfView.closeLoadingDialog();
                mfView.setListView(object.toString());
                break;
            case Request.GET_MYFAVROITE_HOT://设计热帖
                mfView.closeLoadingDialog();
                mfView.setListView(null);
                break;
            case Request.GET_MYATTENTION://我的关注
                mfView.closeLoadingDialog();
                break;

        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.GET_MYFAVROITE_CASE://设计案例
                mfView.closeLoadingDialog();
                mfView.setListView(null);
                break;
            case Request.GET_MYFAVROITE_HOT://设计热帖
                mfView.closeLoadingDialog();
                mfView.setListView(null);
                break;
            case Request.GET_MYATTENTION://我的关注
                mfView.closeLoadingDialog();
                break;
        }
    }


    public String getGzjsj() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("msgcode", "0");
            jo.put("msg", "成功");
            JSONArray ja = new JSONArray();
            for (int i = 0; i < 4; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nickName", "FuYUIOnG" + (i + 1));
                jsonObject.put("post", "资深设计师");
                jsonObject.put("avatar", "");
                jsonObject.put("phone", "18888888888");
                jsonObject.put("birthday", "1987-01-01");
                jsonObject.put("sex", "男");
                jsonObject.put("shop", "广州|梦天旗舰店");
                jsonObject.put("design_experience", "10年设计经验");
                jsonObject.put("fens", 12);
                ja.put(i, jsonObject);
            }
            jo.put("data", ja.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }
}
