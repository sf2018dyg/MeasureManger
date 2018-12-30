package com.soonfor.repository.model.knowledge;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/31 0031 14:39
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class SearchTitleBean implements Parcelable{
    private String id;
    private String title;

    protected SearchTitleBean(Parcel in) {
        id = in.readString();
        title = in.readString();
    }

    public static final Creator<SearchTitleBean> CREATOR = new Creator<SearchTitleBean>() {
        @Override
        public SearchTitleBean createFromParcel(Parcel in) {
            return new SearchTitleBean(in);
        }

        @Override
        public SearchTitleBean[] newArray(int size) {
            return new SearchTitleBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
    }
}
