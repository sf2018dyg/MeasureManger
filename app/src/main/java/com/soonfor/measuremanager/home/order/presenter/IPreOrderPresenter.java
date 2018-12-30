package com.soonfor.measuremanager.home.order.presenter;


import com.soonfor.measuremanager.bases.IBasePresenter;

/**
 * Created by Administrator on 2018/1/20.
 */

public interface IPreOrderPresenter extends IBasePresenter {
    void getPreOrder(String orderNo);
    /**
     * 获取网络数据，更新界面
     *
     * @param isRefresh 是否是swiperefresh的
     */
    void getData(boolean isRefresh);


}
