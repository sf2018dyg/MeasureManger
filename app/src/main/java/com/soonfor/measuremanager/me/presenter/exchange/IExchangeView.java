package com.soonfor.measuremanager.me.presenter.exchange;

import com.soonfor.measuremanager.bases.IBaseView;
import com.soonfor.measuremanager.me.bean.WorkPointsBean;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public interface IExchangeView extends IBaseView {
    void showLoadingDialog();

    void closeLoadingDialog();

    void setData(WorkPointsBean bean);
}
