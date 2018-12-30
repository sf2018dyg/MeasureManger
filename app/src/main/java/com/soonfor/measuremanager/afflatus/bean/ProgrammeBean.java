package com.soonfor.measuremanager.afflatus.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:44
 * 邮箱：suibozhu@139.com
 */
/*
fangwei
alias 别名
desc
list<tuzhi>
*/

public class ProgrammeBean implements Parcelable {

    private String fangwei;
    private String alias;
    private String desc;
    private List<ProgrammeDetail> ltdetail;

    public ProgrammeBean(String fangwei, String alias, String desc, List<ProgrammeDetail> ltdetail) {
        this.fangwei = fangwei;
        this.alias = alias;
        this.desc = desc;
        this.ltdetail = ltdetail;
    }

    public String getFangwei() {
        return fangwei;
    }

    public void setFangwei(String fangwei) {
        this.fangwei = fangwei;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ProgrammeDetail> getLtdetail() {
        return ltdetail;
    }

    public void setLtdetail(List<ProgrammeDetail> ltdetail) {
        this.ltdetail = ltdetail;
    }

    protected ProgrammeBean(Parcel in) {
        fangwei = in.readString();
        alias = in.readString();
        desc = in.readString();
    }

    public static final Creator<ProgrammeBean> CREATOR = new Creator<ProgrammeBean>() {
        @Override
        public ProgrammeBean createFromParcel(Parcel in) {
            return new ProgrammeBean(in);
        }

        @Override
        public ProgrammeBean[] newArray(int size) {
            return new ProgrammeBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fangwei);
        dest.writeString(alias);
        dest.writeString(desc);
    }
}
