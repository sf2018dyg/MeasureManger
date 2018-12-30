package com.soonfor.measuremanager.afflatus.bean;

import java.io.Serializable;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/23 08:45
 * 邮箱：suibozhu@139.com
 */

public class ProgrammeDetail implements Serializable {
    private String type;//图的类型   0: 3D图 1 实景图
    private String typename;//图类型的名称
    private String imgpath;//图的路径

    public ProgrammeDetail(String type, String typename, String imgpath) {
        this.type = type;
        this.typename = typename;
        this.imgpath = imgpath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
