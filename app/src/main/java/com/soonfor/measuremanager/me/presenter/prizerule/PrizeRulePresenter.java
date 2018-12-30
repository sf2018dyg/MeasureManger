package com.soonfor.measuremanager.me.presenter.prizerule;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.PrizeRuleActivity;
import com.soonfor.measuremanager.me.bean.PrizeRuleBean;
import com.soonfor.measuremanager.tools.LogTools;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class PrizeRulePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private PrizeRuleActivity activity;
    private String prizeId;
    private Gson gson;


    public PrizeRulePresenter(PrizeRuleActivity activity, String prizeId) {
        this.activity = activity;
        this.prizeId = prizeId;
        activity.mLoadingDialog.show();
        Request.getPrizeRule(activity, prizeId, this);
    }

    public void getKey() {
        activity.mLoadingDialog.show();
        Request.ExecuteLottery(activity, prizeId, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        activity.mLoadingDialog.dismiss();
        switch (requestCode) {
            case Request.GET_PRIZE_RULE:
                gson = new Gson();
                PrizeRuleBean bean = gson.fromJson(object.toString(), PrizeRuleBean.class);
                activity.setData(bean);
                break;
            case Request.EXECUTE_LOTTERY:
                gson = new Gson();
                LogTools.showLog(activity, object.toString());
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        activity.closeLoadingDialog();
    }
}
