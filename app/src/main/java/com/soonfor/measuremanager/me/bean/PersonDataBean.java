package com.soonfor.measuremanager.me.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/1/16 0016.
 * 个人资料对象
 */

public class PersonDataBean implements Parcelable{

    private String nickName;//姓名
    private String avatar;//头像地址
    private String post;//职位
    private String phone;//电话
    private String birthday;//生日
    private String sex;//性别
    private String qrCard;//二维码名片

    private String shop;//所在门店
    private String design_experience;//设计经验
    private String goodstyles;//擅长风格
    private String design_idea;//设计理念
    private String fens;//粉丝数量

    public PersonDataBean() {
    }

    protected PersonDataBean(Parcel in) {
        nickName = in.readString();
        avatar = in.readString();
        post = in.readString();
        phone = in.readString();
        birthday = in.readString();
        sex = in.readString();
        qrCard = in.readString();
        shop = in.readString();
        design_experience = in.readString();
        goodstyles = in.readString();
        design_idea = in.readString();
    }


    public static final Creator<PersonDataBean> CREATOR = new Creator<PersonDataBean>() {
        @Override
        public PersonDataBean createFromParcel(Parcel in) {
            return new PersonDataBean(in);
        }

        @Override
        public PersonDataBean[] newArray(int size) {
            return new PersonDataBean[size];
        }
    };

    public String getNickName() {
        return nickName==null?"":nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday==null?"":birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex==null?"":sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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


    public String getPhone() {
        return phone==null?"":phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQrCard() {
        return qrCard==null?"":qrCard;
    }

    public void setQrCard(String qrCard) {
        this.qrCard = qrCard;
    }

    public String getShop() {
        return shop==null?"":shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getDesign_experience() {
        return design_experience==null?"":design_experience;
    }

    public String getFens() {
        return fens==null?"":fens;
    }

    public void setFens(String fens) {
        this.fens = fens;
    }

    public void setDesign_experience(String design_experience) {
        this.design_experience = design_experience;
    }

    public String getGoodstyles() {
        return goodstyles==null?"":goodstyles;
    }

    public void setGoodstyles(String goodstyles) {
        this.goodstyles = goodstyles;
    }

    public String getDesign_idea() {
        return design_idea==null?"":design_idea;
    }

    public void setDesign_idea(String design_idea) {
        this.design_idea = design_idea;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickName);
        dest.writeString(avatar);
        dest.writeString(post);
        dest.writeString(phone);
        dest.writeString(birthday);
        dest.writeString(sex);
        dest.writeString(qrCard);
        dest.writeString(shop);
        dest.writeString(design_experience);
        dest.writeString(goodstyles);
        dest.writeString(design_idea);
        dest.writeString(fens);
    }
}
