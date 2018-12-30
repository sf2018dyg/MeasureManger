package com.soonfor.measuremanager.home.complexruler.presenter.detail;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.complexruler.fragment.detail.ComplexRulerFragment;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 11:03
 * 邮箱：suibozhu@139.com
 */

public class FuChiComplexRulerBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private ComplexRulerFragment bgFragment;
    private Gson gson;


    public void getTaskCompleteInfo(String taskNo, String taskType, String orderNo) {
        bgFragment.mLoadingDialog.show();
        Request.getTaskCompleteInfo(bgFragment.getContext(), this, taskNo, taskType, orderNo);
    }


    public FuChiComplexRulerBasePresenter(ComplexRulerFragment bgFragment) {
        this.bgFragment = bgFragment;
        gson = new Gson();
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        switch (requestCode) {
            case Request.GET_TASK_COMPLETE_INFO:
                bgFragment.closeLoadingDialog();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        bgFragment.showDataToView(data);
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
    }
}
