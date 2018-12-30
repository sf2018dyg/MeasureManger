package com.soonfor.evaluate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.evaluate.bean.EvaluateRankingBean;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.view.RoundImageView;

import java.util.List;

import butterknife.BindView;

/**
 * 作者：DC-DingYG on 2018-12-05 19:35
 * 邮箱：dingyg012655@126.com
 */
public class EvaluateRankAdapter extends BaseAdapter<EvaluateRankAdapter.ViewHolder, EvaluateRankingBean> {

    List<EvaluateRankingBean> mList;
    public EvaluateRankAdapter(Context context) {
        super(context);
    }

    @Override
    public void notifyDataSetChanged(List<EvaluateRankingBean> dataList) {
        this.mList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_evaluaterank, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(context, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvfAMyRanking)
        TextView tvfAMyRanking;
        @BindView(R.id.myfAdapter_avatar)
        RoundImageView myAvatar;
        @BindView(R.id.tvfAName)
        TextView tvfAName;
        @BindView(R.id.tvfAClientGood)
        TextView tvfAClientGood;
        @BindView(R.id.tvfARevistV)
        TextView tvfARevistV;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfAMyRanking = itemView.findViewById(R.id.tvfAMyRanking);
            this.myAvatar = itemView.findViewById(R.id.myfAdapter_avatar);
            this.tvfAName = itemView.findViewById(R.id.tvfAName);
            this.tvfAClientGood = itemView.findViewById(R.id.tvfAClientGood);
            this.tvfARevistV = itemView.findViewById(R.id.tvfARevistV);
        }
        public void setData(Context context, EvaluateRankingBean bean){
            this.tvfAMyRanking.setText(bean.getRankSort());
            this.tvfAName.setText(bean.getUserName());
            this.tvfAClientGood.setText(bean.getGoodRank() + "%");
            this.tvfARevistV.setText(bean.getFeedbackAvg()+"分");
            ImageUtil.loadImage(context, bean.getFheadpic(), this.myAvatar);
        }
    }
}
