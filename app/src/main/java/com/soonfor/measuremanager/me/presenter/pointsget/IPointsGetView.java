package com.soonfor.measuremanager.me.presenter.pointsget;

import com.soonfor.measuremanager.bases.IBaseView;
import com.soonfor.measuremanager.me.bean.PointsDetailsBean;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public interface IPointsGetView extends IBaseView {
    void showLoadingDialog();

    void closeLoadingDialog();

    void setData(PointsDetailsBean.DataBean bean);
}
