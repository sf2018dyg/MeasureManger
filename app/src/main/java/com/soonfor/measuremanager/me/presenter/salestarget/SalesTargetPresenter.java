package com.soonfor.measuremanager.me.presenter.salestarget;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.bean.SaleTargetBean;
import com.soonfor.measuremanager.me.fragment.SalesTargetFragment;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class SalesTargetPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private SalesTargetFragment fragment;

    public SalesTargetPresenter(SalesTargetFragment fragment, int index) {
        this.fragment = fragment;
        Request.getSaleTarget(fragment.getContext(), index + 1, this);
    }

    @Override
    public void success(int requestCode, final JSONObject object) {
        fragment.mLoadingDialog.dismiss();
        switch (requestCode) {
            case Request.GET_SALE_TARGET:
                final Gson gson = new Gson();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            SaleTargetBean bean = gson.fromJson(data, SaleTargetBean.class);
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
