package com.soonfor.measuremanager.home.login.bean;

import android.os.Parcelable;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class LoginBean{

    /**
     * msgcode : 0
     * msg : 成功
     * data : 14587af7-3a07-409b-ac35-37e061a00076
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
        return msgcode==0;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
