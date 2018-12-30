package com.soonfor.measuremanager.home.othertask.model.bean.detail;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ljc on 2018/1/18.
 */

public class TaskDetailBean implements Serializable {


    /**
     * msgcode : 0
     * msg : 请求成功
     * data : {"planID":465,"customerIdx":24886,"taskNox":460,"customerId":"24886","taskNo":"460","taskType":"common","taskTitle":"我用苹果测试","taskDesc":"哈哈","taskSourceType":"2","taskResultType":"1","customerName":"名字很长","houseAddress":"曾培养","phone":"15684943784","priorityType":"2","taskStartDate":"2018-06-19T13:55:00","taskCompleteDate":"2018-06-19T13:55:00","personType":"4","personName":"i测试门店","workPoints":0,"attaches":[{"attachId":"448","attachUrl":"","attachType":4,"attachDesc":null,"location":"中国广东省东莞市新基路2号"},{"attachId":"449","attachUrl":"D:\\Soonfor\\MS_CRM\\Attachment\\image\\20180619\\20180619135601_5798\\20180619135601.aac","attachType":1,"attachDesc":null,"location":null},{"attachId":"450","attachUrl":"D:\\Soonfor\\MS_CRM\\Attachment\\image\\20180619\\20180619135601_8997\\20180619135601.png","attachType":0,"attachDesc":null,"location":null},{"attachId":"451","attachUrl":"D:\\Soonfor\\MS_CRM\\Attachment\\image\\20180619\\20180619135602_2651\\20180619135602.png","attachType":0,"attachDesc":null,"location":null}],"itemAttaches":[],"note":null,"buildID":0,"buildName":null,"finishDate":null,"isMyTask":"1"}
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

    public static class DataBean implements Serializable {
        /**
         * planID : 465
         * customerIdx : 24886
         * taskNox : 460
         * customerId : 24886
         * taskNo : 460
         * taskType : common
         * taskTitle : 我用苹果测试
         * taskDesc : 哈哈
         * taskSourceType : 2
         * taskResultType : 1
         * customerName : 名字很长
         * houseAddress : 曾培养
         * phone : 15684943784
         * priorityType : 2
         * taskStartDate : 2018-06-19T13:55:00
         * taskCompleteDate : 2018-06-19T13:55:00
         * personType : 4
         * personName : i测试门店
         * workPoints : 0
         * attaches : [{"attachId":"448","attachUrl":"","attachType":4,"attachDesc":null,"location":"中国广东省东莞市新基路2号"},{"attachId":"449","attachUrl":"D:\\Soonfor\\MS_CRM\\Attachment\\image\\20180619\\20180619135601_5798\\20180619135601.aac","attachType":1,"attachDesc":null,"location":null},{"attachId":"450","attachUrl":"D:\\Soonfor\\MS_CRM\\Attachment\\image\\20180619\\20180619135601_8997\\20180619135601.png","attachType":0,"attachDesc":null,"location":null},{"attachId":"451","attachUrl":"D:\\Soonfor\\MS_CRM\\Attachment\\image\\20180619\\20180619135602_2651\\20180619135602.png","attachType":0,"attachDesc":null,"location":null}]
         * itemAttaches : []
         * note : null
         * buildID : 0
         * buildName : null
         * finishDate : null
         * isMyTask : 1
         */

        private int planID;
        private int customerIdx;
        private int taskNox;
        private String customerId;
        private String taskNo;
        private String taskType;
        private String taskTitle;
        private String taskDesc;
        private String taskSourceType;
        private String taskResultType;
        private String customerName;
        private String houseAddress;
        private String phone;
        private String priorityType;
        private String taskStartDate;
        private String taskCompleteDate;
        private String personType;
        private String personName;
        private int workPoints;
        private String note;
        private int buildID;
        private String buildName;
        private String finishDate;
        private String isMyTask;
        private List<AttachesBean> attaches;
        private List<AttachesBean> itemAttaches;

        public int getPlanID() {
            return planID;
        }

        public void setPlanID(int planID) {
            this.planID = planID;
        }

        public int getCustomerIdx() {
            return customerIdx;
        }

        public void setCustomerIdx(int customerIdx) {
            this.customerIdx = customerIdx;
        }

        public int getTaskNox() {
            return taskNox;
        }

        public void setTaskNox(int taskNox) {
            this.taskNox = taskNox;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getTaskNo() {
            return taskNo;
        }

        public void setTaskNo(String taskNo) {
            this.taskNo = taskNo;
        }

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public String getTaskTitle() {
            return taskTitle;
        }

        public void setTaskTitle(String taskTitle) {
            this.taskTitle = taskTitle;
        }

        public String getTaskDesc() {
            return taskDesc;
        }

        public void setTaskDesc(String taskDesc) {
            this.taskDesc = taskDesc;
        }

        public String getTaskSourceType() {
            return taskSourceType;
        }

        public void setTaskSourceType(String taskSourceType) {
            this.taskSourceType = taskSourceType;
        }

        public String getTaskResultType() {
            return taskResultType;
        }

        public void setTaskResultType(String taskResultType) {
            this.taskResultType = taskResultType;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getHouseAddress() {
            return houseAddress;
        }

        public void setHouseAddress(String houseAddress) {
            this.houseAddress = houseAddress;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPriorityType() {
            return priorityType;
        }

        public void setPriorityType(String priorityType) {
            this.priorityType = priorityType;
        }

        public String getTaskStartDate() {
            return taskStartDate;
        }

        public void setTaskStartDate(String taskStartDate) {
            this.taskStartDate = taskStartDate;
        }

        public String getTaskCompleteDate() {
            return taskCompleteDate;
        }

        public void setTaskCompleteDate(String taskCompleteDate) {
            this.taskCompleteDate = taskCompleteDate;
        }

        public String getPersonType() {
            return personType;
        }

        public void setPersonType(String personType) {
            this.personType = personType;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public int getWorkPoints() {
            return workPoints;
        }

        public void setWorkPoints(int workPoints) {
            this.workPoints = workPoints;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getBuildID() {
            return buildID;
        }

        public void setBuildID(int buildID) {
            this.buildID = buildID;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
        }

        public String getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(String finishDate) {
            this.finishDate = finishDate;
        }

        public String getIsMyTask() {
            return isMyTask;
        }

        public void setIsMyTask(String isMyTask) {
            this.isMyTask = isMyTask;
        }

        public List<AttachesBean> getAttaches() {
            return attaches;
        }

        public void setAttaches(List<AttachesBean> attaches) {
            this.attaches = attaches;
        }

        public List<AttachesBean> getItemAttaches() {
            return itemAttaches;
        }

        public void setItemAttaches(List<AttachesBean> itemAttaches) {
            this.itemAttaches = itemAttaches;
        }

        public static class AttachesBean implements Serializable {
            /**
             * attachId : 448
             * attachUrl :
             * attachType : 4
             * attachDesc : null
             * location : 中国广东省东莞市新基路2号
             */

            private String attachId;
            private String attachUrl;
            private int attachType;
            private Object attachDesc;
            private String location;

            public String getAttachId() {
                return attachId;
            }

            public void setAttachId(String attachId) {
                this.attachId = attachId;
            }

            public String getAttachUrl() {
                return attachUrl;
            }

            public void setAttachUrl(String attachUrl) {
                this.attachUrl = attachUrl;
            }

            public int getAttachType() {
                return attachType;
            }

            public void setAttachType(int attachType) {
                this.attachType = attachType;
            }

            public Object getAttachDesc() {
                return attachDesc;
            }

            public void setAttachDesc(Object attachDesc) {
                this.attachDesc = attachDesc;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }
        }
    }
}
