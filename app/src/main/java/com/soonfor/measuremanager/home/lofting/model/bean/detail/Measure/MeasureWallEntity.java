package com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * @author tanljs
 * @date 2018-03-16
 */
public class MeasureWallEntity implements Parcelable{
    private String name;//	string	墙面名称
    private Map<String,String> prop;//墙面基本信息（比如墙长、垂直/水平基线等）
    private List<String> livePictures;//	List<string>	量尺照片
    private List<String> fixPictures;//	List<string>	整改照片
    private List<MeasureComponentEntity> components;// 		部件清单

    private boolean isShow;

    protected MeasureWallEntity(Parcel in) {
        name = in.readString();
        livePictures = in.createStringArrayList();
        fixPictures = in.createStringArrayList();
        components = in.createTypedArrayList(MeasureComponentEntity.CREATOR);
    }

    public static final Creator<MeasureWallEntity> CREATOR = new Creator<MeasureWallEntity>() {
        @Override
        public MeasureWallEntity createFromParcel(Parcel in) {
            return new MeasureWallEntity(in);
        }

        @Override
        public MeasureWallEntity[] newArray(int size) {
            return new MeasureWallEntity[size];
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

    public List<String> getLivePictures() {
        return livePictures;
    }

    public void setLivePictures(List<String> livePictures) {
        this.livePictures = livePictures;
    }

    public List<String> getFixPictures() {
        return fixPictures;
    }

    public void setFixPictures(List<String> fixPictures) {
        this.fixPictures = fixPictures;
    }

    public List<MeasureComponentEntity> getComponents() {
        return components;
    }

    public void setComponents(List<MeasureComponentEntity> components) {
        this.components = components;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeStringList(livePictures);
        dest.writeStringList(fixPictures);
        dest.writeTypedList(components);
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
