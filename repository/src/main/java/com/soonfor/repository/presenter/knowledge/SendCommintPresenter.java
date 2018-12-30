package com.soonfor.repository.presenter.knowledge;

import android.content.Context;

import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.ui.activity.knowledge.KnowledgeDetailActivity;
import com.soonfor.repository.view.knowledge.ISendCommentView;

/**
 * 作者：DC-DingYG on 2018-07-27 9:48
 * 邮箱：dingyg012655@126.com
 */
public class SendCommintPresenter extends RepBasePresenter<ISendCommentView> implements RepAsyncUtils.AsyncCallback{
    private ISendCommentView view;

    public SendCommintPresenter(ISendCommentView view) {
        this.view = view;
    }
    /**
     *  //回复评论
     * @param context
     * @param commId 评论id
     * @param replyuserId 回复对象id
     * @param content 内容
     */
    public void ReplyToComment(Context context, String commId, String replyuserId, String content){
        RepRequest.Knowledge.ReplyToComment(context, commId, replyuserId, content, this);
    }

    /**
     * 保存评论
     * @param cxt
     * @param knowledgeId
     * @param content
     */
    public void saveComment(Context cxt, String knowledgeId, String content){
        RepRequest.Knowledge.SaveComment(cxt, knowledgeId, content, this);
    }
    //收藏
    public void collect(Context cxt, String _id){
        RepRequest.Knowledge.Collect(cxt, _id, this);
    }
    //点赞
    public void like(Context cxt, String _id){
        RepRequest.Knowledge.Like(cxt, _id, this);
    }
    @Override
    public void success(int requestCode, String data) {
        view.closeLoadingDialog();
        switch (requestCode) {
            case RepRequest.Knowledge.SAVE_COMMENTREPLY://保存评论回复
                view.afterSaveReplay(true, data);
                break;
            case RepRequest.Knowledge.SAVE_COMMENT://保存评论
                view.afterSaveComment(true, data);
                break;
            case RepRequest.Knowledge.COLLECT://收藏
                view.setAfterCollect(true, KnowledgeDetailActivity.listPositon);
                break;
            case RepRequest.Knowledge.LIKE://点赞
                view.setAfterLike(true, KnowledgeDetailActivity.listPositon);
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        view.closeLoadingDialog();
        switch (requestCode) {
            case RepRequest.Knowledge.SAVE_COMMENTREPLY://保存评论回复
                view.afterSaveReplay(false, msg);
                break;
            case RepRequest.Knowledge.SAVE_COMMENT://保存评论
                view.afterSaveComment(false, msg);
                break;
            case RepRequest.Knowledge.COLLECT:
                view.setAfterCollect(false, KnowledgeDetailActivity.listPositon);
                break;
            case RepRequest.Knowledge.LIKE:
                view.setAfterLike(false, KnowledgeDetailActivity.listPositon);
                break;
        }
    }
}
