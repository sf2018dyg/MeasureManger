package com.soonfor.measuremanager.home.order.bean;

import java.util.List;

/**
 * 销货订单详情
 * Created by Administrator on 2018/1/20.
 */

public class OrderDetailBean {

    /**
     * msgcode : 0
     * msg : 成功
     * data : {"fordid":996273534,"orderNo":"A201712260003","customerId":6195,"contractId":"nl","guideName":"倪莉","guidePhone":"13312541696","customerName":"杨仕智2","customerPhone":"13728665225","customerAddress":"home xosk d sidkk","emgType":"","staffName":"","staffPhone":"","installDate":"2017-12-26 00:00:00.0","activityPackage":"","remark":"","totalReceivable":12321,"totalReceived":15222,"totalUnbundled":12321,"fGoodsAmt":12321,"fFare":0,"fTotalRebate":0,"fTaxAmt":0,"fUrgentAmt":0,"fBreakAmt":0,"receivable":12321,"funds":[{"amount":12321,"name":"贷款金额"},{"amount":0,"name":"加收运费"},{"amount":0,"name":"整单折扣"},{"amount":0,"name":"加收税金"},{"amount":0,"name":"商家违约金"},{"amount":0,"name":"加急费用"}],"batches":[{"name":"哑口套24","orderProducts":[{"number":1,"thumbnail":"","code":"312040001","size":"","price":12321,"name":"MT52门套"}]}],"status":"待转工厂"}
     */

    private int msgcode;
    private String msg;
    private DataBean data;

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

    public static class DataBean {
        /**
         * fordid : 996273534
         * orderNo : A201712260003
         * customerId : 6195
         * contractId : nl
         * guideName : 倪莉
         * guidePhone : 13312541696
         * customerName : 杨仕智2
         * customerPhone : 13728665225
         * customerAddress : home xosk d sidkk
         * emgType :
         * staffName :
         * staffPhone :
         * installDate : 2017-12-26 00:00:00.0
         * activityPackage :
         * remark :
         * totalReceivable : 12321.0
         * totalReceived : 15222.0
         * totalUnbundled : 12321.0
         * fGoodsAmt : 12321.0
         * fFare : 0.0
         * fTotalRebate : 0.0
         * fTaxAmt : 0.0
         * fUrgentAmt : 0.0
         * fBreakAmt : 0.0
         * receivable : 12321.0
         * funds : [{"amount":12321,"name":"贷款金额"},{"amount":0,"name":"加收运费"},{"amount":0,"name":"整单折扣"},{"amount":0,"name":"加收税金"},{"amount":0,"name":"商家违约金"},{"amount":0,"name":"加急费用"}]
         * batches : [{"name":"哑口套24","orderProducts":[{"number":1,"thumbnail":"","code":"312040001","size":"","price":12321,"name":"MT52门套"}]}]
         * status : 待转工厂
         */

        private String fordid;
        private String orderNo;
        private String customerId;
        private String contractId;
        private String guideName;
        private String guidePhone;
        private String customerName;
        private String customerPhone;
        private String customerAddress;
        private String emgType;
        private String staffName;
        private String staffPhone;
        private String installDate;
        private String activityPackage;
        private String remark;
        private double totalReceivable;
        private double totalReceived;
        private double totalUnbundled;
        private double fGoodsAmt;
        private double fFare;
        private double fTotalRebate;
        private double fTaxAmt;
        private double fUrgentAmt;
        private double fBreakAmt;
        private double receivable;
        private String status;
        private List<FundsBean> funds;
        private List<BatchesBean> batches;

        public String getFordid() {
            return fordid;
        }

        public void setFordid(String fordid) {
            this.fordid = fordid;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getContractId() {
            return contractId;
        }

        public void setContractId(String contractId) {
            this.contractId = contractId;
        }

        public String getGuideName() {
            return guideName;
        }

        public void setGuideName(String guideName) {
            this.guideName = guideName;
        }

        public String getGuidePhone() {
            return guidePhone;
        }

        public void setGuidePhone(String guidePhone) {
            this.guidePhone = guidePhone;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        public String getEmgType() {
            return emgType;
        }

        public void setEmgType(String emgType) {
            this.emgType = emgType;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public String getStaffPhone() {
            return staffPhone;
        }

        public void setStaffPhone(String staffPhone) {
            this.staffPhone = staffPhone;
        }

        public String getInstallDate() {
            return installDate;
        }

        public void setInstallDate(String installDate) {
            this.installDate = installDate;
        }

        public String getActivityPackage() {
            return activityPackage;
        }

        public void setActivityPackage(String activityPackage) {
            this.activityPackage = activityPackage;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getTotalReceivable() {
            return totalReceivable;
        }

        public void setTotalReceivable(double totalReceivable) {
            this.totalReceivable = totalReceivable;
        }

        public double getTotalReceived() {
            return totalReceived;
        }

        public void setTotalReceived(double totalReceived) {
            this.totalReceived = totalReceived;
        }

        public double getTotalUnbundled() {
            return totalUnbundled;
        }

        public void setTotalUnbundled(double totalUnbundled) {
            this.totalUnbundled = totalUnbundled;
        }

        public double getFGoodsAmt() {
            return fGoodsAmt;
        }

        public void setFGoodsAmt(double fGoodsAmt) {
            this.fGoodsAmt = fGoodsAmt;
        }

        public double getFFare() {
            return fFare;
        }

        public void setFFare(double fFare) {
            this.fFare = fFare;
        }

        public double getFTotalRebate() {
            return fTotalRebate;
        }

        public void setFTotalRebate(double fTotalRebate) {
            this.fTotalRebate = fTotalRebate;
        }

        public double getFTaxAmt() {
            return fTaxAmt;
        }

        public void setFTaxAmt(double fTaxAmt) {
            this.fTaxAmt = fTaxAmt;
        }

        public double getFUrgentAmt() {
            return fUrgentAmt;
        }

        public void setFUrgentAmt(double fUrgentAmt) {
            this.fUrgentAmt = fUrgentAmt;
        }

        public double getFBreakAmt() {
            return fBreakAmt;
        }

        public void setFBreakAmt(double fBreakAmt) {
            this.fBreakAmt = fBreakAmt;
        }

        public double getReceivable() {
            return receivable;
        }

        public void setReceivable(double receivable) {
            this.receivable = receivable;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<FundsBean> getFunds() {
            return funds;
        }

        public void setFunds(List<FundsBean> funds) {
            this.funds = funds;
        }

        public List<BatchesBean> getBatches() {
            return batches;
        }

        public void setBatches(List<BatchesBean> batches) {
            this.batches = batches;
        }

        public static class FundsBean {
            /**
             * amount : 12321.0
             * name : 贷款金额
             */

            private double amount;
            private String name;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class BatchesBean {
            /**
             * name : 哑口套24
             * orderProducts : [{"number":1,"thumbnail":"","code":"312040001","size":"","price":12321,"name":"MT52门套"}]
             */

            private String name;
            private List<OrderProductsBean> orderProducts;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<OrderProductsBean> getOrderProducts() {
                return orderProducts;
            }

            public void setOrderProducts(List<OrderProductsBean> orderProducts) {
                this.orderProducts = orderProducts;
            }

            public static class OrderProductsBean {
                /**
                 * number : 1.0
                 * thumbnail :
                 * code : 312040001
                 * size :
                 * price : 12321.0
                 * name : MT52门套
                 */

                private String number;
                private String thumbnail;
                private String code;
                private String size;
                private String price;
                private String name;

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(String thumbnail) {
                    this.thumbnail = thumbnail;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
