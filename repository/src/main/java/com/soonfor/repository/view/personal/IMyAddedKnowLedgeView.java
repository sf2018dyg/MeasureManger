package com.soonfor.repository.view.personal;

import com.soonfor.repository.base.IRepBaseView;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.MyAddedKnowLedgeBean;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/2 0002 08:43
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public interface IMyAddedKnowLedgeView extends IRepBaseView {
    void setDatas(RepPageTurn pageTurn, List<MyAddedKnowLedgeBean> beans);
}
