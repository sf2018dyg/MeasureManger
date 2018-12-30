package com.soonfor.measuremanager.home.lofting.presenter.detail;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.lofting.fragment.detail.LoftingInfoFragment;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Mark.MarkResultEntity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by DingYg on 2018-01-31.
 */

public class LoftingInfoPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private LoftingInfoFragment bgFragment;
    private Gson gson;

    /**
     * 获取网络数据，更新界面
     *
     * @param isRefresh 是否是swiperefresh的
     */
    public void getData(boolean isRefresh) {
    }

    public void getData(String taskNo, String taskType, String orderNo) {
        this.bgFragment.showLoadingDialog();
        Request.Loft.getTaskDetailInfo(bgFragment.getContext(), this, taskNo, taskType, orderNo, Request.Loft.GET_TASK_DETAIL_LOFT_LOFTING);
    }

    public LoftingInfoPresenter(LoftingInfoFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    //切换户型后刷新数据
    public void refreshView(MarkResultEntity resultEntity) {
        bgFragment.initFragment(resultEntity);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.Loft.GET_TASK_DETAIL_LOFT_LOFTING:
                bgFragment.closeLoadingDialog();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.showNoDataHint(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        bgFragment.showDataToView(data);
                    }
                });
                //bgFragment.showDataToView(DataTools.getJiashuju());
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.Loft.GET_TASK_DETAIL_LOFT_LOFTING:
                bgFragment.closeLoadingDialog();
                bgFragment.showNoDataHint("请求出错啦");
                break;
        }
    }
}
