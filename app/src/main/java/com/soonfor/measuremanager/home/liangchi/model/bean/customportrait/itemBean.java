package com.soonfor.measuremanager.home.liangchi.model.bean.customportrait;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/23 0023 11:42
 * 邮箱：suibozhu@139.com
 */

public class itemBean {
    String code;
    String name;
    String index;
    List<itemBean> items;
    int posi;
    boolean isSelected;

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

    public List<itemBean> getItems() {
        return items;
    }

    public void setItems(List<itemBean> items) {
        this.items = items;
    }

    public int getPosi() {
        return posi;
    }

    public void setPosi(int posi) {
        this.posi = posi;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
