package com.soonfor.measuremanager.home.othertask.adapter.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.TaskProgressBean;

import java.util.List;

/**
 * Created by DingYg on 2018-03-08.
 */

public class TaskProgressAdapter extends BaseAdapter<TaskProgressAdapter.ViewHolder, TaskProgressBean> {

    private List<TaskProgressBean> list;

    public TaskProgressAdapter(Context context,List<TaskProgressBean> list) {
        super(context);
        this.list = list;
    }

    @Override
    public TaskProgressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_taskprogress, parent, false));
    }

    @Override
    public void onBindViewHolder(TaskProgressAdapter.ViewHolder holder, int position) {
        if(position==0){
            holder.imgfLineLeft.setVisibility(View.INVISIBLE);
            holder.imgfLineRight.setVisibility(View.VISIBLE);
        }else if(position==list.size()-1){
            holder.imgfLineLeft.setVisibility(View.VISIBLE);
            holder.imgfLineRight.setVisibility(View.GONE);
        }else{
            holder.imgfLineLeft.setVisibility(View.VISIBLE);
            holder.imgfLineRight.setVisibility(View.VISIBLE);
        }
        TaskProgressBean progressBean = list.get(position);
        holder.tvfStatus.setText(progressBean.getSpName());
        try {
            if(progressBean.getIndex() == position){
                switch (Integer.parseInt(progressBean.getStatus())){
                    case -1:
                        holder.imgfCircle.setImageResource(R.mipmap.icon_zero);
                        break;
                    case 0:
                        holder.imgfCircle.setImageResource(R.mipmap.icon_waiting);
                        break;
                    case 1:
                        holder.imgfCircle.setImageResource(R.mipmap.icon_finish);
                        break;
                    case 2:
                        holder.imgfCircle.setImageResource(R.mipmap.icn_stop);
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void notifyDataSetChanged(List<TaskProgressBean> dataList) {
        list = dataList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgfCircle;
        public ImageView imgfLineLeft;
        public ImageView imgfLineRight;
        public TextView tvfStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            imgfCircle = itemView.findViewById(R.id.imgfCircle);
            imgfLineLeft = itemView.findViewById(R.id.imgfLineLeft);
            imgfLineRight = itemView.findViewById(R.id.imgfLineRight);
            tvfStatus = itemView.findViewById(R.id.tvfStatusText);
        }
    }
}
