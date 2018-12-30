package com.dynamicpicpro.model;

/**
 * Created by Administrator on 2017-06-15.
 */

import java.io.Serializable;


public class UploadGoodsBean implements Serializable {
    private int index;
    private String url;
    private Boolean isNet;

    public UploadGoodsBean() {
        super();
    }

    public UploadGoodsBean(int index, String url, Boolean isNet) {
        super();
        this.index = index;
        this.url = url;
        this.isNet = isNet;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsNet() {
        return isNet;
    }

    public void setIsNet(Boolean isNet) {
        this.isNet = isNet;
    }


}