package com.soonfor.measuremanager.home.complexruler.bean.detail;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/1 09:51
 * 邮箱：suibozhu@139.com
 */

public class measureChild {
    String goodname;
    String goodsize;

    public measureChild(String goodname,String goodsize){
        this.goodname = goodname;
        this.goodsize = goodsize;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getGoodsize() {
        return goodsize;
    }

    public void setGoodsize(String goodsize) {
        this.goodsize = goodsize;
    }
}
