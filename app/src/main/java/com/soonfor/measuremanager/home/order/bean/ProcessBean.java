package com.soonfor.measuremanager.home.order.bean;

import java.util.List;

/**
 * Created by ljc on 2018/1/19.
 */

public class ProcessBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"list":[{"orderNo":null,"orderType":"预订单","customerStage":{"mainProcess":"A01","subProcess":"A0102","status":1,"processType":"AssignOrderProcess"},"personType":null,"personName":null,"taskNo":null,"taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"0001-01-01T00:00:00","depositPrice":0,"receiptAmount":0},{"orderNo":null,"orderType":"预订单","customerStage":{"mainProcess":"A02","subProcess":"A0204","status":1,"processType":"AssignOrderProcess"},"personType":"测量员","personName":"李立 18812345678","taskNo":"2e44865f-22dd-4d81-a7d4-a8eb6cb63296","taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"2018-01-03T15:20:33","depositPrice":0,"receiptAmount":0},{"orderNo":null,"orderType":"预订单","customerStage":{"mainProcess":"A03","subProcess":"A0305","status":1,"processType":"AssignOrderProcess"},"personType":"设计师","personName":"李立 18812345678","taskNo":"9f2c3fda-e2a9-4787-9b75-8c83c7a9dfb5","taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"2018-01-06T15:20:33","depositPrice":0,"receiptAmount":0},{"orderNo":null,"orderType":"预订单","customerStage":{"mainProcess":"A04","subProcess":null,"status":1,"processType":"AssignOrderProcess"},"personType":null,"personName":null,"taskNo":"5e6b5bf7-08b8-459e-adbb-99e5777e2926","taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"2018-01-09T15:20:33","depositPrice":300,"receiptAmount":0},{"orderNo":null,"orderType":"预订单","customerStage":{"mainProcess":"A05","subProcess":"A0504","status":1,"processType":"AssignOrderProcess"},"personType":"设计师","personName":"李立 18812345678","taskNo":"4cb14d8f-9d93-4997-9d54-370a3cd24029","taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"2018-01-10T15:20:33","depositPrice":0,"receiptAmount":0},{"orderNo":null,"orderType":"预订单","customerStage":{"mainProcess":"A07","subProcess":null,"status":1,"processType":"AssignOrderProcess"},"personType":null,"personName":null,"taskNo":"aba5e853-987b-463b-9500-334924810f61","taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"2018-01-12T15:20:33","depositPrice":5000,"receiptAmount":0},{"orderNo":null,"orderType":"预订单","customerStage":null,"personType":null,"personName":null,"taskNo":"063be6d6-d593-4f22-af65-25e50372ce5f","taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"2018-01-13T15:20:33","depositPrice":0,"receiptAmount":0},{"orderNo":null,"orderType":"预订单","customerStage":{"mainProcess":"A08","subProcess":"A0804","status":1,"processType":"AssignOrderProcess"},"personType":"放样人员","personName":"蔡杰 18812345678","taskNo":"da9825ab-4240-4aaa-b868-1c45cdf4f858","taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"2018-01-15T15:20:33","depositPrice":0,"receiptAmount":0},{"orderNo":null,"orderType":"预订单","customerStage":{"mainProcess":"A09","subProcess":"A0904","status":1,"processType":"AssignOrderProcess"},"personType":null,"personName":null,"taskNo":"b348cd72-9168-4e7b-ad6f-b57bf1b6e587","taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"2018-01-16T15:20:33","depositPrice":0,"receiptAmount":5800},{"orderNo":null,"orderType":"预订单","customerStage":{"mainProcess":"A11","subProcess":"A1104","status":1,"processType":"AssignOrderProcess"},"personType":"送货人员","personName":"蔡杰 18812345678","taskNo":"aa0e1690-99ee-4880-9759-67f647cac857","taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"2018-01-20T15:20:33","depositPrice":0,"receiptAmount":0},{"orderNo":null,"orderType":null,"customerStage":null,"personType":null,"personName":null,"taskNo":null,"taskStartDate":"0001-01-01T00:00:00","taskCompleteDate":"0001-01-01T00:00:00","depositPrice":0,"receiptAmount":0},{"orderNo":null,"orderType":"预订单","customerStage":{"mainProcess":"A12","subProcess":"A1202","status":0,"processType":"AssignOrderProcess"},"personType":"安装人员","personName":"刘涛 18812345678","taskNo":"0127930c-87a7-4ab0-bbe9-18b46f06e8ee","taskStartDate":"2018-01-02T11:20:33","taskCompleteDate":"2018-01-22T15:20:33","depositPrice":0,"receiptAmount":0}]}
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
             * orderNo : null
             * orderType : 预订单
             * customerStage : {"mainProcess":"A01","subProcess":"A0102","status":1,"processType":"AssignOrderProcess"}
             * personType : null
             * personName : null
             * taskNo : null
             * taskStartDate : 2018-01-02T11:20:33
             * taskCompleteDate : 0001-01-01T00:00:00
             * depositPrice : 0
             * receiptAmount : 0
             */

            private String orderNo;
            private String orderType;
            private CustomerStageBean customerStage;
            private String userID;

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }

            private String personType;
            private String personName;
            private String taskNo;
            private String taskStartDate;
            private String taskCompleteDate;
            private double depositPrice;
            private double receiptAmount;

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public CustomerStageBean getCustomerStage() {
                return customerStage;
            }

            public void setCustomerStage(CustomerStageBean customerStage) {
                this.customerStage = customerStage;
            }

            public String getPersonType() {
                return personType;
            }

            public void setPersonType(String personType) {
                this.personType = personType;
            }

            public String getPersonName() {
                return personName;
            }

            public void setPersonName(String personName) {
                this.personName = personName;
            }

            public String getTaskNo() {
                return taskNo;
            }

            private String taskType;

            public String getTaskType() {
                return taskType;
            }

            public void setTaskType(String taskType) {
                this.taskType = taskType;
            }

            public void setTaskNo(String taskNo) {
                this.taskNo = taskNo;
            }

            public String getTaskStartDate() {
                return taskStartDate;
            }

            public void setTaskStartDate(String taskStartDate) {
                this.taskStartDate = taskStartDate;
            }

            public String getTaskCompleteDate() {
                return taskCompleteDate;
            }

            public void setTaskCompleteDate(String taskCompleteDate) {
                this.taskCompleteDate = taskCompleteDate;
            }

            public double getDepositPrice() {
                return depositPrice;
            }

            public void setDepositPrice(double depositPrice) {
                this.depositPrice = depositPrice;
            }

            public double getReceiptAmount() {
                return receiptAmount;
            }

            public void setReceiptAmount(double receiptAmount) {
                this.receiptAmount = receiptAmount;
            }

            public static class CustomerStageBean {
                /**
                 * mainProcess : A01
                 * subProcess : A0102
                 * status : 1
                 * processType : AssignOrderProcess
                 */

                private String mainProcess;
                private String subProcess;
                private int status;
                private String processType;

                public String getMainProcess() {
                    return mainProcess;
                }

                public void setMainProcess(String mainProcess) {
                    this.mainProcess = mainProcess;
                }

                public String getSubProcess() {
                    return subProcess;
                }

                public void setSubProcess(String subProcess) {
                    this.subProcess = subProcess;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getProcessType() {
                    return processType;
                }

                public void setProcessType(String processType) {
                    this.processType = processType;
                }
            }
        }
    }
}
