package com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/5 0005 09:34
 * 邮箱：suibozhu@139.com
 */

public class mainbean {
    private String taskNo;
    private String taskType;
    private String customerId;
    private taskprogress taskProgress;
    private measureinfo measureInfo;
    private String designInfo;
    private String markInfo;
    private String deliveryInfo;
    private String installInfo;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public taskprogress getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(taskprogress taskProgress) {
        this.taskProgress = taskProgress;
    }

    public measureinfo getMeasureInfo() {
        return measureInfo;
    }

    public void setMeasureInfo(measureinfo measureInfo) {
        this.measureInfo = measureInfo;
    }

    public String getDesignInfo() {
        return designInfo;
    }

    public void setDesignInfo(String designInfo) {
        this.designInfo = designInfo;
    }

    public String getMarkInfo() {
        return markInfo;
    }

    public void setMarkInfo(String markInfo) {
        this.markInfo = markInfo;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getInstallInfo() {
        return installInfo;
    }

    public void setInstallInfo(String installInfo) {
        this.installInfo = installInfo;
    }
}
