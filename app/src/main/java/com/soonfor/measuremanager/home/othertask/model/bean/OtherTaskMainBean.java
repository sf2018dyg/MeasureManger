package com.soonfor.measuremanager.home.othertask.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.DateTool;

import java.io.Serializable;

/**
 * Created by DingYg on 2018-01-30.
 */

public class OtherTaskMainBean implements Serializable{

    /**
     ------楼盘	building	string	@mock=金和家园
     ------标题	title	string	@mock=111
     ------完成时间	finishDate
     ------任务号	taskNo	number	@mock=270800
     ------客户名称	customerName	string	@mock=陈红
     ------执行时间	execDate	string	@mock=30 10 2018 15:46:42:540
     ------任务类型,4=主动追踪服务任务,5=人工回	taskType	number	@mock=5
     ------状态1、进行中2、已逾期、3、已完成	status	number	@mock=3
     ------任务ID	taskId	number
     ------性别	customerSex	string	@mock=0
     ------执行方式0.电话，1.上门，2.邮寄，3.自主执行，4.系统执行	exectype	string	@mock=0
     ------订单号	orderNo	string	@mock=SBJ0100035
     ------地址	address	string	@mock=朝阳区双桥东路远洋一方嘉园5-703
     ------客户ID	customerId	number	@mock=144886
     ------工分	workPoints
     */
    private String taskId;//任务id
    private String taskNo;//任务No
    private String taskType;//任务类型（0.量尺（量房）,1.复尺，2.放样，3设计，4主动追踪，5人工回访）
    private String orderNo;//订单ID
    private String customerId;//客户号
    private String customerName;//客户姓名
    private String customerPhone;//客户联系电话
    private String customerSex;//客户性别
    private String customerNeeds;//客户需求
    private String status;//任务状态-1、进行中2、已逾期、3、已完成
    private String title;//标题
    private String building;//楼盘
    private String address;//地址
    private String appointDate;//预约时间
    private String confirmDate;//确认上门时间
    private String finishDate;//完成时间
    private String workPoints;//工分
    private String execDate;//执行时间
    private int execType;//执行方式
    private String fserviceprjid;//项目id
    private String foriType;//回访类型1=量尺;2=方案设计;3=复尺;4=放样;5=送货;6=安装
    private String foriId;//来源ID

    private String statusDesc;//状态描述
    private String exectypeDesc;//执行方式描述

    public String getTaskId() {
        return CommonUtils.formatStr(taskId);
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

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

    public long getAppointDate() {
        String appoint = CommonUtils.formatStr(appointDate);
        if (appoint.equals("")) {
            return 0;
        } else {
            return Long.parseLong(appoint);
        }
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public long getConfirmDate() {
        String confirm = CommonUtils.formatStr(confirmDate);
        if (confirm.equals("")) {
            return 0;
        } else {
            return Long.parseLong(confirm);
        }
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
        return DataTools.getExactStrNum(workPoints);
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

    public int getExectype() {
       return execType;
    }

    public void setExectype(int exectype) {
        this.execType = exectype;
    }

    public String getStatusDesc() {
        statusDesc = "进行中";
        switch (getStatus()) {
            case 1:
                statusDesc = "进行中";
                break;
            case 2:
                statusDesc = "已逾期";
                break;
            case 3:
                statusDesc = "已完成";
                break;
        }
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getExectypeDesc() {
        exectypeDesc = "电话";
        switch (getExectype()){
            case 0:
                exectypeDesc = "电话";
                break;
            case 1:
                exectypeDesc = "上门";
                break;
            case 2:
                exectypeDesc = "邮寄";
                break;
            case 3:
                exectypeDesc = "自主执行";
                break;
            case 4:
                exectypeDesc = "系统执行";
                break;
        }
        return exectypeDesc;
    }

    public void setExectypeDesc(String exectypeDesc) {
        this.exectypeDesc = exectypeDesc;
    }

    public String getFserviceprjid() {
        return CommonUtils.formatStr(fserviceprjid);
    }

    public String getForiType() {
        return CommonUtils.formatNum(foriType);
    }

    public String getForiId() {
        return CommonUtils.formatStr(foriId);
    }
}
