package com.soonfor.measuremanager.home.liangchi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.measuremanager.tools.CommonUtils;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:44
 * 邮箱：suibozhu@139.com
 */

public class LiangChiBean implements Parcelable {

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
    //缺预约描述
    //导购名称
    //导购电话
    private String confirmDate;//确认上门时间
    private String finishDate;//量尺完成时间
    private String address;//地址
    private String workPoints;//工分
    //private int statusCode;//状态代号
    private int row;

    public LiangChiBean() {
    }


    protected LiangChiBean(Parcel in) {
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
    }

    public static final Creator<LiangChiBean> CREATOR = new Creator<LiangChiBean>() {
        @Override
        public LiangChiBean createFromParcel(Parcel in) {
            return new LiangChiBean(in);
        }

        @Override
        public LiangChiBean[] newArray(int size) {
            return new LiangChiBean[size];
        }
    };

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
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

    public String getCustomerSex() {
        return customerSex;
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
        return building;
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
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkPoints() {
        return workPoints+"";
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
    }
}
