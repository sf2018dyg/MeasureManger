package com.soonfor.measuremanager.home.othertask.model.bean.detail;

/**
 * 作者：DC-DingYG on 2018-11-12 15:23
 * 邮箱：dingyg012655@126.com
 */
public class FileBean {
    private String attachId;//附件id
    private String attachUrl;//附件url
    private String attachName;//附件名称

    public FileBean(String attachId, String attachUrl, String attachName) {
        this.attachId = attachId;
        this.attachUrl = attachUrl;
        this.attachName = attachName;
    }

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

    public String getAttachName() {
        return attachName==null?"":attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }
    public String getFileFormat(){
        String fileName = getAttachName().trim();
        if(fileName.contains(".")) {
            String format = fileName.substring(fileName.indexOf(".") + 1, fileName.length()).toLowerCase();
            return format;
        }
        return "";
    }
}
