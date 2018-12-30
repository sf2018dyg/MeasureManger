package com.soonfor.measuremanager.home.othertask.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.othertask.activity.OtherTaskMainActivity;
import com.soonfor.measuremanager.home.othertask.fragment.BaseOtherTaskMainFragment;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.http.api.CustomerInfo;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYg on 2018-01-29.
 */

public class OtherTaskMainPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private OtherTaskMainActivity mActivity;
    private BaseOtherTaskMainFragment bgFragment;
    private int type = 1;
    private int ItemPosition;

    public void getData(int type, int pageNo, boolean isNeedProgressDialog) {
        this.type = type;
//        if (isNeedProgressDialog) {
//            this.bgFragment.showLoadingDialog();
//        }
        //获取其它任务列表
        JSONObject jo = new JSONObject();
        try {
            jo.put("status", type + "");
            jo.put("pageNo", pageNo);
            jo.put("pageSize", 10);
        } catch (Exception e) {
        }
        Request.OtherTask.getOtherTaskList(bgFragment.getContext(), jo.toString(), this);
    }

    public OtherTaskMainPresenter(OtherTaskMainActivity mActivity) {
        this.mActivity = mActivity;
    }

    public OtherTaskMainPresenter(BaseOtherTaskMainFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.OtherTask.GET_OTHERTASKLIST:
                bgFragment.closeLoadingDialog();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.showError(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject jo = new JSONObject(data);
                            PageTurnBean pageBean = JsonUtils.getPageBean(jo.getString("pageTurn"));
                            JSONArray ja = new JSONArray(jo.getString("list"));
                            List<OtherTaskMainBean> mLists = new ArrayList<>();
                            if (ja != null && ja.length() > 0) {
                                for (int i = 0; i < ja.length(); i++) {
                                    OtherTaskMainBean mBean = new Gson().fromJson(ja.getJSONObject(i).toString(), OtherTaskMainBean.class);
                                    mLists.add(mBean);
                                }
                            }
                            bgFragment.setListView(type, pageBean, mLists);
                        } catch (Exception e) {
                            bgFragment.showError(e.toString());
                        }
                    }
                });
                break;
//            case Request.ACCEPT_TASK:
//                HeadBean headBean = RepJsonUtils.getHeadBean(object.toString());
//                if (headBean != null) {
//                    if(headBean.getMsgcode()==0 || headBean.isSuccess())
//                        bgFragment.refreshAfterAccept(ItemPosition);
//                    else
//                        MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
//                }
//                break;
//            case Request.REJECT_TASK:
//                HeadBean headBean1 = RepJsonUtils.getHeadBean(object.toString());
//                if (headBean1 != null) {
//                    if((headBean1.getMsgcode()==0 || headBean1.isSuccess()))
//                        bgFragment.refreshAfterReject(ItemPosition);
//                    else
//                        MyToast.showFailToast(bgFragment.getContext(), headBean1.getFaileMsg());
//                }
//                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.OtherTask.GET_OTHERTASKLIST:
                bgFragment.closeLoadingDialog();
                bgFragment.showNoDataHint("请求出错");
                break;
            case Request.GET_OPTION:
                mActivity.closeLoadingDialog();
                //mActivity.showNoDataHint();
                break;
            // bgFragment.showFailText(bgFragment.getContext(), throwable, errorResponse);
        }
    }
}
