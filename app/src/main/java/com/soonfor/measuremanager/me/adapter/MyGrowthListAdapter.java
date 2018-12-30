package com.soonfor.measuremanager.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.me.bean.MyGrowthItemBean;
import com.soonfor.measuremanager.tools.DateTool;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class MyGrowthListAdapter extends RecyclerView.Adapter<MyGrowthListAdapter.ViewHolder> {

    private MyGrowthItemBean bean;
    private LayoutInflater mInflater;

    public MyGrowthListAdapter(Context context, MyGrowthItemBean list) {
        this.bean = list;
        mInflater = LayoutInflater.from(context);
    }

    public void notifyDataSetChanged(MyGrowthItemBean bean) {
        this.bean = bean;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bean == null ? 0 : bean.getList().size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_my_growth_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(bean.getList().get(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDate;
        TextView tvGrowth;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle =  itemView.findViewById(R.id.tvTitle);
            tvDate =  itemView.findViewById(R.id.tvDate);
            tvGrowth =  itemView.findViewById(R.id.tvGrowth);
        }

        public void setData(MyGrowthItemBean.ListBean listBean) {
            this.tvTitle.setText(listBean.getName());
            this.tvGrowth.setText("+" + listBean.getValue());
            this.tvDate.setText(DateTool.getTimeTimestamp(listBean.getGrowthDate(),"yyyy-MM-dd HH:mm"));
        }
    }

}