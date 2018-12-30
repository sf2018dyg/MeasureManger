package com.soonfor.measuremanager.home.complexruler.presenter.detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.complexruler.activity.FuChiMainActivity;
import com.soonfor.measuremanager.home.complexruler.fragment.detail.TaskInfoFragment;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
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

public class FuChiDetailBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

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
        Request.getProgressTaskInfo(bgFragment.getContext(), this, taskno, "remeasure", orderno);
    }

    //获取订单总进度
    public void getAllOrderProcess() {
        //获取总进度
        Request.getOption(bgFragment.getActivity(), this, "AssignOrderProcess", Request.GET_OPTION);
    }


    public void acceptTask(String taskno) {
        bgFragment.mLoadingDialog.show();
        Request.acceptTask(bgFragment.getContext(), this, PreferenceUtil.getString(UserInfo.USERNAME, ""), "remeasure", taskno);
    }

    public void rejectTask(String taskno, String taskDescribe) {
        bgFragment.mLoadingDialog.show();
        Request.rejectTask(bgFragment.getContext(), this, PreferenceUtil.getString(UserInfo.USERNAME, ""), "remeasure", taskno, taskDescribe);
    }

    public FuChiDetailBasePresenter(TaskInfoFragment bgFragment) {
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
                        NLogger.e("复尺详情：", msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            beanTotal result1 = gson.fromJson(data, new TypeToken<beanTotal>() {
                            }.getType());
                            try {
                                bgFragment.getProgressTaskInfo(result1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            NLogger.e("复尺详情：", e.getMessage());
                            //bgFragment.showTip(e.toString());
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
//        switch (requestCode) {
//            case Request.GET_MINE:
//                gson = new Gson();
//                MeMineBean mineBean = gson.fromJson(object.toString(), MeMineBean.class);
//                if (mineBean.isSuccess()){
//                    meFragment.setMine(mineBean);
//                }else
//                    MyToast.showFailToast(meFragment.getContext(),mineBean.getData().toString());
//                break;
//            case Request.GET_SALE_TARGET:
//                LogTools.showLog(meFragment.getContext(),object.toString());
//                gson = new Gson();
//                SaleTargetBean saleTargetBean = gson.fromJson(object.toString(),SaleTargetBean.class);
//                if (saleTargetBean.isSuccess()){
//                    meFragment.setSaleTarget(saleTargetBean);
//                }else
//                    MyToast.showFailToast(meFragment.getContext(),saleTargetBean.getData().toString());
//                break;
//            case Request.GET_MY_RANKING:
//                LogTools.showLog(meFragment.getContext(),object.toString());
//                gson = new Gson();
//                MyRankingBean rankingBean = gson.fromJson(object.toString(),MyRankingBean.class);
//                if (rankingBean.isSuccess()){
//                    meFragment.setRanking(rankingBean);
//                }else
//                    MyToast.showFailToast(meFragment.getContext(),rankingBean.getData().toString());
//                break;
//        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
    }
}
