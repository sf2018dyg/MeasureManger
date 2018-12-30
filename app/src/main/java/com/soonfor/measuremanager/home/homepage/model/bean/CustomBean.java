package com.soonfor.measuremanager.home.homepage.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/19.
 */

public class CustomBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"pageTurn":{"rowCount":23,"pageNo":1,"pageSize":10},"list":[{"customerId":"85fbe76e-3248-4480-bfaa-d50210e5bf5b","orderNo":null,"customerStage":null,"inTime":"2017-11-22T12:21:23","customerName":"张三","phone":"13812345678","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":0,"receiptAmount":0,"lastFollowDate":"2017-11-25T12:21:23","lastFollowPerson":"陈明","importantNote":"本客户请在20171010前量尺，逾期将回归公海","customerType":"潜在客户"},{"customerId":"7fbbdce9-8fcb-4a5c-bce0-810164ad2b4f","orderNo":null,"customerStage":{"mainProcess":"A01","subProcess":"A0102","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-23T12:21:23","customerName":"王五","phone":"13812345722","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":0,"receiptAmount":0,"lastFollowDate":"2017-11-26T12:21:23","lastFollowPerson":"袁天","importantNote":null,"customerType":"潜在客户"},{"customerId":"b37a2187-0a88-47c7-bee4-8832e15b6dd5","orderNo":"A22322321199","customerStage":{"mainProcess":"A02","subProcess":"A0201","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-22T12:21:23","customerName":"张三","phone":"13812345678","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":0,"receiptAmount":0,"lastFollowDate":"0001-01-01T00:00:00","lastFollowPerson":null,"importantNote":null,"customerType":"待量尺"},{"customerId":"4a7ff76b-7bba-46cc-932c-c064c143aae5","orderNo":"A22322321199","customerStage":{"mainProcess":"A02","subProcess":"A0202","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-22T12:21:23","customerName":"张三","phone":"13812345678","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":300,"receiptAmount":1000,"lastFollowDate":"2017-11-13T08:30:30","lastFollowPerson":"陈明","importantNote":null,"customerType":"待量尺"},{"customerId":"1ddbf8cd-2aee-4598-b284-06dad309455c","orderNo":"A22322321199","customerStage":{"mainProcess":"A02","subProcess":"A0202","status":2,"processType":"AssignOrderProcess"},"inTime":"2017-11-22T12:21:23","customerName":"张三","phone":"13812345678","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":300,"receiptAmount":1000,"lastFollowDate":"2017-11-13T08:30:30","lastFollowPerson":"陈明","importantNote":null,"customerType":"待量尺"},{"customerId":"2b551fb0-496b-4adc-b02d-5fd971ff8ad2","orderNo":"A22322321199","customerStage":{"mainProcess":"A02","subProcess":"A0203","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-22T12:21:23","customerName":"张三","phone":"13812345678","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":300,"receiptAmount":1000,"lastFollowDate":"2017-11-17T08:30:30","lastFollowPerson":"陈明","importantNote":null,"customerType":"待量尺"},{"customerId":"3865b780-0212-4fbc-8b6a-6c6886b648c7","orderNo":"A223223564199","customerStage":{"mainProcess":"A03","subProcess":"A0301","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-26T12:21:23","customerName":"李四","phone":"13812345788","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":400,"receiptAmount":1200,"lastFollowDate":"2017-11-29T12:21:23","lastFollowPerson":"袁天","importantNote":null,"customerType":"待设计"},{"customerId":"dd7cabd3-4d85-4369-babd-d5f05ec2a0a2","orderNo":"A223223564199","customerStage":{"mainProcess":"A03","subProcess":"A0304","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-26T12:21:23","customerName":"李四","phone":"13812345788","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":400,"receiptAmount":1200,"lastFollowDate":"2017-11-30T12:21:23","lastFollowPerson":"袁天","importantNote":null,"customerType":"设计中"},{"customerId":"2c6b0bde-2052-43a9-ae12-f9f976991adf","orderNo":"A223223564122","customerStage":{"mainProcess":"A05","subProcess":"A0501","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-26T12:21:23","customerName":"李四","phone":"13812345789","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":400,"receiptAmount":1200,"lastFollowDate":"2017-12-05T12:21:23","lastFollowPerson":"袁天","importantNote":null,"customerType":"待复尺"},{"customerId":"8da65be0-b03c-4fdc-9109-15d31b2e0fb2","orderNo":"A223223564199","customerStage":{"mainProcess":"A03","subProcess":"A0305","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-26T12:21:23","customerName":"李四","phone":"13812345788","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":400,"receiptAmount":1200,"lastFollowDate":"2017-12-08T12:21:23","lastFollowPerson":"袁天","importantNote":null,"customerType":"设计中"}]}
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
         * pageTurn : {"rowCount":23,"pageNo":1,"pageSize":10}
         * list : [{"customerId":"85fbe76e-3248-4480-bfaa-d50210e5bf5b","orderNo":null,"customerStage":null,"inTime":"2017-11-22T12:21:23","customerName":"张三","phone":"13812345678","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":0,"receiptAmount":0,"lastFollowDate":"2017-11-25T12:21:23","lastFollowPerson":"陈明","importantNote":"本客户请在20171010前量尺，逾期将回归公海","customerType":"潜在客户"},{"customerId":"7fbbdce9-8fcb-4a5c-bce0-810164ad2b4f","orderNo":null,"customerStage":{"mainProcess":"A01","subProcess":"A0102","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-23T12:21:23","customerName":"王五","phone":"13812345722","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":0,"receiptAmount":0,"lastFollowDate":"2017-11-26T12:21:23","lastFollowPerson":"袁天","importantNote":null,"customerType":"潜在客户"},{"customerId":"b37a2187-0a88-47c7-bee4-8832e15b6dd5","orderNo":"A22322321199","customerStage":{"mainProcess":"A02","subProcess":"A0201","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-22T12:21:23","customerName":"张三","phone":"13812345678","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":0,"receiptAmount":0,"lastFollowDate":"0001-01-01T00:00:00","lastFollowPerson":null,"importantNote":null,"customerType":"待量尺"},{"customerId":"4a7ff76b-7bba-46cc-932c-c064c143aae5","orderNo":"A22322321199","customerStage":{"mainProcess":"A02","subProcess":"A0202","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-22T12:21:23","customerName":"张三","phone":"13812345678","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":300,"receiptAmount":1000,"lastFollowDate":"2017-11-13T08:30:30","lastFollowPerson":"陈明","importantNote":null,"customerType":"待量尺"},{"customerId":"1ddbf8cd-2aee-4598-b284-06dad309455c","orderNo":"A22322321199","customerStage":{"mainProcess":"A02","subProcess":"A0202","status":2,"processType":"AssignOrderProcess"},"inTime":"2017-11-22T12:21:23","customerName":"张三","phone":"13812345678","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":300,"receiptAmount":1000,"lastFollowDate":"2017-11-13T08:30:30","lastFollowPerson":"陈明","importantNote":null,"customerType":"待量尺"},{"customerId":"2b551fb0-496b-4adc-b02d-5fd971ff8ad2","orderNo":"A22322321199","customerStage":{"mainProcess":"A02","subProcess":"A0203","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-22T12:21:23","customerName":"张三","phone":"13812345678","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":300,"receiptAmount":1000,"lastFollowDate":"2017-11-17T08:30:30","lastFollowPerson":"陈明","importantNote":null,"customerType":"待量尺"},{"customerId":"3865b780-0212-4fbc-8b6a-6c6886b648c7","orderNo":"A223223564199","customerStage":{"mainProcess":"A03","subProcess":"A0301","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-26T12:21:23","customerName":"李四","phone":"13812345788","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":400,"receiptAmount":1200,"lastFollowDate":"2017-11-29T12:21:23","lastFollowPerson":"袁天","importantNote":null,"customerType":"待设计"},{"customerId":"dd7cabd3-4d85-4369-babd-d5f05ec2a0a2","orderNo":"A223223564199","customerStage":{"mainProcess":"A03","subProcess":"A0304","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-26T12:21:23","customerName":"李四","phone":"13812345788","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":400,"receiptAmount":1200,"lastFollowDate":"2017-11-30T12:21:23","lastFollowPerson":"袁天","importantNote":null,"customerType":"设计中"},{"customerId":"2c6b0bde-2052-43a9-ae12-f9f976991adf","orderNo":"A223223564122","customerStage":{"mainProcess":"A05","subProcess":"A0501","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-26T12:21:23","customerName":"李四","phone":"13812345789","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":400,"receiptAmount":1200,"lastFollowDate":"2017-12-05T12:21:23","lastFollowPerson":"袁天","importantNote":null,"customerType":"待复尺"},{"customerId":"8da65be0-b03c-4fdc-9109-15d31b2e0fb2","orderNo":"A223223564199","customerStage":{"mainProcess":"A03","subProcess":"A0305","status":0,"processType":"AssignOrderProcess"},"inTime":"2017-11-26T12:21:23","customerName":"李四","phone":"13812345788","sexType":"先生","houseAddress":"雅居乐天域小区","location":"89,223","depositPrice":400,"receiptAmount":1200,"lastFollowDate":"2017-12-08T12:21:23","lastFollowPerson":"袁天","importantNote":null,"customerType":"设计中"}]
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
             * rowCount : 23
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
             * customerId : 85fbe76e-3248-4480-bfaa-d50210e5bf5b
             * orderNo : null
             * customerStage : null
             * inTime : 2017-11-22T12:21:23
             * customerName : 张三
             * phone : 13812345678
             * sexType : 先生
             * houseAddress : 雅居乐天域小区
             * location : 89,223
             * depositPrice : 0
             * receiptAmount : 0
             * lastFollowDate : 2017-11-25T12:21:23
             * lastFollowPerson : 陈明
             * importantNote : 本客户请在20171010前量尺，逾期将回归公海
             * customerType : 潜在客户
             */

            private String customerId;
            private String orderNo;
            private String inTime;
            private String customerName;
            private String phone;
            private String sexType;
            private String houseAddress;
            private String location;
            private int depositPrice;
            private int receiptAmount;
            private String lastFollowDate;
            private String lastFollowPerson;
            private String importantNote;
            private String customerType;

            private CustomerStage customerStage;


            public CustomerStage getCustomerStage() {
                return customerStage;
            }

            public void setCustomerStage(CustomerStage customerStage) {
                this.customerStage = customerStage;
            }

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }



            public String getInTime() {
                return inTime;
            }

            public void setInTime(String inTime) {
                this.inTime = inTime;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getSexType() {
                return sexType;
            }

            public void setSexType(String sexType) {
                this.sexType = sexType;
            }

            public String getHouseAddress() {
                return houseAddress;
            }

            public void setHouseAddress(String houseAddress) {
                this.houseAddress = houseAddress;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public int getDepositPrice() {
                return depositPrice;
            }

            public void setDepositPrice(int depositPrice) {
                this.depositPrice = depositPrice;
            }

            public int getReceiptAmount() {
                return receiptAmount;
            }

            public void setReceiptAmount(int receiptAmount) {
                this.receiptAmount = receiptAmount;
            }

            public String getLastFollowDate() {
                return lastFollowDate;
            }

            public void setLastFollowDate(String lastFollowDate) {
                this.lastFollowDate = lastFollowDate;
            }

            public String getLastFollowPerson() {
                return lastFollowPerson;
            }

            public void setLastFollowPerson(String lastFollowPerson) {
                this.lastFollowPerson = lastFollowPerson;
            }

            public String getImportantNote() {
                return importantNote;
            }

            public void setImportantNote(String importantNote) {
                this.importantNote = importantNote;
            }

            public String getCustomerType() {
                return customerType;
            }

            public void setCustomerType(String customerType) {
                this.customerType = customerType;
            }

            public static class CustomerStage{
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
