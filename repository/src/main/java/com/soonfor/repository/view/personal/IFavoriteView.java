package com.soonfor.repository.view.personal;

import com.soonfor.repository.base.IRepBaseView;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/2 0002 08:43
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public interface IFavoriteView extends IRepBaseView {
    void setDatas(RepPageTurn pageTurn, List<KnowledgeBean> beans);
    void setAfterLike(boolean isSuccess, int position);//点击点赞按钮后
    void afterDel(String data);
}
