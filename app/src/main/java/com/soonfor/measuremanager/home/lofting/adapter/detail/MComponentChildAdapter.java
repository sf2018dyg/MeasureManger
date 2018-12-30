package com.soonfor.measuremanager.home.lofting.adapter.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.Size2Bean;

import java.util.List;

/**
 * Created by DingYg on 2018-03-20.
 */

public class MComponentChildAdapter extends BaseAdapter<MComponentChildAdapter.topAchievementViewHolder, Size2Bean> {

    private List<Size2Bean> beans;
    private Context mContext;

    public MComponentChildAdapter(Context context, List<Size2Bean> beans) {
        super(context);
        mContext = context;
        this.beans = beans;
    }

    @Override
    public void notifyDataSetChanged(List<Size2Bean> dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    @Override
    public topAchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_measurementgrandson, parent, false);
        return new MComponentChildAdapter.topAchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(topAchievementViewHolder holder, int position) {
        topAchievementViewHolder viewHolder = (topAchievementViewHolder) holder;
        Size2Bean size2 = beans.get(position);
        if(size2!=null) {
            viewHolder.key1.setText(size2.getKey1());
            viewHolder.value1.setText(size2.getValue1());
            viewHolder.key2.setText(size2.getKey2());
            viewHolder.value2.setText(size2.getValue2());
        }
    }

    @Override
    public int getItemCount() {
        return beans == null ? 0 : beans.size();
    }

    public class topAchievementViewHolder extends RecyclerView.ViewHolder {
        public TextView key1;
        public TextView value1;
        public TextView key2;
        public TextView value2;

        public topAchievementViewHolder(View itemView) {
            super(itemView);
            key1 = itemView.findViewById(R.id.tvfLeftName);
            value1 = itemView.findViewById(R.id.tvfLeftValue);
            key2 = itemView.findViewById(R.id.tvfRighName);
            value2 = itemView.findViewById(R.id.tvfRightValue);

        }
    }
}
