package com.soonfor.measuremanager.home.lofting.model.bean.detail.Mark;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;

import java.util.List;

/**
 * @author tanljs
 * @date 2018-02-09
 */
public class MarkResultEntity implements Parcelable{
    private List<MarkDrawing> markDrawing;//	string	放样图
    private List<MarkWallEntity> rooms;// rooms	List<MarkWallEntity>	放样清单
    private String unitName; //户型名称
    private boolean isShowing;//展示的户型数据是否为当前数据


    protected MarkResultEntity(Parcel in) {
        markDrawing = in.createTypedArrayList(MarkDrawing.CREATOR);
        rooms = in.createTypedArrayList(MarkWallEntity.CREATOR);
        unitName = in.readString();
        isShowing = in.readByte() != 0;
    }

    public static final Creator<MarkResultEntity> CREATOR = new Creator<MarkResultEntity>() {
        @Override
        public MarkResultEntity createFromParcel(Parcel in) {
            return new MarkResultEntity(in);
        }

        @Override
        public MarkResultEntity[] newArray(int size) {
            return new MarkResultEntity[size];
        }
    };

    public List<MarkDrawing> getMarkDrawing() {
        return markDrawing;
    }

    public void setMarkDrawing(List<MarkDrawing> markDrawing) {
        this.markDrawing = markDrawing;
    }

    public String getUnitName() {
        return unitName==null?"":unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<MarkWallEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<MarkWallEntity> rooms) {
        this.rooms = rooms;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(markDrawing);
        dest.writeTypedList(rooms);
        dest.writeString(unitName);
        dest.writeByte((byte) (isShowing ? 1 : 0));
    }
}
