package com.soonfor.measuremanager.home.othertask.presenter.updatetaskresult;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.othertask.activity.UpdateTaskResultActivity;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.UpdateTaskResultBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.evaluate.bean.ReturnVisitBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/1/25.
 */

public class UpdateTaskResultPresenter implements IUpdateTaskResultPresenter, AsyncUtils.AsyncCallback {

    private UpdateTaskResultActivity view;

    public UpdateTaskResultPresenter(UpdateTaskResultActivity view) {
        this.view = view;
    }

    //获取详情信息
//    @Override
//    public void getDetailInfo(int taskType, String json) {
//        Request.OtherTask.getOtherTaskDetailInfo(taskType, view, json, this);
//    }

    //更新任务结果
    @Override
    public void updateTaskResult(UpdateTaskResultBean bean) {
        Gson gson = new Gson();
        Request.OtherTask.updateTaskResult(view, this, gson.toJson(bean));
    }

    //获取调查问卷模板
    @Override
    public void getQuestionnaireTemplate(String fproid){
        Request.OtherTask.getQuestionnaireTemplate(view, fproid, this);
    }


    @Override
    public void success(int requestCode, JSONObject object) {
        view.mLoadingDialog.dismiss();
        Gson gson = new Gson();
        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
        switch (requestCode) {
            case Request.OtherTask.GET_OTHERTASK_DETAIL:
//                RepHeadBean headBean = RepJsonUtils.getHeadBean(object.toString());
//                if (headBean != null && (headBean.getMsgcode()==0 || headBean.isSuccess())) {
//                    OtherTaskDetailBean tBean = gson.fromJson(headBean.getData(), OtherTaskDetailBean.class);
//                    if (tBean != null) {
//                        view.setViewByDetailInfo(tBean);
//                    }
//                }
                break;
            case Request.OtherTask.GET_QUESTIONNAIRE_TEMPLATE://获取调查问卷模板
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
                                        ReturnVisitBean.Answer answer = new ReturnVisitBean.Answer("是", visitBean.getFpoint()+"", visitBean.getFynval());
                                        answers.add(answer);
                                        answer = new ReturnVisitBean.Answer("否", visitBean.getFpoint()+"", 0);
                                        answers.add(answer);
                                        visitBean.setResultItemDto(answers);
                                    }
                                    rvists.add(visitBean);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        view.setGetReturnVisit(true, rvists);
                    }
                }else {
                    view.setGetReturnVisit(false, null);
                }
                break;
            case Request.UPDATE_TASK_RESULT:
                if (headBean != null) {
                    if (headBean.getMsgcode() == 0) {
                        if (view.getButtonType() == 1) {
                            view.goToProfile();
                        } else {//if(view.getButtonType()==2){
                            try {
                                JSONObject js = new JSONObject(headBean.getData());
                                view.geToEvaluateCustomer(CommonUtils.formatStr(js.getString("ret")),
                                        CommonUtils.formatStr(js.getString("fappcstifuse")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }else {
                        view.showTrop("保存不成功："+ headBean.getFaileMsg());
                    }
                }else {
                    view.showTrop("保存不成功：结果返回为空");
                }
                view.closeLoadingDialog();
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        view.mLoadingDialog.dismiss();
        switch (requestCode) {
            case Request.UPDATE_TASK_RESULT:
                view.showTrop("保存失败！");
                break;
        }
    }
}
