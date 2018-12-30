package com.soonfor.measuremanager.me.presenter.pointsget;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.bean.PointsDetailsBean;
import com.soonfor.measuremanager.me.fragment.WorkPointsGetFragment;
import com.soonfor.measuremanager.tools.LogTools;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class PointsGetPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private WorkPointsGetFragment fragment;
    private Context mContext;

    public PointsGetPresenter(WorkPointsGetFragment fragment) {
        this.fragment = fragment;
        mContext = this.fragment.getContext();
        Request.getWorkPointsDetails(mContext,this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode){
            case Request.GET_WORK_POINT_DETAILS:
                fragment.closeLoadingDialog();
                LogTools.showLog(mContext,object.toString());
                Gson gson = new Gson();
                PointsDetailsBean bean = gson.fromJson(object.toString(),PointsDetailsBean.class);
                fragment.setData(bean.getData());
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        fragment.closeLoadingDialog();
    }
}
