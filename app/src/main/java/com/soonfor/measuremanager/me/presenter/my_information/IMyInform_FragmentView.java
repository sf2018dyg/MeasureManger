package com.soonfor.measuremanager.me.presenter.my_information;

import com.soonfor.measuremanager.me.bean.AnnouncementBean;
import com.soonfor.measuremanager.me.bean.InformBean;

import java.util.List;

/**
 * Created by Administrator on 2018-02-26.
 */

public interface IMyInform_FragmentView {
    /**
     * 显示通知列表数据
     */
    public void showInformList(List<InformBean> informBeans, String msg);

    /**
     * 显示公告列表数据
     */
    public void showAnnList(List<AnnouncementBean> annBeans, String msg);
}
