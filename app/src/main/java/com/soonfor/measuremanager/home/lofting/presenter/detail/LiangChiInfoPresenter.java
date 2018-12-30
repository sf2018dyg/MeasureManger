package com.soonfor.measuremanager.home.lofting.presenter.detail;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.lofting.fragment.detail.LiangchiInfoFragment;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

public class LiangChiInfoPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private LiangchiInfoFragment bgFragment;
    private Gson gson;
    private String orderNo;

    public void getData(String taskNo, String taskType, String orderNo) {
        //this.bgFragment.showLoadingDialog();
        Request.Loft.getTaskDetailInfo(bgFragment.getContext(), this, taskNo, taskType, orderNo, Request.Loft.GET_TASK_DETAIL_LOFT_LIANGCHI);
    }

    public void getLiangchiTaskNoByOrderNo(String orderNo) {
        this.orderNo = orderNo;
        this.bgFragment.showLoadingDialog();
        Request.Loft.getRemeasureTasknoByOrderNo(bgFragment.getContext(), orderNo, this, Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_FRAGMENT);
    }

    public LiangChiInfoPresenter(LiangchiInfoFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        HeadBean headBean = null;
        switch (requestCode) {
            case Request.Loft.GET_TASK_DETAIL_LOFT_LIANGCHI:
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
                break;
            case Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_FRAGMENT:
                headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                    String newTaskNo = "";
                    boolean isFuchi = false;
                    try {
                        JSONObject jo = new JSONObject(headBean.getData());
                        if (jo != null) {
                            //判断量尺是否为空
                            String measureId = "";
                            try {
                                measureId = CommonUtils.formatStr(jo.getString("measureId"));
                            } catch (Exception e) {
                            }
                            if (!measureId.equals("")) {
                                newTaskNo = measureId;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (newTaskNo.equals("")) {
                        if (bgFragment != null) {
                            bgFragment.closeLoadingDialog();
                            bgFragment.showNoDataHint("获取量尺任务号失败");
                        }
                    } else {//取到了复尺或量尺任务号，开始请求数据
                        bgFragment.postDataWithLcTaskNo(newTaskNo);
                    }
                } else {
                    if (bgFragment != null) {
                        bgFragment.closeLoadingDialog();
                        bgFragment.showNoDataHint("获取量尺任务号失败");
                    }
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.Loft.GET_TASK_DETAIL_LOFT_FUCHI_FRAGMENT:
                bgFragment.closeLoadingDialog();
                bgFragment.showNoDataHint("请求出错啦");
                break;
            case Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_FRAGMENT:
                bgFragment.closeLoadingDialog();
                break;
        }
    }
}
