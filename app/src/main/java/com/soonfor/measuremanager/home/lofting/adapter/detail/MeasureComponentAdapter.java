package com.soonfor.measuremanager.home.lofting.adapter.detail;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureComponentEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.Size;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.Size2Bean;
import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYg on 2018-03-20.
 */

public class MeasureComponentAdapter extends BaseAdapter<MeasureComponentAdapter.topAchievementViewHolder, MeasureComponentEntity> {

    private List<MeasureComponentEntity> beans;
    private Context mContext;

    public MeasureComponentAdapter(Context context, List<MeasureComponentEntity> beans) {
        super(context);
        mContext = context;
        this.beans = beans;
    }

    @Override
    public void notifyDataSetChanged(List<MeasureComponentEntity> dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    @Override
    public topAchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_measurementchild, parent, false);
        return new MeasureComponentAdapter.topAchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(topAchievementViewHolder holder, int position) {
        topAchievementViewHolder viewHolder = (topAchievementViewHolder) holder;
        viewHolder.goodnametxt.setText(CommonUtils.formatStr(beans.get(position).getName()));
//        String sizes = "";
        List<Size> sizeList = beans.get(position).getSizeList();
//        for (String name : beans.get(position).getSizes().keySet()) {
//            sizeList.add(new Size(name,beans.get(position).getSizes().get(name)));
////            if (index%2==0){
////                sizes += name + "    " + beans.get(position).getSizes().get(name) + "            ";
////            }else{
////                sizes += name + "    " + beans.get(position).getSizes().get(name) + "\n";
////            }
//        }
        if(sizeList!=null) {
            List<Size2Bean> size2Beans = new ArrayList<>();
            for (int i = 0; i < sizeList.size(); i += 2) {
                Size2Bean size2 = new Size2Bean();
                size2.setKey1(sizeList.get(i).getName());
                size2.setValue1(sizeList.get(i).getValue());
                if (i + 1 < sizeList.size()) {
                    size2.setKey2(sizeList.get(i + 1).getName());
                    size2.setValue2(sizeList.get(i + 1).getValue());
                }
                size2Beans.add(size2);
            }
            //viewHolder.goodsizetxt.setText(CommonUtils.formatStr(sizes));

            GridLayoutManager manager = new GridLayoutManager(mContext, 1);
            MComponentChildAdapter childAdpter = new MComponentChildAdapter(mContext, size2Beans);
            holder.G_recyclerView.setLayoutManager(manager);
            holder.G_recyclerView.setAdapter(childAdpter);
        }
    }

    @Override
    public int getItemCount() {
        return beans == null ? 0 : beans.size();
    }

    public class topAchievementViewHolder extends RecyclerView.ViewHolder {
        public TextView goodnametxt;
       // public TextView goodsizetxt;
        public RecyclerView G_recyclerView;
        public topAchievementViewHolder(View itemView) {
            super(itemView);
            goodnametxt = itemView.findViewById(R.id.goodnametxt);
            G_recyclerView = itemView.findViewById(R.id.goodsizetxt);
        }
    }
}
