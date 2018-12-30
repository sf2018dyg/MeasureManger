package com.soonfor.repository.model.knowledge;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.repository.tools.DateTools;

import java.util.ArrayList;

/**
 * 作者：DC-DingYG on 2018/7/20 13:50
 * 邮箱：dingyg012655@126.com
 */
/*
"id":"a891f1d0a3ef4ce49cce068eb037077e",
                "content":"哈哈哈哈哈！不错不错！小姐姐约不约？",
                "createTime":1532481712000,
                "creator":"mt_1008611",
                "userInfo":{
                    "id":"mt_1008611",
                    "nickName":"测试门店（南城店）",
                    "avatar":""
                },
                "replyList":[
                    {
                        "id":"0f43b3b3cf2b4cf7a83b3485d6ec9db0",
                        "content":"约吖！",
                        "user":"测试门店（南城店）",
                        "userId":"mt_1008611",
                        "replyUser":"测试门店（南城店）",
                        "replyUserId":"mt_1008611"
                    }
                ],
                "timeString":"1天前"
*/

public class CommentBean implements Parcelable{
    private String id;
    private String content;//内容
    private long createTime;//创帖时间
    private String creator;
    private String timeString;
    private KUserInfo userInfo;
    private ArrayList<ReplyBean> replyList;

    public CommentBean() {
    }

    public CommentBean(String id) {
        this.id = id;
    }

    protected CommentBean(Parcel in) {
        id = in.readString();
        content = in.readString();
        createTime = in.readLong();
        creator = in.readString();
        timeString = in.readString();
        userInfo = in.readParcelable(KUserInfo.class.getClassLoader());
        replyList = in.createTypedArrayList(ReplyBean.CREATOR);
    }

    public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
        @Override
        public CommentBean createFromParcel(Parcel in) {
            return new CommentBean(in);
        }

        @Override
        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };

    public String getId() {
        return id==null?"":id;
    }

    public String getContent() {
        return content==null?"":content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getCreator() {
        return creator==null?"":creator;
    }

    public String getTimeString() {
        String time = DateTools.getTimestamp(getCreateTime(),"yyyy-MM-dd HH:mm");
        return timeString==null?time:timeString;
    }

    public KUserInfo getUserInfo() {
        return userInfo==null?new KUserInfo():userInfo;
    }

    public ArrayList<ReplyBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(ArrayList<ReplyBean> replyList) {
        this.replyList = replyList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(content);
        dest.writeLong(createTime);
        dest.writeString(creator);
        dest.writeString(timeString);
        dest.writeParcelable(userInfo, flags);
        dest.writeTypedList(replyList);
    }
}
