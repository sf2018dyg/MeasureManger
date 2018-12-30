package com.soonfor.repository.view.seekhelp;

import com.soonfor.repository.base.IRepBaseView;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.model.knowledge.SearchTitleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-06-20 13:47
 * 邮箱：dingyg012655@126.com
 */
public interface ISearchView extends IRepBaseView {
    void showSearchTitle(boolean isSuccess, List<SearchTitleBean> bean);
    void showSearchKnowLedge(boolean isSuccess, RepPageTurn pageTurn, ArrayList<KnowledgeBean> beans);
    void setAfterLike(boolean isSuccess, int position);//点击点赞按钮后
}
