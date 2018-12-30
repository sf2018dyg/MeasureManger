package com.soonfor.measuremanager.me.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class PerformanceBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"pageTurn":{"rowCount":3,"pageNo":1,"pageSize":10},"list":[{"perfDate":"2017-12-11T00:00:00","customerId":"e609551b-a1ce-4c26-8b33-8c0a852b496d","customerName":"张先生","orderAmount":3000,"species":"提成","amount":200},{"perfDate":"2017-12-07T00:00:00","customerId":"775a271a-44ed-4d30-9ac7-c6c7412fdccc","customerName":"李先生","orderAmount":4000,"species":"佣金","amount":300},{"perfDate":"2017-11-17T00:00:00","customerId":"fd0ca335-9b45-4678-8909-bc93004f50f3","customerName":"王先生","orderAmount":2500,"species":"提成","amount":100}]}
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
         * pageTurn : {"rowCount":3,"pageNo":1,"pageSize":10}
         * list : [{"perfDate":"2017-12-11T00:00:00","customerId":"e609551b-a1ce-4c26-8b33-8c0a852b496d","customerName":"张先生","orderAmount":3000,"species":"提成","amount":200},{"perfDate":"2017-12-07T00:00:00","customerId":"775a271a-44ed-4d30-9ac7-c6c7412fdccc","customerName":"李先生","orderAmount":4000,"species":"佣金","amount":300},{"perfDate":"2017-11-17T00:00:00","customerId":"fd0ca335-9b45-4678-8909-bc93004f50f3","customerName":"王先生","orderAmount":2500,"species":"提成","amount":100}]
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
             * rowCount : 3
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
             * perfDate : 2017-12-11T00:00:00
             * customerId : e609551b-a1ce-4c26-8b33-8c0a852b496d
             * customerName : 张先生
             * orderAmount : 3000
             * species : 提成
             * amount : 200
             */

            private String perfDate;
            private String customerId;
            private String customerName;
            private int orderAmount;
            private String species;
            private int amount;

            public String getPerfDate() {
                return perfDate;
            }

            public void setPerfDate(String perfDate) {
                this.perfDate = perfDate;
            }

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public int getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(int orderAmount) {
                this.orderAmount = orderAmount;
            }

            public String getSpecies() {
                return species;
            }

            public void setSpecies(String species) {
                this.species = species;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }
        }
    }
}
