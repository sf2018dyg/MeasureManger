package com.soonfor.measuremanager.home.grab.presenter;

import android.content.Context;

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

import cn.jesse.nativelogger.NLogger;

/**
 * Created by Administrator on 2018-01-29.
 */

public class GrabComPresenter implements AsyncUtils.AsyncCallback{

    private Context mContext;
    private IGrabComView view;

    public GrabComPresenter(Context mContext, IGrabComView view){
        this.mContext = mContext;
        this.view = view;
    }
    /**
     * 抢单
     *
     * @param taskid
     */
    public void grabTask(String taskid, String tasktype) {
        Request.Grab.postToGrab(mContext, taskid,tasktype, this);
    }


    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.Grab.POST_GRAB:
                HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null) {
                    if(headBean.getMsgcode()==0 || headBean.isSuccess())
                        view.refreshAfterGrabTask(true, null);
                    else {
                        view.refreshAfterGrabTask(false, headBean.getFaileMsg());
                    }
                }else{
                    view.refreshAfterGrabTask(false, headBean.getFaileMsg());
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        if(requestCode == Request.Grab.POST_GRAB){
            view.refreshAfterGrabTask(true, "抢单失败");
        }
    }
}
