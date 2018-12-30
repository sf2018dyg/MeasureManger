package com.soonfor.measuremanager.home.lofting.adapter.detail;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Design.DesignPlanBean;
import com.soonfor.measuremanager.tools.DateTool;

import java.util.List;

/**
 * Created by DingYg on 2018-02-01.
 */

public class DesignPlanListAdapter extends BaseAdapter<DesignPlanListAdapter.ViewHolder, DesignPlanBean> implements View.OnClickListener {

    private DesignPlanBean data;

    public DesignPlanListAdapter(Context context, DesignPlanBean data) {
        super(context);
        this.data = data;
    }

    @Override
    public DesignPlanListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_designplan_list, parent, false));
    }

    @Override
    public void onBindViewHolder(DesignPlanListAdapter.ViewHolder holder, int position) {
        holder.setData(data);
    }

    public void notifyDataSetChanged(DesignPlanBean data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged(List<DesignPlanBean> dataList) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvfPlanName;//方案名称
        TextView tvfPlanTiem;//设计或调整方案的时间
        TextView tvfAuditStatus;//审核状态
        RecyclerView rvfDesplanDrawing;//设计图册list
        Context mContext;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfPlanName = itemView.findViewById(R.id.tvfDesignPlanName);
            this.tvfPlanTiem = itemView.findViewById(R.id.tvfDesignPlanTime);
            this.tvfAuditStatus = itemView.findViewById(R.id.tvfAuditStatus);
            this.rvfDesplanDrawing = itemView.findViewById(R.id.rvfDesignPics);
        }

        public void setData(DesignPlanBean dpBean) {
            this.tvfPlanName.setText("设计方案");
            if(!dpBean.getDesignTime().equals("")){
                this.tvfPlanTiem.setText(DateTool.getTimeTimestamp(dpBean.getDesignTime(),"yyyy-MM-dd HH:mm"));
            }
            this.tvfAuditStatus.setText(dpBean.getStatus());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            DesignPlanListChildAdapter goAdapter = new DesignPlanListChildAdapter(context, dpBean.getProjectList());
            // 设置布局管理器
            rvfDesplanDrawing.setLayoutManager(mLayoutManager);
            // 设置adapter
            rvfDesplanDrawing.setAdapter(goAdapter);
            rvfDesplanDrawing.setFocusable(false);
        }
    }
}
