package com.soonfor.measuremanager.me.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018-02-26.
 */

public class InformBean implements Parcelable{
    private int informType;//通知类型 0 系统通知 1 任务 2评论回复
    private String informName;//通知名称
    private String informTime;//通知时间
    private int informStauts;//通知状态
    private String informContent;//通知内容

    public InformBean() {
    }

    protected InformBean(Parcel in) {
        informType = in.readInt();
        informName = in.readString();
        informTime = in.readString();
        informStauts = in.readInt();
        informContent = in.readString();
    }

    public static final Creator<InformBean> CREATOR = new Creator<InformBean>() {
        @Override
        public InformBean createFromParcel(Parcel in) {
            return new InformBean(in);
        }

        @Override
        public InformBean[] newArray(int size) {
            return new InformBean[size];
        }
    };

    public int getInformType() {
        return informType;
    }

    public void setInformType(int informType) {
        this.informType = informType;
    }

    public String getInformName() {
        return informName;
    }

    public void setInformName(String informName) {
        this.informName = informName;
    }

    public String getInformTime() {
        return informTime;
    }

    public void setInformTime(String informTime) {
        this.informTime = informTime;
    }

    public int getInformStauts() {
        return informStauts;
    }

    public void setInformStauts(int informStauts) {
        this.informStauts = informStauts;
    }

    public String getInformContent() {
        return informContent;
    }

    public void setInformContent(String informContent) {
        this.informContent = informContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(informType);
        dest.writeString(informName);
        dest.writeString(informTime);
        dest.writeInt(informStauts);
        dest.writeString(informContent);
    }
}
