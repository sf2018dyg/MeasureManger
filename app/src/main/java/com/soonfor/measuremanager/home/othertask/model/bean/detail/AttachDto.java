package com.soonfor.measuremanager.home.othertask.model.bean.detail;

/**
 * 作者：DC-DingYG on 2018-11-13 8:50
 * 邮箱：dingyg012655@126.com
 */
public class AttachDto {
    private String attachId;
    private String attachUrl;
    private String attachType;//0 图片 1语音 2.视频 3.文档 4.其它（包括定位）
    private String attachDesc;
    private String location;

    public String getAttachId() {
        return attachId==null?"":attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    public String getAttachUrl() {
        return attachUrl==null?"":attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public String getAttachType() {
        return attachType==null?"":attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    public String getAttachDesc() {
        return attachDesc==null?"":attachDesc;
    }

    public void setAttachDesc(String attachDesc) {
        this.attachDesc = attachDesc;
    }

    public String getLocation() {
        return location==null?"":location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
