package com.soonfor.measuremanager.home.liangchi.view.detail;

import com.soonfor.measuremanager.bases.IBaseView;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 11:03
 * 邮箱：suibozhu@139.com
 */

public interface ILiangchiDetailBaseView extends IBaseView {
    void showLoadingDialog();
    void closeLoadingDialog();
    void showTip(String msg);
    /**
     * 获取任务详情
     * **/
    void getProgressTaskInfo(beanTotal b);
}
