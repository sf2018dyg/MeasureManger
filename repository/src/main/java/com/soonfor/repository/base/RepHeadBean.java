package com.soonfor.repository.base;

import com.soonfor.repository.tools.ComTools;

/**
 * 作者：DC-DingYG on 2018-04-27 9:03
 * 邮箱：dingyg012655@126.com
 */
public class RepHeadBean {
    /*{"Data":null,"Msg":null,"MsgCode":"1","Success":true}*/
    private int MsgCode;
    private String Msg;
    private String Data;
    private boolean Success = true;

    public int getMsgCode() {
        return MsgCode;
    }

    public void setMsgCode(int msgCode) {
        MsgCode = msgCode;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getFaileMsg(){
        String smsg = "";
        String s_data = ComTools.ToString(Data);
        if(s_data.equals("") || !ComTools.isIncludeChinese(s_data)){
            String s_msg = ComTools.ToString(Msg);
            if(s_msg.equals("")||!ComTools.isIncludeChinese(s_msg)){smsg = "请求出错!";}
            else smsg = s_msg;
        }else {
            String s_msg = ComTools.ToString(Msg);
            if(s_msg.equals("")||!ComTools.isIncludeChinese(s_msg)){smsg = "请求出错:" + s_data;}
            else smsg = s_msg + ":" + s_data;
        }
        return smsg;
    }
}
