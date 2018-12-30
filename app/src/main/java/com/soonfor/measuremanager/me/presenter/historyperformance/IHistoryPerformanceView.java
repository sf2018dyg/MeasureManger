package com.soonfor.measuremanager.me.presenter.historyperformance;

import com.soonfor.measuremanager.bases.IBaseView;
import com.soonfor.measuremanager.me.bean.PerformanceBean;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public interface IHistoryPerformanceView extends IBaseView {
    void setListData(PerformanceBean.DataBean bean);
}
