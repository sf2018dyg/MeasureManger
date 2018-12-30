package com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk;

import android.os.Parcel;
import android.os.Parcelable;

import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.ninegrid.ImageInfo;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.http.api.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYg on 2018-03-18.
 * 墙面信息
 */

public class MarkWallEntity implements Parcelable{
    private boolean isShowing;
    private String roomCode;
    private String roomName;
    private String wallCode;
    private String wallName;
    private ArrayList<MarkPhoto> photos;
    private ArrayList<MarkComponentEntity> components;
    private String proxyNumber;//sdk系统的墙面编号的代理
    private String needMark;//是否需要放样（0否1是）
    private String isMark;//是否已放样（0否1是）

    public MarkWallEntity() {
        this.isShowing = false;
    }


    protected MarkWallEntity(Parcel in) {
        isShowing = in.readByte() != 0;
        roomCode = in.readString();
        roomName = in.readString();
        wallCode = in.readString();
        wallName = in.readString();
        photos = in.createTypedArrayList(MarkPhoto.CREATOR);
        components = in.createTypedArrayList(MarkComponentEntity.CREATOR);
        proxyNumber = in.readString();
        needMark = in.readString();
        isMark = in.readString();
    }

    public static final Creator<MarkWallEntity> CREATOR = new Creator<MarkWallEntity>() {
        @Override
        public MarkWallEntity createFromParcel(Parcel in) {
            return new MarkWallEntity(in);
        }

        @Override
        public MarkWallEntity[] newArray(int size) {
            return new MarkWallEntity[size];
        }
    };

    public List<MarkPhoto> getPhotos() {
        return photos==null?new ArrayList<>():photos;
    }


    /**
     * 获取ImageInfo图片集合
     * @return
     */
    public ArrayList<ImageInfo> getGridPics(){
        ArrayList<ImageInfo> infos = new ArrayList<>();
        if(photos!=null && photos.size()>0){
            for(int i=0;i<photos.size();i++) {
                ImageInfo info = new ImageInfo();
                String path = photos.get(i).getUrl();
                if(path.startsWith("http") || path.startsWith("/storage/") || path.startsWith("/data/")){
                    info.setBigImageUrl(path);
                    info.setThumbnailUrl(path);
                }else {
                    String httpPath = "http://" + Hawk.get(SoonforApplication.ServerAdr_fj, "") + UserInfo.DOWNLOAD_FILE;
                    info.setBigImageUrl(httpPath + path);
                    info.setThumbnailUrl(httpPath + path);
                }
                infos.add(info);
            }
        }
        return infos;
    }
    public void setPhotos(ArrayList<MarkPhoto> photos) {
        this.photos = photos;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    public String getRoomCode() {
        return roomCode == null ? "" : roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName == null ? "" : roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getWallCode() {
        return wallCode == null ? "" : wallCode;
    }

    public void setWallCode(String wallCode) {
        this.wallCode = wallCode;
    }

    public String getWallName() {
        return wallName == null ? "" : wallName;
    }

    public void setWallName(String wallName) {
        this.wallName = wallName;
    }

    public String getProxyNumber() {
        return proxyNumber==null?"":proxyNumber;
    }

    public void setProxyNumber(String proxyNumber) {
        this.proxyNumber = proxyNumber;
    }

    public String getNeedMark() {
        return needMark==null?"1":needMark;
    }

    public void setNeedMark(String needMark) {
        this.needMark = needMark;
    }

    public ArrayList<MarkComponentEntity> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<MarkComponentEntity> components) {
        this.components = components;
    }

//    public boolean getFyStatus(){
//        boolean isFyed_wall = false;
//        if(components!=null && components.size()>0){
//            for(int j=0; j<components.size(); j++){
//                if(components.get(j).getStatus().equals("已放样")){
//                    isFyed_wall = true;
//                }
//            }
//        }
//        return isFyed_wall;
//    }

    public String getIsMark() {
        return isMark==null?"0":isMark;
    }

    public void setIsMark(String isMark) {
        this.isMark = isMark;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isShowing ? 1 : 0));
        dest.writeString(roomCode);
        dest.writeString(roomName);
        dest.writeString(wallCode);
        dest.writeString(wallName);
        dest.writeTypedList(photos);
        dest.writeTypedList(components);
        dest.writeString(proxyNumber);
        dest.writeString(needMark);
        dest.writeString(isMark);
    }
}
