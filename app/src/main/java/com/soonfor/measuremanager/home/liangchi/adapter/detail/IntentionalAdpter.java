package com.soonfor.measuremanager.home.liangchi.adapter.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.IntentionalEntity;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.view.RoundJiaoImageView;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 9:12
 * 邮箱：suibozhu@139.com
 */
public class IntentionalAdpter extends BaseAdapter<IntentionalAdpter.ViewHolder, IntentionalEntity> {

    private List<IntentionalEntity> beans;
    private Context mContext;

    public IntentionalAdpter(Context context, List<IntentionalEntity> beans) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_intentional, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(beans.get(position), position);
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RoundJiaoImageView imgpath;
        public TextView title;
        public TextView specifical;
        public TextView color;
        public TextView texture;
        public TextView price;
        public TextView unit;
        public TextView num;
        public View lines;

        public ViewHolder(View itemView) {
            super(itemView);
            imgpath = itemView.findViewById(R.id.imgpath);
            title = itemView.findViewById(R.id.title);
            specifical = itemView.findViewById(R.id.specifical);
            color = itemView.findViewById(R.id.color);
            texture = itemView.findViewById(R.id.texture);
            price = itemView.findViewById(R.id.price);
            unit = itemView.findViewById(R.id.unit);
            num = itemView.findViewById(R.id.num);
            lines = itemView.findViewById(R.id.lines);
        }

        public void setData(IntentionalEntity gpBean, int position) {
            ImageUtil.loadImage(mContext, gpBean.getThumbnail(), imgpath);
            title.setText(CommonUtils.formatStr(gpBean.getName()));
            specifical.setText(CommonUtils.formatStr(gpBean.getSizeDesc()));
            color.setText(CommonUtils.formatStr(gpBean.getColor()));
            texture.setText(CommonUtils.formatStr(gpBean.getTexture()));
            price.setText(CommonUtils.formatStr(gpBean.getUnitPrice()));
            unit.setText(CommonUtils.formatStr(gpBean.getfStdUnit()));
            num.setText("X" + CommonUtils.formatStr(gpBean.getQuantity()));
            if (position == beans.size() - 1) {
                lines.setVisibility(View.INVISIBLE);
            }
        }
    }

}