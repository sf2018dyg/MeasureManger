package com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/5 0005 09:38
 * 邮箱：suibozhu@139.com
 */

public class rooms {
    private String name;
    private String showOrder;
    private List<components> components;

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

    public List<com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans.components> getComponents() {
        return components;
    }

    public void setComponents(List<com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans.components> components) {
        this.components = components;
    }
}
