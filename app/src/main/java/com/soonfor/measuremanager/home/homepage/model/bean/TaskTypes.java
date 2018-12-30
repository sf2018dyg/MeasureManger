package com.soonfor.measuremanager.home.homepage.model.bean;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/2 0002 09:10
 * 邮箱：suibozhu@139.com
 */

public class TaskTypes {
    String taskType;
    List<TaskTypesDetail> taskDetails;

    public TaskTypes(String taskType, List<TaskTypesDetail> taskDetails) {
        this.taskType = taskType;
        this.taskDetails = taskDetails;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public List<TaskTypesDetail> getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(List<TaskTypesDetail> taskDetails) {
        this.taskDetails = taskDetails;
    }
}
