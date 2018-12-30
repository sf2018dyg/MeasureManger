package com.soonfor.measuremanager.me.bean;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class MyPerformanceBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"monthName":"本月绩效","monthAmount":1200,"totalAmount":2000,"perfRule":"绩效规则..."}
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
         * monthName : 本月绩效
         * monthAmount : 1200
         * totalAmount : 2000
         * perfRule : 绩效规则...
         */

        private String monthName;
        private int monthAmount;
        private int totalAmount;
        private String perfRule;

        public String getMonthName() {
            return monthName;
        }

        public void setMonthName(String monthName) {
            this.monthName = monthName;
        }

        public int getMonthAmount() {
            return monthAmount;
        }

        public void setMonthAmount(int monthAmount) {
            this.monthAmount = monthAmount;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getPerfRule() {
            return perfRule;
        }

        public void setPerfRule(String perfRule) {
            this.perfRule = perfRule;
        }
    }
}
