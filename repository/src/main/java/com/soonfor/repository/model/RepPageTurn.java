package com.soonfor.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：DC-DingYG on 2018-06-21 8:42
 * 邮箱：dingyg012655@126.com
 */
public class RepPageTurn implements Parcelable{
    private int rowCount;
    private int prevPage;//
    private int pageNo;
    private int pageCount;
    private int nextPage;
    private int pageSize;
    private int firstPage;

    public RepPageTurn() {
    }

    public RepPageTurn(int rowCount, int prevPage, int pageNo, int pageCount, int nextPage, int pageSize, int firstPage) {
        this.rowCount = rowCount;
        this.prevPage = prevPage;
        this.pageNo = pageNo;
        this.pageCount = pageCount;
        this.nextPage = nextPage;
        this.pageSize = pageSize;
        this.firstPage = firstPage;
    }

    protected RepPageTurn(Parcel in) {
        rowCount = in.readInt();
        prevPage = in.readInt();
        pageNo = in.readInt();
        pageCount = in.readInt();
        nextPage = in.readInt();
        pageSize = in.readInt();
        firstPage = in.readInt();
    }

    public static final Creator<RepPageTurn> CREATOR = new Creator<RepPageTurn>() {
        @Override
        public RepPageTurn createFromParcel(Parcel in) {
            return new RepPageTurn(in);
        }

        @Override
        public RepPageTurn[] newArray(int size) {
            return new RepPageTurn[size];
        }
    };

    public int getRowCount() {
        return rowCount;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getFirstPage() {
        return firstPage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rowCount);
        dest.writeInt(prevPage);
        dest.writeInt(pageNo);
        dest.writeInt(pageCount);
        dest.writeInt(nextPage);
        dest.writeInt(pageSize);
        dest.writeInt(firstPage);
    }
}
