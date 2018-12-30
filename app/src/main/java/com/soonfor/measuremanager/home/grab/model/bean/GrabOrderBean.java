package com.soonfor.measuremanager.home.grab.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;

import java.util.Date;

/**
 * Created by Administrator on 2018-01-30.
 */

public class GrabOrderBean{

    /**
     ------完成时间 finishDate	string	@mock=$order(-2209017600000,-2209017600000,-2209017600000)
     ------任务ID	taskId	number	@mock=$order(996273389,996273562,996273685)
     ------客户名称	customerName	string	@mock=$order('陈加','陈加','陈加')
     ------任务类型（0.量尺（量房）,1.复尺，2.放样，3设计，4主动追踪，5人工回访）	taskType	string	@mock=$order('4','4','4')
     ------确认时间	confirmDate	string	@mock=$order('','','')
     ------客户需求	customerNeeds	string	@mock=$order('','','')
     ------楼盘	building	string
     ------订单ID	orderNo	string	@mock=$order('A201711090005','A201801170002','A201803050006')
     ------标题	title	string
     ------地址	address	string	@mock=$order('啊实打实的安顺达as','qweqweqwe','1111')
     ------工分	workPoints	number	@mock=$order(10,,)
     ------执行时间	execDate	string
     ------客户id	customerId	number	@mock=$order(221621,221621,221621)
     ------状态（1：待抢单）	status	number	@mock=$order(1,1,1)
     ------客户性别	customerSex	string	@mock=$order('1','1','1')
     ------预约时间	appointDate
     */
    private String taskNo;//任务
    private String taskType;//任务类型（0.量尺（量房）,1.复尺，2.放样，3设计，4主动追踪，5人工回访）
    private String orderNo;//订单ID
    private String customerId;//客户号
    private String customerName;//客户姓名
    private String customerPhone;//客户联系电话
    private String customerSex;//客户性别
    private String customerNeeds;//客户需求
    private String status;//任务状态-状态（1：待抢单）
    private String title;//标题
    private String building;//楼盘
    private String appointDate;//预约时间
    private String confirmDate;//确认上门时间
    private String finishDate;//完成时间
    private String address;//地址
    private String workPoints;//工分
    private String execDate;//执行时间

    public String getTaskNo() {
        return CommonUtils.formatStr(taskNo);
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getExecDate() {
        return DateTool.getTimeTimestamp(execDate, null);
    }

    public void setExecDate(String execDate) {
        this.execDate = execDate;
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
        if (s_status.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(s_status);
        }
    }


    public String getBuilding() {
        return CommonUtils.formatStr(building);
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getAppointDate() {
        return DateTool.getTimeTimestamp(appointDate, null);
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public String getConfirmDate() {
       return DateTool.getTimeTimestamp(confirmDate, null);
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public long getFinishDate() {
        String finishS = CommonUtils.formatStr(finishDate);
        if (finishS.equals("")) {
            return 0;
        } else {
            return Long.parseLong(finishS);
        }
    }

    public void setStatus(String status) {
        this.status = status;
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



    public String getCustomerNeeds() {
        return CommonUtils.formatStr(customerNeeds);
    }

    public void setCustomerNeeds(String customerNeeds) {
        this.customerNeeds = customerNeeds;
    }


    public String getTitle() {
        return CommonUtils.formatStr(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
