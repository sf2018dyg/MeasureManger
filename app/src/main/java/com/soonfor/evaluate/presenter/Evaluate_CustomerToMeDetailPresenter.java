package com.soonfor.evaluate.presenter;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.evaluate.activity.Evaluate_CustomersToMeDetailActivity;
import com.soonfor.evaluate.bean.Evaluate_CustomersToMeDetailBean;
import com.soonfor.evaluate.bean.ReturnVisitBean;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-18 11:29
 * 邮箱：dingyg012655@126.com
 */
public class Evaluate_CustomerToMeDetailPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private Evaluate_CustomersToMeDetailActivity detailActivity;

    public Evaluate_CustomerToMeDetailPresenter(Evaluate_CustomersToMeDetailActivity detailActivity) {
        this.detailActivity = detailActivity;
    }

    public void getData(boolean isRefresh, String _id, String reVisitid) {
        //请求评价详情信息
        Request.Evaluate.getEvaluateToMeDetailInfo(detailActivity.getBaseContext(), _id,this);
        //请求人工回访问题列表
        Request.Evaluate.getReturnVisitList(detailActivity.getBaseContext(), reVisitid, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode){
            case Request.Evaluate.GET_EVALUATETOMEDETAILINFO://获取详情
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        detailActivity.setGetDetail(false, null);
                    }
                    @Override
                    public void doingInSuccess(String data) {
                        Evaluate_CustomersToMeDetailBean detailBean = gson.fromJson(data, Evaluate_CustomersToMeDetailBean.class);
                        detailActivity.setGetDetail(true, detailBean);
                    }
                });
                break;
            case Request.Evaluate.GET_QUESTIONNAIRE_RESULT://获取人工回访问题列表
                HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null) {
                    if(headBean.getMsgcode() == 0 || headBean.isSuccess()) {
                        List<ReturnVisitBean> rvists = new ArrayList<>();
                        try {
                            JSONArray ja = new JSONArray(headBean.getData());
                            if(ja!=null && ja.length()>0){
                                for(int i=0; i<ja.length(); i++){
                                    ReturnVisitBean visitBean = gson.fromJson(ja.getJSONObject(i).toString(), ReturnVisitBean.class);
                                    rvists.add(visitBean);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        detailActivity.setGetReturnVisit( true, rvists);}
                }else {
                    detailActivity.setGetReturnVisit(false, null);
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode){
            case Request.Evaluate.GET_EVALUATETOMEDETAILINFO://获取详情
                detailActivity.setGetDetail(false, null);
                break;
            case Request.Evaluate.GET_QUESTIONNAIRE_RESULT://获取人工回访问题列表
                detailActivity.setGetReturnVisit(false, null);
                break;
        }
    }
}
