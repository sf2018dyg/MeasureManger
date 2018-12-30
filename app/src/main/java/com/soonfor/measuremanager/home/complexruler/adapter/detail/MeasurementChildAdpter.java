package com.soonfor.measuremanager.home.complexruler.adapter.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.measureChild;
import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/1 09:28
 * 邮箱：suibozhu@139.com
 */

public class MeasurementChildAdpter extends BaseAdapter {

    private List<measureChild> beans;
    private Context mContext;

    public MeasurementChildAdpter(Context context, List<measureChild> beans) {
        super(context);
        mContext = context;
        this.beans = beans;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_measurementchild, parent, false);
        return new topAchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        topAchievementViewHolder viewHolder = (topAchievementViewHolder) holder;
        viewHolder.goodnametxt.setText(CommonUtils.formatStr(beans.get(position).getGoodname()));
        viewHolder.goodsizetxt.setText(CommonUtils.formatStr(beans.get(position).getGoodsize()));
    }

    @Override
    public int getItemCount() {
        return beans == null ? 0:beans.size();
    }

    public class topAchievementViewHolder extends RecyclerView.ViewHolder {
        public TextView goodnametxt;
        public TextView goodsizetxt;

        public topAchievementViewHolder(View itemView) {
            super(itemView);
            goodnametxt = itemView.findViewById(R.id.goodnametxt);
            goodsizetxt = itemView.findViewById(R.id.goodsizetxt);
        }
    }
}
