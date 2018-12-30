package com.soonfor.evaluate.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.evaluate.activity.Evaluate_IToCustomersDetailActivity;
import com.soonfor.evaluate.bean.Evaluate_IToCustomersDetailBean;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.json.JSONObject;

/**
 * 作者：DC-DingYG on 2018-10-18 11:29
 * 邮箱：dingyg012655@126.com
 * 类用途：
 */
public class Evaluate_IToCustomersDetailPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private Evaluate_IToCustomersDetailActivity view;
    private Context mContext;

    public Evaluate_IToCustomersDetailPresenter(Evaluate_IToCustomersDetailActivity view) {
        this.view = view;
    }

    public void getDetail(Context mContext, String _id) {
        this.mContext = mContext;
        Request.Evaluate.getIToCustomerDetailInfo(mContext, _id, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.Evaluate.GET_EVALUATE_ITOCUSTOMERINFO:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        view.showViewByData(null, msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            Evaluate_IToCustomersDetailBean detailBean = gson.fromJson(data, Evaluate_IToCustomersDetailBean.class);
                            view.showViewByData(detailBean, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.Evaluate.GET_EVALUATE_ITOCUSTOMERINFO:
                view.showNoDataHint(errorMsg);
                break;
        }
    }
}