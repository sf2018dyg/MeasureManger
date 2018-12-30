package com.soonfor.measuremanager.home.lofting.adapter.immloft;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.LoftListSecondActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;
import com.soonfor.measuremanager.tools.Tokens;

import java.util.List;

/**
 * Created by DingYg on 2018-02-09.
 */

public class LoftListFirstAdapter extends BaseAdapter<LoftListFirstAdapter.ViewHolder, MarkWallEntity> implements View.OnClickListener {

    private List<MarkWallEntity> list;
    private RecyclerView.LayoutManager mLayoutManager;
    private String taskno;
    private String sContractNo;

    public LoftListFirstAdapter(Context context, String taskno, String sContractNo, List<MarkWallEntity> list) {
        super(context);
        this.taskno = taskno;
        this.sContractNo = sContractNo;
        this.list = list;
    }

    @Override
    public LoftListFirstAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_lofting_imm_lsit_item, parent, false));
    }

    @Override
    public void onBindViewHolder(LoftListFirstAdapter.ViewHolder holder, int position) {
        holder.rlfhead.setTag(position);
        holder.rlfShow.setTag(position);
        holder.rlfhead.setOnClickListener(this);
        holder.rlfShow.setOnClickListener(this);
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
        int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.rlfhead:
                MarkWallEntity lfInfoBean = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(Tokens.Lofing.DETAILSKIPIMMELOFT_TITLE_WALL, lfInfoBean.getWallName()+"-放样清单");
                bundle.putString("fy_taskNo",taskno);
                bundle.putString("fy_contractNo",sContractNo);
                bundle.putString("fy_wallCode",lfInfoBean.getWallCode());
                Intent intent = new Intent(context,LoftListSecondActivity.class);
                intent.putExtras(bundle);
                ((Activity)context).startActivityForResult(intent, Tokens.Lofing.REQUEST_CODE_FIRST_TO_SECOEND);
                break;
            case R.id.rlfShow:
                if (list.get(position).isShowing()) {
                    list.get(position).setShowing(false);
                    notifyDataSetChanged();
                } else {
                    list.get(position).setShowing(true);
                    notifyDataSetChanged();
                }
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvfHeadtxt;
        public RelativeLayout rlfhead;
        public RelativeLayout rlfShow;
        public ImageView imgfHeadShow;
        public LinearLayout llfChildContent;
        public RecyclerView rlviewChild;
        public LoftListFirstChildAdpter childAdpter;

        public ViewHolder(View itemView) {
            super(itemView);
            tvfHeadtxt = itemView.findViewById(R.id.tvfHeadtxt);
            rlfhead = itemView.findViewById(R.id.rlfhead);
            imgfHeadShow = itemView.findViewById(R.id.imgfHeadShow);
            rlfShow = itemView.findViewById(R.id.rlfShow);
            llfChildContent = itemView.findViewById(R.id.llfChildContent);
            rlviewChild = itemView.findViewById(R.id.rlviewChild);
        }

        public void setData(MarkWallEntity linfoBean) {
            tvfHeadtxt.setText(linfoBean.getWallName());
            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            childAdpter = new LoftListFirstChildAdpter(context, linfoBean.getComponents(), 1);
            rlviewChild.setLayoutManager(mLayoutManager);
            rlviewChild.setAdapter(childAdpter);
            if (linfoBean.isShowing()) {
                imgfHeadShow.setImageResource(R.mipmap.icon_up);
                llfChildContent.setVisibility(View.VISIBLE);
            } else {
                imgfHeadShow.setImageResource(R.mipmap.icon_down);
                llfChildContent.setVisibility(View.GONE);
            }
        }
    }
}