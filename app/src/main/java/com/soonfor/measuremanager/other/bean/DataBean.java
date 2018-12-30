package com.soonfor.measuremanager.other.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/1/19.
 */

public class DataBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * code : A01
         * name : 预订单
         * index : 0
         * items : null
         */

        private String code;
        private String name;
        private int index;
        private List<ListBean> items;

        public String getCode() {
            return code==null?"":code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name==null?"":name;
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

        public List<ListBean> getItems() {
            if(items==null)
                return new ArrayList<>();
            else
                return items;
        }

        public void setItems(List<ListBean> items) {
            this.items = items;
        }
    }
}
