package com.soonfor.measuremanager.me.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class MyGrowthBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"currentValue":3600,"monthList":[{"monthName":"9月","growthValue":1000},{"monthName":"10月","growthValue":900},{"monthName":"11月","growthValue":2000},{"monthName":"当前值","growthValue":3600}]}
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
         * currentValue : 3600
         * monthList : [{"monthName":"9月","growthValue":1000},{"monthName":"10月","growthValue":900},{"monthName":"11月","growthValue":2000},{"monthName":"当前值","growthValue":3600}]
         */

        private int currentValue;
        private List<MonthListBean> monthList;

        public int getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(int currentValue) {
            this.currentValue = currentValue;
        }

        public List<MonthListBean> getMonthList() {
            return monthList;
        }

        public void setMonthList(List<MonthListBean> monthList) {
            this.monthList = monthList;
        }

        public static class MonthListBean {
            /**
             * monthName : 9月
             * growthValue : 1000
             */

            private String monthName;
            private int growthValue;

            public String getMonthName() {
                return monthName;
            }

            public void setMonthName(String monthName) {
                this.monthName = monthName;
            }

            public int getGrowthValue() {
                return growthValue;
            }

            public void setGrowthValue(int growthValue) {
                this.growthValue = growthValue;
            }
        }
    }
}
