package com.soonfor.evaluate.bean;

import com.soonfor.measuremanager.tools.CommonUtils;

import java.io.Serializable;

/**
 * 作者：DC-DingYG on 2018-11-16 10:11
 * 邮箱：dingyg012655@126.com
 * 跳转至“评价客户”界面所需要的信息
 */
public class RequestTemplateDto implements Serializable{
    private String id;//评价id
    private String taskId;//任务id
    private String taskType;//任务类型
    private String fserviceprjid;//项目id

    public void setId(String id) {
        this.id = id;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setFserviceprjid(String fserviceprjid) {
        this.fserviceprjid = fserviceprjid;
    }

    public String getId() {
        return CommonUtils.formatStr(id);
    }

    public String getTaskId() {
        return CommonUtils.formatStr(taskId);
    }

    public String getTaskType() {
        return CommonUtils.formatStr(taskType);
    }

    public String getFserviceprjid() {
        return CommonUtils.formatStr(fserviceprjid);
    }
}
