package com.soonfor.measuremanager.me.adapter.mycase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.afflatus.bean.DesignCaseBean;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.TimeUtils;
import com.soonfor.measuremanager.view.RoundJiaoImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018-02-26.
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/4/4 0004 14:04
 * 邮箱：suibozhu@139.com
 * 修改目的：加选中事件
 */
public class MycaseListAdapter extends BaseAdapter<MycaseListAdapter.ViewHolder, DesignCaseBean> {

    private List<DesignCaseBean> informList;
    int curPosi = -1;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    boolean isFavroiteCase;//共用设配器（是否是收藏设计方案）

    public MycaseListAdapter(Context context, List<DesignCaseBean> informBeans) {
        super(context);
        this.informList = informBeans;
        mOnItemClickListener = null;
        this.isFavroiteCase = false;
    }
    public MycaseListAdapter(Context context, List<DesignCaseBean> informBeans, boolean isFavroiteCase) {
        super(context);
        this.informList = informBeans;
        mOnItemClickListener = null;
        this.isFavroiteCase = true;
    }

    @Override
    public void notifyDataSetChanged(List<DesignCaseBean> dataList) {
        this.informList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(isFavroiteCase) {
            view = mInflater.inflate(R.layout.adapter_favorite_case, parent, false);
        } else {
            view = mInflater.inflate(R.layout.adapter_mycase, parent, false);
        }
        return new ViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (informList != null && informList.get(position) != null)
            holder.setData(informList.get(position));
        holder.rlfItem.setTag(position);
        if(!isFavroiteCase) {
            if (position == getCurPosi()) {
                holder.showSelected.setVisibility(View.VISIBLE);
            } else {
                holder.showSelected.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return informList == null ? 0 : informList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnRecyclerViewItemClickListener mOnItemClickListener = null;
        LinearLayout llTotal;
        RelativeLayout rlfItem;
        public RoundJiaoImageView imgpath;
        public TextView title;
        public TextView tvfBuilding;
        public TextView tvfDate;
        public TextView tvfReadingNum;
        public TextView tvfCommentNum;
        public TextView tvfLikeNum;
        RelativeLayout showSelected;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener mListener) {
            super(itemView);
            this.mOnItemClickListener = mListener;
            llTotal = itemView.findViewById(R.id.llTotal);
            rlfItem = itemView.findViewById(R.id.rlfItem);
            imgpath = itemView.findViewById(R.id.imgpath);
            title = itemView.findViewById(R.id.tvfCaseName);
            tvfBuilding = itemView.findViewById(R.id.tvfCase_building);
            tvfDate = itemView.findViewById(R.id.tvfCase_date);
            tvfReadingNum = itemView.findViewById(R.id.tvfReadingNum);
            if(!isFavroiteCase) {
                tvfCommentNum = itemView.findViewById(R.id.tvfComment);
                tvfLikeNum = itemView.findViewById(R.id.tvfLike);
                showSelected = itemView.findViewById(R.id.showSelected);
                showSelected.setVisibility(View.INVISIBLE);
            }
        }

        public void setData(DesignCaseBean cb) {
            this.title.setText(CommonUtils.formatStr(cb.getTitle()));
            this.tvfBuilding.setText(CommonUtils.formatStr(cb.getBuildName()));
            this.tvfDate.setText(CommonUtils.formatStr(TimeUtils.long2String(cb.getCreateTime())));
            this.tvfReadingNum.setText(CommonUtils.formatStr(cb.getComments()));
            String imageUrl = CommonUtils.formatStr(cb.getSurfacePlotUrl());
            if (imageUrl.equals("")) {
                this.imgpath.setImageResource(R.mipmap.zanuw);
            } else {
                ImageUtil.loadCaselibImage(context, imageUrl, imgpath);
            }
            rlfItem.setOnClickListener(this);
            if(!isFavroiteCase) {
                this.tvfCommentNum.setText(CommonUtils.formatStr(cb.getComments()));
                this.tvfLikeNum.setText(CommonUtils.formatStr(cb.getLikes()));
            }
        }

//        public View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.rlfItem:
//                        int position = (int) v.getTag();
//                        if(getCurPosi()>=0 && getCurPosi() == position){
//                            setCurPosi(-1);
//                        }else{
//                            setCurPosi(position);
//                        }
//                        notifyDataSetChanged();
//                        break;
//                }
//            }
//        };

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            } else if(!isFavroiteCase){
                int position = (int) v.getTag();
                if (getCurPosi() >= 0 && getCurPosi() == position) {
                    setCurPosi(-1);
                } else {
                    setCurPosi(position);
                }
                notifyDataSetChanged();
            }
        }
    }

    public int getCurPosi() {
        return curPosi;
    }

    public void setCurPosi(int curPosi) {
        this.curPosi = curPosi;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }
}
