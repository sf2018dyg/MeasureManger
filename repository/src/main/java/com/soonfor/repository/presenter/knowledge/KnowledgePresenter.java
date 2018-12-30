package com.soonfor.repository.presenter.knowledge;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.CategoryBean;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.view.knowledge.IKnowledgeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-06-14 16:56
 * 邮箱：dingyg012655@126.com
 */
public class KnowledgePresenter extends RepBasePresenter<IKnowledgeView> implements RepAsyncUtils.AsyncCallback {

    private IKnowledgeView view;
    private int Like_Pos;
    private int postPage = -1;//请求的页码

    public KnowledgePresenter(IKnowledgeView view) {
        this.view = view;
    }

    /**
     * 获取分类类型
     */
    public void getTabTitles(Context context, boolean isRefresh) {
        if (DataTools.fTypes != null && DataTools.fTypes.size() > 0 && DataTools.sTypes != null && DataTools.sTypes.size() > 0) {
            view.setGetCategory(true, null);
        } else {
//            if (!isRefresh) {
//                view.showLoadingDialog();
//            }
            RepRequest.Knowledge.getCategoryList(context, this);
        }
    }

    /**
     * 请求热门分页数据
     */
    public void getHotList(Context context, int pageno, boolean isRefresh) {
//        if (!isRefresh) {
//            view.showLoadingDialog();
//        }
        postPage = pageno;
        RepRequest.Knowledge.getHotList(context, pageno, this);
    }

    /**
     * 请求数据(分类条件)
     */
    public void getDatas(Context mContext, List<String> Ids, int pageno, boolean isRefresh) {
//        if (!isRefresh) {
//            view.showLoadingDialog();
//        }
        postPage = pageno;
        JSONArray jsonArray = new JSONArray();
        if (Ids != null && Ids.size() > 0) {
            for (int i = 0; i < Ids.size(); i++) {
                try {
                    jsonArray.put(i, Ids.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        RepRequest.Knowledge.getKnowledgeList(mContext, jsonArray, pageno, this);
    }

    //点赞
    public void like(Context cxt, int position, String _id) {
        postPage = -1;
        this.Like_Pos = position;
        RepRequest.Knowledge.Like(cxt, _id, this);
    }

    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        try {
            switch (requestCode) {
                case RepRequest.Knowledge.GET_CATEGORY_LIST:
                    JSONArray ja = new JSONArray(data);
                    if (ja != null && ja.length() > 0) {
                        if (DataTools.fTypes != null) {
                            DataTools.fTypes.clear();
                        } else {
                            DataTools.fTypes = new ArrayList<>();
                        }
                        if (DataTools.sTypes != null) {
                            DataTools.sTypes.clear();
                        } else {
                            DataTools.sTypes = new HashMap<>();
                        }
                        CategoryBean hotBean = new CategoryBean("hot", "热门");
                        ArrayList<CategoryBean> child = new ArrayList<>();
                        child.add(new CategoryBean("hot", "热门", "hot", "热门"));
                        hotBean.setChildren(child);
                        DataTools.fTypes.add(hotBean);
                        DataTools.sTypes.put("hot", child);
                        for (int i = 0; i < ja.length(); i++) {
                            CategoryBean categoryBean = gson.fromJson(ja.getJSONObject(i).toString(), new TypeToken<CategoryBean>() {
                            }.getType());
                            categoryBean.setParentNameToChild();
                            DataTools.fTypes.add(categoryBean);
                            DataTools.sTypes.put(categoryBean.getId(), categoryBean.getChildren());
                        }
                        view.setGetCategory(true, null);
                    } else {
                        view.setGetCategory(false, "分类列表为空");
                    }
                    break;
                case RepRequest.Knowledge.GET_HOT_LIST:
                    JSONObject jo1 = new JSONObject(data);
                    RepPageTurn pageTurn1 = gson.fromJson(jo1.getString("pageTurn"), new TypeToken<RepPageTurn>() {
                    }.getType());
                    JSONArray jo1ja = jo1.getJSONArray("list");
                    if (jo1ja != null && jo1ja.length() > 0) {
                        ArrayList<KnowledgeBean> hots = new ArrayList<>();
                        for (int i = 0; i < jo1ja.length(); i++) {
                            KnowledgeBean hotBean = gson.fromJson(jo1ja.getJSONObject(i).toString(), new TypeToken<KnowledgeBean>() {
                            }.getType());
                            hots.add(hotBean);
                        }
                        view.setGetHotList(true, pageTurn1, hots, null);
                    } else {
                        view.setGetHotList(false, pageTurn1, null, "分类列表为空");
                    }
                    view.closeLoadingDialog();
                    break;
                case RepRequest.Knowledge.GET_KNOWLEDGE_LIST:
                    JSONObject jo2 = new JSONObject(data);
                    RepPageTurn pageTurn2 = gson.fromJson(jo2.getString("pageTurn"), new TypeToken<RepPageTurn>() {
                    }.getType());
                    JSONArray jo2ja = jo2.getJSONArray("list");
                    if (jo2ja != null && jo2ja.length() > 0) {
                        ArrayList<KnowledgeBean> knowledges = new ArrayList<>();
                        for (int i = 0; i < jo2ja.length(); i++) {
                            KnowledgeBean beans = gson.fromJson(jo2ja.getJSONObject(i).toString(), new TypeToken<KnowledgeBean>() {
                            }.getType());
                            knowledges.add(beans);
                        }
                        view.setGetHnowledwList(true, pageTurn2, knowledges, null);
                    } else {
                        view.setGetHnowledwList(false, pageTurn2, null, "分类列表为空");
                    }
                    view.closeLoadingDialog();
                    break;
                case RepRequest.Knowledge.LIKE:
                    //点赞成功
                    view.setAfterLike(true, Like_Pos);
                    break;
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            view.closeLoadingDialog();
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        if(msg.contains("您的WLAN和移动网络均未连接")){
            if(postPage==1)
                view.showNoDataHint(msg);
        }else {
            if(postPage==1)
                view.showNoDataHint(msg);
            if (requestCode == RepRequest.Knowledge.LIKE) {
                //点赞失败
                view.setAfterLike(false, Like_Pos);
            }
        }
    }
}
