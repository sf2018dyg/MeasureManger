package com.soonfor.measuremanager.home.lofting.presenter.immloft;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.lofting.activity.immloft.ImmediateLoftingActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.LoftItemBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYg on 2018-01-29.
 */

public class ImmeLoftPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private ImmediateLoftingActivity ImmActivity;
    private Gson gson;
    private LoftingMainBean task;

    public void getData(LoftingMainBean task, boolean isRefresh) {
        if (isRefresh) {
            this.ImmActivity.showLoadingDialog();
        }
        /**
         * 获取放样详情列表
         */
        this.task = task;
        if (task != null && !task.getTaskNo().equals("")) {
            Request.Loft.getLoftingDetailList(ImmActivity, task.getTaskNo(), this);
        }
    }

    public ImmeLoftPresenter(ImmediateLoftingActivity bgFragment) {
        this.ImmActivity = bgFragment;
    }

    /**
     * 提交
     *
     * @param taskNo
     */
    public void setFinishTask(String taskNo) {
        Request.finishTask(ImmActivity, this, taskNo);
    }


    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        ImmActivity.closeLoadingDialog();
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        ImmActivity.closeLoadingDialog();
        switch (requestCode) {
            case Request.Loft.GET_LOFTING_DETAIL_LIST:
                gson = new Gson();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        MyToast.showFailToast(ImmActivity, msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        List<LoftItemBean> detailBeans = new ArrayList<>();
                        try {
                            JSONArray ja = new JSONArray(data);
                            if (ja != null && ja.length() > 0) {
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    LoftItemBean lIBean = new LoftItemBean();
                                    lIBean.setFcname(jo.getString("fcname"));
                                    lIBean.setFstatus(jo.getString("fstatus"));
                                    lIBean.setFmeafloor(jo.getString("fmeafloor"));
                                    lIBean.setFobsplanid(jo.getString("fobsplanid"));
                                    lIBean.setFcdate(jo.getString("fcdate"));
                                    lIBean.setFimgpath(jo.getString("fimgpath"));
                                    if (task != null) {
                                        lIBean.setTaskNo(task.getTaskNo());
                                        lIBean.setTaskType(task.getTaskType());
                                        lIBean.setForderNo(task.getOrderNo());
                                        lIBean.setCustomBulid(task.getBuilding());
                                        lIBean.setCustomAddress(task.getAddress());
                                    }
                                    detailBeans.add(lIBean);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            MyToast.showFailToast(ImmActivity, "请求出错：" + e.toString());
                        }
                        if (detailBeans.size() == 0) {
                            MyToast.showFailToast(ImmActivity, "暂无待放样户型清单");
                        }
                        ImmActivity.setListdata(detailBeans);
                    }
                });
                break;
            case Request.FINISH_TASK:
                HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null) {
                    if (headBean.getMsgcode() == 0) {
                        ImmActivity.finish_task();
                    } else {
                        MyToast.showFailToast(ImmActivity, "提交失败：" + headBean.getFaileMsg());
                    }
                } else {
                    MyToast.showFailToast(ImmActivity, "提交失败");
                }
                break;
        }
    }

}
