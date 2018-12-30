package com.soonfor.measuremanager.home.lofting.model.bean.immloft;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.measuremanager.tools.CommonUtils;

/**
 * Created by DingYg on 2018-03-12.
 */

public class LoftLookDetailBean implements Parcelable{
    private String name;
    private int icon;
    private String orderNo;
    private String fobsplanid;
    private String fcTaskNo;//复尺任务号

    public LoftLookDetailBean(String name,int icon,String orderNo,String fcTaskNo, String fobsplanid) {
        this.name = name;
        this.icon = icon;
        this.orderNo = orderNo;
        this.fobsplanid = fobsplanid;
        this.fcTaskNo = fcTaskNo;
    }

    protected LoftLookDetailBean(Parcel in) {
        name = in.readString();
        icon = in.readInt();
        orderNo = in.readString();
        fobsplanid = in.readString();
        fcTaskNo = in.readString();
    }

    public static final Creator<LoftLookDetailBean> CREATOR = new Creator<LoftLookDetailBean>() {
        @Override
        public LoftLookDetailBean createFromParcel(Parcel in) {
            return new LoftLookDetailBean(in);
        }

        @Override
        public LoftLookDetailBean[] newArray(int size) {
            return new LoftLookDetailBean[size];
        }
    };

    public String getName() {
        return CommonUtils.formatStr(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getOrderNo() {
        return CommonUtils.formatStr(orderNo);
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFobsplanid() {
        return CommonUtils.formatStr(fobsplanid);
    }

    public void setFobsplanid(String fobsplanid) {
        this.fobsplanid = fobsplanid;
    }

    public String getFcTaskNo() {
        return CommonUtils.formatStr(fcTaskNo);
    }

    public void setFcTaskNo(String fcTaskNo) {
        this.fcTaskNo = fcTaskNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(icon);
        dest.writeString(orderNo);
        dest.writeString(fobsplanid);
        dest.writeString(fcTaskNo);
    }
}
