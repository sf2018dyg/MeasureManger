
package com.soonfor.repository.base;

/**
 * Created by kelvin on 2016/8/23.
 * 基础 Presenter
 */
public interface IRepBasePresenter {

    /**
     * 获取网络数据，更新界面
     *
     * @param isRefresh 是否是swiperefresh的
     */
    void getData(boolean isRefresh);

    /**
     * 加载更多数据
     */
    void getMoreData();

}