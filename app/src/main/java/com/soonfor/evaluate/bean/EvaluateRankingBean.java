package com.soonfor.evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.measuremanager.tools.CommonUtils;

/**
 * 作者：DC-DingYG on 2018-10-17 13:29
 * 邮箱：dingyg012655@126.com
 */
public class EvaluateRankingBean implements Parcelable{
    /**
     * [{"rankSort":1,"goodRank":0,"feedbackAvg":null,"good":0,"medium":0,"bad":0,"userId":"0118101000001","userName":"贵阳啊","fheadpic":null}]
     */
    private String userId;
    private String userName;//人员名称
    private String feedbackAvg;//回访评价分
    private String goodRank;//客户好评率
    private String bad;//差评;
    private String good;//好评
    private String medium;//中评
    private String rankSort;//评价排行
    private String fheadpic;//头像

    protected EvaluateRankingBean(Parcel in) {
        userId = in.readString();
        userName = in.readString();
        feedbackAvg = in.readString();
        goodRank = in.readString();
        bad = in.readString();
        good = in.readString();
        medium = in.readString();
        rankSort = in.readString();
        fheadpic = in.readString();
    }

    public static final Creator<EvaluateRankingBean> CREATOR = new Creator<EvaluateRankingBean>() {
        @Override
        public EvaluateRankingBean createFromParcel(Parcel in) {
            return new EvaluateRankingBean(in);
        }

        @Override
        public EvaluateRankingBean[] newArray(int size) {
            return new EvaluateRankingBean[size];
        }
    };

    public String getUserId() {
        return CommonUtils.formatStr(userId);
    }

    public String getUserName() {
        return CommonUtils.formatStr(userName);
    }

    public String getFeedbackAvg() {
        return CommonUtils.formatAdecimal(feedbackAvg);
    }

    public String getGoodRank() {
        return CommonUtils.formatPercentage(goodRank);
    }

    public String getBad() {
        return CommonUtils.formatAdecimal(bad);
    }

    public String getGood() {
        return CommonUtils.formatAdecimal(good);
    }

    public String getMedium() {
        return CommonUtils.formatAdecimal(medium);
    }

    public String getRankSort() {
        return CommonUtils.formatStr(rankSort);
    }

    public String getFheadpic() {
        return CommonUtils.formatStr(fheadpic);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(feedbackAvg);
        dest.writeString(goodRank);
        dest.writeString(bad);
        dest.writeString(good);
        dest.writeString(medium);
        dest.writeString(rankSort);
        dest.writeString(fheadpic);
    }
}
