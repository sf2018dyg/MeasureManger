package com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans;

import com.soonfor.measuremanager.home.liangchi.model.bean.detail.IntentionalEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.Size;
import com.soonfor.measuremanager.other.bean.DataBean;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/2 0002 13:42
 * 邮箱：suibozhu@139.com
 */

public class CustomerInfo {
    private String id;
    private String name;
    private String address;
    private String phone;

    private String buildName;
    private String userId;
    private String customerId;
    private String customerName;

    private String houseType;//建筑代号
    private String houseAddress;//住址
    private String doorType;//户型代号
    private String houseSize;//面积

    private String estimateDate;//预计装修日期
    private IntentionProduct intentionProduct;//意向列表

    private String needs;//初步沟通要求

    public class IntentionProduct{
        private List<String> series;
        private List<String> styles;

        public List<String> getSeries() {
            return series;
        }

        public List<String> getStyles() {
            return styles;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBuildName() {
        return buildName==null?"":buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getUserId() {
        return userId==null?"":userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId==null?"":customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName==null?"":customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getHouseType() {
        return houseType==null?"":houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getEstimateDate() {
        return estimateDate==null?"":estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getNeeds() {
        return needs==null?"":needs;
    }

    public void setNeeds(String needs) {
        this.needs = needs;
    }

    public IntentionProduct getIntentionProduct() {
        return intentionProduct;
    }

    public void setIntentionProduct(IntentionProduct intentionProduct) {
        this.intentionProduct = intentionProduct;
    }

    public String getHouseAddress() {
        return houseAddress==null?"":houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getDoorType() {
        return doorType==null?"":doorType;
    }

    public void setDoorType(String doorType) {
        this.doorType = doorType;
    }

    public String getHouseSize() {
        return houseSize==null?"":houseSize;
    }

    public void setHouseSize(String houseSize) {
        this.houseSize = houseSize;
    }
}
