package com.soonfor.measuremanager.me.presenter.my_information;

import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.me.bean.AnnouncementBean;
import com.soonfor.measuremanager.me.bean.InformBean;
import com.soonfor.measuremanager.me.fragment.my_information.AnnouncementFragment;
import com.soonfor.measuremanager.me.fragment.my_information.InformFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-02-26.
 */

public class MyInformation_FragmentPresenter implements IBasePresenter{

    private InformFragment inFragment;
    private AnnouncementFragment anFragment;

    public MyInformation_FragmentPresenter(InformFragment inFragment) {
        this.inFragment = inFragment;
    }
    public MyInformation_FragmentPresenter(AnnouncementFragment anFragment) {
        this.anFragment = anFragment;
    }

    /**
     * 获取通知
     */
    public void getInformList(){
        List<InformBean> infromList = new ArrayList<>();
        InformBean inform = new InformBean();
        inform.setInformType(1);
        inform.setInformName("任务");
        inform.setInformTime("2018年1月12日 12:20");
        inform.setInformStauts(0);
        String content1 = "店长给你分配了设计任务\n";
        String content2 = "方案沟通时间 1月8日 15:00\n"+ "请尽快接收哦！";
        String content = content1 + content2;
        inform.setInformContent(content);
        infromList.add(inform);

        inform = new InformBean();
        inform.setInformType(0);
        inform.setInformName("系统通知");
        inform.setInformTime("2018年1月10日 12:20");
        inform.setInformStauts(0);
        content = "恭喜！你的设计师登记提升为\n[资深设计师]";
        inform.setInformContent(content);
        infromList.add(inform);

        inform = new InformBean();
        inform.setInformType(3);
        inform.setInformName("评论回复");
        inform.setInformTime("2018年1月12日 10:20");
        inform.setInformStauts(0);
         content1 = "李先生：@您 ";
         content2 = "针对该设计方案，我觉得很赞";
         content = content1 + content2;
        inform.setInformContent(content);
        infromList.add(inform);

        inform = new InformBean();
        inform.setInformType(0);
        inform.setInformName("系统通知");
        inform.setInformTime("2018年1月9日 12:20");
        inform.setInformStauts(1);
        content1 = "恭喜！您的本月任务已完成 ";
        content2 = "50%";
        content = content1 + content2;
       inform.setInformContent(content);
        infromList.add(inform);
        inFragment.showInformList(infromList, "");
    }
    /**
     * 获取公告
     */
    public void getAnnList(){
        List<AnnouncementBean> annlist = new ArrayList<>();
        for (int i=0;i<5;i++) {
            AnnouncementBean annBean = new AnnouncementBean();
            annBean.setfAnnTheme("杨帆2018年|数夫展会预告");
            annBean.setfAnnTime("2018年1月12日 12:20");
            annBean.setfAnnDesc("数字化浪潮风起云涌，数夫以领先的技术实力和强大的综合能力...");
            annBean.setReadingVolume("2000");
            annBean.setfAnnImagePath("http://img0.imgtn.bdimg.com/it/u=1594387174,2650700642&fm=27&gp=0.jpg");
            annlist.add(annBean);
        }
        anFragment.showAnnList(annlist, null);
    }
}
