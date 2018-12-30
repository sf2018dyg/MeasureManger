package com.soonfor.repository.view.knowledge;

import com.soonfor.repository.base.IRepBaseView;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;

import java.util.ArrayList;

/**
 * 作者：DC-DingYG on 2018-06-14 15:11
 * 邮箱：dingyg012655@126.com
 */
public interface IKnowledgeView extends IRepBaseView {
    void setGetCategory(boolean isSuccess, String msg);//设置分类标题
    void setGetHotList(boolean isSuccess, RepPageTurn pageTurn, ArrayList<KnowledgeBean> hotbeans, String msg);//设置热门列表
    void setGetHnowledwList(boolean isSuccess, RepPageTurn pageTurn, ArrayList<KnowledgeBean> kbeans, String msg);//设置知识列表
    void setAfterLike(boolean isSuccess, int position);//点击点赞按钮后
}
