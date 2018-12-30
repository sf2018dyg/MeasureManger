package com.soonfor.measuremanager.home.design.presenter.detail;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.design.fragment.detail.DesignPlanFragment;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

public class DesignPlanPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private DesignPlanFragment bgFragment;
    private Gson gson;


    public void getDatas(String taskNo, String orderNo) {
        this.bgFragment.showLoadingDialog();
        Request.getTaskCompleteInfo(bgFragment.getContext(), this, taskNo, "design", orderNo);
    }


    public DesignPlanPresenter(DesignPlanFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        switch (requestCode) {
            case Request.GET_TASK_COMPLETE_INFO:
                HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null && headBean.getMsgcode() == 0 && headBean.getData() != null) {
                    bgFragment.setListView(headBean.getData());
                }
//                else {
//                    MyToast.showFailToast(mContext, "请求出错，" + bean.getFaileMsg() + "！");
//                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
    }
}