package com.soonfor.evaluate.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.design.model.DesignItemBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.evaluate.bean.Evaluate_CustomersToMeBean;
import com.soonfor.evaluate.activity.fragment.Evaluated_CustomersToMeFragment;
import com.soonfor.evaluate.activity.fragment.UnEvaluate_CustomersToMeFragment;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-17 15:20
 * 邮箱：dingyg012655@126.com
 */
public class Evaluate_CustomersToMePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private Activity mActivity;
    private Evaluated_CustomersToMeFragment yFragment;
    private UnEvaluate_CustomersToMeFragment nFragment;


    public Evaluate_CustomersToMePresenter(Evaluated_CustomersToMeFragment yFragment, Activity mActivity) {
        this.yFragment = yFragment;
        this.mActivity = mActivity;
    }

    public Evaluate_CustomersToMePresenter(UnEvaluate_CustomersToMeFragment nFragment, Activity mActivity) {
        this.nFragment = nFragment;
        this.mActivity = mActivity;
    }

    //获取已评价列表
    public void getY_EvaluatedList(int pageNo) {
        Request.Evaluate.getEvaluateToMeList(yFragment.getContext(), pageNo, 1, this);
    }

    //获取待评价列表
    public void getN_EvaluatedList(int pageNo) {
        Request.Evaluate.getEvaluateToMeList(nFragment.getContext(), pageNo, 0, this);
    }


    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.Evaluate.GET_EVALUATETOMELIST_Y:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        yFragment.closeLoadingDialog();
                        yFragment.showYList(false, null, null, msg);
                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject jo = new JSONObject(data);
                            PageTurnBean pageBean = JsonUtils.getPageBean(jo.getString("pageTurn"));
                            JSONArray ja = new JSONArray(jo.getString("list"));
                            List<Evaluate_CustomersToMeBean> mLists = new ArrayList<>();
                            if(ja!=null && ja.length()>0){
                                for(int i=0; i<ja.length(); i++){
                                    JSONObject ja_o = ja.getJSONObject(i);
                                    Evaluate_CustomersToMeBean mBean = gson.fromJson(ja_o.toString(), Evaluate_CustomersToMeBean.class);
                                    mLists.add(mBean);
                                }
                            }
                            yFragment.showYList(true, pageBean, mLists, "数据为空");
                        }catch (Exception e){
                            yFragment.showYList(false, null, null, "请求出错");
                        }
                        yFragment.closeLoadingDialog();
                    }
                });
                break;
            case Request.Evaluate.GET_EVALUATETOMELIST_N:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        nFragment.closeLoadingDialog();
                        nFragment.showNList(false, null, null, msg);
                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject jo = new JSONObject(data);
                            PageTurnBean pageBean = JsonUtils.getPageBean(jo.getString("pageTurn"));
                            JSONArray ja = new JSONArray(jo.getString("list"));
                            List<Evaluate_CustomersToMeBean> mLists = new ArrayList<>();
                            if(ja!=null && ja.length()>0){
                                for(int i=0; i<ja.length(); i++){
                                    JSONObject ja_o = ja.getJSONObject(i);
                                    Evaluate_CustomersToMeBean mBean = gson.fromJson(ja_o.toString(), Evaluate_CustomersToMeBean.class);
                                    mLists.add(mBean);
                                }
                            }
                            nFragment.showNList(true, pageBean, mLists, "数据为空");
                        }catch (Exception e){
                            nFragment.showNList(false, null, null, "请求出错");
                        }
                        nFragment.closeLoadingDialog();
                    }
                });
                break;
        }

    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.Evaluate.GET_EVALUATETOMELIST_Y:
               yFragment.showYList(false, null, null, errorMsg);
               yFragment.closeLoadingDialog();
                break;
            case Request.Evaluate.GET_EVALUATETOMELIST_N:
                nFragment.showNList(false, null, null, errorMsg);
                nFragment.closeLoadingDialog();
                break;
        }
    }
    /**
     * 转为量尺、复尺任务对象
     *
     * @param ectBean
     * @return
     */
    public LiangChiBean convertToLcbean(Evaluate_CustomersToMeBean ectBean) {
        if (ectBean != null) {
            LiangChiBean liangChiBean = new LiangChiBean();
            liangChiBean.setTaskNo(ectBean.getTaskno());
            liangChiBean.setTaskType(ectBean.getTasktype());
            liangChiBean.setOrderNo(ectBean.getOrderno());
            liangChiBean.setCustomerId(ectBean.getCustomerid());
            liangChiBean.setCustomerName(ectBean.getCustomername());
            liangChiBean.setCustomerSex(ectBean.getCustomersex());
            liangChiBean.setCustomerPhone(ectBean.getCustomerphone());
            liangChiBean.setAddress(ectBean.getAddress());
            liangChiBean.setStatus("4");
            return liangChiBean;
        } else {
            return null;
        }
    }

    /**
     * 转为放样任务对象
     */
    public LoftingMainBean convertToFybean(Evaluate_CustomersToMeBean ectBean) {
        if (ectBean != null) {
            LoftingMainBean loftingMainBean = new LoftingMainBean();
            loftingMainBean.setTaskNo(ectBean.getTaskno());
            loftingMainBean.setTaskType("mark");
            loftingMainBean.setOrderNo(ectBean.getOrderno());
            loftingMainBean.setCustomerId(ectBean.getCustomerid());
            loftingMainBean.setCustomerName(ectBean.getCustomername());
            loftingMainBean.setCustomerSex(ectBean.getCustomersex());
            loftingMainBean.setCustomerPhone(ectBean.getCustomerphone());
            loftingMainBean.setAddress(ectBean.getAddress());
            loftingMainBean.setStatus("4");
            return loftingMainBean;
        } else {
            return null;
        }
    }

    /**
     * 转为设计任务对象
     */
    public DesignItemBean convertToDesignbean(Evaluate_CustomersToMeBean ectBean) {
        if (ectBean != null) {
            DesignItemBean design = new DesignItemBean();
            design.setTaskNo(ectBean.getTaskno());
            design.setTaskType(ectBean.getTasktype());
            design.setOrderNo(ectBean.getOrderno());
            design.setCustomerId(ectBean.getCustomerid());
            design.setCustomerName(ectBean.getCustomername());
            design.setCustomerSex(ectBean.getCustomersex());
            design.setCustomerMobile(ectBean.getCustomerphone());
            design.setAddress(ectBean.getAddress());
            design.setStatus("4");
            return design;
        } else return null;
    }

    /**
     * 转为其它任务对象
     */
    public OtherTaskMainBean convertToOtherTaskbean(Evaluate_CustomersToMeBean goBean) {
        if (goBean != null) {
            OtherTaskMainBean result = new OtherTaskMainBean();
            result.setTaskId(goBean.getTaskno());
            result.setTaskType(goBean.getTasktype());
            result.setOrderNo(goBean.getOrderno());
            result.setCustomerId(goBean.getCustomerid());
            result.setCustomerSex(goBean.getCustomersex());
            result.setCustomerName(goBean.getCustomername());
            result.setCustomerPhone(goBean.getCustomerphone());
            result.setAddress(goBean.getAddress());
            result.setStatus("3");
            result.setExectype(-1);
            return result;
        } else return null;
    }
}
