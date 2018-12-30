package com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * @author tanljs
 * @date 2018-02-09
 */
public class MeasureRoomEntity implements Parcelable{
    private String name;//	string	房间名称
    private Map<String,String> prop;//房间基本信息（比如层高、踢脚线等）
    private List<MeasureWallEntity> walls;// 		墙面清单

    protected MeasureRoomEntity(Parcel in) {
        name = in.readString();
        walls = in.createTypedArrayList(MeasureWallEntity.CREATOR);
    }

    public static final Creator<MeasureRoomEntity> CREATOR = new Creator<MeasureRoomEntity>() {
        @Override
        public MeasureRoomEntity createFromParcel(Parcel in) {
            return new MeasureRoomEntity(in);
        }

        @Override
        public MeasureRoomEntity[] newArray(int size) {
            return new MeasureRoomEntity[size];
        }
    };

    public String getName() {
        return name==null?"":name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getProp() {
        return prop;
    }

    public void setProp(Map<String, String> prop) {
        this.prop = prop;
    }

    public List<MeasureWallEntity> getWalls() {
        return walls;
    }

    public void setWalls(List<MeasureWallEntity> walls) {
        this.walls = walls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(walls);
    }
}
