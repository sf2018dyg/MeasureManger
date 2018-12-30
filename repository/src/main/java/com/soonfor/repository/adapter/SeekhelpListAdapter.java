package com.soonfor.repository.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.repository.R;
import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.ui.activity.knowledge.KnowledgeDetailActivity;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-06-20 16:56
 * 邮箱：dingyg012655@126.com
 */
public class SeekhelpListAdapter extends RepBaseAdapter<SeekhelpListAdapter.ViewHolder, KnowledgeBean> {

    private List<KnowledgeBean> list;

    public SeekhelpListAdapter(Context context) {
        super(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_seekhelplist, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(context, position);
        holder.rlfItem.setTag(position);
        holder.rlfItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KnowledgeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ITEM_ID", list.get(position).getId());
                bundle.putParcelable("ITEM_BEAN", list.get(position));
                bundle.putInt("LIST_POSITION", position);
                bundle.putString("LAST_VIEWTYPE", "SeekhelpFragment");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlfItem;
        TextView tvfRanking;//排名
        TextView tvfMatter;//题目
        TextView tvfPageviewNum;//浏览量
        TextView tvfContext;//内容

        public ViewHolder(View itemView) {
            super(itemView);
            this.rlfItem = itemView.findViewById(R.id.rlfItem);
            this.tvfRanking = itemView.findViewById(R.id.tvfRanking);
            this.tvfMatter = itemView.findViewById(R.id.tvfMatter);
            this.tvfPageviewNum = itemView.findViewById(R.id.tvfPageViewNum);
            this.tvfContext = itemView.findViewById(R.id.tvfContent);
        }

        public void setData(Context mContext, int position) {
            KnowledgeBean gpBean = list.get(position);
            // String ranking = String.valueOf(position + 1);
            String ranking = "";
            switch (position) {
                case 0:
                    tvfRanking.setBackgroundColor(Color.parseColor("#FF2D46"));
                    ranking = "  1  ";
                    break;
                case 1:
                    tvfRanking.setBackgroundColor(Color.parseColor("#FF7F49"));
                    ranking = "  2  ";
                    break;
                case 2:
                    tvfRanking.setBackgroundColor(Color.parseColor("#FFAA3B"));
                    ranking = "  3  ";
                    break;
                default:
                    tvfRanking.setBackgroundColor(Color.parseColor("#ffffff"));
                    int rank = position + 1;
                    if(rank<10){
                        ranking = "0"+rank;
                    }else {
                        ranking = ""+rank;
                    }
                    break;
            }
            tvfRanking.setText(ranking);
            tvfMatter.setText(gpBean.getTitle());
            tvfPageviewNum.setText(gpBean.getViewCount() + "");
            tvfContext.setText(gpBean.getSummary());
        }

    }
}