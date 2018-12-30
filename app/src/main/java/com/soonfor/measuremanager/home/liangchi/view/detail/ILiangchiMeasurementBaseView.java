package com.soonfor.measuremanager.home.liangchi.view.detail;

import com.soonfor.measuremanager.bases.IBaseView;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans.mainbean;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 11:03
 * 邮箱：suibozhu@139.com
 */

public interface ILiangchiMeasurementBaseView extends IBaseView {
    void showLoadingDialog();
    void closeLoadingDialog();
    void getTaskCompleteInfo(mainbean mb);
    void showTip(String msg);
}
