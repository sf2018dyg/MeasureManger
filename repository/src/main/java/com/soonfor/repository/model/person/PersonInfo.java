package com.soonfor.repository.model.person;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：DC-DingYG on 2018-09-23 12:58
 * 邮箱：dingyg012655@126.com
 */
public class PersonInfo implements Parcelable{
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
    private String storeName;

    public PersonInfo() {
    }

    protected PersonInfo(Parcel in) {
        name = in.readString();
        level = in.readDouble();
        avatar = in.readString();
        post = in.readString();
        storeName = in.readString();
    }

    public static final Creator<PersonInfo> CREATOR = new Creator<PersonInfo>() {
        @Override
        public PersonInfo createFromParcel(Parcel in) {
            return new PersonInfo(in);
        }

        @Override
        public PersonInfo[] newArray(int size) {
            return new PersonInfo[size];
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
        return avatar==null?"":avatar;
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

    public String getStoreName() {
        return storeName==null?"":storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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
        dest.writeString(storeName);
    }
}
