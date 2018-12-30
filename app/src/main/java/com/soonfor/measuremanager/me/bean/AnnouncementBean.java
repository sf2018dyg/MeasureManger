package com.soonfor.measuremanager.me.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018-02-26.
 */

public class AnnouncementBean implements Parcelable{
    private String fAnnTheme;//通告主题
    private String fAnnTime;//通告发布时间
    private String fAnnImagePath;//通告封面图片路径
    private String fAnnDesc;//通告活动介绍
    private String readingVolume;//阅读量

    public AnnouncementBean() {
    }

    protected AnnouncementBean(Parcel in) {
        fAnnTheme = in.readString();
        fAnnTime = in.readString();
        fAnnImagePath = in.readString();
        fAnnDesc = in.readString();
        readingVolume = in.readString();
    }

    public String getfAnnTheme() {
        return fAnnTheme==null?"":fAnnTheme;
    }

    public void setfAnnTheme(String fAnnTheme) {
        this.fAnnTheme = fAnnTheme;
    }

    public String getfAnnTime() {
        return fAnnTime==null?"":fAnnTime;
    }

    public void setfAnnTime(String fAnnTime) {
        this.fAnnTime = fAnnTime;
    }

    public String getfAnnImagePath() {
        return fAnnImagePath==null?"":fAnnImagePath;
    }

    public void setfAnnImagePath(String fAnnImagePath) {
        this.fAnnImagePath = fAnnImagePath;
    }

    public String getfAnnDesc() {
        return fAnnDesc==null?"":fAnnDesc;
    }

    public void setfAnnDesc(String fAnnDesc) {
        this.fAnnDesc = fAnnDesc;
    }

    public String getReadingVolume() {
        return readingVolume==null?"":readingVolume;
    }

    public void setReadingVolume(String readingVolume) {
        this.readingVolume = readingVolume;
    }

    public static final Creator<AnnouncementBean> CREATOR = new Creator<AnnouncementBean>() {
        @Override
        public AnnouncementBean createFromParcel(Parcel in) {
            return new AnnouncementBean(in);
        }

        @Override
        public AnnouncementBean[] newArray(int size) {
            return new AnnouncementBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fAnnTheme);
        dest.writeString(fAnnTime);
        dest.writeString(fAnnImagePath);
        dest.writeString(fAnnDesc);
        dest.writeString(readingVolume);
    }
}
