package com.soonfor.measuremanager.home.homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.complexruler.activity.FuChiMainActivity;
import com.soonfor.measuremanager.home.design.activity.DesignMainActivity;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiMainActivity;
import com.soonfor.measuremanager.home.lofting.activity.LoftingMainActivity;
import com.soonfor.measuremanager.home.othertask.activity.OtherTaskMainActivity;
import com.soonfor.measuremanager.home.homepage.model.bean.TaskTypesDetail;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;

import java.util.List;

/**
 * Created by ljc on 2018/1/16.
 */

public class SaleFunnelAdapter extends BaseAdapter {

    private int type;
    private List<TaskTypesDetail> beans;

    public SaleFunnelAdapter(Context context, List<TaskTypesDetail> beans, int type) {
        super(context);
        this.beans = beans;
        this.type = type;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_custom_intent, parent, false);
        SaleFunnelViewHolder holder = new SaleFunnelViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SaleFunnelViewHolder saleFunnelViewHolder = (SaleFunnelViewHolder) holder;
        if (type == 1) {
            saleFunnelViewHolder.iv.setImageResource(R.drawable.dot_red);
            saleFunnelViewHolder.linearLayout.setOnClickListener(lcListener);
            saleFunnelViewHolder.linearLayout.setTag(position);
        } else if (type == 2) {
            saleFunnelViewHolder.iv.setImageResource(R.drawable.dot_green);
            saleFunnelViewHolder.linearLayout.setOnClickListener(sjListener);
            saleFunnelViewHolder.linearLayout.setTag(position);
        } else if (type == 3) {
            saleFunnelViewHolder.iv.setImageResource(R.drawable.blue_dot);
            saleFunnelViewHolder.linearLayout.setOnClickListener(fcListener);
            saleFunnelViewHolder.linearLayout.setTag(position);
        } else if (type == 4) {
            saleFunnelViewHolder.iv.setImageResource(R.drawable.orange_dot);
            saleFunnelViewHolder.linearLayout.setOnClickListener(fyListener);
            saleFunnelViewHolder.linearLayout.setTag(position);
        }else if(type == 5){
            saleFunnelViewHolder.iv.setImageResource(R.drawable.orange_dot);
            saleFunnelViewHolder.linearLayout.setOnClickListener(otherListener);
            saleFunnelViewHolder.linearLayout.setTag(position);
        }

        if (position == 2 | position == 5) {
            saleFunnelViewHolder.spaceline.setVisibility(View.INVISIBLE);
        }

        saleFunnelViewHolder.tvType.setText(CommonUtils.formatStr(beans.get(position).getName()));
        saleFunnelViewHolder.tvNumber.setText(CommonUtils.formatStr(beans.get(position).getValue()) + "");
    }

    //量尺的单击
    private View.OnClickListener lcListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer i = (Integer) v.getTag();
            Intent intent = new Intent(context, LiangChiMainActivity.class);
            intent.putExtra("posi", i);
            context.startActivity(intent);
        }
    };

    //设计的单击
    private View.OnClickListener sjListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int fragmentPositon = (int) v.getTag();
            Intent intentFy = new Intent(context, DesignMainActivity.class);
            intentFy.putExtra(Tokens.Design.SKIPTOFRAGMENT_STATUS, fragmentPositon);
            context.startActivity(intentFy);
        }
    };

    //复尺
    private View.OnClickListener fcListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer i = (Integer) v.getTag();
            Intent intent = new Intent(context, FuChiMainActivity.class);
            intent.putExtra("posi", i);
            context.startActivity(intent);
        }
    };

    //放样
    private View.OnClickListener fyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int fragmentPositon = (int) v.getTag();
            Intent intentFy = new Intent(context, LoftingMainActivity.class);
            intentFy.putExtra(Tokens.Lofing.SKIPTOFRAGMENT_STATUS, fragmentPositon);
            context.startActivity(intentFy);
        }
    };
    //其它任务
    private View.OnClickListener otherListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int fragmentPositon = (int) v.getTag();
            Intent intentOther = new Intent(context, OtherTaskMainActivity.class);
            intentOther.putExtra(Tokens.OtherTask.SKIPTOFRAGMENT_STATUS, fragmentPositon);
            context.startActivity(intentOther);
        }
    };

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class SaleFunnelViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv;
        public TextView tvNumber, tvType;
        public ImageView spaceline;
        public LinearLayout linearLayout;

        public SaleFunnelViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvType = itemView.findViewById(R.id.tv_type);
            spaceline = itemView.findViewById(R.id.spaceline);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

}
