package com.soonfor.measuremanager.home.order.presenter;


import com.soonfor.measuremanager.bases.IBasePresenter;

/**
 * Created by Administrator on 2018/1/20.
 */

public interface IOrderDetailPresenter extends IBasePresenter {
    void getOrderDetail(String orderNo);
}
