package com.soonfor.evaluate.bean;

import com.soonfor.measuremanager.tools.CommonUtils;

import java.io.Serializable;

/**
 * 作者：DC-DingYG on 2018-10-17 13:29
 * 邮箱：dingyg012655@126.com
 */
public class Evaluate_CustomersToMeBean implements Serializable{

    /**
     * ------评价ID	id
     ------客户ID	customerid	number
     ------客户名称	customername	string	@mock=杨仕智2
     ------客户性别	customersex	string	@mock=1
     ------地址	address	string
     ------楼盘
     ------服务项目名称	fserviceprjname	string	@mock=上门量尺
     ------完成时间	finishdate	string
     ------任务类型	tasktype	string	@mock=0
     * ------整体评价	eval	number	@mock=0
     ------回访状态	feedbackstatus	number	@mock=0
     ------是否启用评价客户	fappcstifuse	number	@mock=1

     ------订单号	orderno	number	@mock=996272061
     ------任务号	taskno	number	@mock=116
     ------是否启用人工回访	fifrtnvisit	number	@mock=0
     ------项目ID	fserviceprjid	number	@mock=1
     ------任务标题	title	string	@mock=量尺任务
     ------是否启用客户评价	fifuse	number	@mock=1
     ------回访得分	vissorce
     ------客户评价	evalstatus	number	@mock=0
     
     */
    private String id;
    private String title;//任务标题
    private String customerid;//客户号
    private String customername;//客户姓名
    private String customerphone;//客户联系电话
    private String customersex;
    private String fserviceprjname;//服务项目名称
    private String finishdate;//量尺完成时间
    private String building;//楼盘
    private String address;//地址
    private String tasktype;
    private String taskno;
    private String orderid;//
    private String orderno;

    private int ifallhighappraise;//是否需要整体评价
    private String eval;//整体评价

    private int fappcstifuse;//是否启用评价客户
    private int fifrtnvisit;//是否启用人工回访
    private int fifuse;//是否启用客户评价
    
    private String fserviceprjid;//项目id
    
    private String feedbackstatus;//回访状态
    
    private String evalstatus;// 客户评价状态
    private String vissorce;//回访得分
    private String vistTaskId;//回访id

    public String getId() {
        return CommonUtils.formatStr(id);
    }

    public String getTitle() {
        return CommonUtils.formatStr(title);
    }

    public String getCustomerid() {
        return CommonUtils.formatStr(customerid);
    }

    public String getCustomername() {
        return CommonUtils.formatStr(customername);
    }

    public String getCustomerphone() {
        return CommonUtils.formatStr(customerphone);
    }

    public String getCustomersex() {
        return CommonUtils.formatStr(customersex);
    }

    public String getFserviceprjname() {
        return CommonUtils.formatStr(fserviceprjname);
    }

    public String getFinishdate() {
        return CommonUtils.formatStr(finishdate);
    }

    public String getAddress() {
        return CommonUtils.formatStr(address);
    }

    public String getBuilding() {
        return CommonUtils.formatStr(building);
    }

    public String getTasktype() {
        return CommonUtils.formatStr(tasktype);
    }

    public String getTaskno() {
        return CommonUtils.formatStr(taskno);
    }

    public String getOrderid() {
        return CommonUtils.formatStr(orderid);
    }

    public String getOrderno() {
        return CommonUtils.formatStr(orderno);
    }

    public int getIfallhighappraise() {
        return ifallhighappraise;
    }

    public String getEval() {//整体好评结果1:好评；2：中评;3:差评
        switch (Integer.parseInt(CommonUtils.formatNum(eval))){
            case 1:
                return "好评";
            case 2:
                return "中评";
            case 3:
                return "差评";
        }
        return "";
    }

    public int getFappcstifuse() {
        return fappcstifuse;
    }

    public void setFappcstifuse(int fappcstifuse) {
        this.fappcstifuse = fappcstifuse;
    }

    public int getFifrtnvisit() {
        return fifrtnvisit;
    }

    public int getFifuse() {
        return fifuse;
    }

    public String getFserviceprjid() {
        return CommonUtils.formatStr(fserviceprjid);
    }

    public String getFeedbackstatus() {
        return CommonUtils.formatStr(feedbackstatus);
    }

    public String getEvalstatus() {
        return CommonUtils.formatStr(evalstatus);
    }

    public String getVissorce() {
        return CommonUtils.formatStr(vissorce);
    }

    public String getVistTaskId() {
        return CommonUtils.formatStr(vistTaskId);
    }
}
