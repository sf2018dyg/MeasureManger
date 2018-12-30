package com.soonfor.measuremanager.home.othertask.presenter.updatetaskresult;

import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.UpdateTaskResultBean;

/**
 * Created by ljc on 2018/1/25.
 */

public interface IUpdateTaskResultPresenter extends IBasePresenter {

    //请求详情信息
   // void getDetailInfo(int taskType, String json);
    //更新任务结果
    void updateTaskResult(UpdateTaskResultBean bean);
    //请求调查问卷数据
    void getQuestionnaireTemplate(String taskid);
}
