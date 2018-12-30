package com.soonfor.measuremanager.home.othertask.model.bean.detail;

import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-11-03 9:12
 * 邮箱：dingyg012655@126.com
 */
public class DisposeBean {
    /**
     * ---完成说明	note	string
     * ---任务类型 5=主动追踪服务任务,6=人工回访任务	taskType	string	@mock=5
     * ---是否已评价客户	ifappraisecst	number	@mock=0
     * ---附件	attachDtos	array<object>
     * ------附件信息		object
     * ---------附件ID	attachId	string
     * ---------url	attachUrl	string
     * ---------附件类型	attachType	string
     * ---------附件名称	attachDesc	string
     * ---------定位	location	string
     * ---执行类型0.电话，1.上门，2.邮寄，3.自主执行，4.系统执行	exectype	number	@mock=1
     * ---客户是否已评价	ifcstappraise	number	@mock=0
     * ---执行时间	execDate	string	@mock=2016-11-29 00:00:00.0
     * ---邮寄信息	mailDesc	string
     * ---邮寄状态0:无需邮寄;1:已邮寄	mailstatus	number	@mock=0
     * ---任务状态0=未开始,1=进行中,2=完成,3=未完成,4=已取消	status	string	@mock=1
     * ---任务ID
     */
    private String note;//完成说明
    private String taskType;//任务类型（0.量尺（量房）,1.复尺，2.放样，3设计，4主动追踪，5人工回访）
    private String ifappraisecst;//是否已评价客户
    private String ifcstappraise;//客户是否已评价
    private List<AttachDto> attachDtos;

    private int execType;//执行方式
    private String execDate;//执行时间
    private String mailDesc;//邮寄信息
    private int mailstatus;//邮寄状态
    private String status;//任务状态
    private String taskId;//任务ID

    private String statusDesc;//状态描述
    private String exectypeDesc;//执行方式描述

    public String getNote() {
        return CommonUtils.formatStr(note);
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTaskType() {
        return CommonUtils.formatStr(taskType);
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getIfappraisecst() {
        return CommonUtils.formatStr(ifappraisecst);
    }

    public void setIfappraisecst(String ifappraisecst) {
        this.ifappraisecst = ifappraisecst;
    }

    public String getIfcstappraise() {
        return CommonUtils.formatStr(ifcstappraise);
    }

    public void setIfcstappraise(String ifcstappraise) {
        this.ifcstappraise = ifcstappraise;
    }

    public List<AttachDto> getAttachDtos() {
        return attachDtos == null ? new ArrayList<>() : attachDtos;
    }

    public void setAttachDtos(List<AttachDto> attachDtos) {
        this.attachDtos = attachDtos;
    }

    public int getExectype() {
        return execType;
    }

    public void setExectype(int exectype) {
        this.execType = exectype;
    }

    public String getExectypeDesc() {
        exectypeDesc = "电话";
        switch (execType) {
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

    public String getExecDate() {
        return DateTool.getTimeTimestamp(execDate, "yyyy-MM-dd HH:mm");
    }

    public void setExecDate(String execDate) {
        this.execDate = execDate;
    }

    public String getMailDesc() {
        return CommonUtils.formatStr(mailDesc);
    }

    public void setMailDesc(String mailDesc) {
        this.mailDesc = mailDesc;
    }

    public int getMailstatus() {
        return mailstatus;
    }

    public void setMailstatus(int mailstatus) {
        this.mailstatus = mailstatus;
    }

    public int getStatus() {
        String s_status = CommonUtils.formatStr(status);
        if (s_status.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(s_status);
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskId() {
        return CommonUtils.formatStr(taskId);
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatusDesc() {
        switch (getStatus()) {
            case 2:
                statusDesc = "已完成";
                break;
            default:
                statusDesc = "未完成";
                break;
        }
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

}
