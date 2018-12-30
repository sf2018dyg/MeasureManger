package com.soonfor.measuremanager.home.order.bean;

import com.soonfor.measuremanager.tools.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/20.
 */

public class PreOrderBean {

    /**
     * msgcode : 0
     * msg : 成功
     * data : {"customerId":24140,"customerName":"甘先生","customerPhone":"15933336789","depositPrice":700,"receivedAmt":1000,"requestDelivery":"","fordid":897150874,"orderNo":"A201803190002","contractId":"ACS057399990001","guideName":"i测试门店","guidePhone":"","customerAddress":"","remark":"这是大客户","communicateNeeds":"","sourceTime":"2018-03-19 16:04:00.0","sourceMethod":"手工新增","doorType":"3室2厅","doorPicture":"","intentionProducts":[{"goodsCode":"3110100048","goodsName":"SM03门扇","specifical":null,"fStdUnit":"PCS","thumbnail":"SM03.png","unitPrice":300,"quantity":1,"name":"3110100048SM03门扇"},{"goodsCode":"3110100049","goodsName":"YM52-2S门扇","specifical":null,"fStdUnit":"PCS","thumbnail":"YM52-2S.png","unitPrice":200,"quantity":2,"name":"3110100049YM52-2S门扇"}]}
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
         * customerId : 24140
         * customerName : 甘先生
         * customerPhone : 15933336789
         * depositPrice : 700.0
         * receivedAmt : 1000.0
         * requestDelivery :
         * fordid : 897150874
         * orderNo : A201803190002
         * contractId : ACS057399990001
         * guideName : i测试门店
         * guidePhone :
         * customerAddress :
         * remark : 这是大客户
         * communicateNeeds :
         * sourceTime : 2018-03-19 16:04:00.0
         * sourceMethod : 手工新增
         * doorType : 3室2厅
         * doorPicture :
         * intentionProducts : [{"goodsCode":"3110100048","goodsName":"SM03门扇","specifical":null,"fStdUnit":"PCS","thumbnail":"SM03.png","unitPrice":300,"quantity":1,"name":"3110100048SM03门扇"},{"goodsCode":"3110100049","goodsName":"YM52-2S门扇","specifical":null,"fStdUnit":"PCS","thumbnail":"YM52-2S.png","unitPrice":200,"quantity":2,"name":"3110100049YM52-2S门扇"}]
         */

        private double budget;

        private String fitmentDate;

        public String getFitmentDate() {
            return fitmentDate;
        }

        public void setFitmentDate(String fitmentDate) {
            this.fitmentDate = fitmentDate;
        }

        public double getBudget() {
            return budget;
        }

        public void setBudget(double budget) {
            this.budget = budget;
        }

        private String customerId;
        private String customerName;
        private String customerPhone;
        private double depositPrice;
        private double receivedAmt;
        private String requestDelivery;
        private int fordid;
        private String orderNo;
        private String contractId;
        private String guideName;
        private String guidePhone;
        private String customerAddress;
        private String remark;
        private String communicateNeeds;
        private String sourceTime;
        private String sourceMethod;
        private String doorType;
        private String tel;
        private ArrayList<String> doorPicture;
        private List<IntentionProductsBean> intentionProducts;
        private List<AttachesBean> attaches;

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

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public double getDepositPrice() {
            return depositPrice;
        }

        public void setDepositPrice(double depositPrice) {
            this.depositPrice = depositPrice;
        }

        public double getReceivedAmt() {
            return receivedAmt;
        }

        public void setReceivedAmt(double receivedAmt) {
            this.receivedAmt = receivedAmt;
        }

        public String getRequestDelivery() {
            return requestDelivery;
        }

        public void setRequestDelivery(String requestDelivery) {
            this.requestDelivery = requestDelivery;
        }
        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getFordid() {
            return fordid;
        }

        public void setFordid(int fordid) {
            this.fordid = fordid;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
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

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCommunicateNeeds() {
            return communicateNeeds;
        }

        public void setCommunicateNeeds(String communicateNeeds) {
            this.communicateNeeds = communicateNeeds;
        }

        public String getSourceTime() {
            return sourceTime;
        }

        public void setSourceTime(String sourceTime) {
            this.sourceTime = sourceTime;
        }

        public String getSourceMethod() {
            return sourceMethod;
        }

        public void setSourceMethod(String sourceMethod) {
            this.sourceMethod = sourceMethod;
        }

        public String getDoorType() {
            return doorType;
        }

        public void setDoorType(String doorType) {
            this.doorType = doorType;
        }

        public ArrayList<String> getDoorPicture() {
            return doorPicture;
        }

        public void setDoorPicture(ArrayList<String> doorPicture) {
            this.doorPicture = doorPicture;
        }

        public List<IntentionProductsBean> getIntentionProducts() {
            return intentionProducts;
        }

        public void setIntentionProducts(List<IntentionProductsBean> intentionProducts) {
            this.intentionProducts = intentionProducts;
        }

        public List<AttachesBean> getAttaches() {
            return attaches;
        }

        public void setAttaches(List<AttachesBean> attaches) {
            this.attaches = attaches;
        }

        public static class IntentionProductsBean {
            /**
             * goodsCode : 3110100048
             * goodsName : SM03门扇
             * specifical : null
             * fStdUnit : PCS
             * thumbnail : SM03.png
             * unitPrice : 300.0
             * quantity : 1.0
             * name : 3110100048SM03门扇
             */

            private String goodsCode;
            private String goodsName;
            private String specifical;
            private String sizeDesc;
            private String texture;
            private String color;
            private String unit;

            public String getSizeDesc() {
                return CommonUtils.formatStr(sizeDesc);
            }

            public void setSizeDesc(String sizeDesc) {
                this.sizeDesc = sizeDesc;
            }

            public String getTexture() {
                return CommonUtils.formatStr(texture);
            }

            public void setTexture(String texture) {
                this.texture = texture;
            }

            public String getColor() {
                return CommonUtils.formatStr(color);
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getUnit() {
                return CommonUtils.formatStr(unit);
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            private String fStdUnit;
            private String thumbnail;
            private double unitPrice;
            private double quantity;
            private String name;

            public String getGoodsCode() {
                return goodsCode;
            }

            public void setGoodsCode(String goodsCode) {
                this.goodsCode = goodsCode;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getSpecifical() {
                return CommonUtils.formatStr(specifical);
            }

            public void setSpecifical(String specifical) {
                this.specifical = specifical;
            }

            public String getFStdUnit() {
                return fStdUnit;
            }

            public void setFStdUnit(String fStdUnit) {
                this.fStdUnit = fStdUnit;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public double getUnitPrice() {
                return unitPrice;
            }

            public void setUnitPrice(double unitPrice) {
                this.unitPrice = unitPrice;
            }

            public double getQuantity() {
                return quantity;
            }

            public void setQuantity(double quantity) {
                this.quantity = quantity;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
        public static class AttachesBean implements Serializable {
            /**
             * attachId : 448
             * attachUrl :
             * attachType : 4
             * attachDesc : null
             * location : 中国广东省东莞市新基路2号
             */

            private String attachId;
            private String attachUrl;
            private int attachType;
            private Object attachDesc;
            private String location;

            public String getAttachId() {
                return attachId;
            }

            public void setAttachId(String attachId) {
                this.attachId = attachId;
            }

            public String getAttachUrl() {
                return attachUrl;
            }

            public void setAttachUrl(String attachUrl) {
                this.attachUrl = attachUrl;
            }

            public int getAttachType() {
                return attachType;
            }

            public void setAttachType(int attachType) {
                this.attachType = attachType;
            }

            public Object getAttachDesc() {
                return attachDesc;
            }

            public void setAttachDesc(Object attachDesc) {
                this.attachDesc = attachDesc;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }
        }
    }

}
