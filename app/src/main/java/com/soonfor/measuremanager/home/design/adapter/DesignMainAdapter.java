package com.soonfor.measuremanager.home.design.adapter;

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
import com.soonfor.measuremanager.home.design.model.DesignItemBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.DensityUtils;
import com.soonfor.measuremanager.tools.ViewTools;

import java.util.List;

/**
 * Created by Administrator on 2018-01-30.
 */

public class DesignMainAdapter extends BaseAdapter<DesignMainAdapter.ViewHolder, DesignItemBean>{

    private List<DesignItemBean> list;
    private int type;
    View.OnClickListener listener;

    public DesignMainAdapter(Context context, int type, View.OnClickListener listener) {
        super(context);
        this.type = type;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_design, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(context, list.get(position));
        holder.llfItem.setTag(position);
        holder.tvfName.setTag(position);
        holder.llfItem.setOnClickListener(listener);
        holder.tvfName.setOnClickListener(listener);
        //Item底部的按钮
        DesignItemBean design = list.get(position);
        switch (design.getStatus()) {
            case 1:
                holder.btfJushou.setTag(position);
                holder.btfJieshou.setTag(position);
                holder.btfJushou.setOnClickListener(listener);
                holder.btfJieshou.setOnClickListener(listener);
                break;
            case 4:
                holder.btfQuerenyuyue.setTag(position);
                holder.btfQuerenyuyue.setOnClickListener(listener);
                break;
        }
    }

    @Override
    public void notifyDataSetChanged(List<DesignItemBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvfType;//任务状态
        TextView tvfName;//客户姓名和性别
        TextView tvfBuilding;//楼盘
        TextView tvfFaGtTime;//方案沟通时间
        TextView tvfAddressT;//地址标题（当设计时为客户需求）
        TextView tvAddress;//地址
        TextView tvfWorkpoints;//工分
        LinearLayout llfItem;//item整体
        RelativeLayout rlfDesignPhoto;//设计头像
        Button btfJushou;//拒收
        Button btfJieshou;//接收
        Button btfQuerenyuyue;//确认预约
        TextView tvfStatus;//状态类型

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfType = itemView.findViewById(R.id.tvfTaskType);
            this.tvfName = itemView.findViewById(R.id.tvName);
            this.tvfBuilding = itemView.findViewById(R.id.tvfBuilding);
            this.tvfFaGtTime = itemView.findViewById(R.id.tvfYYDate);
            this.tvfAddressT = itemView.findViewById(R.id.tvfAddressT);
            this.tvAddress = itemView.findViewById(R.id.tvfAddress);
            this.tvfWorkpoints = itemView.findViewById(R.id.tvfWorkpoints);
            this.llfItem = itemView.findViewById(R.id.llfItem);
            this.rlfDesignPhoto = itemView.findViewById(R.id.rlfChangePhoto);
            this.btfJushou = itemView.findViewById(R.id.jushou);
            this.btfJieshou = itemView.findViewById(R.id.jieshou);
            this.btfQuerenyuyue = itemView.findViewById(R.id.querenyuyue);
            this.tvfStatus = itemView.findViewById(R.id.tvftypes);
        }

        public void setData(Context mContext, DesignItemBean gpBean) {
            this.tvfType.setText("设计");
            this.tvfType.setBackgroundResource(R.drawable.bg_red);
            this.tvfAddressT.setText("地址：");
            switch (gpBean.getStatus()) {
                case 1:
                    btfJushou.setVisibility(View.VISIBLE);
                    btfJieshou.setVisibility(View.VISIBLE);
                    btfQuerenyuyue.setVisibility(View.GONE);
                    tvfStatus.setText("待接收");
                    break;
                case 2:
                    btfJushou.setVisibility(View.GONE);
                    btfJieshou.setVisibility(View.GONE);
                    btfQuerenyuyue.setVisibility(View.GONE);
                    tvfStatus.setText("待设计");
                    break;
                case 3:
                    btfJushou.setVisibility(View.GONE);
                    btfJieshou.setVisibility(View.GONE);
                    btfQuerenyuyue.setVisibility(View.GONE);
                    tvfStatus.setText("待调整");
                    break;
                case 4:
                    btfJushou.setVisibility(View.GONE);
                    btfJieshou.setVisibility(View.GONE);
                    /**
                     * 修改人：DC-ZhuSuiBo on 2018/8/12 0012 14:27
                     * 邮箱：suibozhu@139.com
                     * 修改目的： bug 873 说去掉
                     */
                    btfQuerenyuyue.setVisibility(View.GONE);
                    tvfStatus.setText("待确图");
                    break;
                case 5:
                    btfJushou.setVisibility(View.GONE);
                    btfJieshou.setVisibility(View.GONE);
                    btfQuerenyuyue.setVisibility(View.GONE);
                    tvfStatus.setText("已确图");
                    break;
            }
            ViewTools.returnDrawable(mContext, tvfName, 3, R.mipmap.btn_huaxiang, new int[]{0, 0, DensityUtils.dp2px(mContext, 18), DensityUtils.dp2px(mContext, 18)});
            CommonUtils.setSex(this.tvfName,gpBean.getCustomerName(),gpBean.getCustomerSex());

            this.tvfBuilding.setText(gpBean.getBuilding());
            this.tvfFaGtTime.setText(DateTool.getTimeTimestamp(gpBean.getCommunicateDate(), "MM月dd日-HH:mm") + "");
            this.tvAddress.setText(gpBean.getAddress());
            this.tvfWorkpoints.setText(gpBean.getWorkPoints());
       }
    }
}
