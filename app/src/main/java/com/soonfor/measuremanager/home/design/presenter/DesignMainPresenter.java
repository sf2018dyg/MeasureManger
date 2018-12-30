package com.soonfor.measuremanager.home.design.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.design.activity.DesignMainActivity;
import com.soonfor.measuremanager.home.design.fragment.BaseDesignMainFragment;
import com.soonfor.measuremanager.home.design.model.DesignItemBean;
import com.soonfor.measuremanager.home.grab.model.bean.GrabOrderBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DesignMainPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private DesignMainActivity mActivity;
    private BaseDesignMainFragment bgFragment;
    private int type = 1;
    private int ItemPosition;


    public void getData(int type, int pageNo, boolean isNeedProgressDialog) {
        this.type = type;
        if (isNeedProgressDialog) {
            this.bgFragment.showLoadingDialog();
        }
        //获取设计客户任务列表
        Request.Design.getDesignTaskList(bgFragment.getContext(), type, "", pageNo, 10, this);
    }


    /**
     * 接收
     *
     * @param taskno
     */
    public void acceptTask(int positon, String taskno) {
        ItemPosition = positon;
        Request.acceptTask(bgFragment.getContext(), this, "", "design", taskno);
    }

    /**
     * 拒收
     *
     * @param taskno
     * @param taskDescribe
     */
    public void rejectTask(int positon, String taskno, String taskDescribe) {
        ItemPosition = positon;
        Request.rejectTask(bgFragment.getContext(), this, PreferenceUtil.getString(UserInfo.USERNAME, ""), "design", taskno, taskDescribe);
    }

    public DesignMainPresenter(DesignMainActivity mActivity) {
        this.mActivity = mActivity;
    }

    public DesignMainPresenter(BaseDesignMainFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.Loft.GET_LOFTING_TASK_LIST:
                bgFragment.closeLoadingDialog();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject jo = new JSONObject(data);
                            PageTurnBean pageBean = JsonUtils.getPageBean(jo.getString("pageTurn"));
                            JSONArray ja = new JSONArray(jo.getString("list"));
                            List<DesignItemBean> mLists = new ArrayList<>();
                            if (ja != null && ja.length() > 0) {
                                for (int i = 0; i < ja.length(); i++) {
                                    DesignItemBean mBean = new Gson().fromJson(ja.getJSONObject(i).toString(), DesignItemBean.class);
                                    mLists.add(mBean);
                                }
                            }
                            bgFragment.setListView(type, pageBean, mLists);
                        } catch (Exception e) {
                            bgFragment.showError(e.toString());
                        }
                    }

                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.showNoDataHint(msg);
                    }
                });
                break;
            case Request.ACCEPT_TASK:
                HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null) {
                    if (headBean.getMsgcode() == 0 || headBean.isSuccess())
                        bgFragment.refreshAfterAccept(ItemPosition);
                    else
                        MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                }
                break;
            case Request.REJECT_TASK:
                HeadBean headBean1 = JsonUtils.getHeadBean(object.toString());
                if (headBean1 != null) {
                    if ((headBean1.getMsgcode() == 0 || headBean1.isSuccess()))
                        bgFragment.refreshAfterReject(ItemPosition);
                    else
                        MyToast.showFailToast(bgFragment.getContext(), headBean1.getFaileMsg());
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
        bgFragment.showNoDataHint("请求出错");
        // bgFragment.showFailText(bgFragment.getContext(), throwable, errorResponse);
    }
}
