package com.soonfor.repository.presenter.personal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.MyAddedKnowLedgeBean;
import com.soonfor.repository.view.personal.IMyAddedKnowLedgeView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/3 0003 11:21
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class MyAddedKnowLedgePresenter extends RepBasePresenter<IMyAddedKnowLedgeView> implements RepAsyncUtils.AsyncCallback {

    private IMyAddedKnowLedgeView view;

    public MyAddedKnowLedgePresenter(IMyAddedKnowLedgeView view) {
        this.view = view;
    }

    public void getMyAddedKnowLedge(Context context, int pageNo, int pageSize) {
        RepRequest.getMyKnowLedgePage(context, pageNo, pageSize, this);
    }

    @Override
    public void success(int requestCode, String data) {
        final Gson gson = new Gson();
        switch (requestCode) {
            case RepRequest.Knowledge.MYKNOWLEDGEPAGE:
                try {
                    JSONObject o = new JSONObject(data);
                    final RepPageTurn pageTurn = gson.fromJson(o.getString("pageTurn"), new TypeToken<RepPageTurn>() {
                    }.getType());
                    final JSONArray jaList = new JSONArray(o.getString("list"));
                    final List<MyAddedKnowLedgeBean> beans = new ArrayList<>();
                    if (jaList != null && jaList.length() > 0) {
                        for (int i = 0; i < jaList.length(); i++) {
                            MyAddedKnowLedgeBean bean = gson.fromJson(jaList.getJSONObject(i).toString(), MyAddedKnowLedgeBean.class);
                            beans.add(bean);
                        }
                    }
                    view.setDatas(pageTurn, beans);
                } catch (Exception e) {
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        if (msg.contains("您的WLAN和移动网络均未连接")) {
            view.showNetError(msg);
        } else {
            view.showNoDataHint(msg);
        }
    }
}
