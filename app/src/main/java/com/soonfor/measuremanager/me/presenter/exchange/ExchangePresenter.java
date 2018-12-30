package com.soonfor.measuremanager.me.presenter.exchange;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.bean.WorkPointsBean;
import com.soonfor.measuremanager.me.fragment.ExchangeFragment;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class ExchangePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private ExchangeFragment fragment;
    private Context mContext;

    public ExchangePresenter(ExchangeFragment fragment) {
        this.fragment = fragment;
        mContext = this.fragment.getContext();
        fragment.showLoadingDialog();
        Request.getWorkPoints(mContext, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        fragment.closeLoadingDialog();
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
                            fragment.setData(bean);
                        } catch (Exception e) {
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        fragment.closeLoadingDialog();
    }
}
