package com.soonfor.measuremanager.me.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class MyGrowthItemBean {

    /**
     * pageTurn : {"rowCount":6,"pageNo":1,"pageSize":10}
     * list : [{"name":"完善资料","growthDate":"2017-12-12T12:22:30","value":20},{"name":"录入新客资料","growthDate":"2017-12-11T11:22:30","value":20},{"name":"订单成交","growthDate":"2017-12-10T12:55:30","value":20},{"name":"我的课程学习","growthDate":"2017-12-09T12:10:30","value":10},{"name":"我的考试未通过","growthDate":"2017-12-02T12:22:30","value":-20},{"name":"分享经验","growthDate":"2017-12-01T12:22:30","value":80}]
     */

    private PageTurnBean pageTurn;
    private List<ListBean> list;

    public PageTurnBean getPageTurn() {
        return pageTurn;
    }

    public void setPageTurn(PageTurnBean pageTurn) {
        this.pageTurn = pageTurn;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class PageTurnBean {
        /**
         * rowCount : 6
         * pageNo : 1
         * pageSize : 10
         */

        private int rowCount;
        private int pageNo;
        private int pageSize;

        public int getRowCount() {
            return rowCount;
        }

        public void setRowCount(int rowCount) {
            this.rowCount = rowCount;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

    public static class ListBean {
        /**
         * name : 完善资料
         * growthDate : 2017-12-12T12:22:30
         * value : 20
         */

        private String name;
        private String growthDate;
        private int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGrowthDate() {
            return growthDate;
        }

        public void setGrowthDate(String growthDate) {
            this.growthDate = growthDate;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
