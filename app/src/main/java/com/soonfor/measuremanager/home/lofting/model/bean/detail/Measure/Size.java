package com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure;

/**
 * Created by DingYg on 2018-03-20.
 */

public class Size {
    private String name;
    private String value;

    public Size() {
    }

    public Size(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name==null?"":name;
    }

    public String getValue() {
        return value = value==null?"":value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
