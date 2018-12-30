package com.soonfor.measuremanager.home.homepage.model.bean;

import java.util.List;

/**
 * Created by ljc on 2018/1/16.
 */

public class ListBean<T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
