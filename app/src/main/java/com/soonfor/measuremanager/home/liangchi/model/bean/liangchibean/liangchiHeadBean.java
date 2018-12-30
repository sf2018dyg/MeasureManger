package com.soonfor.measuremanager.home.liangchi.model.bean.liangchibean;

import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/2 0002 16:38
 * 邮箱：suibozhu@139.com
 */

public class liangchiHeadBean {
    private PageTurnBean pageTurn;
    private List<LiangChiBean> list;

    public PageTurnBean getPageTurn() {
        return pageTurn;
    }

    public void setPageTurn(PageTurnBean pageTurn) {
        this.pageTurn = pageTurn;
    }

    public List<LiangChiBean> getList() {
        return list;
    }

    public void setList(List<LiangChiBean> list) {
        this.list = list;
    }
}
