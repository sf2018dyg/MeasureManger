package com.soonfor.measuremanager.home.othertask.model.bean.detail;

import com.soonfor.evaluate.bean.ReturnVisitBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/1/25.
 */

public class UpdateTaskResultBean {

    /**
     任务类型	taskType	string
     执行情况描述	description	string
     邮寄描述	mailDesc	string
     附件列表信息	taskAttachDtos	array<object>
     ---附件信息	TaskAttachDto	object
     ------附件类型	attachType	string
     ------附件名称	attachDesc	string
     ------定位	location	string
     ------url	attachUrl	string
     任务ID	taskId	number
     客户再销售商机报备	opportunitydesc	string
     邮寄状态	mailstatus	boolean
     执行结果	result	boolean
     */

    private String taskId;//任务ID
    private String taskType;//任务类型
    private String description;//执行情况描述
    private String mailDesc;//邮寄描述
    private String mailstatus;//邮寄状态
    private String result;//执行结果
    private String opportunitydesc;//客户再销售商机报备
    private List<AttachDto> attachInfos;//附件信息
    private List<ReturnVisitBean> rtnVisitParmList;//调查问卷回访结果信息

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMailDesc() {
        return mailDesc;
    }

    public void setMailDesc(String mailDesc) {
        this.mailDesc = mailDesc;
    }

    public String getMailstatus() {
        return mailstatus;
    }

    public void setMailstatus(String mailstatus) {
        this.mailstatus = mailstatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOpportunitydesc() {
        return opportunitydesc;
    }

    public void setOpportunitydesc(String opportunitydesc) {
        this.opportunitydesc = opportunitydesc;
    }

    public List<AttachDto> getAttachDtos() {
        return attachInfos;
    }

    public void setAttachDtos(List<AttachDto> taskAttachDtos) {
        this.attachInfos = taskAttachDtos;
    }

    public List<ReturnVisitBean> getRtnVisitParmList() {
        return rtnVisitParmList==null?new ArrayList<>():rtnVisitParmList;
    }

    public void setRtnVisitParmList(List<ReturnVisitBean> rtnVisitParmList) {
        this.rtnVisitParmList = rtnVisitParmList;
    }

    //    public class TaskAttachDto{
//        private String attachUrl;
//        private String attachType;//0 图片 1语音 2.视频 3.文档 4.其它（包括定位）
//        private String attachDesc;
//        private String location;
//
//        public String getAttachUrl() {
//            return attachUrl==null?"":attachUrl;
//        }
//
//        public void setAttachUrl(String attachUrl) {
//            this.attachUrl = attachUrl;
//        }
//
//        public String getAttachType() {
//            return attachType==null?"":attachType;
//        }
//
//        public void setAttachType(String attachType) {
//            this.attachType = attachType;
//        }
//
//        public String getAttachDesc() {
//            return attachDesc==null?"":attachDesc;
//        }
//
//        public void setAttachDesc(String attachDesc) {
//            this.attachDesc = attachDesc;
//        }
//
//        public String getLocation() {
//            return location==null?"":location;
//        }
//
//        public void setLocation(String location) {
//            this.location = location;
//        }
//    }
}
