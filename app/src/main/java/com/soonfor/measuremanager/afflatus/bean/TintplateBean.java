package com.soonfor.measuremanager.afflatus.bean;

import java.io.Serializable;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:44
 * 邮箱：suibozhu@139.com
 * 色板
 */
public class TintplateBean implements Serializable {
    private String id;
    private String bid;
    private String title;
    private int sort;
    private String remark;
    private int status;
    private String details;
    private String merchantCode;
    private String creator;
    private String createTime;
    private String updater;
    private long updateTime;
    private String imgUrl;
    private int collects;
    private int intents;
    private String offReason;
    private String tname;
    private int ifIntent;
    private String ifCollect;
    private String contentPageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getCollects() {
        return collects;
    }

    public void setCollects(int collects) {
        this.collects = collects;
    }

    public int getIntents() {
        return intents;
    }

    public void setIntents(int intents) {
        this.intents = intents;
    }

    public String getOffReason() {
        return offReason;
    }

    public void setOffReason(String offReason) {
        this.offReason = offReason;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public int getIfIntent() {
        return ifIntent;
    }

    public void setIfIntent(int ifIntent) {
        this.ifIntent = ifIntent;
    }

    public String getIfCollect() {
        return ifCollect;
    }

    public void setIfCollect(String ifCollect) {
        this.ifCollect = ifCollect;
    }

    public String getContentPageUrl() {
        return contentPageUrl;
    }

    public void setContentPageUrl(String contentPageUrl) {
        this.contentPageUrl = contentPageUrl;
    }
}
