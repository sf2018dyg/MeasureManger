package com.soonfor.measuremanager.me.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.me.bean.PointsDetailsBean;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class PointsDetailsAdapter extends android.widget.BaseAdapter {
    private PointsDetailsBean.DataBean beanList;
    private LayoutInflater inflater;
    private Context context;

    public PointsDetailsAdapter(Context context, PointsDetailsBean.DataBean list) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.beanList = list;
    }

    public void changeList(PointsDetailsBean.DataBean list) {
        this.beanList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (beanList.getList() == null)
            return 0;
        return beanList.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_work_points_details, null);
            holder.itemImgPic = convertView.findViewById(R.id.item_img_pic);
            holder.itemTvName = convertView.findViewById(R.id.item_tv_name);
            holder.itemTvDate = convertView.findViewById(R.id.item_tv_date);
            holder.itemTvPoints = convertView.findViewById(R.id.item_tv_points);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PointsDetailsBean.DataBean.ListBean bean = beanList.getList().get(position);
        ImageUtil.loadImage(context, bean.getAttachUrl(),holder.itemImgPic);
        holder.itemTvName.setText(bean.getSourceName());
        holder.itemTvDate.setText(DateTool.getTimeTimestamp(bean.getOccurTime(),"yyyy-MM-dd HH:mm"));
        if (!String.valueOf(bean.getPoints()).contains("-"))
            holder.itemTvPoints.setText("+" + bean.getPoints() + "工分");
        else
            holder.itemTvPoints.setText(bean.getPoints() + "工分");
        return convertView;
    }

    private class ViewHolder {
        ImageView itemImgPic;
        TextView itemTvName;
        TextView itemTvDate;
        TextView itemTvPoints;
    }
}
