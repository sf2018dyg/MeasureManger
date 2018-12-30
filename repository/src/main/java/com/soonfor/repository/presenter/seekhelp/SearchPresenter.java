package com.soonfor.repository.presenter.seekhelp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.model.knowledge.SearchTitleBean;
import com.soonfor.repository.view.seekhelp.ISearchView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-06-20 13:46
 * 邮箱：dingyg012655@126.com
 */
public class SearchPresenter extends RepBasePresenter<ISearchView> implements RepAsyncUtils.AsyncCallback {

    private ISearchView view;
    private int Like_Pos;

    public SearchPresenter(ISearchView view) {
        this.view = view;
    }

    public void SearchTitle(Context context, String keyword) {
        RepRequest.searchTitle(context, keyword, this);
    }

    public void SearchKnowLedge(Context context, String keyword, int pageNo, int pageSize) {
        RepRequest.searchKnowledge(context, keyword, pageNo, pageSize, this);
    }

    //点赞
    public void like(Context cxt, int position, String _id) {
        this.Like_Pos = position;
        RepRequest.Knowledge.Like(cxt, _id, this);
    }

    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        switch (requestCode) {
            case RepRequest.Knowledge.SEARCHTITLE:
                List<SearchTitleBean> stb = gson.fromJson(data, new TypeToken<List<SearchTitleBean>>() {
                }.getType());
                if (stb != null) {
                    view.showSearchTitle(true, stb);
                }else {
                    view.showSearchTitle(false, stb);
                }
                break;
            case RepRequest.Knowledge.SEARCHKNOWLEDGE:
                try {
                    JSONObject o = new JSONObject(data);
                    RepPageTurn pageTurn = gson.fromJson(o.getString("pageTurn"), new TypeToken<RepPageTurn>() {
                    }.getType());
                    ArrayList<KnowledgeBean> beans = gson.fromJson(o.getString("list"), new TypeToken<ArrayList<KnowledgeBean>>() {
                    }.getType());
                    if (pageTurn!=null && beans != null) {
                        view.showSearchKnowLedge(true, pageTurn, beans);
                    }else {
                        view.showSearchKnowLedge(false, pageTurn, beans);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showSearchKnowLedge(false, null, null);
                }
                break;
            case RepRequest.Knowledge.LIKE:
                //点赞成功
                view.setAfterLike(true, Like_Pos);
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        switch (requestCode) {
            case RepRequest.Knowledge.SEARCHTITLE:
               view.showSearchTitle(false, null);
                break;
            case RepRequest.Knowledge.SEARCHKNOWLEDGE:
                 view.showSearchKnowLedge(false, null, null);
                break;
            case RepRequest.Knowledge.LIKE:
                //点赞成功
                view.setAfterLike(true, Like_Pos);
                break;
        }
    }
}
