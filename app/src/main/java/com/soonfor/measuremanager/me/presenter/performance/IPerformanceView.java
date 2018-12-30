package com.soonfor.measuremanager.me.presenter.performance;

import com.soonfor.measuremanager.bases.IBaseView;
import com.soonfor.measuremanager.me.bean.MyPerformanceBean;
import com.soonfor.measuremanager.me.bean.PerformanceBean;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public interface IPerformanceView extends IBaseView {

    void setData(MyPerformanceBean bean);
    void setListData(PerformanceBean.DataBean bean);
}
