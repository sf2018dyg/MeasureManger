package com.soonfor.measuremanager.home.lofting.adapter.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;

import java.util.List;

/**
 * Created by DingYg on 2018-02-02.
 */

public class MarkInfoWallAdpter extends BaseAdapter<MarkInfoWallAdpter.ViewHolder, MarkWallEntity> implements View.OnClickListener {

    private List<MarkWallEntity> list;
    private RecyclerView.LayoutManager mLayoutManager;

    public MarkInfoWallAdpter(Context context, List<MarkWallEntity> list) {
        super(context);
        this.list = list;
    }

    @Override
    public MarkInfoWallAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_lofting_detail_loftinfo, parent, false));
    }

    @Override
    public void onBindViewHolder(MarkInfoWallAdpter.ViewHolder holder, int position) {
        holder.rlfhead.setTag(position);
        holder.rlfhead.setOnClickListener(this);
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void notifyDataSetChanged(List<MarkWallEntity> dataList) {
        list = dataList;
        notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlfhead:
                int position = (int)v.getTag();
                 if(list.get(position).isShowing()){
                        list.get(position).setShowing(false);
                        notifyDataSetChanged();
                    }else{
                        list.get(position).setShowing(true);
                        notifyDataSetChanged();
                    }
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvfHeadtxt;
        public RelativeLayout rlfhead;
        public ImageView imgfHeadShow;
        public LinearLayout llfChildContent;
        public LinearLayout llfFyPics;
        public TextView tvfNoFyPics;
        public RecyclerView rlviewChild;
       // private NineGridView ngfLfPhotos;
        private NineGridView ngfLfDrawings;
        public MarkInfoComponentAdpter childAdpter;

        public ViewHolder(View itemView) {
            super(itemView);
            tvfHeadtxt = itemView.findViewById(R.id.tvfHeadtxt);
            rlfhead = itemView.findViewById(R.id.rlfhead);
            imgfHeadShow = itemView.findViewById(R.id.imgfHeadShow);
            llfChildContent = itemView.findViewById(R.id.llfChildContent);
            this.llfFyPics = itemView.findViewById(R.id.llfFyPics);
            this.tvfNoFyPics = itemView.findViewById(R.id.tvfNoFyPics);
            rlviewChild = itemView.findViewById(R.id.rlviewChild);
          //  ngfLfPhotos = itemView.findViewById(R.id.ngfLoftingPhotos);
            ngfLfDrawings = itemView.findViewById(R.id.ngfLoftingDrawings);
        }
        public void setData(MarkWallEntity wall) {
            tvfHeadtxt.setText(wall.getWallName());
            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            childAdpter = new MarkInfoComponentAdpter(context, wall.getComponents(),2);
            rlviewChild.setLayoutManager(mLayoutManager);
            rlviewChild.setAdapter(childAdpter);
            if(wall.isShowing()){
                imgfHeadShow.setImageResource(R.mipmap.icon_up);
                llfChildContent.setVisibility(View.VISIBLE);
            }else{
               imgfHeadShow.setImageResource(R.mipmap.icon_down);
               llfChildContent.setVisibility(View.GONE);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(wall.getPhotos().size()==0){
                                llfFyPics.setVisibility(View.GONE);
                                tvfNoFyPics.setVisibility(View.VISIBLE);
                            }else {
                                tvfNoFyPics.setVisibility(View.GONE);
                                llfFyPics.setVisibility(View.VISIBLE);
                                ngfLfDrawings.setAdapter(new NineGridViewClickAdapter(context, wall.getGridPics()));
                            }
                        }
                    });
                }
            }).start();
        }
    }

}
