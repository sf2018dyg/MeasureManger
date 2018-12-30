package com.soonfor.repository.presenter.seekhelp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.hawk.Hawk;
import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.view.seekhelp.IOnlinServiceView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-DingYG on 2018-06-20 14:08
 * 邮箱：dingyg012655@126.com
 */
public class OnlineServicePresenter extends RepBasePresenter<IOnlinServiceView> implements RepAsyncUtils.AsyncCallback {

    private IOnlinServiceView view;

    public OnlineServicePresenter(IOnlinServiceView view) {
        this.view = view;
    }

    public void getAnswer(Context context, String keyword) {
        RepRequest.searchKnowledge(context, keyword, 1, 5, this);
    }

    //获取人工客服的h5网址
    public void getArtificialStaffPath(Context mActivity) {
        RepRequest.getSysCodeJimSocket(mActivity, this);
    }

    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        switch (requestCode) {
            case RepRequest.Knowledge.GET_SYSCODE_JIMSOCKET:
                try {
                    JSONArray jr = new JSONArray(data);
                    JSONObject o = new JSONObject(jr.get(0).toString());
                    if (o.getBoolean("success")) {
                        if (o.getString("code").equals("jimSocket")) {
                            String h5_path = o.getString("value");
                            if(h5_path!=null && !h5_path.equals("")){
                                if(!h5_path.toLowerCase().startsWith("http://")){
                                    h5_path = "http://" + h5_path;
                                }
                                if(h5_path.endsWith("/")){
                                    h5_path = h5_path.substring(0, h5_path.length()-1);
                                }
                                Hawk.put(Tokens.SP_ARTIFICIALSTAFFPATH, h5_path);
                            }
                        }
                    }
                } catch (Exception e) {
                    NLogger.e(e.getMessage());
                }
                view.closeLoadingDialog();
                break;
            case RepRequest.Knowledge.SEARCHKNOWLEDGE:
                try {
                    JSONObject o = new JSONObject(data);
                    RepPageTurn pageTurn = gson.fromJson(o.getString("pageTurn"), new TypeToken<RepPageTurn>() {
                    }.getType());
                    ArrayList<KnowledgeBean> beans = gson.fromJson(o.getString("list"), new TypeToken<ArrayList<KnowledgeBean>>() {
                    }.getType());
                    if (pageTurn != null && beans != null) {
                        view.showSearchKnowLedge(true, pageTurn, beans, null);
                    } else {
                        view.showSearchKnowLedge(false, pageTurn, beans, "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showSearchKnowLedge(false, null, null, "");
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        switch (requestCode) {
            case RepRequest.Knowledge.GET_SYSCODE_JIMSOCKET:
                view.closeLoadingDialog();
                break;
            case RepRequest.Knowledge.SEARCHKNOWLEDGE:
                view.showSearchKnowLedge(false, null, null, msg);
                break;
        }
    }
}
