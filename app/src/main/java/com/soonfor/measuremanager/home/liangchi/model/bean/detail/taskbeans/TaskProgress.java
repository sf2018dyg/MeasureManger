package com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/2 0002 13:41
 * 邮箱：suibozhu@139.com
 */

public class TaskProgress {
    private String mainProcess;
    private String subProcess;
    private String status;
    private String processType;
    private String index;

    public String getMainProcess() {
        return mainProcess==null?"":mainProcess;
    }

    public void setMainProcess(String mainProcess) {
        this.mainProcess = mainProcess;
    }

    public String getSubProcess() {
        return subProcess==null?"":subProcess;
    }

    public void setSubProcess(String subProcess) {
        this.subProcess = subProcess;
    }

    public String getStatus() {
        return status==null?"":status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessType() {
        return processType==null?"":processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getIndex() {
        return index==null?"":index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
