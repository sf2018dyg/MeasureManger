package com.soonfor.measuremanager.upload.model.bean;

import com.dynamicpicpro.model.UploadGoodsBean;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/4/2 0002 09:45
 * 邮箱：suibozhu@139.com
 */
public class uploadBean {
    private int index;
    private String name;
    private String subname;
    private List<UploadGoodsBean> upPics;

    public uploadBean(int index, String name, String subname, List<UploadGoodsBean> upPics) {
        this.index = index;
        this.name = name;
        this.subname = subname;
        this.upPics = upPics;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public List<UploadGoodsBean> getUpPics() {
        return upPics;
    }

    public void setUpPics(List<UploadGoodsBean> upPics) {
        this.upPics = upPics;
    }
}
