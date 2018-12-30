package com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：DC-DingYG on 2018-09-11 17:34
 * 邮箱：dingyg012655@126.com
 */
public class WallPath implements Parcelable{
    private String url;
    private String wallCode;

    protected WallPath(Parcel in) {
        url = in.readString();
        wallCode = in.readString();
    }

    public static final Creator<WallPath> CREATOR = new Creator<WallPath>() {
        @Override
        public WallPath createFromParcel(Parcel in) {
            return new WallPath(in);
        }

        @Override
        public WallPath[] newArray(int size) {
            return new WallPath[size];
        }
    };

    public String getUrl() {
        return url==null?"":url;
    }

    public String getWallCode() {
        return wallCode==null?"":wallCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(wallCode);
    }
}
