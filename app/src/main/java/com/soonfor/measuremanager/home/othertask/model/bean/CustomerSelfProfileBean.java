package com.soonfor.measuremanager.home.othertask.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ljc on 2018/1/18.
 */

public class CustomerSelfProfileBean implements Serializable {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"list":[{"customerId":null,"portraitCode":"a0102","portraitName":"有2个小孩","index":0},{"customerId":null,"portraitCode":"a0204","portraitName":"第一套房","index":1}]}
     * success : true
     */

    private int msgcode;
    private String msg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean implements Serializable{
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * customerId : null
             * portraitCode : a0102
             * portraitName : 有2个小孩
             * index : 0
             */

            private Object customerId;
            private String portraitCode;
            private String portraitName;
            private int index;

            public Object getCustomerId() {
                return customerId;
            }

            public void setCustomerId(Object customerId) {
                this.customerId = customerId;
            }

            public String getPortraitCode() {
                return portraitCode;
            }

            public void setPortraitCode(String portraitCode) {
                this.portraitCode = portraitCode;
            }

            public String getPortraitName() {
                return portraitName;
            }

            public void setPortraitName(String portraitName) {
                this.portraitName = portraitName;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }
        }
    }
}
