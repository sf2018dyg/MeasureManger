package com.soonfor.measuremanager.home.homepage.presenter;

import com.soonfor.measuremanager.home.homepage.model.bean.ListBean;
import com.soonfor.measuremanager.home.homepage.model.bean.TaskTypes;
import com.soonfor.measuremanager.home.homepage.model.bean.topAchievementEntity;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-11-27 18:36
 * 邮箱：dingyg012655@126.com
 */
public interface IHomePageView {

    void showPosterPics(int type, List<String> syPicsLt);//展示海报
    void showTaskNum(List<TaskTypes> numInfos);//显示任务数量
    void showGrabNum(String num);//待抢单数量
    void show10Tops(topAchievementEntity topEntity);//前10名
    void showNoticeNum(int num);//消息的数量
    void showTip(String msg);
}
