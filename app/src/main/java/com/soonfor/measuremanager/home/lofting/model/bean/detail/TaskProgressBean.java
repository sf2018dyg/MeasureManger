package com.soonfor.measuremanager.home.lofting.model.bean.detail;

/**
 * Created by DingYg on 2018-02-01.
 */
public class TaskProgressBean {
   /**
    *  "mainProcess": "A08",
    "subProcess": "A0804",
    "status": 1,
    "processType": "AssignOrderProcess",
    "index": 0

    */
   private String mainProcess;
   private String spName;
   private int index;//位置
   private String status;//状态

   public String getMainProcess() {
      return mainProcess;
   }

   public void setMainProcess(String mainProcess) {
      this.mainProcess = mainProcess;
   }

   public String getSpName() {
      return spName;
   }

   public void setSpName(String spName) {
      this.spName = spName;
   }

   public String getStatus() {
      return status==null||status.equals("")?"-1":status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public int getIndex() {
      return index;
   }

   public void setIndex(int index) {
      this.index = index;
   }
}
