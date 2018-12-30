package com.soonfor.measuremanager.home.lofting.model.bean.immloft;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by DingYg on 2018-02-11.
 */

public class SurveyMemberBean implements Parcelable{

    private String member;
    private List<MemberDetailBean> mdBeans;

    public SurveyMemberBean() {
    }

    protected SurveyMemberBean(Parcel in) {
        member = in.readString();
        mdBeans = in.createTypedArrayList(MemberDetailBean.CREATOR);
    }

    public static final Creator<SurveyMemberBean> CREATOR = new Creator<SurveyMemberBean>() {
        @Override
        public SurveyMemberBean createFromParcel(Parcel in) {
            return new SurveyMemberBean(in);
        }

        @Override
        public SurveyMemberBean[] newArray(int size) {
            return new SurveyMemberBean[size];
        }
    };

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public List<MemberDetailBean> getMdBeans() {
        return mdBeans;
    }

    public void setMdBeans(List<MemberDetailBean> mdBeans) {
        this.mdBeans = mdBeans;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(member);
        dest.writeTypedList(mdBeans);
    }
}
