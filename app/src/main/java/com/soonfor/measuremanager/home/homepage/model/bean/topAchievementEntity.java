package com.soonfor.measuremanager.home.homepage.model.bean;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/29 16:22
 * 邮箱：suibozhu@139.com
 */

public class topAchievementEntity {
    String myRank;
    List<topAchievementDetail> designerRanks;

    public topAchievementEntity(String myRank, List<topAchievementDetail> designerRanks) {
        this.myRank = myRank;
        this.designerRanks = designerRanks;
    }

    public String getMyRank() {
        return myRank;
    }

    public void setMyRank(String myRank) {
        this.myRank = myRank;
    }

    public List<topAchievementDetail> getDetails() {
        return designerRanks;
    }

    public void setDetails(List<topAchievementDetail> designerRanks) {
        this.designerRanks = designerRanks;
    }
}
