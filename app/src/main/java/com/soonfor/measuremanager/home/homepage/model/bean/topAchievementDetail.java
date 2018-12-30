package com.soonfor.measuremanager.home.homepage.model.bean;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/29 16:22
 * 邮箱：suibozhu@139.com
 */

public class topAchievementDetail {
    String name;
    String avatarUrl;
    String ranking;

    public topAchievementDetail(String name, String avatarUrl, String ranking) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
}
