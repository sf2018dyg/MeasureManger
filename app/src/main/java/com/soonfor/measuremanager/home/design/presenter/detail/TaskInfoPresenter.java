package com.soonfor.measuremanager.home.design.presenter.detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.TaskProgress;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.home.design.fragment.detail.TaskInfoFragment;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018-01-31.
 */

public class TaskInfoPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private TaskInfoFragment bgFragment;
    private Gson gson;
    private int type;

    public void getData(int type, String task[], boolean isRefresh) {
        this.type = type;
        if(isRefresh)
            this.bgFragment.showLoadingDialog();
        //获取详细信息
        Request.getProgressTaskInfo(bgFragment.getContext(), this, task[0],task[1], task[2]);
    }

    public void getOptionInMain() {
        //获取总进度
        Request.getOption(bgFragment.getActivity(), this, "AssignOrderProcess", Request.GET_OPTION);
    }

    /**
     * 获取进度对象集合
     */
    public void getPargress(TaskProgress progress) {
        bgFragment.setProgressImage(AppCache.getProgressListData(progress, "A03"));
    }

    /**
     * 接收
     *
     * @param taskno
     */
    public void acceptTask(String taskno) {
        Request.acceptTask(bgFragment.getContext(), this, "", "design", taskno);
    }

    /**
     * 拒收
     * @param taskno
     * @param taskDescribe
     */
    public void rejectTask(String taskno, String taskDescribe) {
        Request.rejectTask(bgFragment.getContext(), this, "","design", taskno, taskDescribe);
    }

    /**
     * 确图
     */
    public void sureDsigndPaper(String taskno){
        Request.Design.sureDesignPager(bgFragment.getContext(), taskno,this);
    }

    public TaskInfoPresenter(TaskInfoFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
        try {
            switch (requestCode) {
                case Request.GET_PROGRESS_TASK_INFO:
                    gson = new Gson();
                    if (headBean != null && (headBean.getMsgcode()==0 || headBean.isSuccess())) {
                        beanTotal tBean = gson.fromJson(headBean.getData(), beanTotal.class);
                        if (tBean != null) {
                            bgFragment.setListView(type, tBean);
                            bgFragment.closeLoadingDialog();
                        } else {
                            bgFragment.closeLoadingDialog();
                            MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                        }
                    }else{
                        MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                        bgFragment.setListView(type,null);
                    }
                    break;
                case Request.GET_OPTION:
                    JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {

                        @Override
                        public void doingInFail(String msg) {
                            bgFragment.showNoDataHint(msg);
                        }

                        @Override
                        public void doingInSuccess(String data) {
                            try {
                                DataBean dataBean = new Gson().fromJson(data, new TypeToken<DataBean>() {
                                }.getType());
                                if (dataBean != null) {
                                    AppCache.processBean = dataBean;
                                    bgFragment.setThisPargress();
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
                    break;
                case Request.ACCEPT_TASK:
                    if (headBean != null) {
                        if(headBean.getMsgcode()==0 || headBean.isSuccess())
                            bgFragment.refreshAfterAccept();
                        else
                            MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                    }else{
                        MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                    }
                    break;
                case Request.REJECT_TASK:
                    if (headBean != null) {
                        if(headBean.getMsgcode()==0 || headBean.isSuccess())
                            bgFragment.refreshAfterReject();
                        else
                            MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                    }else{
                        MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                    }
                    break;
                case Request.Design.SURE_DESIGNPAGER:
                    if (headBean != null) {
                        if(headBean.getMsgcode()==0 || headBean.isSuccess())
                            bgFragment.refreshAfterSure();
                        else
                            MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                    }else{
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
        bgFragment.closeLoadingDialog();
    }
}
