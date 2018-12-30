package com.soonfor.measuremanager.home.lofting.model.bean.detail.Mark;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.measuremanager.http.api.UserInfo;

/**
 * @author tanljs
 * @date 2018-08-07
 */
public class MarkDrawing implements Parcelable{
    private String name;
    private String img;

    protected MarkDrawing(Parcel in) {
        name = in.readString();
        img = in.readString();
    }

    public static final Creator<MarkDrawing> CREATOR = new Creator<MarkDrawing>() {
        @Override
        public MarkDrawing createFromParcel(Parcel in) {
            return new MarkDrawing(in);
        }

        @Override
        public MarkDrawing[] newArray(int size) {
            return new MarkDrawing[size];
        }
    };

    public String getName() {
        return name==null?"":name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        if(img!=null){
            if(!img.startsWith("http")){
                String httpPath = UserInfo.DOWNLOAD_FILE + "?filepath=";
                return httpPath + img;
            }else {
                return img;
            }
        }else {
            return "";
        }
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(img);
    }
}
