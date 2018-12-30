package com.soonfor.repository.presenter.knowledge;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.CommentBean;
import com.soonfor.repository.model.knowledge.KnowledgeDetailBean;
import com.soonfor.repository.view.knowledge.IKnowledgeDetailView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * 作者：DC-DingYG on 2018-07-20 11:04
 * 邮箱：dingyg012655@126.com
 */
public class KnowledgeDetailPresenter extends RepBasePresenter<IKnowledgeDetailView> implements RepAsyncUtils.AsyncCallback {

    private IKnowledgeDetailView view;
    private Context mContext;
    private String _id;
    private int positon;
    public KnowledgeDetailPresenter(IKnowledgeDetailView view) {
        this.view = view;
    }

    //请求详情信息
    public void getDetailData(Context context, String _id, boolean isRefresh) {
        this.mContext = context;
        this._id = _id;
//        if (!isRefresh) {
//            view.showLoadingDialog();
//        }
        RepRequest.Knowledge.getDetailData(context, _id, this);
    }
    //增加浏览数量
    public void addViewCount(Context cxt, String _id){
        RepRequest.Knowledge.addViewCount(cxt, _id, this);
    }

    /**
     * //请求评论列表
     * @param context
     * @param _id
     * @param pageno
     * @param isRepost //是否为重新请求当前页数据（为回复评论后刷新数据而制）
     */
    boolean isRepost = false;
    public void getCommentList(Context context, String _id, String pageno, boolean isRepost){
        this.isRepost = isRepost;
        RepRequest.Knowledge.getCommentList(context, _id, pageno,this);
    }
    //收藏
    public void collect(Context cxt, String _id, int positon){
        this.positon = positon;
        RepRequest.Knowledge.Collect(cxt, _id, this);
    }
    //点赞
    public void like(Context cxt, String _id, int positon){
        this.positon = positon;
        RepRequest.Knowledge.Like(cxt, _id, this);
    }
    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        switch (requestCode) {
            case RepRequest.Knowledge.GET_KNOWLEDGE_DETAIL:
                KnowledgeDetailBean detailBean = null;
                try {
                    detailBean = gson.fromJson(data, new TypeToken<KnowledgeDetailBean>() {
                    }.getType());
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
                view.showDetailData(detailBean);
                break;
            case RepRequest.Knowledge.GET_COMMENT_LIST:
                try {
                    JSONObject jo1 = new JSONObject(data);
                    RepPageTurn pageTurn1 = gson.fromJson(jo1.getString("pageTurn"), new TypeToken<RepPageTurn>() {
                    }.getType());
                    JSONArray jo1ja = jo1.getJSONArray("list");
                    if (jo1ja != null && jo1ja.length() > 0) {
                        ArrayList<CommentBean> commentList = new ArrayList<>();
                        for (int i = 0; i < jo1ja.length(); i++) {
                            CommentBean comment = gson.fromJson(jo1ja.getJSONObject(i).toString(), new TypeToken<CommentBean>() {}.getType());
                            commentList.add(comment);
                        }
                        view.setGetComments(pageTurn1, commentList, isRepost);
                    } else {
                        view.showNoDataHint(null);
                    }
                }catch (Exception e){}
                view.closeLoadingDialog();
                break;
            case RepRequest.Knowledge.COLLECT:
                view.setAfterCollect(true, positon);
                break;
            case RepRequest.Knowledge.LIKE:
                view.setAfterLike(true, positon);
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        switch (requestCode){
            case RepRequest.Knowledge.GET_KNOWLEDGE_DETAIL:
                view.showNoDataHint(msg);
                getCommentList(mContext, _id, "1", false);
                break;
            case RepRequest.Knowledge.GET_COMMENT_LIST:
                view.showNoDataHint(msg);
                break;
            case RepRequest.Knowledge.COLLECT:
                view.showNoDataHint(msg);
                view.setAfterCollect(false, positon);
                break;
            case RepRequest.Knowledge.LIKE:
                view.showNoDataHint(msg);
                view.setAfterLike(false, positon);
                break;
        }
    }
}
