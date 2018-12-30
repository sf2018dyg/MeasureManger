package com.soonfor.measuremanager.afflatus.bean.postBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/10/9 0009 08:50
 * 邮箱：suibozhu@139.com
 * 类用途：提交用的
 */
public class PostCaseBean {
    private List<String> meritsIdList = new ArrayList<>();
    private String sortColumn = "";
    private String cstid = "";
    private String sceneId = "";
    private String keyword = "";
    private List<String> styleIdList = new ArrayList<>();
    private List<String> areaIdList = new ArrayList<>();
    private int pageNo = 1;
    private int pageSize = 9999;
    private List<String> huxingIdList = new ArrayList<>();
    private List<String> priceIdList = new ArrayList<>();
    private int sortOrder = 1;

    public List<String> getMeritsIdList() {
        return meritsIdList;
    }

    public void setMeritsIdList(List<String> meritsIdList) {
        this.meritsIdList = meritsIdList;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getCstid() {
        return cstid;
    }

    public void setCstid(String cstid) {
        this.cstid = cstid;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getStyleIdList() {
        return styleIdList;
    }

    public void setStyleIdList(List<String> styleIdList) {
        this.styleIdList = styleIdList;
    }

    public List<String> getAreaIdList() {
        return areaIdList;
    }

    public void setAreaIdList(List<String> areaIdList) {
        this.areaIdList = areaIdList;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getHuxingIdList() {
        return huxingIdList;
    }

    public void setHuxingIdList(List<String> huxingIdList) {
        this.huxingIdList = huxingIdList;
    }

    public List<String> getPriceIdList() {
        return priceIdList;
    }

    public void setPriceIdList(List<String> priceIdList) {
        this.priceIdList = priceIdList;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
