package com.soonfor.measuremanager.home.homepage.model.bean;

import java.util.List;

/**
 * Created by ljc on 2018/1/15.
 */

public class AnalyseListBean {
//    {"code":"A01","name":"潜在客户","value":11,"index":0,"items":null}

    private String code;
    private String name;
    private int value;
    private int index;
    private List<AnalyseListBean> items;

    public AnalyseListBean(String code,String name,int value,int index,List<AnalyseListBean> items){
        this.code = code;
        this.name = name;
        this.value = value;
        this.index = index;
        this.items = items;
    }

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<AnalyseListBean> getItems() {
        return items;
    }

    public void setItems(List<AnalyseListBean> items) {
        this.items = items;
    }
}
