package com.soonfor.evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.http.api.UserInfo;

/**
 * Created by DingYg on 2018-03-19.
 * 上传的图片
 */

public class UploadPhoto implements Parcelable{
    private String id;
    private String url;
    private String localUrl;
    private LocalMedia localMedia;

    public UploadPhoto() {
    }


    protected UploadPhoto(Parcel in) {
        id = in.readString();
        url = in.readString();
        localUrl = in.readString();
        localMedia = in.readParcelable(LocalMedia.class.getClassLoader());
    }

    public static final Creator<UploadPhoto> CREATOR = new Creator<UploadPhoto>() {
        @Override
        public UploadPhoto createFromParcel(Parcel in) {
            return new UploadPhoto(in);
        }

        @Override
        public UploadPhoto[] newArray(int size) {
            return new UploadPhoto[size];
        }
    };

    public String getId() {
        return id==null?"":id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalUrl() {
        return localUrl == null ? "" : localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public LocalMedia getLocalMedia() {
        if(localMedia==null) {
            localMedia = new LocalMedia();
            if(getUrl().startsWith("http") || getUrl().startsWith("/storage/") || getUrl().startsWith("/data/")){
                localMedia.setCompressPath(getUrl());
                localMedia.setPath(getUrl());
            }else {
                String httpPath = "http://" + Hawk.get(SoonforApplication.ServerAdr_fj, "") + UserInfo.DOWNLOAD_FILE;
                localMedia.setCompressPath(httpPath + getUrl());
                localMedia.setPath(httpPath + getUrl());
            }
        }
        return localMedia;
    }

    public void setLocalMedia(LocalMedia localMedia) {
        this.localMedia = localMedia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(url);
        dest.writeString(localUrl);
        dest.writeParcelable(localMedia, flags);
    }
}
