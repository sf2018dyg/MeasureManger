package com.soonfor.measuremanager.home.design.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.measuremanager.home.grab.model.bean.GrabOrderBean;
import com.soonfor.measuremanager.tools.CommonUtils;

/**
 * 作者：DC-DingYG on 2018-10-25 9:34
 * 邮箱：dingyg012655@126.com
 */
public class DesignItemBean implements Parcelable{
    private String taskNo;//任务号
    private String taskType;//任务类型
    private String orderNo;//
    private String customerId;//客户号
    private String customerName;//客户姓名
    private String customerPhone;//客户联系电话
    private String customerSex;//
    private String status;//任务状态
    private String building;//楼盘
    private String appointDate;//预约时间
    private String confirmDate;//确认上门时间
    private String finishDate;//量尺完成时间
    private String address;//地址
    private String workPoints;//工分
    private int row;
    private String customerMobile;//客户手机
    private String communicateDate;//沟通时间

    public DesignItemBean() {
    }

    protected DesignItemBean(Parcel in) {
        taskNo = in.readString();
        taskType = in.readString();
        orderNo = in.readString();
        customerId = in.readString();
        customerName = in.readString();
        customerPhone = in.readString();
        customerSex = in.readString();
        status = in.readString();
        building = in.readString();
        appointDate = in.readString();
        confirmDate = in.readString();
        finishDate = in.readString();
        address = in.readString();
        workPoints = in.readString();
        row = in.readInt();
        customerMobile = in.readString();
        communicateDate = in.readString();
    }

    public static final Creator<DesignItemBean> CREATOR = new Creator<DesignItemBean>() {
        @Override
        public DesignItemBean createFromParcel(Parcel in) {
            return new DesignItemBean(in);
        }

        @Override
        public DesignItemBean[] newArray(int size) {
            return new DesignItemBean[size];
        }
    };

    public String getTaskNo() {
        return CommonUtils.formatStr(taskNo);
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskType() {
        return CommonUtils.formatStr(taskType);
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getOrderNo() {
        return CommonUtils.formatStr(orderNo);
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

    public String getCustomerName() {
        return CommonUtils.formatStr(customerName);
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return CommonUtils.formatStr(customerPhone);
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerSex() {
        return CommonUtils.formatStr(customerSex);
    }

    public void setCustomerSex(String customerSex) {
        this.customerSex = customerSex;
    }

    public int getStatus() {
        String s_status = CommonUtils.formatStr(status);
        if(s_status.equals("")){
            return 0;
        }else {
            return Integer.parseInt(s_status);
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuilding() {
        return CommonUtils.formatStr(building);
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public long getAppointDate() {
        String appoint = CommonUtils.formatStr(appointDate);
        if(appoint.equals("")){
            return 0;
        }else {
            return Long.parseLong(appoint);
        }
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public long getConfirmDate() {
        String confirm = CommonUtils.formatStr(confirmDate);
        if(confirm.equals("")){
            return 0;
        }else {
            return Long.parseLong(confirm);
        }
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public long getFinishDate() {
        String finishS = CommonUtils.formatStr(finishDate);
        if(finishS.equals("")){
            return 0;
        }else {
            return Long.parseLong(finishS);
        }
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getAddress() {
        return CommonUtils.formatStr(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkPoints() {
        return CommonUtils.formatStr(workPoints);
    }

    public void setWorkPoints(String workPoints) {
        this.workPoints = workPoints;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public long getCommunicateDate() {
        String communicate = CommonUtils.formatStr(communicateDate);
        if(communicate.equals("")){
            return 0;
        }else {
            return Long.parseLong(communicate);
        }
    }

    public void setCommunicateDate(String communicateDate) {
        this.communicateDate = communicateDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskNo);
        dest.writeString(taskType);
        dest.writeString(orderNo);
        dest.writeString(customerId);
        dest.writeString(customerName);
        dest.writeString(customerPhone);
        dest.writeString(customerSex);
        dest.writeString(status);
        dest.writeString(building);
        dest.writeString(appointDate);
        dest.writeString(confirmDate);
        dest.writeString(finishDate);
        dest.writeString(address);
        dest.writeString(workPoints);
        dest.writeInt(row);
        dest.writeString(customerMobile);
        dest.writeString(communicateDate);
    }
}
