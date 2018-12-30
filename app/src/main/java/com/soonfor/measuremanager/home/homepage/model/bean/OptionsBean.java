package com.soonfor.measuremanager.home.homepage.model.bean;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/3 0003 15:42
 * 邮箱：suibozhu@139.com
 */

public class OptionsBean {
    private String code;
    private String name;
    private String index;
    private List<OptionsItems> items;

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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<OptionsItems> getItems() {
        return items;
    }

    public void setItems(List<OptionsItems> items) {
        this.items = items;
    }
}
