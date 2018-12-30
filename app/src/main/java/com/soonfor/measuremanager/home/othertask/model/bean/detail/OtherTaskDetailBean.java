package com.soonfor.measuremanager.home.othertask.model.bean.detail;

import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.repository.tools.ComTools;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dingyg on 2018/10/24.
 * 主动追踪及回访任务详情实体
 */

public class OtherTaskDetailBean implements Serializable {
    /**
     * 自增字段
     */
    private String orderTypeDesc;//类型描述
    /**
     * 主动追踪任务详情和人工回访任务详情共用字段
     * ---任务状态	status	string
     ---状态代号	statusCode	string
     ---任务ID	taskId	number
     ---工分	workPoints
     ---任务单号	taskNo	string
     ---客户ID	customerId
     ---客户名称	customerName
     ---手机号码	mobilePhone	string
     ---执行方式	execType	string
     ---执行时间	execDate	string
     ---发布时间	publishTime
     ---楼盘信息	building	string
     ---地址	address	string
     */
    /** 主动追踪任务详情 独有

     ---订单号	orderNo	string
     ---订单类型	ordType	string
     ---订单ID	orderId	string
     ---任务描述	description	string
     */
    /**
     * 人工回访任务详情
     * <p>
     * ---来源ID	foriId
     * ---回访对象信息	visitDto	object
     * ------回访类型1=量尺;2=方案设计;3=复尺;4=放样;5=送货;6=安装foriType	number
     * ------服务项目	ServicePrj	string
     * ------处理人员	ececuterName	string
     * ------完成时间	completeTime	string
     * ---回访来源(0=任务；1=量尺;2=方案设计;3=复尺;4=放样;5=送货;6=安装)	foriType
     */

    private String taskId;
    private String taskNo;
    private String status;
    private String statusCode;
    private String workPoints;
    private String customerId;
    private String customerName;
    private String mobilePhone;
    private String execType;
    private String execDate;
    private String publishTime;
    private String building;
    private String address;
    private String description;
    private String orderNo;
    private String orderId;
    private String ordType;

    private String foriId;
    private String foriType;
    private VisitDto visitDto;
    private String serviceprjid;
    private String ececuterId;
    private String ececuterName;

    public String getTaskId() {
        return CommonUtils.formatStr(taskId);
    }

    public String getWorkPoints() {
        return CommonUtils.formatStr(workPoints);
    }

    public String getMobilePhone() {
        return CommonUtils.formatStr(mobilePhone);
    }

    public String getBuilding() {
        return CommonUtils.formatStr(building);
    }

    public VisitDto getVisitDto() {
        return visitDto;
    }

    public String getForiId() {
        return CommonUtils.formatStr(foriId);
    }


    public String getStatus() {
        return CommonUtils.formatStr(status);
    }

    public String getCustomerId() {
        return CommonUtils.formatStr(customerId);
    }

    public String getCustomerName() {
        return CommonUtils.formatStr(customerName);
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getExecType() {
        return CommonUtils.formatStr(execType);
    }


    public String getPublishTime() {
        return CommonUtils.formatStr(publishTime);
    }


    public String getAddress() {
        return CommonUtils.formatStr(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return CommonUtils.formatStr(description);
    }

    public String getOrderNo() {
        return CommonUtils.formatStr(orderNo);
    }

    public String getOrderId() {
        return CommonUtils.formatStr(orderId);
    }

    public String getOrdType() {
        return CommonUtils.formatNum(ordType);
    }

    public String getForiType() {
        return CommonUtils.formatNum(foriType);
    }

    public String getServiceprjid() {
        return CommonUtils.formatNum(serviceprjid);
    }

    public String getEcecuterId() {
        return CommonUtils.formatNum(ececuterId);
    }

    public String getEcecuterName() {
        return CommonUtils.formatNum(ececuterName);
    }

    public String getOrderTypeDesc() {
        switch (Integer.parseInt(getOrdType())) {
            case 1:
                return "销";
            case 2:
                return "换购";
            case 3:
                return "预";
            case 4:
                return "增补";
            default:
                return "";
        }
    }

    public String getTaskNo() {
        return CommonUtils.formatStr(taskNo);
    }

    public String getStatusCode() {
        return CommonUtils.formatStr(statusCode);
    }

    public String getExecDate() {
        return DateTool.getTimeTimestamp(execDate, null);
    }

    /**
     * 回访内容对象
     */
    public static class VisitDto {
        private String foriType;//回访类型1=量尺;2=方案设计;3=复尺;4=放样;5=送货;6=安装
        private String servicePrj;//服务项目
        private String ececuterName;//处理人员
        private String completeTime;//完成时间

        public String getForiType() {
            switch (Integer.parseInt(CommonUtils.formatNum(foriType))) {
                case 0://普通任务
                    return "执行人员";
                case 1://量尺任务
                    return "测量人员";
                case 2://设计任务
                    return "设计师";
                case 3://复尺任务
                    return "测量人员";
                case 4://放样
                    return "放样人员";
                case 5:
                    return "送货人员";
                case 6:
                    return "安装人员";
            }
            return "执行人员";
        }

        public void setForiType(String foriType) {
            this.foriType = foriType;
        }

        public String getServicePrj() {
            return CommonUtils.formatStr(servicePrj);
        }

        public void setServicePrj(String servicePrj) {
            servicePrj = servicePrj;
        }

        public String getEcecuterName() {
            return CommonUtils.formatStr(ececuterName);
        }

        public void setEcecuterName(String ececuterName) {
            this.ececuterName = ececuterName;
        }

        public String getCompleteTime() {
            return CommonUtils.formatStr(completeTime);
        }

        public void setCompleteTime(String completeTime) {
            this.completeTime = completeTime;
        }
    }

}
