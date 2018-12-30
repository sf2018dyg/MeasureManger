package com.soonfor.measuremanager.me.bean;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class ChangeNickNameBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : 变更成功
     * success : true
     */

    private int msgcode;
    private String msg;
    private String data;
    private boolean success;

    public int getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(int msgcode) {
        this.msgcode = msgcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
