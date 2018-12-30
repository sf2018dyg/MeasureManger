package com.soonfor.evaluate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.evaluate.bean.AppResSortItemDto;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 作者：DC-DingYG on 2018-10-18 15:17
 * 邮箱：dingyg012655@126.com
 * 评价显示 不可操作
 */
public class EvaluateLevlShowAdapter extends BaseAdapter<EvaluateLevlShowAdapter.ViewHolder, AppResSortItemDto> {

    private List<AppResSortItemDto> mlist;

    public EvaluateLevlShowAdapter(Context context, List<AppResSortItemDto> list) {
        super(context);
        this.mlist = list;
    }
    @Override
    public void notifyDataSetChanged(List dataList) {
        mlist = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_evaluate_levl_show, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }


    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvfEvalObj;//评价的项目
        MaterialRatingBar mrBar;
        TextView tvfAttitudePoint;
        TextView tvfAttitudeDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfEvalObj = itemView.findViewById(R.id.tvfEvalObj);
            this.mrBar = itemView.findViewById(R.id.mRatingBar_Attitude);
            this.tvfAttitudePoint = itemView.findViewById(R.id.tvfAttitudePoint);
            this.tvfAttitudeDesc = itemView.findViewById(R.id.tvfAttitudeDesc);
        }

        public void setData(int position) {
            AppResSortItemDto bean = mlist.get(position);
            tvfEvalObj.setText(bean.getFsorttitle().trim());
            if(bean.getFpoint()==0){
                tvfAttitudePoint.setText("");
            }else {
                tvfAttitudePoint.setText(bean.getFpoint() + "分");
            }
            tvfAttitudeDesc.setText(bean.getFsortstardesc().trim());
            mrBar.setNumStars(5);
            mrBar.setRating(bean.getFpoint());
        }
    }
}
