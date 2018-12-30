package com.soonfor.measuremanager.home.liangchi.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:44
 * 邮箱：suibozhu@139.com
 * 户型图
 */

public class ApartmentLayoutBean implements Parcelable {
    /*{"fobsplanid":"19501521614045605","fname":"1楼",
    "fspecname":"null厅null房null卫","fsrcarea":null,
    "fpics":"http:\/\/api.jiamm.cn\/test\/lfj\/floor\/drawing\/5ab1fd17df623c59d31f562d\/5a966ab8b0f
    8298f236ca452\/1920\/1080?chair=false",
    "faddress":"嘉善县大众湖滨花园17栋20层2001号房",
    "row":1}*/
    private String fobsplanid;//
    private String fpics; //户型图路径
    private String fname;//客户名称
    private String faddress;//客户地址
    private String fspecname;//户型图类型
    private String fsrcarea;//面积
    private String row;//

    public String getFobsplanid() {
        return fobsplanid;
    }

    public void setFobsplanid(String fobsplanid) {
        this.fobsplanid = fobsplanid;
    }

    public String getFpics() {
        return fpics;
    }

    public void setFpics(String fpics) {
        this.fpics = fpics;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        this.faddress = faddress;
    }

    public String getFspecname() {
        return fspecname;
    }

    public void setFspecname(String fspecname) {
        this.fspecname = fspecname;
    }

    public String getFsrcarea() {
        return fsrcarea;
    }

    public void setFsrcarea(String fsrcarea) {
        this.fsrcarea = fsrcarea;
    }

    protected ApartmentLayoutBean(Parcel in) {
        fobsplanid = in.readString();
        fpics = in.readString();
        fname = in.readString();
        row = in.readString();
        faddress = in.readString();
        fspecname = in.readString();
        fsrcarea = in.readString();
    }

    public static final Creator<ApartmentLayoutBean> CREATOR = new Creator<ApartmentLayoutBean>() {
        @Override
        public ApartmentLayoutBean createFromParcel(Parcel in) {
            return new ApartmentLayoutBean(in);
        }

        @Override
        public ApartmentLayoutBean[] newArray(int size) {
            return new ApartmentLayoutBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fobsplanid);
        dest.writeString(fpics);
        dest.writeString(fname);
        dest.writeString(row);
        dest.writeString(faddress);
        dest.writeString(fspecname);
        dest.writeString(fsrcarea);
    }
}
