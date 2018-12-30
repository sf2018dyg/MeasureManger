package com.soonfor.measuremanager.me.bean;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class PrizeRuleBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"name":"家用电风扇","id":"null","description":"1、奖品随抽随开!每天奖品数量有限,但参加抽奖次数无限/n/r 2、抽奖1次扣除相应工分/n/r 3、积分满1分，可以参加级数抽奖","workPoints":20,"thumbnail":"http://tb.xxx.com/xxss.jpg","rule":null}
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
        /**
         * name : 家用电风扇
         * id : null
         * description : 1、奖品随抽随开!每天奖品数量有限,但参加抽奖次数无限/n/r 2、抽奖1次扣除相应工分/n/r 3、积分满1分，可以参加级数抽奖
         * workPoints : 20
         * thumbnail : http://tb.xxx.com/xxss.jpg
         * rule : null
         */

        private String name;
        private String id;
        private String description;
        private int workPoints;
        private String thumbnail;
        private Object rule;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getWorkPoints() {
            return workPoints;
        }

        public void setWorkPoints(int workPoints) {
            this.workPoints = workPoints;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public Object getRule() {
            return rule;
        }

        public void setRule(Object rule) {
            this.rule = rule;
        }
    }
}
