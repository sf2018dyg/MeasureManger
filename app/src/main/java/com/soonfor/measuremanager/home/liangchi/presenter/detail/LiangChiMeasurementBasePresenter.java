package com.soonfor.measuremanager.home.liangchi.presenter.detail;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.liangchi.fragment.detail.MeasurementFragment;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 11:03
 * 邮箱：suibozhu@139.com
 */

public class LiangChiMeasurementBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private MeasurementFragment bgFragment;
    private Gson gson;
    private String orderNo;


    /**
     * 根据orderNo获取量尺任务号
     *
     * @param orderNo
     */
    public void getLiangchiTaskNoByOrderNo(String orderNo) {
        this.orderNo = orderNo;
        this.bgFragment.showLoadingDialog();
        Request.Loft.getRemeasureTasknoByOrderNo(bgFragment.getContext(), orderNo, this, Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_FRAGMENT);
    }

    public void getTaskCompleteInfo(String taskNo, String taskType, String orderNo) {
        bgFragment.mLoadingDialog.show();
        Request.getTaskCompleteInfo(bgFragment.getContext(), this, taskNo, taskType, orderNo);
    }

    public LiangChiMeasurementBasePresenter(MeasurementFragment bgFragment) {
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
                        bgFragment.closeLoadingDialog();
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        bgFragment.showDataToView(data);
                    }
                });
                break;
            case Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_FRAGMENT:
                HeadBean headBean = JsonUtils.getHeadBean(object.toString());
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
                            MyToast.showToast(bgFragment.getActivity(), "获取量尺任务号失败");
                            bgFragment.closeLoadingDialog();
                        }
                    } else {//取到了复尺或量尺任务号，开始请求数据
                        bgFragment.postDataWithLcTaskNo(newTaskNo);
                    }
                } else {
                    if (bgFragment != null) {
                        MyToast.showToast(bgFragment.getActivity(), "获取量尺任务号失败");
                        bgFragment.closeLoadingDialog();
                    }
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
    }
}
