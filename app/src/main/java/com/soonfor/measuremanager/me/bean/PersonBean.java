package com.soonfor.measuremanager.me.bean;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class PersonBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"nickName":"李晶钰","avatar":"http://ar.xx.com/ssees.jpg","post":"导购","phone":"13632125478","birthday":"1991-09-10T00:00:00","sex":0,"qrCard":"bsx22a2322"}
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

    public static class DataBean {
        /**
         * nickName : 李晶钰
         * avatar : http://ar.xx.com/ssees.jpg
         * post : 导购
         * phone : 13632125478
         * birthday : 1991-09-10T00:00:00
         * sex : 0
         * qrCard : bsx22a2322
         */

        private String nickName;
        private String avatar;
        private String post;
        private String phone;
        private String birthday;
        private int sex;
        private String qrCard;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getQrCard() {
            return qrCard;
        }

        public void setQrCard(String qrCard) {
            this.qrCard = qrCard;
        }
    }
}
