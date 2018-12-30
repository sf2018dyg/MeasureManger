package com.soonfor.evaluate.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.evaluate.bean.Evaluate_IToCustomersBean;
import com.soonfor.evaluate.activity.fragment.Evaluated_IToCustomersFragment;
import com.soonfor.evaluate.activity.fragment.UnEvaluate_IToCustomersFragment;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-18 11:29
 * 邮箱：dingyg012655@126.com
 * 类用途：
 */
public class Evaluate_IToCustomersPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private Activity mActivity;
    private Evaluated_IToCustomersFragment yFragment;
    private UnEvaluate_IToCustomersFragment nFragment;

    public Evaluate_IToCustomersPresenter(Evaluated_IToCustomersFragment yFragment, Activity mActivity) {
        this.yFragment = yFragment;
        this.mActivity = mActivity;
    }

    public Evaluate_IToCustomersPresenter(UnEvaluate_IToCustomersFragment nFragment, Activity mActivity) {
        this.nFragment = nFragment;
        this.mActivity = mActivity;
    }

    /**
     * 获取评价列表
     *
     * @param type
     * @param pageNo
     */
    public void getEvaluatedList(int type, int pageNo) {
        if (type == 0) {
            Request.Evaluate.getIToCustomerList(nFragment.getContext(), type, pageNo, this);
        } else if (type == 1) {
            Request.Evaluate.getIToCustomerList(yFragment.getContext(), type, pageNo, this);
        }
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.Evaluate.GET_EVALUATE_ITOCUSTOMER_Y:
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
                            List<Evaluate_IToCustomersBean> mLists = new ArrayList<>();
                            if (ja != null && ja.length() > 0) {
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject ja_o = ja.getJSONObject(i);
                                    Evaluate_IToCustomersBean mBean = gson.fromJson(ja_o.toString(), Evaluate_IToCustomersBean.class);
                                    mLists.add(mBean);
                                }
                            }
                            yFragment.showYList(true, pageBean, mLists, null);
                        } catch (Exception e) {
                            yFragment.showYList(false, null, null, "请求出错");
                        }
                        yFragment.closeLoadingDialog();
                    }
                });
                break;
            case Request.Evaluate.GET_EVALUATE_ITOCUSTOMER_N:
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
                            List<Evaluate_IToCustomersBean> mLists = new ArrayList<>();
                            if (ja != null && ja.length() > 0) {
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject ja_o = ja.getJSONObject(i);
                                    Evaluate_IToCustomersBean mBean = gson.fromJson(ja_o.toString(), Evaluate_IToCustomersBean.class);
                                    mLists.add(mBean);
                                }
                            }
                            nFragment.showNList(true, pageBean, mLists, null);
                        } catch (Exception e) {
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
            case Request.Evaluate.GET_EVALUATE_ITOCUSTOMER_Y:
                yFragment.showYList(false, null, null, "请求出错");
                yFragment.closeLoadingDialog();
                break;
            case Request.Evaluate.GET_EVALUATE_ITOCUSTOMER_N:
                nFragment.showNList(false, null, null, "请求出错");
                nFragment.closeLoadingDialog();
                break;
        }
    }

    /**
     * 转为其它任务对象
     */
    public OtherTaskMainBean convertToOtherTaskbean(Evaluate_IToCustomersBean goBean) {
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