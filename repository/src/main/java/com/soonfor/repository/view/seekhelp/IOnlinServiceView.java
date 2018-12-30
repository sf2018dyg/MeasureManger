package com.soonfor.repository.view.seekhelp;

import com.soonfor.repository.base.IRepBaseView;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;

import java.util.ArrayList;

/**
 * 作者：DC-DingYG on 2018-06-20 14:09
 * 邮箱：dingyg012655@126.com
 */
public interface IOnlinServiceView extends IRepBaseView {
    void showSearchKnowLedge(boolean isSuccess, RepPageTurn pageTurn, ArrayList<KnowledgeBean> beans, String msg);
}
