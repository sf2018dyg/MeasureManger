package com.soonfor.measuremanager.afflatus.bean;

import java.io.Serializable;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/26 9:55
 * 邮箱：suibozhu@139.com
 */
/*
huifuzhe 回复者
beihuifuzhe 被回复者 @ 的那个
comment 回复@ 的内容
ise  是否需要显示@
*/

public class CommentDetailBean implements Serializable {
    private String id;
    private String content;
    private String user;
    private String userId;
    private String replyUser;
    private String replyUserId;
    private String bigID;//外面最大的那一个

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getBigID() {
        return bigID;
    }

    public void setBigID(String bigID) {
        this.bigID = bigID;
    }
}
