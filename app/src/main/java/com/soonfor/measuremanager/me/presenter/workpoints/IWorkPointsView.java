package com.soonfor.measuremanager.me.presenter.workpoints;

import com.soonfor.measuremanager.bases.IBaseView;
import com.soonfor.measuremanager.me.bean.WorkPointsBean;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public interface IWorkPointsView extends IBaseView {
    void showLoadingDialog();

    void closeLoadingDialog();

    void setData(WorkPointsBean bean);
}
