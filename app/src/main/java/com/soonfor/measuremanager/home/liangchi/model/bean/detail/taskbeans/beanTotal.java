package com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans;

import com.soonfor.measuremanager.tools.CommonUtils;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/2 0002 13:39
 * 邮箱：suibozhu@139.com
 */

/*{"taskNo":"1692","taskType":"measure","taskProgress":
{"mainProcess":null,"subProcess":null,"status":null,"processType":null,"index":null},
"taskStatus":"已接收","dispatchTime":1508291160000,"workPoints":0,"customerInfo":
{"id":"23582","name":"庆祝","address":"浙江省-嘉兴市磊","phone":"13553809935"},
"staffInfo":{"id":"442528687","name":null,"phone":null,"jobType":"测量员"},"executionTime":1508374800000,
"executionTimeType":"预约上门时间","description":"7","customerNeeds":null}*/


public class beanTotal {
    private String taskNo;
    private String taskType;
    private String taskStatus;
    private String dispatchTime;//派工时间
    private String workPoints;//工分
    private String executionTime;//确认上门时间
    private String executionTimeType;//确认上门
    private String description;//描述
    private String customerNeeds;//客户需求
    private String confirmTime;//确认预约时间
    private String confirmDesc;//确认预约描述
    private String finishTime;//完成时间
    //楼盘
    private CustomerInfo customerInfo;
    private StaffInfo staffInfo;
    private TaskProgress taskProgress;

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

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(String dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getWorkPoints() {
        return workPoints;
    }

    public void setWorkPoints(String workPoints) {
        this.workPoints = workPoints;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getExecutionTimeType() {
        return CommonUtils.formatStr(executionTimeType);
    }

    public void setExecutionTimeType(String executionTimeType) {
        this.executionTimeType = executionTimeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public StaffInfo getStaffInfo() {
        return staffInfo;
    }

    public void setStaffInfo(StaffInfo staffInfo) {
        this.staffInfo = staffInfo;
    }

    public TaskProgress getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(TaskProgress taskProgress) {
        this.taskProgress = taskProgress;
    }

    public String getCustomerNeeds() {
        return customerNeeds;
    }

    public void setCustomerNeeds(String customerNeeds) {
        this.customerNeeds = customerNeeds;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmDesc() {
        return confirmDesc;
    }

    public void setConfirmDesc(String confirmDesc) {
        this.confirmDesc = confirmDesc;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}
