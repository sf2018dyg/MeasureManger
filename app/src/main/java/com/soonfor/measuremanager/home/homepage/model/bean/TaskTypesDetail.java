package com.soonfor.measuremanager.home.homepage.model.bean;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/2 0002 09:11
 * 邮箱：suibozhu@139.com
 */

public class TaskTypesDetail {
    String name;
    String value;

    public TaskTypesDetail(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
