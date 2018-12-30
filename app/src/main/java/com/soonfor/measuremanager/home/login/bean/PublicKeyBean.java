package com.soonfor.measuremanager.home.login.bean;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class PublicKeyBean {

    /**
     * msgcode : 0
     * msg : 成功
     * data : MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDL12Lr2t+kpP0SZ27Qs0WgcFMHDSi1MzGiM098y1P7KH5Zs28Ig6JuLbOXmC9/VYwzp0hVmRCpQjc3sXpDLU1jg4LTvP5cKol9S8aN4voJLXb30PcL2/9uDxTFrexlTC6YfhHzoSVfgmLP0mU+E8IfhO2myVcRe1SsjISSd6R42wIDAQAB
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
