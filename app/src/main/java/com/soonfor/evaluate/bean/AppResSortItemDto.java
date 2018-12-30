package com.soonfor.evaluate.bean;

import com.soonfor.measuremanager.tools.CommonUtils;

/**
 * 作者：DC-DingYG on 2018-11-14 17:04
 * 邮箱：dingyg012655@126.com
 */
public class AppResSortItemDto {
    private int fpoint;//得分值
    private String fsorttitle;//标签标题
    private String fapprresultitemid; //id
    private String fid;//父id
    private String fsortstardesc;//标签星级描述
    private String fsortstartitle;//标签星级标题

    public int getFpoint() {
        return fpoint;
    }

    public String getFsorttitle() {
        return CommonUtils.formatStr(fsorttitle);
    }

    public String getFapprresultitemid() {
        return CommonUtils.formatStr(fapprresultitemid);
    }

    public String getFsortstardesc() {
        return CommonUtils.formatStr(fsortstardesc);
    }

    public String getFsortstartitle() {
        return CommonUtils.formatStr(fsortstartitle);
    }

    public String getFid() {
        return CommonUtils.formatStr(fid);
    }

    public void setFpoint(int fpoint) {
        this.fpoint = fpoint;
    }

    public void setFsorttitle(String fsorttitle) {
        this.fsorttitle = fsorttitle;
    }

    public void setFapprresultitemid(String fapprresultitemid) {
        this.fapprresultitemid = fapprresultitemid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setFsortstardesc(String fsortstardesc) {
        this.fsortstardesc = fsortstardesc;
    }

    public void setFsortstartitle(String fsortstartitle) {
        this.fsortstartitle = fsortstartitle;
    }
}
