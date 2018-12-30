package com.soonfor.measuremanager.me.presenter.performance;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.MyPerformanceActivity;
import com.soonfor.measuremanager.me.bean.MyPerformanceBean;
import com.soonfor.measuremanager.me.bean.PerformanceBean;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class PerformancePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private MyPerformanceActivity activity;
    private Gson gson;

    public PerformancePresenter(MyPerformanceActivity activity) {
        this.activity = activity;
        activity.mLoadingDialog.show();
        Request.getTotalPerformance(activity, this);
        Request.getMonthPerformance(activity, 1, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        activity.mLoadingDialog.dismiss();
        switch (requestCode) {
            case Request.GET_TOTAL_PERFORMANCE:
                gson = new Gson();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {}

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            MyPerformanceBean bean = gson.fromJson(data, MyPerformanceBean.class);
                            activity.setData(bean);
                        } catch (Exception e) {
                        }
                    }
                });
                break;
            case Request.GET_MONTH_PERFORMANCE:
                gson = new Gson();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            PerformanceBean bean1 = gson.fromJson(data, PerformanceBean.class);
                            activity.setListData(bean1.getData());
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
    }
}
