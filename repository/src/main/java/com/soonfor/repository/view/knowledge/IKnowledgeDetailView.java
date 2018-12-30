package com.soonfor.repository.view.knowledge;

import com.soonfor.repository.base.IRepBaseView;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.CommentBean;
import com.soonfor.repository.model.knowledge.KnowledgeDetailBean;

import java.util.ArrayList;

/**
 * 作者：DC-DingYG on 2018-07-20 11:05
 * 邮箱：dingyg012655@126.com
 */
public interface IKnowledgeDetailView extends IRepBaseView {

    void showDetailData(KnowledgeDetailBean detailBean);
    void setGetComments(RepPageTurn pageTurn, ArrayList<CommentBean> commentList, boolean isRePost);
    void setAfterLike(boolean isSuccess, int position);//点赞/取消点赞后
    void setAfterCollect(boolean isSuccess, int positon);//收藏/取消收藏后
}
