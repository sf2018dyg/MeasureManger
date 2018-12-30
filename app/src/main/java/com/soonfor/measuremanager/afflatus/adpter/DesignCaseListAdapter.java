package com.soonfor.measuremanager.afflatus.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.pkmmte.view.CircularImageView;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.DesignCaseBean;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/20 16:56
 * 邮箱：suibozhu@139.com
 */
public class DesignCaseListAdapter extends BaseAdapter<DesignCaseListAdapter.ViewHolder, DesignCaseBean> {

    private List<DesignCaseBean> list;
    static Context mContext;
    static View.OnClickListener listener;

    public DesignCaseListAdapter(Context context, List<DesignCaseBean> list, View.OnClickListener listener) {
        super(context);
        mContext = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_designcase, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.rlfitem.setTag(list.get(position));
    }

    @Override
    public void notifyDataSetChanged(List<DesignCaseBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircularImageView ivhead;
        TextView designerName;
        TextView postDate;
        ImageView imgcover;
        TextView title;
        TextView subtitle;
        RelativeLayout readingVolume;
        RelativeLayout replyVolume;
        RelativeLayout thumbsupVolume;
        TextView readingVolumetxt;
        TextView replyVolumetxt;
        TextView thumbsupVolumetxt;
        RelativeLayout rlfitem;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivhead = itemView.findViewById(R.id.ivhead);
            this.designerName = itemView.findViewById(R.id.designerName);
            this.postDate = itemView.findViewById(R.id.postDate);
            this.imgcover = itemView.findViewById(R.id.imgcover);
            this.title = itemView.findViewById(R.id.title);
            this.subtitle = itemView.findViewById(R.id.subtitle);
            this.readingVolume = itemView.findViewById(R.id.readingVolume);
            this.replyVolume = itemView.findViewById(R.id.replyVolume);
            this.thumbsupVolume = itemView.findViewById(R.id.thumbsupVolume);
            this.readingVolumetxt = itemView.findViewById(R.id.readingVolumetxt);
            this.replyVolumetxt = itemView.findViewById(R.id.replyVolumetxt);
            this.thumbsupVolumetxt = itemView.findViewById(R.id.thumbsupVolumetxt);
            this.rlfitem = itemView.findViewById(R.id.rlfitem);

            rlfitem.setOnClickListener(listener);
            readingVolumetxt.setOnClickListener(listener);
            replyVolumetxt.setOnClickListener(listener);
            thumbsupVolumetxt.setOnClickListener(listener);
        }

        public void setData(DesignCaseBean gpBean) {
            ImageUtil.loadCaselibImage(mContext, gpBean.getSurfacePlotUrl(), imgcover);
            title.setText(CommonUtils.formatStr(gpBean.getTitle()));
            subtitle.setText(CommonUtils.formatStr(gpBean.getBuildName()) + " | " + CommonUtils.formatStr(gpBean.getStyleName()));
            readingVolumetxt.setText(CommonUtils.formatStr(gpBean.getViews()) + "");
            replyVolumetxt.setText(CommonUtils.formatStr(gpBean.getComments()) + "");
            thumbsupVolumetxt.setText(CommonUtils.formatStr(gpBean.getLikes()) + "");
            thumbsupVolumetxt.setTag(gpBean);
        }
    }
}
