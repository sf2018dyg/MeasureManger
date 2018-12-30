package com.soonfor.measuremanager.me.presenter.mygrowth;

import com.soonfor.measuremanager.bases.IBaseView;
import com.soonfor.measuremanager.me.bean.MyGrowthBean;
import com.soonfor.measuremanager.me.bean.MyGrowthItemBean;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public interface IMyGrowthView extends IBaseView {
    void setData(MyGrowthBean growthBean);
    void setList(MyGrowthItemBean itemBean);
}
