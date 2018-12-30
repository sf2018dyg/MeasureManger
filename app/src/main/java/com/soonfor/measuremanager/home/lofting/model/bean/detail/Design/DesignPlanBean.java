package com.soonfor.measuremanager.home.lofting.model.bean.detail.Design;

import com.lzy.ninegrid.ImageInfo;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYg on 2018-02-01.
 */
public class DesignPlanBean {
    /*
     设计方案
     */
    private String planName;//方案名称
    private String designTime;//设计或调整方案的时间
    private String status;//审核状态
    private ArrayList<DesignDrawing> projectList;//设计图册集合

   public static class DesignDrawing {
       /**
        * 设计图册
        */
       private String name;//设计图的名称
       private String number;//设计图的编号
       private ArrayList<ImageInfo> imgList;//图片集合
       private ArrayList<String> renders;//
       private String overallView;//全景图字段

       public String getName() {
           return name==null?"":name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public String getNumber() {
           return number==null?"":number;
       }

       public void setNumber(String number) {
           this.number = number;
       }

       public ArrayList<ImageInfo> getImgList() {
           imgList = new ArrayList<>();
           if(renders!=null && renders.size()>0){
               for(int i=0; i< renders.size(); i++){
                   ImageInfo imageInfo = new ImageInfo();
                   imageInfo.setThumbnailUrl(renders.get(i));
                   imageInfo.setBigImageUrl(renders.get(i));
                   imgList.add(imageInfo);
               }
           }
           return imgList;
       }

       public void setImgList(ArrayList<ImageInfo> imgList) {
           this.imgList = imgList;
       }

       public ArrayList<String> getRenders() {
           return renders;
       }

       public void setRenders(ArrayList<String> renders) {
           this.renders = renders;
       }

       public String getOverallView() {
           return overallView==null?"":overallView;
       }

       public void setOverallView(String overallView) {
           this.overallView = overallView;
       }
   }

    public String getPlanName() {
        return planName==null?"":planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getDesignTime() {
        return designTime==null?"":designTime;
    }

    public void setDesignTime(String designTime) {
        this.designTime = designTime;
    }

    public String getStatus() {
        return status==null?"":status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<DesignDrawing> getProjectList() {
        return projectList;
    }

    public void setProjectList(ArrayList<DesignDrawing> projectList) {
        this.projectList = projectList;
    }
}
