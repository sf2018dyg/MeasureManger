package com.soonfor.measuremanager.afflatus.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:44
 * 邮箱：suibozhu@139.com
 */
/*
logo*/

public class partsBean implements Parcelable {

    private String id;
    private String pid;
    private String name;
    private String logo;//logo地址

    public partsBean(String id,String pid,String name,String logo) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    protected partsBean(Parcel in) {
        id = in.readString();
        pid = in.readString();
        name = in.readString();
        logo = in.readString();
    }

    public static final Creator<partsBean> CREATOR = new Creator<partsBean>() {
        @Override
        public partsBean createFromParcel(Parcel in) {
            return new partsBean(in);
        }

        @Override
        public partsBean[] newArray(int size) {
            return new partsBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pid);
        dest.writeString(name);
        dest.writeString(logo);
    }
}
