package com.soonfor.repository.presenter.personal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.view.personal.IFavoriteView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/2 0002 08:42
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class FavoritePresenter extends RepBasePresenter<IFavoriteView> implements RepAsyncUtils.AsyncCallback {

    private IFavoriteView view;
    private int Like_Pos;

    public FavoritePresenter(IFavoriteView view) {
        this.view = view;
    }

    //点赞
    public void like(Context cxt, int position, String _id) {
        this.Like_Pos = position;
        RepRequest.Knowledge.Like(cxt, _id, this);
    }

    public void getFavorite(Context context, int pageNo, int pageSize) {
        RepRequest.getMyFavorite(context, pageNo, pageSize, this);
    }

    public void delFavorite(Context context, ArrayList<String> ids) {
        RepRequest.delMyFavorite(context, ids, this);
    }

    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        switch (requestCode) {
            case RepRequest.Personal.GET_MYFAVORITE:
                try {
                    JSONObject o = new JSONObject(data);
                    RepPageTurn pageTurn = gson.fromJson(o.getString("pageTurn"), new TypeToken<RepPageTurn>() {
                    }.getType());
                    List<KnowledgeBean> beans = gson.fromJson(o.getString("list"), new TypeToken<List<KnowledgeBean>>() {
                    }.getType());

                    view.setDatas(pageTurn, beans);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case RepRequest.Knowledge.LIKE:
                //点赞成功
                view.setAfterLike(true, Like_Pos);
                break;
            case RepRequest.Personal.DEL_FAVORITE:
                view.afterDel(data);
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        if(msg.contains("您的WLAN和移动网络均未连接")){
            view.showNetError(msg);
        }else {
            view.showNoDataHint(msg);
        }
    }
}
