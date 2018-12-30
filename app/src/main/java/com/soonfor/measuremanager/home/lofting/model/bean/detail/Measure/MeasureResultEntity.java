package com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author tanljs
 * @date 2018-02-09
 */
public class MeasureResultEntity implements Parcelable{
    private String unitName;//户型名称
    private String unitsPicture;//	string;	户型图
    private List<MeasureRoomEntity> rooms;//	List<MeasureRoomEntity>	量尺主清单

    protected MeasureResultEntity(Parcel in) {
        unitName = in.readString();
        unitsPicture = in.readString();
        rooms = in.createTypedArrayList(MeasureRoomEntity.CREATOR);
    }

    public static final Creator<MeasureResultEntity> CREATOR = new Creator<MeasureResultEntity>() {
        @Override
        public MeasureResultEntity createFromParcel(Parcel in) {
            return new MeasureResultEntity(in);
        }

        @Override
        public MeasureResultEntity[] newArray(int size) {
            return new MeasureResultEntity[size];
        }
    };

    public String getUnitName() {
        return unitName==null?"":unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitsPicture() {
        return unitsPicture==null?"":unitsPicture;
    }

    public void setUnitsPicture(String unitsPicture) {
        this.unitsPicture = unitsPicture;
    }

    public List<MeasureRoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<MeasureRoomEntity> rooms) {
        this.rooms = rooms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(unitName);
        dest.writeString(unitsPicture);
        dest.writeTypedList(rooms);
    }
}
