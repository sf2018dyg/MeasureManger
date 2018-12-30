package com.soonfor.evaluate.bean;

import com.soonfor.measuremanager.home.othertask.model.bean.detail.AttachDto;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-11-16 9:28
 * 邮箱：dingyg012655@126.com
 * 保存 评价客户
 */
public class EvalCustSaveBean {
    private int ifstarlvappraise;//标签星级评价
    private int ifallhighappraise;//是否开启整体好评
    private int type;//0.客户评价，1.评价客户
    private String taskType;//任务类型
    private String taskId;//任务id
    private int ifsetappraisecontent;//是否需要评价内容填写
    private String appraisecontent;//评价内容填写
    private int ifuploadimg;//图片上传(是否)
    private int allhighappraiseresult; //* ---整体好评结果1:好评；2：中评;3:差评		number	@mock=1
    private String appraisetempid;//评价模板id
    private List<AttachDto> attachDtos;//图片附件信息  attachType: 0.评价图，1.评价结果回复图
    private List<AppResSortItemDto> appResSortItemDtos;//---标签星级

    public int getIfstarlvappraise() {
        return ifstarlvappraise;
    }

    public void setIfstarlvappraise(int ifstarlvappraise) {
        this.ifstarlvappraise = ifstarlvappraise;
    }

    public int getIfallhighappraise() {
        return ifallhighappraise;
    }

    public void setIfallhighappraise(int ifallhighappraise) {
        this.ifallhighappraise = ifallhighappraise;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getIfsetappraisecontent() {
        return ifsetappraisecontent;
    }

    public void setIfsetappraisecontent(int ifsetappraisecontent) {
        this.ifsetappraisecontent = ifsetappraisecontent;
    }

    public String getAppraisecontent() {
        return appraisecontent;
    }

    public void setAppraisecontent(String appraisecontent) {
        this.appraisecontent = appraisecontent;
    }

    public int getIfuploadimg() {
        return ifuploadimg;
    }

    public void setIfuploadimg(int ifuploadimg) {
        this.ifuploadimg = ifuploadimg;
    }

    public int getAllhighappraiseresult() {
        return allhighappraiseresult;
    }

    public void setAllhighappraiseresult(int allhighappraiseresult) {
        this.allhighappraiseresult = allhighappraiseresult;
    }

    public String getAppraisetempid() {
        return appraisetempid;
    }

    public void setAppraisetempid(String appraisetempid) {
        this.appraisetempid = appraisetempid;
    }

    public List<AttachDto> getAttachDtos() {
        return attachDtos;
    }

    public void setAttachDtos(List<AttachDto> attachDtos) {
        this.attachDtos = attachDtos;
    }

    public List<AppResSortItemDto> getAppResSortItemDtos() {
        return appResSortItemDtos;
    }

    public void setAppResSortItemDtos(List<AppResSortItemDto> appResSortItemDtos) {
        this.appResSortItemDtos = appResSortItemDtos;
    }
}
