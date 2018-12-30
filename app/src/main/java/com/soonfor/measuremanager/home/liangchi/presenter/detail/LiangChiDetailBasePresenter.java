package com.soonfor.measuremanager.home.liangchi.presenter.detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.liangchi.fragment.detail.TaskInfoFragment;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.PreferenceUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 11:03
 * 邮箱：suibozhu@139.com
 */

public class LiangChiDetailBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private TaskInfoFragment bgFragment;
    private Gson gson;

    /**
     * 获取网络数据，更新界面
     *
     * @param isRefresh 是否是swiperefresh的
     */
    public void getData(boolean isRefresh) {
        this.bgFragment.showLoadingDialog();
        String returnStr = null;
        bgFragment.setListView(returnStr);
    }

    public void getProgressTaskInfo(String taskno, String orderno) {
        //获取任务详情
        bgFragment.mLoadingDialog.show();
        Request.getProgressTaskInfo(bgFragment.getContext(), this, taskno, "measure", orderno);
    }
    //获取订单总进度
    public void getAllOrderProcess() {
        //获取总进度
        Request.getOption(bgFragment.getActivity(), this, "AssignOrderProcess", Request.GET_OPTION);
    }
    public void acceptTask(String taskno) {
        bgFragment.mLoadingDialog.show();
        Request.acceptTask(bgFragment.getContext(), this, PreferenceUtil.getCurrentUserBean().getSalesId(), "measure", taskno);
    }

    public void rejectTask(String taskno, String taskDescribe) {
        bgFragment.mLoadingDialog.show();
        Request.rejectTask(bgFragment.getContext(), this, PreferenceUtil.getCurrentUserBean().getSalesId(), "measure", taskno, taskDescribe);
    }

    public LiangChiDetailBasePresenter(TaskInfoFragment bgFragment) {
        this.bgFragment = bgFragment;

    }

    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        gson = new Gson();
        switch (requestCode) {
            case Request.GET_PROGRESS_TASK_INFO:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.closeLoadingDialog();
                        bgFragment.showTip(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            beanTotal result1 = gson.fromJson(data, new TypeToken<beanTotal>() {
                            }.getType());
                            bgFragment.getProgressTaskInfo(result1);
                        } catch (Exception e) {
                            NLogger.e("量尺", e.getMessage());
                            bgFragment.showTip(e.toString());
                        }
                    }
                });
                //bgFragment.getProgressTaskInfo();
                break;
                case Request.GET_OPTION:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {}

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            DataBean result1 = gson.fromJson(data, new TypeToken<DataBean>() {
                            }.getType());
                            try {
                                AppCache.processBean = result1;
                                bgFragment.setProgress();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                        }
                    }
                });
                break;
            case Request.ACCEPT_TASK:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.closeLoadingDialog();
                        bgFragment.showError(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            bgFragment.showTip(data.equals("success") ? "操作成功" : data + "");
                            bgFragment.afterSuccessHideInterface(data.equals("success") ? true : false);
                        } catch (Exception e) {
                            bgFragment.showError(e.toString());
                        }
                    }
                });
                break;
            case Request.REJECT_TASK:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.closeLoadingDialog();
                        bgFragment.showError(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            bgFragment.showTip(data.equals("success") ? "操作成功" : data + "");
                            bgFragment.afterSuccessHideInterface(data.equals("success") ? true : false);
                        } catch (Exception e) {
                            bgFragment.showError(e.toString());
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
    }
}
