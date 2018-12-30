package com.soonfor.measuremanager.home.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.home.order.bean.OrderDetailBean;
import com.soonfor.measuremanager.tools.ImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/1/20.
 */

public class ExpandListViewAdapter extends BaseExpandableListAdapter {
    private Context context;

    private List<OrderDetailBean.DataBean.BatchesBean> beans;

    public ExpandListViewAdapter(Context context, List<OrderDetailBean.DataBean.BatchesBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @Override
    public int getGroupCount() {
        return beans.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return beans.get(i).getOrderProducts().size();
    }

    @Override
    public Object getGroup(int i) {
        return beans.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return beans.get(i).getOrderProducts().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_elv_parent, viewGroup, false);
        }
        TextView text = view.findViewById(R.id.tv);
        text.setText(beans.get(i).getName());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_elv_child, viewGroup, false);
        }
        TextView tvName = view.findViewById(R.id.tv_name);
        tvName.setText(beans.get(i).getOrderProducts().get(i1).getName());
        ImageView iv = view.findViewById(R.id.iv);
        if(!TextUtils.isEmpty(beans.get(i).getOrderProducts().get(i1).getThumbnail()))
            ImageUtil.loadImage(context, beans.get(i).getOrderProducts().get(i1).getThumbnail(), iv);
        TextView tvType = view.findViewById(R.id.tv_type);
        tvType.setText(beans.get(i).getOrderProducts().get(i1).getSize());

        TextView tvMoney = view.findViewById(R.id.tv_money);
        tvMoney.setText("￥" + beans.get(i).getOrderProducts().get(i1).getPrice());

        TextView tvNumber = view.findViewById(R.id.tv_number);
        tvNumber.setText(beans.get(i).getOrderProducts().get(i1).getNumber()+"");
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
