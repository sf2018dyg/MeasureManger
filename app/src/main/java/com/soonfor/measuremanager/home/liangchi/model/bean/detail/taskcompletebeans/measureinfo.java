package com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/5 0005 09:37
 * 邮箱：suibozhu@139.com
 */

public class measureinfo {
    private String finishTime;
    private String unitsPicture;
    private List<String> livePictures;
    private List<rooms> rooms;

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getUnitsPicture() {
        return unitsPicture;
    }

    public void setUnitsPicture(String unitsPicture) {
        this.unitsPicture = unitsPicture;
    }

    public List<String> getLivePictures() {
        return livePictures;
    }

    public void setLivePictures(List<String> livePictures) {
        this.livePictures = livePictures;
    }

    public List<com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans.rooms> getRooms() {
        return rooms;
    }

    public void setRooms(List<com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans.rooms> rooms) {
        this.rooms = rooms;
    }
}
