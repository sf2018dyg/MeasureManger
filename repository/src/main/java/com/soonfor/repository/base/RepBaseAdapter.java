package com.soonfor.repository.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-05-03 8:13
 * 邮箱：dingyg012655@126.com
 */
public abstract class RepBaseAdapter<VH extends RecyclerView.ViewHolder, K> extends RecyclerView.Adapter<VH> {

    protected LayoutInflater mInflater;
    protected Context context;
    public RepBaseAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public abstract void notifyDataSetChanged(List<K> dataList);
    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager   设置RecyclerView对应的manager
     * @param mRecyclerView  当前的RecyclerView
     * @param n  要跳转的位置
     */
    public void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }
    /**
     * 加载图片或头像
     *
     * @param imageUrl 图片地址
     * @param view     图片控件
     */
    public void imageLoading(String imageUrl, ImageView view, int defaultResId) {
        if(imageUrl.equals("")){
           view.setImageResource(defaultResId);
        }else {
            Picasso.with(context).load(imageUrl).placeholder(defaultResId).error(defaultResId).into(view);
        }
    }
}