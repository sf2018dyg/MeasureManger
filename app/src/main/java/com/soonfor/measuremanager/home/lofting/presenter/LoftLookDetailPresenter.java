package com.soonfor.measuremanager.home.lofting.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.headBean;
import com.soonfor.measuremanager.home.lofting.activity.immloft.LoftLookDetailActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.WallPath;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYg on 2018-02-11.
 */

public class LoftLookDetailPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private LoftLookDetailActivity detailActivity;//复尺详情
    private String orderNo;//订单号

    /**
     * 放样详情
     *
     * @param detailActivity
     */
    public LoftLookDetailPresenter(LoftLookDetailActivity detailActivity) {
        this.detailActivity = detailActivity;
    }
    /**
     * 根据订单号，获取复尺任务号
     */
    public void getRemeasureTaskNoByOrderNo(String orderNo, String taskNo) {
        this.orderNo = orderNo;
        this.detailActivity.showLoadingDialog();
        Request.Loft.getRemeasureTasknoByOrderNo(detailActivity, orderNo, this, Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_ACTIVITY);
    }

    /**
     * 请求户型放样清单(当前户型所有墙面清单)
     */
    public void getFyList(String taskno, String sContractNo){
        Request.Loft.getLoftingList(detailActivity, 1, taskno, sContractNo, "", this);
        //detailActivity.setListdata(DataTools.getJiashuju());
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.Loft.GET_FUCHI_TASKNO_BY_ORDERNO_ACTIVITY://获取量尺、复尺任务id
                HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                    String newTaskNo = "";
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
                    detailActivity.getAllWallList(newTaskNo);

                }
                break;
            case Request.Loft.GET_LOFTING_LIST_1:
                HeadBean headBean2 = JsonUtils.getHeadBean(object.toString());
                if (headBean2 != null && headBean2.getMsgcode() == 0) {
                    detailActivity.setListdata(headBean2.getData());
                }else {
                    detailActivity.initview();
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        detailActivity.closeLoadingDialog();
        detailActivity.initview();
    }

}
