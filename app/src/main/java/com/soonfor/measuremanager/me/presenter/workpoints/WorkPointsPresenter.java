package com.soonfor.measuremanager.me.presenter.workpoints;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.WorkPointsCenterActivity;
import com.soonfor.measuremanager.me.bean.WorkPointsBean;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.LogTools;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class WorkPointsPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private WorkPointsCenterActivity mContext;

    public WorkPointsPresenter(WorkPointsCenterActivity activity) {
        mContext = activity;
//        Request.getWorkPoints(mContext,this);
    }

    @Override
    public void success(int requestCode, final JSONObject object) {
        switch (requestCode) {
            case Request.GET_WORK_POINTS:
                final Gson gson = new Gson();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            WorkPointsBean bean = gson.fromJson(data, WorkPointsBean.class);
                            LogTools.showLog(mContext, object.toString());
                        } catch (Exception e) {
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        mContext.closeLoadingDialog();
    }
}
