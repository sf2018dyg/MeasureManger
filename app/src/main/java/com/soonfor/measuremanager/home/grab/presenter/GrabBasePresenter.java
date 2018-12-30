package com.soonfor.measuremanager.home.grab.presenter;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.grab.activity.GrabPondActivity;
import com.soonfor.measuremanager.home.grab.model.bean.GrabOrderBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.headBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-01-29.
 */

public class GrabBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private GrabPondActivity gpActivity;
    private int type;


    public void getData(int type, int pageNo, boolean isRefresh) {
        this.type = type;
        if (!isRefresh) {
            this.gpActivity.showLoadingDialog();
        }
        //获取待抢任务列表
        Request.Grab.getGraborderList(gpActivity, "", pageNo, 10, this);//取所有待抢任务
    }

    public GrabBasePresenter(GrabPondActivity gpActivity) {
        this.gpActivity = gpActivity;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.Grab.GET_GRABORDER_TASK_LIST:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        gpActivity.closeLoadingDialog();
                        gpActivity.showNoDataHint(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject jo = new JSONObject(data);
                            PageTurnBean pageBean = JsonUtils.getPageBean(jo.getString("pageTurn"));
                            JSONArray ja = new JSONArray(jo.getString("list"));
                            List<GrabOrderBean> mLists = new ArrayList<>();
                            if (ja != null && ja.length() > 0) {
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject ja_o = ja.getJSONObject(i);
                                    GrabOrderBean mBean = gson.fromJson(ja_o.toString(), GrabOrderBean.class);
                                    String taskno = mBean.getTaskNo();
                                    if (taskno.equals("")) {
                                        try {
                                            taskno = ja_o.getString("taskId");
                                        } catch (Exception e) {
                                        }
                                    }
                                    mBean.setTaskNo(taskno);
                                    mLists.add(mBean);
                                }
                            }
                            gpActivity.setListView(type, pageBean, mLists);
                        } catch (Exception e) {
                            gpActivity.showNoDataHint("请求出错");
                        }
                        gpActivity.closeLoadingDialog();
                    }
                });
                break;

        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        gpActivity.closeLoadingDialog();
    }

    public String getHintByStatus(String taskType) {
        String text = "执行";
        if (!taskType.equals("")) {
            switch (Integer.parseInt(taskType)) {
                case 0://量尺
                    text = "上门量尺";
                    break;
                case 1://复尺
                    text = "上门复尺";
                    break;
                case 2://放样
                    text = "放样";
                    break;
                case 3://设计
                    text = "设计";
                    break;
                default://其它任务
                    text = "执行";
                    break;
            }
        }
        return text;
    }
}
