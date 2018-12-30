package com.soonfor.evaluate.presenter;

import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.evaluate.bean.Evaluate_CustomersToMeBean;

import java.util.List;

/**
 * Created by Administrator on 2018-02-26.
 */

public interface IEvaluate_CustomerToMeView {
    /**
     * 显示已评价列表数据
     */
    void showYList(boolean isSuccess, PageTurnBean pageBean, List<Evaluate_CustomersToMeBean> beans, String msg);

    /**
     * 显示待评价列表数据
     */
    void showNList(boolean isSuccess, PageTurnBean pageBean, List<Evaluate_CustomersToMeBean> beans, String msg);

    void showLoadingDialog();
    void closeLoadingDialog();
}
