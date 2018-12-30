package com.soonfor.measuremanager.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.me.bean.PersonDataBean;
import com.soonfor.measuremanager.tools.ImageUtil;

import java.util.List;

public class BrandStoryAdapter extends BaseAdapter<BrandStoryAdapter.ViewHolder, String>{

    private List<String> bsList;

    public BrandStoryAdapter(Context context) {
        super(context);
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.bsList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_brandstroy, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageUtil.loadImage(context, bsList.get(position), holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bsList==null?0:bsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.tvfbrandStory);        }

    }

}
