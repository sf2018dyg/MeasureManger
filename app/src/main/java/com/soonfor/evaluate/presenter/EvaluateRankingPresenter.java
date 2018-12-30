package com.soonfor.evaluate.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.evaluate.activity.EvaluateRankingActivity;
import com.soonfor.evaluate.bean.EvaluateRankingBean;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-19 14:29
 * 邮箱：dingyg012655@126.com
 */
public class EvaluateRankingPresenter  implements IBasePresenter, AsyncUtils.AsyncCallback {

    EvaluateRankingActivity mActivity;

    public EvaluateRankingPresenter(EvaluateRankingActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void getEvaluateRanking(Context mContext){
        Request.Evaluate.getEvaluateRanking(mContext, this);
    }


    @Override
    public void success(int requestCode, JSONObject object) {
        if(requestCode == Request.Evaluate.GET_EVALUATERANKING){
            JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {
                @Override
                public void doingInFail(String msg) {
                    mActivity.showViewByData(false, null);
                }
                @Override
                public void doingInSuccess(String data) {
                    List<EvaluateRankingBean> rankingBeanList = new ArrayList<>();
                    try {
                        JSONArray ja = new JSONArray(data);
                        if(ja!=null && ja.length()>0){
                            for(int i=0; i<ja.length(); i++) {
                                EvaluateRankingBean rankingBean = new Gson().fromJson(ja.getJSONObject(i).toString(), EvaluateRankingBean.class);
                                rankingBeanList.add(rankingBean);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mActivity.showViewByData(true, rankingBeanList);
                }
            });
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        if(requestCode == Request.Evaluate.GET_EVALUATERANKING){
           mActivity.showViewByData(false, new ArrayList<>());
        }
    }
}
