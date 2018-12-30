package com.soonfor.measuremanager.me.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.me.bean.PerformanceBean;
import com.soonfor.measuremanager.tools.DateTool;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class PerformanceAdapter extends BaseAdapter {
    private PerformanceBean.DataBean beanList;
    private LayoutInflater inflater;
    private Context context;

    public PerformanceAdapter(Context context, PerformanceBean.DataBean list) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.beanList = list;
    }

    public void changeList(PerformanceBean.DataBean list) {
        this.beanList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (beanList== null)
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
            convertView = inflater.inflate(R.layout.item_my_performance, null);
            holder.itemTvDate = convertView.findViewById(R.id.item_tv_date);
            holder.itemTvName = convertView.findViewById(R.id.item_tv_name);
            holder.itemTvNameType = convertView.findViewById(R.id.item_tv_name_type);
            holder.itemTvOrderAmount = convertView.findViewById(R.id.item_tv_order_amount);
            holder.itemTvOrderAmountType = convertView.findViewById(R.id.item_tv_order_amount_type);
            holder.itemTvAmount = convertView.findViewById(R.id.item_tv_amount);
            holder.itemTvAmountType = convertView.findViewById(R.id.item_tv_amount_type);
            holder.itemDividingLine = convertView.findViewById(R.id.item_dividing_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PerformanceBean.DataBean.ListBean bean = beanList.getList().get(position);
        holder.itemTvDate.setText(DateTool.getDateDay(bean.getPerfDate()));
        holder.itemTvName.setText(bean.getCustomerName());
//        holder.itemTvNameType.setText(bean.getSpecies());
        holder.itemTvOrderAmount.setText(bean.getOrderAmount() + "");
//        holder.itemTvOrderAmountType.setText(bean.getSpecies());
        holder.itemTvAmount.setText(bean.getAmount() + "");
        holder.itemTvAmountType.setText(bean.getSpecies()+ "(元)");
        return convertView;
    }

    private class ViewHolder {
        TextView itemTvDate;
        TextView itemTvName;
        TextView itemTvNameType;
        TextView itemTvOrderAmount;
        TextView itemTvOrderAmountType;
        TextView itemTvAmount;
        TextView itemTvAmountType;
        LinearLayout itemDividingLine;
    }
}
