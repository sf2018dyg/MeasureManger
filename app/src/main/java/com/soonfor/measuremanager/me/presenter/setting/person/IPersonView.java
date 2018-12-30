package com.soonfor.measuremanager.me.presenter.setting.person;

import com.soonfor.measuremanager.bases.IBaseView;
import com.soonfor.measuremanager.me.bean.PersonDataBean;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public interface IPersonView extends IBaseView {
    void showLoadingDialog();

    void closeLoadingDialog();

    void setPersonData(PersonDataBean bean);
}
