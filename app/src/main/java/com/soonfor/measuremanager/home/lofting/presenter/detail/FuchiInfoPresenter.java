package com.soonfor.measuremanager.home.lofting.presenter.detail;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.LookSurveyListActivity;
import com.soonfor.measuremanager.home.lofting.fragment.detail.FuchiInfoFragment;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by DingYg on 2018-01-31.
 */

public class FuchiInfoPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private LookSurveyListActivity lookslActivity;
    private FuchiInfoFragment bgFragment;
    private Gson gson;
    private String orderNo;

    public void getFuchiTaskNoByOrderNo(Context mContext, int type, String orderNo) {
        this.orderNo = orderNo;
        switch (type) {//1、从复尺信息Fragment进入 2、从复尺清单Activity进入
            case 1:
                this.bgFragment.showLoadingDialog();
                Request.Loft.getRemeasureTasknoByOrderNo(bgFragment.getContext(), orderNo, this, Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_FRAGMENT);
                break;
            case 2:
                lookslActivity.showLoadingDialog();
                Request.Loft.getRemeasureTasknoByOrderNo(lookslActivity, orderNo, this, Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_ACTIVITY);
                break;
        }
    }

    public void getData(int type, String taskNo, String taskType, String orderNo, String fulist_contractNo) {
        switch (type) {//1、从复尺信息Fragment进入 2、从复尺清单Activity进入
            case 1:
                Request.Loft.getTaskDetailInfo(bgFragment.getContext(), this, taskNo, taskType, orderNo, Request.Loft.GET_TASK_DETAIL_LOFT_FUCHI_FRAGMENT);
                break;
            case 2:
                Request.getTaskCompleteInfoWithContractNo(lookslActivity, this, taskNo, taskType, orderNo, fulist_contractNo);
                break;
        }
    }

    public FuchiInfoPresenter(LookSurveyListActivity lookslActivity) {
        this.lookslActivity = lookslActivity;
    }

    public FuchiInfoPresenter(FuchiInfoFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        HeadBean headBean = null;
        switch (requestCode) {
            case Request.Loft.GET_TASK_DETAIL_LOFT_FUCHI_FRAGMENT:
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
            case Request.GET_TASK_COMPLETE_INFO:
                lookslActivity.closeLoadingDialog();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.showNoDataHint(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        lookslActivity.showDataToView(data);
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
                            //先判断复尺任务号是否为空
                            String reMeasureId = "";
                            try {
                                reMeasureId = CommonUtils.formatStr(jo.getString("remeasureId"));
                            } catch (Exception e) {
                            }
                            if (!reMeasureId.equals("")) {
                                newTaskNo = reMeasureId;
                            } else {
                                //再判断量尺是否为空
                                String measureId = "";
                                try {
                                    measureId = CommonUtils.formatStr(jo.getString("measureId"));
                                } catch (Exception e) {
                                }
                                if (!measureId.equals("")) {
                                    newTaskNo = measureId;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (newTaskNo.equals("")) {
                        if (bgFragment != null) {
                            //MyToast.showToast(bgFragment.getActivity(), "获取复尺任务号失败");
                            bgFragment.closeLoadingDialog();
                        }
                    } else {//取到了复尺或量尺任务号，开始请求数据
                        bgFragment.postDataWithFcTaskNo(isFuchi, newTaskNo);
                    }
                } else {
                    if (bgFragment != null) {
                        //MyToast.showToast(bgFragment.getActivity(), "获取复尺任务号失败");
                        bgFragment.closeLoadingDialog();
                    }
                }
                break;
            case Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_ACTIVITY:
                headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                    String newTaskNo = "";
                    boolean isFuchi = false;
                    try {
                        JSONObject jo = new JSONObject(headBean.getData());
                        if (jo != null) {
                            //先判断复尺任务号是否为空
                            String reMeasureId = "";
                            try {
                                reMeasureId = CommonUtils.formatStr(jo.getString("remeasureId"));
                            } catch (Exception e) {
                            }
                            if (!reMeasureId.equals("")) {
                                newTaskNo = reMeasureId;
                            } else {
                                //再判断量尺是否为空
                                String measureId = "";
                                try {
                                    measureId = CommonUtils.formatStr(jo.getString("measureId"));
                                } catch (Exception e) {
                                }
                                if (!measureId.equals("")) {
                                    newTaskNo = measureId;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (newTaskNo.equals("")) {
//                        if (lookslActivity != null) {
//                            MyToast.showToast(lookslActivity, "获取复尺任务号失败");
//                        }
                    } else {//取到了复尺或量尺任务号，开始请求数据
                        lookslActivity.postDataWithFcTaskNo(isFuchi, newTaskNo);
                    }
                } else {
//                    if (lookslActivity != null) {
//                        MyToast.showToast(lookslActivity, "获取复尺任务号失败");
//                    }
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
        switch (requestCode) {
            case Request.Loft.GET_TASK_DETAIL_LOFT_FUCHI_FRAGMENT:
                bgFragment.showNoDataHint("请求出错啦");
                bgFragment.closeLoadingDialog();
                break;
            case Request.Loft.GET_TASK_DETAIL_LOFT_FUCHI_ACTIVITY:
                lookslActivity.showNoDataHint("请求出错啦");
                lookslActivity.closeLoadingDialog();
                break;
            case Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_FRAGMENT:
                bgFragment.showNoDataHint("请求出错啦");
                bgFragment.closeLoadingDialog();
                break;
            case Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_ACTIVITY:
                lookslActivity.showNoDataHint("请求出错啦");
                lookslActivity.closeLoadingDialog();
                break;
        }
    }
}
