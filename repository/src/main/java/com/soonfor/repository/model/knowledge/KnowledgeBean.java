package com.soonfor.repository.model.knowledge;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.dingyg.richeditor.RichEditUtils;
import com.dingyg.richeditor.utils.SDCardUtil;

import java.util.ArrayList;

/**
 * 作者：DC-DingYG on 2018-06-14 16:57
 * 邮箱：dingyg012655@126.com
 */
public class KnowledgeBean implements Parcelable{

//    {"id":"251be737917242579700bfb38a5910c7","title":"ddd","viewCount":82,"summary":"gggg"}

    private String id;
    private String title;//标题
    private String viewCount;
    private String summary;//摘要
    private KUserInfo userInfo;//用户信息
    private long publishTime;//发布时间
    private ArrayList<String> picList;//图片地址列表
    private String video;//视频文件地址
    private String framePath;//视频第一帧图片地址
    private String likeCount;//点赞数
    private String commentCount;//评论数
    private String pageviewCount;//浏览数
    private int isLike;//是否点赞 0否1是
    private boolean isEditable = false;//是否打开选中按钮

    protected KnowledgeBean(Parcel in) {
        id = in.readString();
        title = in.readString();
        viewCount = in.readString();
        summary = in.readString();
        userInfo = in.readParcelable(KUserInfo.class.getClassLoader());
        publishTime = in.readLong();
        picList = in.createStringArrayList();
        video = in.readString();
        framePath = in.readString();
        likeCount = in.readString();
        commentCount = in.readString();
        pageviewCount = in.readString();
        isLike = in.readInt();
        isEditable = in.readByte() != 0;
    }

    public static final Creator<KnowledgeBean> CREATOR = new Creator<KnowledgeBean>() {
        @Override
        public KnowledgeBean createFromParcel(Parcel in) {
            return new KnowledgeBean(in);
        }

        @Override
        public KnowledgeBean[] newArray(int size) {
            return new KnowledgeBean[size];
        }
    };

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getTitle() {
        if(title==null){
            return "";
        }else {
            return title.replace("<em>","<font color='#0599fd'>").replace("</em>","</font>");
        }
    }

    public long getPublishTime() {
        return publishTime;
    }

    public String getId() {
        return id==null?"":id;
    }

    public String getSummary() {
        if(summary==null){
            return "";
        }else {
            return summary.replace("<em>","<font color='#0599fd'>").replace("</em>","</font>");
        }
    }

    public ArrayList<String> getPicList() {
        return picList;
    }

    public String getVideo() {
        return video==null?"":video;
    }

    public String getFramePath() {
        if(!getVideo().equals("")){
            if(picList!=null && picList.size()>0){
                return picList.get(0);
            }else
                return "";
        }
        return "";
    }

    public String getLikeCount() {
        return likeCount==null||likeCount.equals("")?"0":likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount==null||commentCount.equals("")?"0":commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getPageviewCount() {
        return pageviewCount==null?"":pageviewCount;
    }

    public KUserInfo getUserInfo() {
        return userInfo==null?new KUserInfo():userInfo;
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
        dest.writeString(framePath);
        dest.writeString(likeCount);
        dest.writeString(commentCount);
        dest.writeString(pageviewCount);
        dest.writeInt(isLike);
        dest.writeByte((byte) (isEditable ? 1 : 0));
    }
}
