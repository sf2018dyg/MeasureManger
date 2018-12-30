package com.soonfor.measuremanager.home.liangchi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:44
 * 邮箱：suibozhu@139.com
 */

public class LiangChiLookDetailBean implements Parcelable {

    /*village:"",     //小区

    buildingNo:"",  //楼栋号
    contractNo:"",  //合同号
    employeeNo:""   //员工编号
    company:""      //所属公司
    templateContractNo:"" //模板合同号（默认为空，需要时赋值真实的合同号便可）
    taskNo:""       //任务号
*/

    private String name;
    private int icon;
    private boolean newhouse;
    private boolean isFuChiHouse;
    private String contractNo;//合同号
    private String taskNo;//
    private String village;//
    private String buildingNo;//
    private String employeeNo;//
    private String company;//
    private String templateContractNo;//
    private String new_contractNo;//复测合同号
    private boolean isActivat;//是否启用按钮
    private String fimgpath;//平面图的路径

    public LiangChiLookDetailBean(String name, int icon, boolean isActivat, boolean newhouse, boolean isFuChiHouse, String contractNo, String taskNo, String village, String buildingNo, String employeeNo, String company, String templateContractNo, String new_contractNo, String fimgpath) {
        this.name = name;
        this.icon = icon;
        this.isActivat = isActivat;
        this.newhouse = newhouse;
        this.isFuChiHouse = isFuChiHouse;
        this.contractNo = contractNo;
        this.taskNo = taskNo;
        this.village = village;
        this.buildingNo = buildingNo;
        this.employeeNo = employeeNo;
        this.company = company;
        this.templateContractNo = templateContractNo;
        this.new_contractNo = new_contractNo;
        this.fimgpath = fimgpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isNewhouse() {
        return newhouse;
    }

    public void setNewhouse(boolean newhouse) {
        this.newhouse = newhouse;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTemplateContractNo() {
        return templateContractNo;
    }

    public void setTemplateContractNo(String templateContractNo) {
        this.templateContractNo = templateContractNo;
    }

    public String getNew_contractNo() {
        return new_contractNo;
    }

    public boolean isFuChiHouse() {
        return isFuChiHouse;
    }

    public void setFuChiHouse(boolean fuChiHouse) {
        isFuChiHouse = fuChiHouse;
    }

    public void setNew_contractNo(String new_contractNo) {
        this.new_contractNo = new_contractNo;
    }

    public boolean isActivat() {
        return isActivat;
    }

    public void setActivat(boolean activat) {
        isActivat = activat;
    }

    public String getFimgpath() {
        return fimgpath;
    }

    public void setFimgpath(String fimgpath) {
        this.fimgpath = fimgpath;
    }

    protected LiangChiLookDetailBean(Parcel in) {
        icon = in.readInt();
        name = in.readString();
        isActivat = in.readByte() != 0;
        contractNo = in.readString();
        taskNo = in.readString();
        village = in.readString();
        buildingNo = in.readString();
        employeeNo = in.readString();
        company = in.readString();
        templateContractNo = in.readString();
        new_contractNo = in.readString();
        newhouse = in.readByte() != 0;     //myBoolean == true if byte != 0
        isFuChiHouse = in.readByte() != 0;
        fimgpath = in.readString();
    }

    public static final Creator<LiangChiLookDetailBean> CREATOR = new Creator<LiangChiLookDetailBean>() {
        @Override
        public LiangChiLookDetailBean createFromParcel(Parcel in) {
            return new LiangChiLookDetailBean(in);
        }

        @Override
        public LiangChiLookDetailBean[] newArray(int size) {
            return new LiangChiLookDetailBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(icon);
        dest.writeString(name);
        dest.writeByte((byte) (isActivat() ? 1 : 0));
        dest.writeString(contractNo);
        dest.writeString(taskNo);
        dest.writeString(village);
        dest.writeString(buildingNo);
        dest.writeString(employeeNo);
        dest.writeString(company);
        dest.writeString(templateContractNo);
        dest.writeString(new_contractNo);
        dest.writeByte((byte) (isNewhouse() ? 1 : 0));     //if myBoolean == true, byte == 1
        dest.writeByte((byte) (isFuChiHouse() ? 1 : 0));
        dest.writeString(fimgpath);
    }
}
