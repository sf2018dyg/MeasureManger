package com.soonfor.measuremanager.home.order.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.soonfor.measuremanager.home.order.PreOrderActivity;
import com.soonfor.measuremanager.home.order.bean.PreOrderBean;
import com.soonfor.measuremanager.home.order.bean.StaffBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/20.
 */

public class PreOrderPresenter implements IPreOrderPresenter, AsyncUtils.AsyncCallback {

    private PreOrderActivity view;

    public PreOrderPresenter(PreOrderActivity view) {
        this.view = view;
    }

    @Override
    public void getData(boolean isRefresh) {
        Request.getStaffs(view, this, 2);
    }

    @Override
    public void getPreOrder(String orderNo) {
        Request.getPreOrder(view, this, orderNo);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.PRE_ORDER:
                Gson gson = new Gson();
                try {
                    PreOrderBean bean = gson.fromJson(object.toString(), PreOrderBean.class);
                    if (bean != null && bean.getMsgcode() == 0) {
                        view.showPreOrder(bean);
                    }
                }catch (Exception e){
                    e.getMessage();
                }
                break;
            case Request.GET_STAFFS:
                gson = new Gson();
                StaffBean staffBean = gson.fromJson(object.toString(), StaffBean.class);
                if (staffBean != null && staffBean.getMsgcode() == 0) {
                    view.setBeans(staffBean.getData().getList());
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        view.mLoadingDialog.dismiss();
        MyToast.showToast(view, "操作失败");
    }
}
