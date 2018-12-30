package com.soonfor.measuremanager.home.homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.homepage.activity.DesignReTieDetailActivity;
import com.soonfor.measuremanager.home.homepage.model.bean.topPostsEntity;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.view.RoundJiaoImageView;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 9:12
 * 邮箱：suibozhu@139.com
 */
public class topPostsAdpter extends BaseAdapter {

    private List<topPostsEntity> beans;
    private Context mContext;

    public topPostsAdpter(Context context, List<topPostsEntity> beans) {
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
        View view = mInflater.inflate(R.layout.item_topposts, parent, false);
        return new topAchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        topAchievementViewHolder viewHolder = (topAchievementViewHolder) holder;
        ImageUtil.loadImage(mContext,beans.get(position).getImgpath(), viewHolder.imgpath);
        viewHolder.title.setText(CommonUtils.formatStr(beans.get(position).getTitle()));
        viewHolder.author.setText((beans.get(position).getAuthor()));
        viewHolder.readingVolume.setText(CommonUtils.formatStr(beans.get(position).getReadingVolume()));
        if (position == 2) {
            viewHolder.lines.setVisibility(View.INVISIBLE);
        }
        viewHolder.rlitem.setTag(beans.get(position));
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class topAchievementViewHolder extends RecyclerView.ViewHolder {

        public RoundJiaoImageView imgpath;
        public TextView title;
        public TextView author;
        public TextView readingVolume;
        public View lines;
        RelativeLayout rlitem;

        public topAchievementViewHolder(View itemView) {
            super(itemView);
            imgpath = itemView.findViewById(R.id.imgpath);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            readingVolume = itemView.findViewById(R.id.readingVolume);
            lines = itemView.findViewById(R.id.lines);
            rlitem = itemView.findViewById(R.id.rlitem);
            rlitem.setOnClickListener(listener);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rlitem:
                    topPostsEntity te = (topPostsEntity) v.getTag();
                    Intent intent = new Intent(mContext, DesignReTieDetailActivity.class);
                    intent.putExtra("topPostsEntity", te);
                    mContext.startActivity(intent);
                    break;
            }
        }
    };

}