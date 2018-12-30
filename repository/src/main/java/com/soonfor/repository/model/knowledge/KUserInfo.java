package com.soonfor.repository.model.knowledge;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：DC-DingYG on 2018-07-20 15:33
 * 邮箱：dingyg012655@126.com
 */
public class KUserInfo implements Parcelable{
    private String avatar;//头像
    private String nickName;//姓名
    private String id;//用户id

    public KUserInfo() {
    }

    protected KUserInfo(Parcel in) {
        avatar = in.readString();
        nickName = in.readString();
        id = in.readString();
    }

    public static final Creator<KUserInfo> CREATOR = new Creator<KUserInfo>() {
        @Override
        public KUserInfo createFromParcel(Parcel in) {
            return new KUserInfo(in);
        }

        @Override
        public KUserInfo[] newArray(int size) {
            return new KUserInfo[size];
        }
    };

    public String getAvatar() {
        return avatar==null?"":avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName==null?"":nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getId() {
        return id==null?"":id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
        dest.writeString(nickName);
        dest.writeString(id);
    }
}
