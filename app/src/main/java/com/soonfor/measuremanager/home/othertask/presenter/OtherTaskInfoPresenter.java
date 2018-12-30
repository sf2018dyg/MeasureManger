package com.soonfor.measuremanager.home.othertask.presenter;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.othertask.fragment.detail.OtherTaskInfoFragment;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.OtherTaskDetailBean;
import com.soonfor.measuremanager.http.api.CustomerInfo;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DingYg on 2018-01-31.
 */

public class OtherTaskInfoPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private OtherTaskInfoFragment bgFragment;
    private Gson gson;
    private int taskType;

    public OtherTaskInfoPresenter(OtherTaskInfoFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    //获取任务详情
    public void getTaskDetail(int taskType, String _id) {
        this.taskType = taskType;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", _id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request.OtherTask.getOtherTaskDetailInfo(taskType, bgFragment.getContext(), jsonObject.toString(), this);
    }

    /**
     * 抢单
     *
     * @param taskid
     */
    public void grabTask(String taskid, String tasktype) {
        Request.Grab.postToGrab(bgFragment.getContext(), taskid, tasktype, this);
    }


    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
        try {
            switch (requestCode) {
                case Request.OtherTask.GET_OTHERTASK_DETAIL:
                    gson = new Gson();
                    if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                        OtherTaskDetailBean tBean = gson.fromJson(headBean.getData(), OtherTaskDetailBean.class);
                        if (tBean != null) {
                            bgFragment.setViewByDetailInfo(false, tBean);
                        } else
                            MyToast.showFailToast(bgFragment.getContext(), "详情数据为空！");
                    } else {
                        MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                    }
                    break;
                case Request.Grab.POST_GRAB:
                    if (headBean != null) {
                        if (headBean.getMsgcode() == 0 || headBean.isSuccess())
                            bgFragment.refreshAfterGrabTask();
                        else
                            MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                    } else {
                        MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        if (bgFragment != null) {
            bgFragment.closeLoadingDialog();
        }
    }
}
