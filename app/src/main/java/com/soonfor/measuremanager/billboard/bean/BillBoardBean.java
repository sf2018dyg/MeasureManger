package com.soonfor.measuremanager.billboard.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:44
 * 邮箱：suibozhu@139.com
 */

public class BillBoardBean implements Parcelable {

    private String billno;//排名
    private String headPath;//头像地址
    private String designerName;//设计师名称
    private String designFamous;//名言
    private String yeji;//业绩
    private String dianzan;//点赞
    private String fenshishu;//粉丝数

    public BillBoardBean(String billno, String headPath, String designerName, String designFamous, String yeji, String dianzan, String fenshishu) {
        this.billno = billno;
        this.headPath = headPath;
        this.designerName = designerName;
        this.designFamous = designFamous;
        this.yeji = yeji;
        this.dianzan = dianzan;
        this.fenshishu = fenshishu;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getDesignFamous() {
        return designFamous;
    }

    public void setDesignFamous(String designFamous) {
        this.designFamous = designFamous;
    }

    public String getYeji() {
        return yeji;
    }

    public void setYeji(String yeji) {
        this.yeji = yeji;
    }

    public String getDianzan() {
        return dianzan;
    }

    public void setDianzan(String dianzan) {
        this.dianzan = dianzan;
    }

    public String getFenshishu() {
        return fenshishu;
    }

    public void setFenshishu(String fenshishu) {
        this.fenshishu = fenshishu;
    }

    protected BillBoardBean(Parcel in) {
        billno = in.readString();
        headPath = in.readString();
        designerName = in.readString();
        designFamous = in.readString();
        yeji = in.readString();
        dianzan = in.readString();
        fenshishu = in.readString();
    }

    public static final Creator<BillBoardBean> CREATOR = new Creator<BillBoardBean>() {
        @Override
        public BillBoardBean createFromParcel(Parcel in) {
            return new BillBoardBean(in);
        }

        @Override
        public BillBoardBean[] newArray(int size) {
            return new BillBoardBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(billno);
        dest.writeString(headPath);
        dest.writeString(designerName);
        dest.writeString(designFamous);
        dest.writeString(yeji);
        dest.writeString(dianzan);
        dest.writeString(fenshishu);
    }
}
