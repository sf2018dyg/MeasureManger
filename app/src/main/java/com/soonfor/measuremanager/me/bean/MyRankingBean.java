package com.soonfor.measuremanager.me.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class MyRankingBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"list":[{"userName":"张航程","userId":"1ac04ff1-873d-43e2-86c0-4b6ea12ff7f2","indicatorValue":1000,"ranking":5,"isMe":0,"avatarUrl":"http://ava.xx.com/ss.jpg"},{"userName":"李昌钰 ","userId":"99b7856d-1ebe-4897-a115-b3bdd3d59b2b","indicatorValue":900,"ranking":6,"isMe":1,"avatarUrl":"http://ava.xx.com/ss.jpg"},{"userName":"王庆诚 ","userId":"b76d9ed5-d419-4fd5-b478-1d7db1fc4941","indicatorValue":710,"ranking":7,"isMe":0,"avatarUrl":"http://ava.xx.com/ss.jpg"}]}
     * success : true
     */

    private int msgcode;
    private String msg;
    private DataBean data;
    private boolean success;

    public int getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(int msgcode) {
        this.msgcode = msgcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * userName : 张航程
             * userId : 1ac04ff1-873d-43e2-86c0-4b6ea12ff7f2
             * indicatorValue : 1000
             * ranking : 5
             * isMe : 0
             * avatarUrl : http://ava.xx.com/ss.jpg
             */

            private String userName;
            private String userId;
            private int indicatorValue;
            private int ranking;
            private int isMe;
            private String avatarUrl;

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getIndicatorValue() {
                return indicatorValue;
            }

            public void setIndicatorValue(int indicatorValue) {
                this.indicatorValue = indicatorValue;
            }

            public int getRanking() {
                return ranking;
            }

            public void setRanking(int ranking) {
                this.ranking = ranking;
            }

            public int getIsMe() {
                return isMe;
            }

            public void setIsMe(int isMe) {
                this.isMe = isMe;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }
        }
    }
}
