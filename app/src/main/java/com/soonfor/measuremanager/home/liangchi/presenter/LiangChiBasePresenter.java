package com.soonfor.measuremanager.home.liangchi.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.liangchi.fragment.BaseLiangChiFragment;
import com.soonfor.measuremanager.home.liangchi.model.bean.liangchibean.liangchiHeadBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.PreferenceUtil;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:30
 * 邮箱：suibozhu@139.com
 */

public class LiangChiBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private BaseLiangChiFragment bgFragment;
    private Gson gson;
    int type;
    private int ItemPosition;


    public void getData(String type, boolean isRefresh, int pageNo, int pageSize) {
    }

    public void getData(int type, int pageNo, boolean isRefresh) {
        this.type = type;
        if (isRefresh) {
            this.bgFragment.showLoadingDialog();
        }
        //获取量尺列表
        Request.getMeasureTasks(bgFragment.getContext(), this, type + 1, pageNo, 10);
    }

    public void acceptTask(int positon, String taskno) {
        ItemPosition = positon;
        bgFragment.mLoadingDialog.show();
        Request.acceptTask(bgFragment.getContext(), this, PreferenceUtil.getCurrentUserBean().getSalesId(), "measure", taskno);
    }

    public void rejectTask(int positon, String taskno, String taskDescribe) {
        ItemPosition = positon;
        bgFragment.mLoadingDialog.show();
        Request.rejectTask(bgFragment.getContext(), this, PreferenceUtil.getCurrentUserBean().getSalesId(), "measure", taskno, taskDescribe);
    }


    public LiangChiBasePresenter(BaseLiangChiFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        gson = new Gson();
        switch (requestCode) {
            case Request.GET_MEASURE_TASKS:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.closeLoadingDialog();
                        bgFragment.showError(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            liangchiHeadBean result1 = gson.fromJson(data, new TypeToken<liangchiHeadBean>() {
                            }.getType());
                            bgFragment.setListView(type, result1.getPageTurn(), result1);
                        } catch (Exception e) {
                            bgFragment.showError(e.toString());
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
                            bgFragment.refreshAfterAccept(ItemPosition);
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
                            bgFragment.refreshAfterReject(ItemPosition);
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
