package com.soonfor.measuremanager.bases;

import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.RegularTool;

/**
 * Created by Administrator on 2018-02-20.
 */

public class HeadBean {
    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"list":[{"code":"A01","name":"预订单","index":0,"items":null},{"code":"A02","name":"量尺","index":1,"items":[{"code":"A0201","name":"派工","index":0,"items":null},{"code":"A0202","name":"接收","index":1,"items":null},{"code":"A0203","name":"预约","index":2,"items":null},{"code":"A0204","name":"上门量尺","index":3,"items":null}]},{"code":"A03","name":"设计","index":2,"items":[{"code":"A0301","name":"派工","index":0,"items":null},{"code":"A0302","name":"接收","index":1,"items":null},{"code":"A0303","name":"设计","index":2,"items":null},{"code":"A0304","name":"审核","index":3,"items":null},{"code":"A0305","name":"确认方案","index":4,"items":null}]},{"code":"A04","name":"订金","index":3,"items":null},{"code":"A05","name":"设计","index":4,"items":[{"code":"A0501","name":"派工","index":0,"items":null},{"code":"A0502","name":"接收","index":1,"items":null},{"code":"A0503","name":"预约","index":2,"items":null},{"code":"A0504","name":"上门复尺","index":3,"items":null}]},{"code":"A06","name":"定金","index":5,"items":null},{"code":"A07","name":"合同上传","index":6,"items":null},{"code":"A08","name":"放样","index":7,"items":[{"code":"A0801","name":"派工","index":0,"items":null},{"code":"A0802","name":"接收","index":1,"items":null},{"code":"A0803","name":"预约","index":2,"items":null},{"code":"A0804","name":"上门放样","index":3,"items":null}]},{"code":"A09","name":"下单扣款","index":8,"items":[{"code":"A0901","name":"下单","index":0,"items":null},{"code":"A0902","name":"转工厂","index":1,"items":null},{"code":"A0903","name":"确认报价","index":2,"items":null},{"code":"A0904","name":"扣货款","index":3,"items":null}]},{"code":"A10","name":"收货","index":9,"items":[{"code":"A1001","name":"生产","index":0,"items":null},{"code":"A1002","name":"工厂发货","index":1,"items":null},{"code":"A1003","name":"收货","index":2,"items":null}]},{"code":"A11","name":"送货","index":10,"items":[{"code":"A1101","name":"派工","index":0,"items":null},{"code":"A1102","name":"接收","index":1,"items":null},{"code":"A1103","name":"预约","index":2,"items":null},{"code":"A1104","name":"签收","index":3,"items":null}]},{"code":"A12","name":"安装","index":11,"items":[{"code":"A1201","name":"派工","index":0,"items":null},{"code":"A1202","name":"接收","index":1,"items":null},{"code":"A1203","name":"预约","index":2,"items":null},{"code":"A1204","name":"上门安装","index":3,"items":null}]}]}
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
        return data==null || data.equals("null")?"":data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        if(msgcode==0)
            return true;
        else
            return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFaileMsg(){
        String smsg = "";
        smsg = CommonUtils.formatStr(data);
        if(smsg.equals("") || !RegularTool.isIncludeChinese(smsg)){
            smsg = CommonUtils.formatStr(msg);
            if(smsg.equals("")||!RegularTool.isIncludeChinese(smsg))
                smsg = "请求出错！";
        }
        return smsg;
    }
}
