package com.soonfor.measuremanager.me.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class PointsDetailsBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"list":[{"sourceName":"每日签到","occurTime":"2017-12-22T19:23:21","points":10,"attachUrl":"http://ax.xxcx.com/stest1.jpg","userId":"7446cd3e-1cdd-4a01-9983-e0baea84dc23"},{"sourceName":"抽取奖品","occurTime":"2017-12-20T12:23:21","points":-20,"attachUrl":"http://ax.xxcx.com/stest2.jpg","userId":"cb120204-0b45-4bf0-aa1b-59ac876618cd"},{"sourceName":"每日签到","occurTime":"2017-12-18T10:23:21","points":10,"attachUrl":"http://ax.xxcx.com/stest3.jpg","userId":"bc6d0cd9-f2db-4375-8bf6-be121ef6ea2a"},{"sourceName":"每日签到","occurTime":"2017-12-16T13:25:21","points":10,"attachUrl":"http://ax.xxcx.com/stest3.jpg","userId":"c48c7425-a582-4fc2-828c-597be7fb93b4"},{"sourceName":"抽取奖品","occurTime":"2017-12-12T15:25:21","points":-20,"attachUrl":"http://ax.xxcx.com/stest4.jpg","userId":"a272f4ca-9e1d-4b6a-bd29-23537ae4860c"},{"sourceName":"登记客户","occurTime":"2017-12-08T15:20:21","points":80,"attachUrl":"http://ax.xxcx.com/stest5.jpg","userId":"0d6bba37-8dce-471f-9b19-7b875f68c437"}]}
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
             * sourceName : 每日签到
             * occurTime : 2017-12-22T19:23:21
             * points : 10
             * attachUrl : http://ax.xxcx.com/stest1.jpg
             * userId : 7446cd3e-1cdd-4a01-9983-e0baea84dc23
             */

            private String sourceName;
            private String occurTime;
            private int points;
            private String attachUrl;
            private String userId;

            public String getSourceName() {
                return sourceName;
            }

            public void setSourceName(String sourceName) {
                this.sourceName = sourceName;
            }

            public String getOccurTime() {
                return occurTime;
            }

            public void setOccurTime(String occurTime) {
                this.occurTime = occurTime;
            }

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }

            public String getAttachUrl() {
                return attachUrl;
            }

            public void setAttachUrl(String attachUrl) {
                this.attachUrl = attachUrl;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}
