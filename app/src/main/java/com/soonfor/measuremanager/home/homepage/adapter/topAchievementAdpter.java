package com.soonfor.measuremanager.home.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.homepage.model.bean.topAchievementDetail;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.MyToast;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/29 16:20
 * 邮箱：suibozhu@139.com
 */

public class topAchievementAdpter extends BaseAdapter {

    private List<topAchievementDetail> beans;
    private Context mContext;

    public topAchievementAdpter(Context context, List<topAchievementDetail> beans) {
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
        View view = mInflater.inflate(R.layout.item_achievement, parent, false);
        return new topAchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        topAchievementViewHolder viewHolder = (topAchievementViewHolder) holder;
        switch (position) {
            case 0:
                viewHolder.iv.setImageResource(R.mipmap.ranking_first);
                break;
            case 1:
                viewHolder.iv.setImageResource(R.mipmap.ranking_second);
                break;
            case 2:
                viewHolder.iv.setImageResource(R.mipmap.ranking_third);
                break;
            case 4:
                viewHolder.iv.setImageResource(R.mipmap.ranking_fourth);
                break;
            default:
                viewHolder.iv.setImageResource(R.mipmap.ranking_fourth);
                break;
        }
        ImageUtil.loadImage(mContext, beans.get(position).getAvatarUrl(), viewHolder.imgfCirimgview);
        viewHolder.tv_number.setText(CommonUtils.formatStr(beans.get(position).getRanking()) + "");
        viewHolder.tv_name.setText(CommonUtils.formatStr(beans.get(position).getName()) + "");
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class topAchievementViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv;
        public CircularImageView imgfCirimgview;
        public TextView tv_name;
        public TextView tv_number;
        public LinearLayout linearLayout;

        public topAchievementViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            imgfCirimgview = itemView.findViewById(R.id.ivhead);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_number = itemView.findViewById(R.id.tv_number);
            linearLayout = itemView.findViewById(R.id.linearLayout);

            linearLayout.setOnClickListener(listener);
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.showToast(mContext,"开发中..");
            }
        };
    }

}