package com.soonfor.repository.model.knowledge;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 作者：DC-DingYG on 2018-07-20 15:03
 * 邮箱：dingyg012655@126.com
 */
public class KnowledgeDetailBean implements Parcelable{

    private String id;
    private String title;//标题
    private String publisher;//登录用户
    private KUserInfo userInfo;//用户信息
    private String content;//内容
    private long publishTime;//发布时间
    private ArrayList<FileBean> fileList;//附件列表
    private String collectCount;//收藏数量
    private String likeCount;//点赞数
    private String commentCount;//评论数
    private String viewCount;//浏览数
    private int isLike;//是否点赞 0否1是
    private int isCollect;//是否收藏 0否1是

    public KnowledgeDetailBean() {
    }

    protected KnowledgeDetailBean(Parcel in) {
        id = in.readString();
        title = in.readString();
        publisher = in.readString();
        userInfo = in.readParcelable(KUserInfo.class.getClassLoader());
        content = in.readString();
        publishTime = in.readLong();
        fileList = in.createTypedArrayList(FileBean.CREATOR);
        collectCount = in.readString();
        likeCount = in.readString();
        commentCount = in.readString();
        viewCount = in.readString();
        isLike = in.readInt();
        isCollect = in.readInt();
    }

    public static final Creator<KnowledgeDetailBean> CREATOR = new Creator<KnowledgeDetailBean>() {
        @Override
        public KnowledgeDetailBean createFromParcel(Parcel in) {
            return new KnowledgeDetailBean(in);
        }

        @Override
        public KnowledgeDetailBean[] newArray(int size) {
            return new KnowledgeDetailBean[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() { return publisher == null ? "" : publisher; }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public ArrayList<FileBean> getFileList() {
        return fileList;
    }

    public String getCollectCount() {
        return collectCount == null ||collectCount.equals("")? "0": collectCount;
    }

    public String getViewCount() {
        return viewCount == null ||viewCount.equals("")? "0": viewCount;
    }

    public String getLikeCount() {
        return likeCount == null ||likeCount.equals("")? "0": likeCount;
    }

    public String getCommentCount() {
        return commentCount == null ||collectCount.equals("")? "0" : commentCount;
    }

    public KUserInfo getUserInfo() {
        return userInfo == null ? new KUserInfo() : userInfo;
    }

    public void setCollectCount(String collectCount) {
        this.collectCount = collectCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeParcelable(userInfo, flags);
        dest.writeString(content);
        dest.writeLong(publishTime);
        dest.writeTypedList(fileList);
        dest.writeString(collectCount);
        dest.writeString(likeCount);
        dest.writeString(commentCount);
        dest.writeString(viewCount);
        dest.writeInt(isLike);
        dest.writeInt(isCollect);
    }
}
