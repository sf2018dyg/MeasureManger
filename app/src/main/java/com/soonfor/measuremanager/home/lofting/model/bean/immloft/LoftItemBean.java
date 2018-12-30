package com.soonfor.measuremanager.home.lofting.model.bean.immloft;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.measuremanager.tools.CommonUtils;

/**
 * Created by DingYg on 2018-03-08.
 * 放样明细实体
 */

public class LoftItemBean implements Parcelable{

    private String taskNo;//
    private String taskType;// 状态
    private String forderNo;
    private String fcname;//名称
    private String fimgpath;//图片路径
    private String fcdate;//日期
    private String fmeafloor;//户型名称
    private String fobsplanid;//合同号
    private String fstatus;//任务状态

    private String customBulid;//楼盘
    private String customAddress;//客户地址

    public LoftItemBean() {
    }


    protected LoftItemBean(Parcel in) {
        taskNo = in.readString();
        taskType = in.readString();
        forderNo = in.readString();
        fcname = in.readString();
        fimgpath = in.readString();
        fcdate = in.readString();
        fmeafloor = in.readString();
        fobsplanid = in.readString();
        fstatus = in.readString();
        customBulid = in.readString();
        customAddress = in.readString();
    }

    public static final Creator<LoftItemBean> CREATOR = new Creator<LoftItemBean>() {
        @Override
        public LoftItemBean createFromParcel(Parcel in) {
            return new LoftItemBean(in);
        }

        @Override
        public LoftItemBean[] newArray(int size) {
            return new LoftItemBean[size];
        }
    };

    public String getTaskNo() {
        return CommonUtils.formatStr(taskNo);
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskType() {
        return CommonUtils.formatStr(taskType);
    }

    public String getForderNo() {
        return forderNo;
    }

    public void setForderNo(String forderNo) {
        this.forderNo = forderNo;
    }

    public void setTaskType(String taskType) {
        this.taskType = CommonUtils.formatStr(taskType);
    }

    public String getFcname() {
        return CommonUtils.formatStr(fcname);
    }

    public void setFcname(String fcname) {
        this.fcname = fcname;
    }

    public String getFimgpath() {
        return CommonUtils.formatStr(fimgpath);
    }

    public void setFimgpath(String fimgpath) {
        this.fimgpath = fimgpath;
    }

    public String getFcdate() {
        return CommonUtils.formatStr(fcdate);
    }

    public void setFcdate(String fcdate) {
        this.fcdate = fcdate;
    }

    public String getFmeafloor() {
        return CommonUtils.formatStr(fmeafloor);
    }

    public void setFmeafloor(String fmeafloor) {
        this.fmeafloor = fmeafloor;
    }

    public String getFobsplanid() {
        return CommonUtils.formatStr(fobsplanid);
    }

    public void setFobsplanid(String fobsplanid) {
        this.fobsplanid = fobsplanid;
    }

    public String getFstatus() {
        return CommonUtils.formatStr(fstatus);
    }

    public void setFstatus(String fstatus) {
        this.fstatus = fstatus;
    }

    public String getCustomBulid() {
        return CommonUtils.formatStr(customBulid);
    }

    public void setCustomBulid(String customBulid) {
        this.customBulid = customBulid;
    }

    public String getCustomAddress() {
        return CommonUtils.formatStr(customAddress);
    }

    public void setCustomAddress(String customAddress) {
        this.customAddress = customAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskNo);
        dest.writeString(taskType);
        dest.writeString(forderNo);
        dest.writeString(fcname);
        dest.writeString(fimgpath);
        dest.writeString(fcdate);
        dest.writeString(fmeafloor);
        dest.writeString(fobsplanid);
        dest.writeString(fstatus);
        dest.writeString(customBulid);
        dest.writeString(customAddress);
    }
}
