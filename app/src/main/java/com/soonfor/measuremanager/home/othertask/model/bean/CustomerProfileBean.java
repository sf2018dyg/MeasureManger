package com.soonfor.measuremanager.home.othertask.model.bean;

import java.util.List;

/**
 * Created by ljc on 2018/1/18.
 */

public class CustomerProfileBean {

    /**
     * msgcode : 1
     * msg : 请求成功
     * data : {"list":[{"code":"a01","name":"自然特征","index":0,"items":[{"code":"a0101","name":"有1个小孩","index":0,"items":null},{"code":"a0102","name":"有2个小孩","index":1,"items":null},{"code":"a0103","name":"有多个小孩","index":2,"items":null},{"code":"a0104","name":"独生","index":3,"items":null},{"code":"a0105","name":"多个兄弟","index":4,"items":null},{"code":"a0106","name":"多个姐妹","index":5,"items":null}]},{"code":"a02","name":"社会特征","index":1,"items":[{"code":"a0201","name":"白领","index":0,"items":null},{"code":"a0202","name":"蓝领","index":1,"items":null},{"code":"a0203","name":"金领","index":2,"items":null},{"code":"a0204","name":"第一套房","index":3,"items":null},{"code":"a0205","name":"多套房","index":4,"items":null},{"code":"a0206","name":"自建房","index":5,"items":null}]},{"code":"a03","name":"消费特征","index":2,"items":[{"code":"a0301","name":"高收入","index":0,"items":null},{"code":"a0302","name":"中等收入","index":1,"items":null},{"code":"a0303","name":"低收入","index":2,"items":null},{"code":"a0304","name":"固定品牌","index":3,"items":null},{"code":"a0305","name":"喜欢名牌","index":4,"items":null},{"code":"a0306","name":"喜欢折扣","index":5,"items":null}]}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * code : a01
             * name : 自然特征
             * index : 0
             * items : [{"code":"a0101","name":"有1个小孩","index":0,"items":null},{"code":"a0102","name":"有2个小孩","index":1,"items":null},{"code":"a0103","name":"有多个小孩","index":2,"items":null},{"code":"a0104","name":"独生","index":3,"items":null},{"code":"a0105","name":"多个兄弟","index":4,"items":null},{"code":"a0106","name":"多个姐妹","index":5,"items":null}]
             */

            private String code;
            private String name;
            private int index;
            private List<ItemsBean> items;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                /**
                 * code : a0101
                 * name : 有1个小孩
                 * index : 0
                 * items : null
                 */

                private String code;
                private String name;
                private int index;
                private Object items;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getIndex() {
                    return index;
                }

                public void setIndex(int index) {
                    this.index = index;
                }

                public Object getItems() {
                    return items;
                }

                public void setItems(Object items) {
                    this.items = items;
                }
            }
        }
    }
}
