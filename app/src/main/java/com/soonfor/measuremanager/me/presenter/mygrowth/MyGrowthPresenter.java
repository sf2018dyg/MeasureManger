package com.soonfor.measuremanager.me.presenter.mygrowth;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.MyGrowthActivity;
import com.soonfor.measuremanager.me.bean.MyGrowthBean;
import com.soonfor.measuremanager.me.bean.MyGrowthItemBean;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class MyGrowthPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private MyGrowthActivity activity;
    private Gson gson;

    public MyGrowthPresenter(MyGrowthActivity activity) {
        this.activity = activity;
        activity.mLoadingDialog.show();
        Request.getMygrowthValue(activity, this);
        Request.getGrowthItem(activity, this, 1, 10);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        activity.mLoadingDialog.dismiss();
        NLogger.e("成长值", object.toString());
        switch (requestCode) {
            case Request.GET_MY_GROWTH_VALUE:
                gson = new Gson();
                MyGrowthBean growthBean = gson.fromJson(object.toString(), MyGrowthBean.class);
                activity.setData(growthBean);
                break;
            case Request.GET_GROWTH_ITEM:
                gson = new Gson();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            MyGrowthItemBean itemBean = gson.fromJson(data, MyGrowthItemBean.class);
                            activity.setList(itemBean);
                        } catch (Exception e) {
                        }
                    }
                });

                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        activity.closeLoadingDialog();
        ;
    }
}
