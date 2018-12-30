package com.soonfor.measuremanager.home.order.bean;

import java.util.List;

/**
 * Created by ljc on 2018/1/24.
 */

public class StaffBean {

    /**
     * data : {"list":[{"code":"ACS05739999180004","id":928834087,"jobType":"测量员","name":"陈文辉","phone":"13143087476"},{"code":"ACS05739999180005","id":928945288,"jobType":"测量员","name":"李彩桦","phone":""},{"code":"ACS05739999180006","id":929031815,"jobType":"测量员","name":"张力博","phone":""}]}
     * msg : 请求成功
     * msgcode : 0
     * success : true
     */

    private DataBean data;
    private String msg;
    private int msgcode;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(int msgcode) {
        this.msgcode = msgcode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * code : ACS05739999180004
             * id : 928834087
             * jobType : 测量员
             * name : 陈文辉
             * phone : 13143087476
             */

            private String code;
            private int id;
            private String jobType;
            private String name;
            private String phone;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getJobType() {
                return jobType;
            }

            public void setJobType(String jobType) {
                this.jobType = jobType;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
