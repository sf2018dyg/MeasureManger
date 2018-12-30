package com.soonfor.measuremanager.home.liangchi.adapter.detail;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.measureChild;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.measureHead;
import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/1 09:28
 * 邮箱：suibozhu@139.com
 */

public class MeasurementHeadAdpter extends BaseAdapter implements View.OnClickListener {

    private List<measureHead> beans;
    private Context mContext;
    private GridLayoutManager manager;

    public MeasurementHeadAdpter(Context context, List<measureHead> beans) {
        super(context);
        mContext = context;
        this.beans = beans;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_measurementhead, parent, false);
        return new topAchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        topAchievementViewHolder viewHolder = (topAchievementViewHolder) holder;
        viewHolder.headtxt.setText(CommonUtils.formatStr(beans.get(position).getHead()));
        viewHolder.llfhead.setOnClickListener(this);
        boolean bl = (boolean)viewHolder.llfhead.getTag();
        if(bl){
            viewHolder.headimg.setImageResource(R.mipmap.icon_up);
            viewHolder.rlviewChild.setVisibility(View.VISIBLE);
        }else{
            viewHolder.headimg.setImageResource(R.mipmap.icon_down);
            viewHolder.rlviewChild.setVisibility(View.GONE);
        }

        //初始化child的数据
        viewHolder.childAdpter.notifyDataSetChanged(beans.get(position).getChilds());
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class topAchievementViewHolder extends RecyclerView.ViewHolder {
        public TextView headtxt;
        public ImageView headimg;
        public RelativeLayout llfhead;
        public RecyclerView rlviewChild;
        public MeasurementChildAdpter childAdpter;

        public topAchievementViewHolder(View itemView) {
            super(itemView);
            headtxt = itemView.findViewById(R.id.headtxt);
            headimg = itemView.findViewById(R.id.headimg);
            llfhead = itemView.findViewById(R.id.llfhead);
            rlviewChild = itemView.findViewById(R.id.rlviewChild);
            llfhead.setTag(true);

            manager = new GridLayoutManager(mContext, 1);
            childAdpter = new MeasurementChildAdpter(mContext, new ArrayList<measureChild>());
            rlviewChild.setLayoutManager(manager);
            rlviewChild.setAdapter(childAdpter);
        }
    }

    @Override
    public void onClick(View v) {
        boolean bl = (boolean)v.getTag();
        if(bl){
           v.setTag(false);
           notifyDataSetChanged();
        }else{
            v.setTag(true);
            notifyDataSetChanged();
        }
    }
}
