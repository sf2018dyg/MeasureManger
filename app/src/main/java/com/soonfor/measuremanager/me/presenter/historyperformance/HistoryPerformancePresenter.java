package com.soonfor.measuremanager.me.presenter.historyperformance;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.HistoryPerformanceActivity;
import com.soonfor.measuremanager.me.activity.MonthPerformanceActivity;
import com.soonfor.measuremanager.me.bean.PerformanceBean;
import com.soonfor.measuremanager.tools.DateTool;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class HistoryPerformancePresenter implements IBasePresenter, AsyncUtils.AsyncCallback, AdapterView.OnItemClickListener {
    private HistoryPerformanceActivity activity;
    private PerformanceBean bean;

    public HistoryPerformancePresenter(HistoryPerformanceActivity activity,ListView listView) {
        this.activity = activity;
        activity.mLoadingDialog.show();
        Request.getMonthPerformance(activity,1,this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        activity.mLoadingDialog.dismiss();
        switch (requestCode){
            case Request.GET_MONTH_PERFORMANCE:
                Gson gson = new Gson();
                bean = gson.fromJson(object.toString(),PerformanceBean.class);
                activity.setListData(bean.getData());
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        activity.mLoadingDialog.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String month = bean.getData().getList().get(position).getPerfDate();
        Bundle bundle = new Bundle();
        bundle.putString("monthType", DateTool.getTimeTimestamp(month,"yyyy-MM"));
        activity.startNewAct(MonthPerformanceActivity.class,bundle);
    }
}
