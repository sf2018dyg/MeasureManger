package com.soonfor.measuremanager.me.presenter.monthperformance;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.MonthPerformanceActivity;
import com.soonfor.measuremanager.me.bean.PerformanceBean;
import com.soonfor.measuremanager.tools.LogTools;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class MonthPerformancePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private MonthPerformanceActivity activity;

    public MonthPerformancePresenter(MonthPerformanceActivity activity,String monthType) {
        this.activity = activity;
        activity.mLoadingDialog.show();
        Request.getHistoryPerformance(activity,monthType,1,this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        activity.mLoadingDialog.dismiss();
        switch (requestCode){
            case Request.GET_HISTORY_PERFORMANCE:
                LogTools.showLog(activity,object.toString());
                Gson gson = new Gson();
                PerformanceBean bean = gson.fromJson(object.toString(),PerformanceBean.class);
                activity.setListData(bean.getData());
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        activity.closeLoadingDialog();
    }
}
