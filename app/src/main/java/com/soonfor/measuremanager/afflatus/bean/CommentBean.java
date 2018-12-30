package com.soonfor.measuremanager.afflatus.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/26 9:55
 * 邮箱：suibozhu@139.com
 */
/*
headpath 头像地址
name 名字
louceng 楼层
huifudate 回复日期
comment 回复内容
*/

/*{
        "id":"040ac69980374ed2a499e8b1a26c8080",
        "content":"苹果测试一下吧",
        "createTime":1538294442000,
        "creator":"mt_ACS057399990001",
        "userInfo":{
        "id":"mt_ACS057399990001",
        "nickName":"i测试门店",
        "avatar":""
        },
        "replyList":[
        {
        "id":"d157af6359214df199d3df05bb137ba5",
        "content":"你说的话？不能确定",
        "user":"i测试门店",
        "userId":"mt_ACS057399990001",
        "replyUser":"i测试门店",
        "replyUserId":"mt_ACS057399990001"
        }
        ],
        "timeString":"09-30 16:00"
        },*/

public class CommentBean implements Serializable {

    private String id;
    private String content;
    private long createTime;
    private String creator;
    private UserInfos userInfo;
    private List<CommentDetailBean> replyList;

    public class UserInfos{
        private String id;
        private String nickName;
        private String avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar==null?"":avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public UserInfos getUserInfo() {
        return userInfo==null?new UserInfos():userInfo;
    }

    public void setUserInfo(UserInfos userInfo) {
        this.userInfo = userInfo;
    }

    public List<CommentDetailBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<CommentDetailBean> replyList) {
        this.replyList = replyList;
    }
}
