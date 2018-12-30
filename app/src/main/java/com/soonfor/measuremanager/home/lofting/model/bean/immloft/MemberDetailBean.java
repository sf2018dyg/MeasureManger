package com.soonfor.measuremanager.home.lofting.model.bean.immloft;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DingYg on 2018-02-11.
 */

public class MemberDetailBean implements Parcelable{

    private String goodname;
    private String goodsize;

    public MemberDetailBean() {
    }

    protected MemberDetailBean(Parcel in) {
        goodname = in.readString();
        goodsize = in.readString();
    }

    public static final Creator<MemberDetailBean> CREATOR = new Creator<MemberDetailBean>() {
        @Override
        public MemberDetailBean createFromParcel(Parcel in) {
            return new MemberDetailBean(in);
        }

        @Override
        public MemberDetailBean[] newArray(int size) {
            return new MemberDetailBean[size];
        }
    };

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getGoodsize() {
        return goodsize;
    }

    public void setGoodsize(String goodsize) {
        this.goodsize = goodsize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goodname);
        dest.writeString(goodsize);
    }
}
