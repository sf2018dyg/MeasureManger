package com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tanljs
 * @date 2018-02-09
 */
public class MeasureComponentEntity implements Parcelable {
    private String name;//	string	部件名称
    private Map<String, String> sizes;//	尺寸清单


    protected MeasureComponentEntity(Parcel in) {
        name = in.readString();
    }

    public static final Creator<MeasureComponentEntity> CREATOR = new Creator<MeasureComponentEntity>() {
        @Override
        public MeasureComponentEntity createFromParcel(Parcel in) {
            return new MeasureComponentEntity(in);
        }

        @Override
        public MeasureComponentEntity[] newArray(int size) {
            return new MeasureComponentEntity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public Map<String, String> getSizes() {
        return sizes;
    }

    public List<Size> getSizeList() {
        if (sizes != null && sizes.size() > 0) {
            List<Size> sizeList = new ArrayList<>();
            for (String name : sizes.keySet()) {
                String sizeValue = sizes.get(name);

                /**
                 * 修改人：DC-ZhuSuiBo on 2018/5/29 0029 16:30
                 * 邮箱：suibozhu@139.com
                 * 修改目的：判断是否是数字,决定是否添加 mm
                 * start
                 */
                boolean isDigit = false;
                for (int i = 0; i < sizeValue.length(); i++) {
                    if (Character.isDigit(sizeValue.charAt(i))) {     //用char包装类中的判断数字的方法判断每一个字符
                        isDigit = true;
                    }
                }

                if (isDigit) {
                    if (!sizeValue.contains("mm")) {
                        sizeList.add(new Size(name, sizeValue + "mm"));
                    } else {
                        sizeList.add(new Size(name, sizeValue));
                    }
                } else {
                    sizeList.add(new Size(name, sizeValue));
                }
                /**
                 * end
                 * **/

            }
            return sizeList;
        } else {
            return null;
        }
    }

    public void setSizes(Map<String, String> sizes) {
        this.sizes = sizes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
