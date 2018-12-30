package com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/5 0005 09:39
 * 邮箱：suibozhu@139.com
 */

public class components {
    private String name;
    private String showOrder;
    private List<sizes> sizes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(String showOrder) {
        this.showOrder = showOrder;
    }

    public List<com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans.sizes> getSizes() {
        return sizes;
    }

    public void setSizes(List<com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans.sizes> sizes) {
        this.sizes = sizes;
    }
}
