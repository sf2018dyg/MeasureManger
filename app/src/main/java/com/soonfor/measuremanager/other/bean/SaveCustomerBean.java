package com.soonfor.measuremanager.other.bean;

/**
 * Created by ljc on 2018/1/16.
 */

public class SaveCustomerBean {

    /**
     * userId : string
     * customerId : string
     * custSourceType : string
     * inTime : 2018-01-16T05:42:55.683Z
     * customerName : string
     * phone : string
     * sexType : string
     * ageType : string
     * number : 0
     * custIntentType : string
     * houseType : string
     * houseAddress : string
     * houseFloor : string
     * doorType : string
     * houseSize : 0
     * estimateDate : 2018-01-16T05:42:55.683Z
     * inType : 0
     * wechat : string
     * telephone : string
     * qq : string
     * email : string
     * professionType : string
     * birthday : 2018-01-16T05:42:55.683Z
     * intentionProduct : string
     * budget : 0
     * needs : string
     * attachInfo : {"attachId":"string","attachType":0,"attachDesc":"string","location":"string"}
     */

    private String userId;
    private String customerId;
    private String custSourceType;
    private String inTime;
    private String customerName;
    private String phone;
    private String sexType;
    private String ageType;
    private int number;
    private String custIntentType;
    private String houseType;
    private String houseAddress;
    private String houseFloor;
    private String doorType;
    private int houseSize;
    private String estimateDate;
    private int inType;
    private String wechat;
    private String telephone;
    private String qq;
    private String email;
    private String professionType;
    private String birthday;
    private String intentionProduct;
    private int budget;
    private String needs;
    private AttachInfoBean attachInfo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustSourceType() {
        return custSourceType;
    }

    public void setCustSourceType(String custSourceType) {
        this.custSourceType = custSourceType;
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

    public String getAgeType() {
        return ageType;
    }

    public void setAgeType(String ageType) {
        this.ageType = ageType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCustIntentType() {
        return custIntentType;
    }

    public void setCustIntentType(String custIntentType) {
        this.custIntentType = custIntentType;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getHouseFloor() {
        return houseFloor;
    }

    public void setHouseFloor(String houseFloor) {
        this.houseFloor = houseFloor;
    }

    public String getDoorType() {
        return doorType;
    }

    public void setDoorType(String doorType) {
        this.doorType = doorType;
    }

    public int getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(int houseSize) {
        this.houseSize = houseSize;
    }

    public String getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
        this.estimateDate = estimateDate;
    }

    public int getInType() {
        return inType;
    }

    public void setInType(int inType) {
        this.inType = inType;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfessionType() {
        return professionType;
    }

    public void setProfessionType(String professionType) {
        this.professionType = professionType;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIntentionProduct() {
        return intentionProduct;
    }

    public void setIntentionProduct(String intentionProduct) {
        this.intentionProduct = intentionProduct;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getNeeds() {
        return needs;
    }

    public void setNeeds(String needs) {
        this.needs = needs;
    }

    public AttachInfoBean getAttachInfo() {
        return attachInfo;
    }

    public void setAttachInfo(AttachInfoBean attachInfo) {
        this.attachInfo = attachInfo;
    }

    public static class AttachInfoBean {
        /**
         * attachId : string
         * attachType : 0
         * attachDesc : string
         * location : string
         */

        private String attachId;
        private int attachType;
        private String attachDesc;
        private String location;

        public String getAttachId() {
            return attachId;
        }

        public void setAttachId(String attachId) {
            this.attachId = attachId;
        }

        public int getAttachType() {
            return attachType;
        }

        public void setAttachType(int attachType) {
            this.attachType = attachType;
        }

        public String getAttachDesc() {
            return attachDesc;
        }

        public void setAttachDesc(String attachDesc) {
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
