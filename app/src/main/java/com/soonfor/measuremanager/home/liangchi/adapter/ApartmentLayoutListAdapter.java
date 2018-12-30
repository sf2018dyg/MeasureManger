package com.soonfor.measuremanager.home.liangchi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.liangchi.activity.ApartmentLayoutActivity;
import com.soonfor.measuremanager.home.liangchi.model.bean.ApartmentLayoutBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.view.RoundJiaoImageView;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 16:11
 * 邮箱：suibozhu@139.com
 * 立刻量尺
 */
/**
 * 修改人：DC-ZhuSuiBo on 2018/5/3 0003 8:36
 * 邮箱：suibozhu@139.com
 * 修改目的：用Glide加载图片 Picasso在不停notifyDataSetChanged的情况下会位置显示错乱
 */
public class ApartmentLayoutListAdapter extends BaseAdapter<ApartmentLayoutListAdapter.ViewHolder, ApartmentLayoutBean> {

    private List<ApartmentLayoutBean> list;
    private static ApartmentLayoutActivity mainActivity;
    int curPosi = -1;
    String selectkey = "";

    public ApartmentLayoutListAdapter(Context context, ApartmentLayoutActivity mainActivity, List<ApartmentLayoutBean> list) {
        super(context);
        this.mainActivity = mainActivity;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_liangchi_partmentlayout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.llfItem.setOnClickListener(holder.listener);
        holder.llfItem.setTag(position);
        if (selectkey.equals(list.get(position).getFobsplanid())) {
            holder.showSelected.setVisibility(View.VISIBLE);
        } else {
            holder.showSelected.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void notifyDataSetChanged(List<ApartmentLayoutBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llfItem;
        ImageView imgpath;
        TextView tvfcustom;
        TextView tvfhuxingtype;
        TextView tvfmeasureArea;
        TextView tvfadress;
        RelativeLayout showSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            llfItem = itemView.findViewById(R.id.llfItem);
            imgpath = itemView.findViewById(R.id.imgpath);
            tvfcustom = itemView.findViewById(R.id.tvfcustom);
            tvfhuxingtype = itemView.findViewById(R.id.tvfhuxingtype);
            tvfmeasureArea = itemView.findViewById(R.id.tvfmeasureArea);
            tvfadress = itemView.findViewById(R.id.tvfadress);
            showSelected = itemView.findViewById(R.id.showSelected);
            showSelected.setVisibility(View.INVISIBLE);
        }

        public void setData(ApartmentLayoutBean gpBean) {
           // Picasso.with(context).load(gpBean.getFpics()).placeholder(R.mipmap.zanuw).error(R.mipmap.zanuw).into(imgpath);
            Glide.with(context).load(gpBean.getFpics()).into(imgpath);

            tvfcustom.setText(CommonUtils.formatStr(CommonUtils.formatStr(gpBean.getFname())));
            tvfhuxingtype.setText(CommonUtils.formatStr(gpBean.getFspecname()));
            tvfmeasureArea.setText(CommonUtils.formatStr(gpBean.getFsrcarea()));
            tvfadress.setText(CommonUtils.formatStr(gpBean.getFaddress()));
        }

        public View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.llfItem:
                        int tag = Integer.parseInt(v.getTag()+"");
                        setCurPosi(tag);
                        selectkey = list.get(tag).getFobsplanid();
                        notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    public int getCurPosi() {
        return curPosi;
    }

    public void setCurPosi(int curPosi) {
        this.curPosi = curPosi;
    }
}
