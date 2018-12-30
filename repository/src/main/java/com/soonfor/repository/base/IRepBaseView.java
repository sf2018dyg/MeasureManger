package com.soonfor.repository.base;

import android.support.annotation.Nullable;

/**
 * 作者：DC-DingYG on 2018-04-27 9:03
 * 邮箱：dingyg012655@126.com
 * 基础 BaseView 接口
 */
public interface IRepBaseView {

    /**
     * 显示加载动画
     */
    void showLoadingDialog();

    void showLoadingDialog(String actionName);
    /**
     * 隐藏加载
     */
    void closeLoadingDialog();
    /**
     * 显示加载动画
     */
    void showLoading();

    void hideLoading();

    /**
     * 显示网络错误
     */
    void showNetError(String msg);

    /**
     * 完成刷新, 新增控制刷新
     */
    public void finishRefresh();

    /**
     * 有数据时刷新界面
     */
    public void showDataToView(String returnData);

    /**
     * 无数据时显示界面提示
     */
    public void showNoDataHint(String msg);

}
