package com.soonfor.measuremanager.other.bean;

/**
 * Created by ljc on 2018/1/24.
 */

public class FileUploadBean {


    /**
     * filepath : D:\Soonfor\MS_CRM\Attachment\image\20180319\20180319135234_8280\Luban_1521438751623.jpg
     * fileext : .jpg
     * title : Luban_1521438751623.jpg
     * message :
     * url : ../../../Attachment/image/20180319/20180319135234_8280/Luban_1521438751623.jpg
     * filesize : 0.09
     * error : 0
     */

    private String filepath;
    private String fileext;
    private String title;
    private String message;
    private String url;
    private String filesize;
    private int error;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFileext() {
        return fileext;
    }

    public void setFileext(String fileext) {
        this.fileext = fileext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
