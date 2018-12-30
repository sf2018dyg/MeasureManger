package com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DingYg on 2018-03-18.
 * 部件对象
 */

public class MarkComponentEntity implements Parcelable{
    
    private String objId;//部件id
    private String name;//部件名称
    private int width;//宽度
    private int actWidth;//实际宽度
    private int height;//高度
    private int actHeight;//实际高度
    private int deep;//深度
    private int actDeep;//实际深度
    private String remark;//备注

    private String status;//放样状态（是否已放样）
    private String unit;//单位
    private int editState;//1 未编辑 2编辑后发现尺寸有差别 3 编辑成功无差别
    private int differsize_w;//相差的宽度
    private int differsize_h;//相差的高度
    private int differsize_d;//相差的厚度

    public MarkComponentEntity() {
    }


    protected MarkComponentEntity(Parcel in) {
        objId = in.readString();
        name = in.readString();
        width = in.readInt();
        actWidth = in.readInt();
        height = in.readInt();
        actHeight = in.readInt();
        deep = in.readInt();
        actDeep = in.readInt();
        remark = in.readString();
        status = in.readString();
        unit = in.readString();
        editState = in.readInt();
        differsize_w = in.readInt();
        differsize_h = in.readInt();
        differsize_d = in.readInt();
    }

    public static final Creator<MarkComponentEntity> CREATOR = new Creator<MarkComponentEntity>() {
        @Override
        public MarkComponentEntity createFromParcel(Parcel in) {
            return new MarkComponentEntity(in);
        }

        @Override
        public MarkComponentEntity[] newArray(int size) {
            return new MarkComponentEntity[size];
        }
    };

    public String getName() {
        return name==null?"":name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark==null?"":remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getObjId() {
        return objId==null?"":objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getActWidth() {
        return actWidth;
    }

    public void setActWidth(int actWidth) {
        this.actWidth = actWidth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getActHeight() {
        return actHeight;
    }

    public void setActHeight(int actHeight) {
        this.actHeight = actHeight;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public int getActDeep() {
        return actDeep;
    }

    public void setActDeep(int actDeep) {
        this.actDeep = actDeep;
    }

    public String getStatus() {
        if (getActWidth() == 0 || getActHeight() == 0
                || getActDeep() == 0) {
            return "待放样";
        } else {
            return "已放样";
        }
    }

    public String getUnit() {
        return unit==null||unit.equals("null")||unit.equals("")?"mm":unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getEditState() {
        if (getActWidth() == 0 && getActHeight() == 0
                && getActDeep() == 0) {
            return 1;
        } else if (getActWidth() == getWidth()
                && getActHeight() == getHeight()
                && getActDeep() == getDeep()) {
            return 3;
        } else {
            return 2;
        }
    }

    public int getDiffersize_w() {
        return getActWidth() - getWidth();
    }

    public int getDiffersize_h() {
        return getActHeight() - getHeight();
    }

    public int getDiffersize_d() {
        return getActDeep() - getDeep();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objId);
        dest.writeString(name);
        dest.writeInt(width);
        dest.writeInt(actWidth);
        dest.writeInt(height);
        dest.writeInt(actHeight);
        dest.writeInt(deep);
        dest.writeInt(actDeep);
        dest.writeString(remark);
        dest.writeString(status);
        dest.writeString(unit);
        dest.writeInt(editState);
        dest.writeInt(differsize_w);
        dest.writeInt(differsize_h);
        dest.writeInt(differsize_d);
    }
}