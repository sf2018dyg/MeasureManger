package com.soonfor.measuremanager.bases;

/**
 * Created by Administrator on 2018-03-13.
 */

public class ErrorBean {

    private String timestamp;
    private String status;
    private String error;
    private String exception;
    private String message;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status==null?"":status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error==null?"":error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception==null?"":exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message==null?"":message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
