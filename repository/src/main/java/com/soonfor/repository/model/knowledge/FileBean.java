package com.soonfor.repository.model.knowledge;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：DC-DingYG on 2018-07-20 15:26
 * 邮箱：dingyg012655@126.com
 */
public class FileBean implements Parcelable{
    private String fileUrl;
    private String fileName;
    private byte fileSize;

    protected FileBean(Parcel in) {
        fileUrl = in.readString();
        fileName = in.readString();
        fileSize = in.readByte();
    }

    public static final Creator<FileBean> CREATOR = new Creator<FileBean>() {
        @Override
        public FileBean createFromParcel(Parcel in) {
            return new FileBean(in);
        }

        @Override
        public FileBean[] newArray(int size) {
            return new FileBean[size];
        }
    };

    public String getFileUrl() {
        if(fileUrl==null){
            fileUrl = "";
        }else if(!fileUrl.startsWith("/")){
            fileUrl = "/" + fileUrl;
        }
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName==null?"":fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte getFileSize() {
        return fileSize;
    }

    public void setFileSize(byte fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileUrl);
        dest.writeString(fileName);
        dest.writeByte(fileSize);
    }

    public String getFileFormat(){
        String fileName = getFileName().trim();
        if(fileName.contains(".")) {
            String format = fileName.substring(fileName.indexOf(".") + 1, fileName.length()).toLowerCase();
            return format;
        }
        return "";
    }
}
