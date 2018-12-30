package com.soonfor.measuremanager.home.liangchi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:44
 * 邮箱：suibozhu@139.com
 * 户型图
 */

public class LiangChiImmeaditelyBean implements Parcelable {

    /*[{"fimgpath":"","fcdate":1521046396837,"fobsplanid":"1521046396367","fmeafloor":"1楼","fstatus":"0","fcname":"刘常鑫"}]*/

    private String fobsplanid;////合同号
    private String fimgpath; //户型图路径
    private String fcname;//客户名称
    private String customBulid;//楼盘
    private String customAddress;//客户地址
    private String fmeafloor;//户型图类型
    private String fcdate;//修改时间
    private String taskTypeValue;//
    private String taskType;// 状态
    private String fstatus;//
    private String taskNo;//
    private Boolean isNews = true;//是否新建量房
    private String floorName;//
    private String orderNo;//订单号

    public LiangChiImmeaditelyBean() {
    }

    public String getFobsplanid() {
        return fobsplanid;
    }

    public void setFobsplanid(String fobsplanid) {
        this.fobsplanid = fobsplanid;
    }

    public String getFimgpath() {
        return fimgpath;
    }

    public void setFimgpath(String fimgpath) {
        this.fimgpath = fimgpath;
    }

    public String getFcname() {
        return fcname;
    }

    public void setFcname(String fcname) {
        this.fcname = fcname;
    }

    public String getCustomBulid() {
        return customBulid;
    }

    public void setCustomBulid(String customBulid) {
        this.customBulid = customBulid;
    }

    public String getCustomAddress() {
        return customAddress;
    }

    public void setCustomAddress(String customAddress) {
        this.customAddress = customAddress;
    }

    public String getFmeafloor() {
        return fmeafloor;
    }

    public void setFmeafloor(String fmeafloor) {
        this.fmeafloor = fmeafloor;
    }

    public String getFcdate() {
        return fcdate;
    }

    public void setFcdate(String fcdate) {
        this.fcdate = fcdate;
    }

    public String getTaskTypeValue() {
        return taskTypeValue;
    }

    public void setTaskTypeValue(String taskTypeValue) {
        this.taskTypeValue = taskTypeValue;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getFstatus() {
        return fstatus;
    }

    public void setFstatus(String fstatus) {
        this.fstatus = fstatus;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public Boolean getNews() {
        return isNews;
    }

    public void setNews(Boolean news) {
        isNews = news;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    protected LiangChiImmeaditelyBean(Parcel in) {
        fobsplanid = in.readString();
        fimgpath = in.readString();
        fcname = in.readString();
        customBulid = in.readString();
        customAddress = in.readString();
        fmeafloor = in.readString();
        fcdate = in.readString();
        taskTypeValue = in.readString();
        taskType = in.readString();
        fstatus = in.readString();
        taskNo = in.readString();
        isNews = in.readByte() != 0;
        floorName = in.readString();
        orderNo = in.readString();
    }

    public static final Creator<LiangChiImmeaditelyBean> CREATOR = new Creator<LiangChiImmeaditelyBean>() {
        @Override
        public LiangChiImmeaditelyBean createFromParcel(Parcel in) {
            return new LiangChiImmeaditelyBean(in);
        }

        @Override
        public LiangChiImmeaditelyBean[] newArray(int size) {
            return new LiangChiImmeaditelyBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fobsplanid);
        dest.writeString(fimgpath);
        dest.writeString(fcname);
        dest.writeString(customBulid);
        dest.writeString(customAddress);
        dest.writeString(fmeafloor);
        dest.writeString(fcdate);
        dest.writeString(taskTypeValue);
        dest.writeString(taskType);
        dest.writeString(fstatus);
        dest.writeString(taskNo);
        dest.writeByte((byte) (isNews ? 1 : 0));
        dest.writeString(floorName);
        dest.writeString(orderNo);
    }
}
