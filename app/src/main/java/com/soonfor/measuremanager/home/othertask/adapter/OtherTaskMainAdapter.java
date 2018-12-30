package com.soonfor.measuremanager.home.othertask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.DensityUtils;
import com.soonfor.measuremanager.tools.ViewTools;

import java.util.List;

/**
 * Created by DingYg on 2018-01-30.
 */

public class OtherTaskMainAdapter extends BaseAdapter<OtherTaskMainAdapter.ViewHolder, OtherTaskMainBean>{

    private List<OtherTaskMainBean> list;
    private int type;
    View.OnClickListener listener;

    public OtherTaskMainAdapter(Context context, int type, View.OnClickListener listener) {
        super(context);
        this.type = type;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_othertask, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(context, list.get(position));
        holder.llfItem.setTag(position);
        holder.tvfName.setTag(position);
        holder.llfItem.setOnClickListener(listener);
        holder.tvfName.setOnClickListener(listener);
        //Item底部的按钮
        OtherTaskMainBean lofBean = list.get(position);
        switch (lofBean.getStatus()) {
            case 1:
            case 2:
                holder.btnUpdateTaskResult.setTag(position);
                holder.btnUpdateTaskResult.setOnClickListener(listener);
                break;
            case 3:
                holder.btnLookDisDetail.setTag(position);
                holder.btnLookDisDetail.setOnClickListener(listener);
                break;
        }
    }

    @Override
    public void notifyDataSetChanged(List<OtherTaskMainBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvfTaskTitle;//任务类型
        TextView tvfName;//客户姓名和性别
        TextView tvfBuilding;//楼盘
        TextView tvfTimeT;//执行时间（或完成时间）
        TextView tvfExecuteTime;//执行时间

        TextView tvfWorkpoints;//工分
        LinearLayout llfItem;//item整体

        Button btnUpdateTaskResult;//更新任务结果
        Button btnLookDisDetail;//查看处理详情
        TextView tvfStatus;//状态类型


        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfTaskTitle = itemView.findViewById(R.id.tvfTaskTitle);
            this.tvfName = itemView.findViewById(R.id.tvName);
            this.tvfBuilding = itemView.findViewById(R.id.tvfBuilding);
            this.tvfTimeT = itemView.findViewById(R.id.tvfTiemTitle);
            this.tvfExecuteTime = itemView.findViewById(R.id.tvfExecuteDate);

            this.tvfWorkpoints = itemView.findViewById(R.id.tvfWorkpoints);
            this.llfItem = itemView.findViewById(R.id.llfItem);


            btnLookDisDetail = itemView.findViewById(R.id.btnLookDisDetail);
            btnUpdateTaskResult = itemView.findViewById(R.id.btnUpdateTaskResult);
            tvfStatus = itemView.findViewById(R.id.tvftypes);
        }

        public void setData(Context mContext, OtherTaskMainBean gpBean) {
            this.tvfTaskTitle.setText(gpBean.getTitle());
            switch (gpBean.getStatus()) {
                case 1://进行中
                    tvfStatus.setText("进行中");
                    tvfTimeT.setText("执行时间：");
                    btnLookDisDetail.setVisibility(View.GONE);
                    btnUpdateTaskResult.setVisibility(View.VISIBLE);
                    break;
                case 2://已逾期
                    tvfStatus.setText("已逾期");
                    tvfTimeT.setText("执行时间：");
                    btnLookDisDetail.setVisibility(View.GONE);
                    btnUpdateTaskResult.setVisibility(View.VISIBLE);
                    break;
                case 3://已完成
                    tvfStatus.setText("已完成");
                    tvfTimeT.setText("完成时间：");
                    btnLookDisDetail.setVisibility(View.VISIBLE);
                    btnUpdateTaskResult.setVisibility(View.GONE);
                    break;

            }
            CommonUtils.setSex(this.tvfName,gpBean.getCustomerName(),gpBean.getCustomerSex());
            this.tvfBuilding.setText(gpBean.getBuilding());
            this.tvfExecuteTime.setText(gpBean.getExecDate());
            this.tvfWorkpoints.setText(gpBean.getWorkPoints());
        }
    }
}
