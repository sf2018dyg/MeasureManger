package com.soonfor.measuremanager.home.login.view;

import com.soonfor.measuremanager.bases.IBaseView;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public interface ILoginView extends IBaseView {
    void showLoadingDialog();

    void closeLoadingDialog();

    void setPassword(String password);

    void userName(String userName);
}
