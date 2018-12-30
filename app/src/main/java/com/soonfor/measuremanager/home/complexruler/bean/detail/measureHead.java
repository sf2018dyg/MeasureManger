package com.soonfor.measuremanager.home.complexruler.bean.detail;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/1 09:51
 * 邮箱：suibozhu@139.com
 */

public class measureHead {
    String head;
    List<measureChild> childs;

    public measureHead(String head, List<measureChild> childs) {
        this.head = head;
        this.childs = childs;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<measureChild> getChilds() {
        return childs;
    }

    public void setChilds(List<measureChild> childs) {
        this.childs = childs;
    }
}
