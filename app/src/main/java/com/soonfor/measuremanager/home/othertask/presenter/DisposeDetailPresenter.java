package com.soonfor.measuremanager.home.othertask.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.othertask.fragment.detail.DisposeDetailFragment;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.DisposeBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.evaluate.bean.ReturnVisitBean;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-DingYG on 2018-10-16 19:25
 * 邮箱：dingyg012655@126.com
 */
public class DisposeDetailPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private DisposeDetailFragment fragment;

    public DisposeDetailPresenter(DisposeDetailFragment fragment) {
        this.fragment = fragment;
    }

    //获取处理详情
    public void getOtherTaskResult(Context context, String taskid) {
        Request.OtherTask.getOtherTaskResult(fragment.getContext(), taskid, this);
    }
    //请求调查问卷数据
    public void getQuestionnaire(Context cxt, String taskid){
        Request.Evaluate.getReturnVisitList(fragment.getContext(), taskid, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
        switch (requestCode) {
            case Request.OtherTask.GET_OTHERTASK_RESULT:
                try {
                    if (headBean != null) {
                        if(headBean.getMsgcode() == 0 || headBean.isSuccess()) {
                            DisposeBean dpBean = gson.fromJson(headBean.getData(), DisposeBean.class);
                            if (dpBean != null) {
                                fragment.setDisposeView(true, dpBean, null);
                            } else
                                fragment.setDisposeView(false, null, "结果为空");
                        }else {
                            fragment.setDisposeView(false, null, headBean.getFaileMsg());
                        }
                    }
                } catch (Exception e) {
                    NLogger.e("解析结果详情报错：" + e.getMessage());
                }
                break;
            case Request.Evaluate.GET_QUESTIONNAIRE_RESULT://获取调查问卷结果
                if (headBean != null) {
                    if(headBean.getMsgcode() == 0 || headBean.isSuccess()) {
                        List<ReturnVisitBean> rvists = new ArrayList<>();
                        try {
                            JSONArray ja = new JSONArray(headBean.getData());
                            if(ja!=null && ja.length()>0){
                                for(int i=0; i<ja.length(); i++){
                                    ReturnVisitBean visitBean = gson.fromJson(ja.getJSONObject(i).toString(), ReturnVisitBean.class);
                                    if(visitBean.getFtype().equals("1")){
                                        List<ReturnVisitBean.Answer> answers = new ArrayList<>();
                                        ReturnVisitBean.Answer answer = new ReturnVisitBean.Answer("是", visitBean.getFactpoint()+"", visitBean.getFynval());
                                        answers.add(answer);
                                        answer = new ReturnVisitBean.Answer("否", visitBean.getFynval()+"", visitBean.getFynval()== 0 ? 1:0);
                                        answers.add(answer);
                                        visitBean.setResultItemDto(answers);
                                    }
                                    rvists.add(visitBean);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (rvists.size()>0) {
                            fragment.setGetReturnVisit(true, rvists);
                        }else {
                            fragment.setGetReturnVisit(false, rvists);
                        }
                    }
                }else {
                    fragment.setGetReturnVisit(false, null);
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        fragment.closeLoadingDialog();
        switch (requestCode) {
            case Request.OtherTask.GET_OTHERTASK_RESULT:
                fragment.setDisposeView(false, null, "请求出错");
                break;
            case Request.Evaluate.GET_QUESTIONNAIRE_RESULT://获取调查问卷结果
                fragment.setGetReturnVisit(false, null);
                break;
        }
    }
}
