package com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk;

import android.os.Parcel;
import android.os.Parcelable;

import com.luck.picture.lib.entity.LocalMedia;
import com.nostra13.universalimageloader.utils.L;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.http.api.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYg on 2018-03-19.
 * 墙面放样图
 */

public class MarkPhoto implements Parcelable{
    private String id;
    private String url;
    private String localpath;
    private LocalMedia localMedia;

    public MarkPhoto() {
    }


    protected MarkPhoto(Parcel in) {
        id = in.readString();
        url = in.readString();
        localpath = in.readString();
        localMedia = in.readParcelable(LocalMedia.class.getClassLoader());
    }

    public static final Creator<MarkPhoto> CREATOR = new Creator<MarkPhoto>() {
        @Override
        public MarkPhoto createFromParcel(Parcel in) {
            return new MarkPhoto(in);
        }

        @Override
        public MarkPhoto[] newArray(int size) {
            return new MarkPhoto[size];
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

    public String getLocalpath() {
        return localpath == null ? "" : localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath;
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
        dest.writeString(localpath);
        dest.writeParcelable(localMedia, flags);
    }
}
