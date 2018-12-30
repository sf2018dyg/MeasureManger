package com.soonfor.measuremanager.home.lofting.model.bean.taskinfo;

import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.TaskProgress;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Design.DesignPlanBean;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureResultEntity;
import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.List;

/**
 * Created by DingYg on 2018-03-20.
 * 量尺、复尺和放样信息共用实体
 */

public class TaskCompleteInfoBean {
    private String taskNo;
    private String taskType;
    private String customerId;
    private TaskProgress taskProgress;
    private List<MeasureResultEntity> measureInfo;
    private DesignPlanBean designInfo;
    private String markInfo;
    private String deliveryInfo;
    private String installInfo;

    public String getTaskNo() {
        return CommonUtils.formatStr(taskNo);
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo=="null"?null:taskNo;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType=="null"?null:taskType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId=="null"?null:customerId;
    }

    public TaskProgress getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(TaskProgress taskProgress) {
        this.taskProgress = taskProgress;
    }

    public List<MeasureResultEntity> getMeasureInfo() {
        return measureInfo;
    }

    public void setMeasureInfo(List<MeasureResultEntity> measureInfo) {
        this.measureInfo = measureInfo;
    }

    public DesignPlanBean getDesignInfos() {
        return designInfo;
    }

    public void setDesignInfos(DesignPlanBean designInfo) {
        this.designInfo = designInfo;
    }

    public String getMarkInfo() {
        return markInfo;
    }

    public void setMarkInfo(String markInfo) {
        this.markInfo = markInfo=="null"?null:markInfo;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo=="null"?null:deliveryInfo;
    }

    public String getInstallInfo() {
        return installInfo;
    }

    public void setInstallInfo(String installInfo) {
        this.installInfo = installInfo=="null"?null:installInfo;
    }

}
