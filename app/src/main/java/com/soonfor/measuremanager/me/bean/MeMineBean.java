package com.soonfor.measuremanager.me.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/1/16 0016.
 * 我的
 */

public class MeMineBean implements Parcelable {

    /**
     * name : 李金陵
     * level : 3.5
     * avatar : http://ar.xx.com/ssees.jpg
     * post : 导购
     * points : 256
     * performance : 3000
     * storeName : 东莞总店
     * growthValue : 3000
     */

    private String name;
    private double level;
    private String avatar;
    private String post;
    private int points;
    private int performance;
    private String storeName;
    private int growthValue;

    protected MeMineBean(Parcel in) {
        name = in.readString();
        level = in.readDouble();
        avatar = in.readString();
        post = in.readString();
        points = in.readInt();
        performance = in.readInt();
        storeName = in.readString();
        growthValue = in.readInt();
    }

    public static final Creator<MeMineBean> CREATOR = new Creator<MeMineBean>() {
        @Override
        public MeMineBean createFromParcel(Parcel in) {
            return new MeMineBean(in);
        }

        @Override
        public MeMineBean[] newArray(int size) {
            return new MeMineBean[size];
        }
    };

    public String getName() {
        return name==null?"":name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public String getAvatar() {
        return avatar == null?"":avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPost() {
        return post==null?"":post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public String getStoreName() {
        return storeName==null?"":storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getGrowthValue() {
        return growthValue;
    }

    public void setGrowthValue(int growthValue) {
        this.growthValue = growthValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(level);
        dest.writeString(avatar);
        dest.writeString(post);
        dest.writeInt(points);
        dest.writeInt(performance);
        dest.writeString(storeName);
        dest.writeInt(growthValue);
    }
}
