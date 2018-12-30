package com.soonfor.evaluate.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.evaluate.activity.EvaluateCustomersActivity;
import com.soonfor.evaluate.bean.EvalCustSaveBean;
import com.soonfor.evaluate.bean.EvaluateTemplateBean;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.json.JSONObject;

/**
 * 作者：DC-DingYG on 2018-10-19 14:29
 * 邮箱：dingyg012655@126.com
 */
public class EvaluateCustomersPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    EvaluateCustomersActivity mActivity;

    public EvaluateCustomersPresenter(EvaluateCustomersActivity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 获取评价模板
     */
    public void getEvaluateTemplateBean(Context cxt, String _id) {
        Request.Evaluate.getEvaluateTemplate(cxt, Request.Evaluate.POST_GETEVALUATETEMP, _id, this);
    }

    /**
     * 评价客户（保存）
     */
    public void saveEvalCustomer(Context cxt, EvalCustSaveBean saveBean) {
        String jsonprams = new Gson().toJson(saveBean);
        Request.Evaluate.saveEvaluateCustomer(cxt, Request.Evaluate.POST_SAVEEVALCUSTOMER, jsonprams, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.Evaluate.POST_GETEVALUATETEMP:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        mActivity.showViewByData(false, null);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        EvaluateTemplateBean templateBean = new Gson().fromJson(data, EvaluateTemplateBean.class);
                        if (templateBean != null) {
                            mActivity.showViewByData(true, templateBean);
                        } else {
                            mActivity.showViewByData(false, templateBean);
                        }
                    }
                });
                break;
            case Request.Evaluate.POST_SAVEEVALCUSTOMER:
                HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null) {
                    if (headBean.getMsgcode() == 0) {
                        mActivity.refreshAfterSave(true, headBean.getData());
                    } else {
                        mActivity.refreshAfterSave(false, headBean.getFaileMsg());
                    }
                } else {
                    mActivity.refreshAfterSave(false, "保存失败");
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.Evaluate.POST_GETEVALUATETEMP:
                mActivity.showViewByData(false, null);
                break;
            case Request.Evaluate.POST_SAVEEVALCUSTOMER:
                mActivity.refreshAfterSave(false, "保存失败");
                break;
        }
    }
}
