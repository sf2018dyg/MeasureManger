package com.soonfor.repository.model.knowledge;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.dingyg.richeditor.RichEditUtils;

import java.util.ArrayList;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/2 0002 11:28
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class MyAddedKnowLedgeBean implements Parcelable{

//    {"id":"251be737917242579700bfb38a5910c7","title":"ddd","viewCount":82,"summary":"gggg"}

    private String id;
    private String title;//标题
    private String viewCount;
    private String summary;//摘要
    private KUserInfo userInfo;//用户信息
    private long publishTime;//发布时间
    private ArrayList<String> picList;//图片地址列表
    private String video;//视频文件地址
    private String videoFramPic;//第一帧图片
    private String likeCount;//点赞数
    private String commentCount;//评论数
    private String pageviewCount;//浏览数
    private int isLike;//是否点赞 0否1是
    private boolean isEditable = false;//是否打开选中按钮
    private String auditStatusStr;//审核状态含义
    private String auditMsg;//审核不通过原因
    private String auditStatus;//审核状态
    private long createTime;//提交时间

    protected MyAddedKnowLedgeBean(Parcel in) {
        id = in.readString();
        title = in.readString();
        viewCount = in.readString();
        summary = in.readString();
        userInfo = in.readParcelable(KUserInfo.class.getClassLoader());
        publishTime = in.readLong();
        picList = in.createStringArrayList();
        video = in.readString();
        videoFramPic = in.readString();
        likeCount = in.readString();
        commentCount = in.readString();
        pageviewCount = in.readString();
        isLike = in.readInt();
        isEditable = in.readByte() != 0;
        auditStatusStr = in.readString();
        auditMsg = in.readString();
        auditStatus = in.readString();
        createTime = in.readLong();
    }

    public static final Creator<MyAddedKnowLedgeBean> CREATOR = new Creator<MyAddedKnowLedgeBean>() {
        @Override
        public MyAddedKnowLedgeBean createFromParcel(Parcel in) {
            return new MyAddedKnowLedgeBean(in);
        }

        @Override
        public MyAddedKnowLedgeBean[] newArray(int size) {
            return new MyAddedKnowLedgeBean[size];
        }
    };

    public String getAuditStatusStr() {
        return auditStatusStr==null?"":auditStatusStr;
    }

    public void setAuditStatusStr(String auditStatusStr) {
        this.auditStatusStr = auditStatusStr;
    }

    public String getAuditMsg() {
        return auditMsg==null?"":auditMsg;
    }

    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg;
    }

    public String getAuditStatus() {
        return auditStatus==null?"":auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getViewCount() {
        return viewCount==null?"":viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public String getSummary() {
        return summary == null ? "" : summary;
    }

    public ArrayList<String> getPicList() {
        return picList;
    }

    public String getVideo() {
        return video == null ? "" : video;
    }

    public String getVideoFramPic() {
        if(!getVideo().equals("")){
            if(picList!=null && picList.size()>0){
                return picList.get(0);
            }else
                return "";
        }
        return "";
    }

    public String getLikeCount() {
        return likeCount == null || likeCount.equals("") ? "0" : likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount == null || commentCount.equals("") ? "0" : commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getPageviewCount() {
        return pageviewCount == null ? "" : pageviewCount;
    }

    public KUserInfo getUserInfo() {
        return userInfo == null ? new KUserInfo() : userInfo;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(viewCount);
        dest.writeString(summary);
        dest.writeParcelable(userInfo, flags);
        dest.writeLong(publishTime);
        dest.writeStringList(picList);
        dest.writeString(video);
        dest.writeString(videoFramPic);
        dest.writeString(likeCount);
        dest.writeString(commentCount);
        dest.writeString(pageviewCount);
        dest.writeInt(isLike);
        dest.writeByte((byte) (isEditable ? 1 : 0));
        dest.writeString(auditStatusStr);
        dest.writeString(auditMsg);
        dest.writeString(auditStatus);
        dest.writeLong(createTime);
    }
}
