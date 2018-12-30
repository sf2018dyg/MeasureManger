package com.soonfor.measuremanager.home.lofting.adapter;

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
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.DensityUtils;
import com.soonfor.measuremanager.tools.ViewTools;

import java.util.List;

/**
 * Created by DingYg on 2018-01-30.
 */

public class LoftingMainAdapter extends BaseAdapter<LoftingMainAdapter.ViewHolder, LoftingMainBean>{

    private List<LoftingMainBean> list;
    private int type;
    View.OnClickListener listener;

    public LoftingMainAdapter(Context context,int type, View.OnClickListener listener) {
        super(context);
        this.type = type;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_loft, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(context, list.get(position));
        holder.llfItem.setTag(position);
        holder.tvfName.setTag(position);
        holder.llfItem.setOnClickListener(listener);
        holder.tvfName.setOnClickListener(listener);
        //Item底部的按钮
        LoftingMainBean lofBean = list.get(position);
        switch (lofBean.getStatus()) {
            case 1:
                holder.btfJushou.setTag(position);
                holder.btfJieshou.setTag(position);
                holder.btfJushou.setOnClickListener(listener);
                holder.btfJieshou.setOnClickListener(listener);
                break;
            case 2:
                holder.btfQuerenyuyue.setTag(position);
                holder.btfQuerenyuyue.setOnClickListener(listener);
                break;
            case 3:
                holder.btfLijify.setTag(position);
                holder.btfLijify.setOnClickListener(listener);
                break;
        }
    }

    @Override
    public void notifyDataSetChanged(List<LoftingMainBean> dataList) {
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
        TextView tvfAppointTime;//预约时间
        TextView tvfAddressT;//地址标题（当设计时为客户需求）
        TextView tvAddress;//地址
        TextView tvfWorkpoints;//工分
        LinearLayout llfItem;//item整体
        RelativeLayout rlfDesignPhoto;//设计头像
        LinearLayout llfQrsmsj;//确认上门时间ll
        LinearLayout llfFywcsj;//放样完成时间ll
        Button btfJushou;//拒收
        Button btfJieshou;//接收
        Button btfQuerenyuyue;//确认预约
        Button btfLijify;//立即放样
        TextView tvfStatus;//状态类型
        TextView tvfconfirmtime;//确认上门时间
        TextView tvfFyfinishtime;//放样完成时间

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfType = itemView.findViewById(R.id.tvfTaskType);
            this.tvfName = itemView.findViewById(R.id.tvName);
            this.tvfBuilding = itemView.findViewById(R.id.tvfBuilding);
            this.tvfAppointTime = itemView.findViewById(R.id.tvfYYDate);
            this.tvfAddressT = itemView.findViewById(R.id.tvfAddressT);
            this.tvAddress = itemView.findViewById(R.id.tvfAddress);
            this.tvfWorkpoints = itemView.findViewById(R.id.tvfWorkpoints);
            this.llfItem = itemView.findViewById(R.id.llfItem);
            this.rlfDesignPhoto = itemView.findViewById(R.id.rlfChangePhoto);
            llfQrsmsj = itemView.findViewById(R.id.qrsmsj);
            llfFywcsj = itemView.findViewById(R.id.lcwcsj);
            btfJushou = itemView.findViewById(R.id.jushou);
            btfJieshou = itemView.findViewById(R.id.jieshou);
            btfQuerenyuyue = itemView.findViewById(R.id.querenyuyue);
            btfLijify = itemView.findViewById(R.id.lijiFy);
            tvfStatus = itemView.findViewById(R.id.tvftypes);
            tvfconfirmtime = itemView.findViewById(R.id.tvfconfirmtime);
            tvfFyfinishtime = itemView.findViewById(R.id.tvfliangchifinishtime);
        }

        public void setData(Context mContext, LoftingMainBean gpBean) {
            this.tvfType.setText("放样");
            this.tvfType.setBackgroundResource(R.drawable.bg_red);
            this.tvfAddressT.setText("地址：");
            switch (gpBean.getStatus()) {
                case 1:
                    llfQrsmsj.setVisibility(View.GONE);
                    llfFywcsj.setVisibility(View.GONE);
                    btfJushou.setVisibility(View.VISIBLE);
                    btfJieshou.setVisibility(View.VISIBLE);
                    btfQuerenyuyue.setVisibility(View.GONE);
                    btfLijify.setVisibility(View.GONE);
                    tvfStatus.setText("待接收");
                    break;
                case 2:
                    llfQrsmsj.setVisibility(View.GONE);
                    llfFywcsj.setVisibility(View.GONE);
                    btfJushou.setVisibility(View.GONE);
                    btfJieshou.setVisibility(View.GONE);
                    btfQuerenyuyue.setVisibility(View.VISIBLE);
                    btfLijify.setVisibility(View.GONE);
                    tvfStatus.setText("待上门确认");
                    break;
                case 3:
                    llfQrsmsj.setVisibility(View.VISIBLE);
                    llfFywcsj.setVisibility(View.GONE);
                    btfJushou.setVisibility(View.GONE);
                    btfJieshou.setVisibility(View.GONE);
                    btfQuerenyuyue.setVisibility(View.GONE);
                    btfLijify.setVisibility(View.VISIBLE);
                    tvfStatus.setText("待放样");
                    break;
                case 4:
                    llfQrsmsj.setVisibility(View.VISIBLE);
                    llfFywcsj.setVisibility(View.VISIBLE);
                    btfJushou.setVisibility(View.GONE);
                    btfJieshou.setVisibility(View.GONE);
                    btfQuerenyuyue.setVisibility(View.GONE);
                    btfLijify.setVisibility(View.GONE);
                    tvfStatus.setText("放样完成");
                    break;
            }
            ViewTools.returnDrawable(mContext, tvfName, 3, R.mipmap.btn_huaxiang, new int[]{0, 0, DensityUtils.dp2px(mContext, 18), DensityUtils.dp2px(mContext, 18)});
            CommonUtils.setSex(this.tvfName,gpBean.getCustomerName(),gpBean.getCustomerSex());

            this.tvfBuilding.setText(gpBean.getBuilding());
            this.tvfAppointTime.setText(DateTool.getTimeTimestamp(gpBean.getAppointDate(), "MM月dd日-HH:mm") + "");
            this.tvAddress.setText(gpBean.getAddress());
            this.tvfWorkpoints.setText(gpBean.getWorkPoints());
            tvfconfirmtime.setText(DateTool.getTimeTimestamp(gpBean.getConfirmDate(), "MM月dd日-HH:mm") + "");
            tvfFyfinishtime.setText(DateTool.getTimeTimestamp(gpBean.getFinishDate(), "MM月dd日-HH:mm") + "");
        }
    }
}
