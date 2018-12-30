package com.soonfor.measuremanager.home.lofting.adapter.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.ShowLfMemberActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkComponentEntity;
import com.soonfor.measuremanager.tools.NoDoubleClickUtils;
import com.soonfor.measuremanager.tools.Tokens;

import java.util.List;

/**
 * Created by DingYg on 2018-02-02.
 */

public class MarkInfoComponentAdpter extends BaseAdapter<MarkInfoComponentAdpter.ViewHolder, MarkComponentEntity> implements View.OnClickListener {

    private List<MarkComponentEntity> clist;
    private int viewType;

    public MarkInfoComponentAdpter(Context context, List<MarkComponentEntity> list, int viewType) {
        super(context);
        this.clist = list;
        this.viewType = viewType;
    }

    @Override
    public MarkInfoComponentAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_lofting_detail_loftinfo_child, parent, false));
    }

    @Override
    public void onBindViewHolder(MarkInfoComponentAdpter.ViewHolder holder, int position) {
        if (viewType == 2) {
            holder.tvfMember.setTag(position);
            holder.tvfMember.setOnClickListener(this);
        }
        holder.setData(clist.get(position));
    }

    @Override
    public int getItemCount() {
        return clist == null ? 0 : clist.size();
    }

    @Override
    public void notifyDataSetChanged(List<MarkComponentEntity> dataList) {
        clist = dataList;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvfLfMember:
                int positon = (int) v.getTag();
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    Intent intent = new Intent(context, ShowLfMemberActivity.class);
                    intent.putExtra(Tokens.Lofing.SKIP_ENTER_SHOWMEMBER_SIZE_STRIGN, clist.get(positon));
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvfMember;
        public TextView tvfStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvfMember = itemView.findViewById(R.id.tvfLfMember);
            tvfStatus = itemView.findViewById(R.id.tvfLoftingStatus);
        }

        public void setData(MarkComponentEntity componentEntity) {
            tvfMember.setText(componentEntity.getName());
            String status = componentEntity.getStatus();
            if(status.equals("待放样")){
                tvfStatus.setTextColor(Color.parseColor("#eb433a"));
            }else if(status.equals("已放样")){
                tvfStatus.setTextColor(Color.parseColor("#0599fd"));
            }else{
                tvfStatus.setTextColor(Color.parseColor("#FE841E"));
            }
            tvfStatus.setText(status);
        }
    }
}
