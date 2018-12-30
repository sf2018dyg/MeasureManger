package com.soonfor.repository.model.knowledge;

/*
comment 回复@ 的内容
replyUser  回复者
*/

import android.os.Parcel;
import android.os.Parcelable;

public class ReplyBean implements Parcelable{

    private String _commentid;//贴子id
    private String id;//评论id
    private String content;
    private String user;
    private String userId;
    private String replyUser;
    private String replyUserId;

    protected ReplyBean(Parcel in) {
        _commentid = in.readString();
        id = in.readString();
        content = in.readString();
        user = in.readString();
        userId = in.readString();
        replyUser = in.readString();
        replyUserId = in.readString();
    }

    public static final Creator<ReplyBean> CREATOR = new Creator<ReplyBean>() {
        @Override
        public ReplyBean createFromParcel(Parcel in) {
            return new ReplyBean(in);
        }

        @Override
        public ReplyBean[] newArray(int size) {
            return new ReplyBean[size];
        }
    };

    public String get_commentid() {
        return _commentid==null?"":_commentid;
    }

    public void set_commentid(String _commentid) {
        this._commentid = _commentid;
    }

    public String getId() {
        return id==null?"":id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content==null?"":content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user==null?"":user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserId() {
        return userId==null?"":userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReplyUser() {
        return replyUser==null?"":replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    public String getReplyUserId() {
        return replyUserId==null?"":replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_commentid);
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(user);
        dest.writeString(userId);
        dest.writeString(replyUser);
        dest.writeString(replyUserId);
    }
}
