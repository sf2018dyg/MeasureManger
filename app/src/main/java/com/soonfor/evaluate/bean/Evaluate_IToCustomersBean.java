package com.soonfor.evaluate.bean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;

import java.io.Serializable;

/**
 * 作者：DC-DingYG on 2018-10-19 8:34
 * 邮箱：dingyg012655@126.com
 */
public class Evaluate_IToCustomersBean implements Serializable{
    /**
     * ------客户ID	customerid
     ------客户评价	evalstatus	number	@mock=0
     ------地址	address	string	@mock=4
     ------回访状态	feedbackstatus	number	@mock=0
     ------	fifuse	number	@mock=1
     ------完成时间	finishdate
     ------项目ID	fserviceprjid	number	@mock=1
     ------客户名称	customername	string	@mock=杨仕智2
     ------	fappcstifuse	number	@mock=1
     ------订单号	orderno	number	@mock=996272061
     ------客户性别	customersex	string	@mock=1
     ------回访得分	vissorce
     ------整体评价	eval	number	@mock=0
     ------服务项目名称	fserviceprjname	string	@mock=上门量尺
     ------评价id	id
     ------	fifrtnvisit	number	@mock=0
     ------任务号	taskno	number	@mock=116
     ------任务类型	tasktype	string	@mock=0
     ------任务标题	title	string
     */
    private String customerid;//-----客户ID
    private String	customername;
    private String customerphone;//客户联系电话
    private String	evalstatus;
    private String building;//楼盘
    private String address;//地址
    private String	feedbackstatus;
    private String	fifuse;
    private String	finishdate;
    private String	fserviceprjid;
    private String	fappcstifuse;
    private String	orderno;
    private String customersex;
    private String 	vissorce;
    private int ifallhighappraise;
    private String 	eval;
    private String 	fserviceprjname;
    private String 	id;
    private String fifrtnvisit;
    private String taskno;
    private String 	tasktype;
    private String title;

    public String getCustomerid() {
        return CommonUtils.formatStr(customerid);
    }

    public String getCustomername() {
        return CommonUtils.formatStr(customername);
    }

    public String getCustomerphone() {
        return CommonUtils.formatStr(customerphone);
    }

    public String getEvalstatus() {
        return CommonUtils.formatStr(evalstatus);
    }

    public String getAddress() {
        return CommonUtils.formatStr(address);
    }

    public String getBuilding() {
        return CommonUtils.formatStr(building);
    }

    public String getFeedbackstatus() {
        return CommonUtils.formatStr(feedbackstatus);
    }

    public String getFifuse() {
        return CommonUtils.formatStr(fifuse);
    }

    public String getFinishdate() {
        return DateTool.getTimeTimestamp(finishdate, null);
    }

    public String getFserviceprjid() {
        return CommonUtils.formatStr(fserviceprjid);
    }


    public String getFappcstifuse() {
        return CommonUtils.formatStr(fappcstifuse);
    }

    public String getOrderno() {
        return CommonUtils.formatStr(orderno);
    }

    public String getCustomersex() {
        return CommonUtils.formatStr(customersex);
    }

    public String getVissorce() {
        return CommonUtils.formatStr(vissorce);
    }

    public int getIfallhighappraise() {
        return ifallhighappraise;
    }

    public String getEval() {
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

    public String getFserviceprjname() {
        return CommonUtils.formatStr(fserviceprjname);
    }

    public String getId() {
        return CommonUtils.formatStr(id);
    }

    public String getFifrtnvisit() {
        return CommonUtils.formatStr(fifrtnvisit);
    }

    public String getTaskno() {
        return CommonUtils.formatStr(taskno);
    }

    public String getTasktype() {
        return CommonUtils.formatStr(tasktype);
    }

    public String getTitle() {
        return CommonUtils.formatStr(title);
    }
}
