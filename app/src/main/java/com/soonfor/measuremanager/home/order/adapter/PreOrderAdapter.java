package com.soonfor.measuremanager.home.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.order.bean.PreOrderBean;
import com.soonfor.measuremanager.tools.ImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ljc on 2018/1/16.
 */

public class PreOrderAdapter extends BaseAdapter {

    List<PreOrderBean.DataBean.IntentionProductsBean> beans;
    private Context context;
    public PreOrderAdapter(Context context, List<PreOrderBean.DataBean.IntentionProductsBean> beans) {
        super(context);
        this.context = context;
        this.beans = beans;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_elv_child, parent, false);
        PreOrderViewHolder holder = new PreOrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PreOrderBean.DataBean.IntentionProductsBean bean = beans.get(position);
        PreOrderViewHolder viewHolder = (PreOrderViewHolder) holder;

        if (!bean.getThumbnail().isEmpty())
            ImageUtil.loadImage(context, bean.getThumbnail(), viewHolder.iv);
        viewHolder.tvMoney.setText(bean.getUnitPrice() + "/" + bean.getUnit());
        viewHolder.tvName.setText(bean.getName() + "  " + bean.getColor());
        viewHolder.tvNumber.setText("x"+bean.getQuantity() + "");
        if(bean.getSpecifical().equals("")) {
            viewHolder.tvType.setText("材质：" + bean.getTexture() + "；" + "规格描述：" + bean.getSizeDesc());
        }else
            viewHolder.tvType.setText(bean.getSpecifical());

    }

    @Override
    public int getItemCount() {
        if (beans == null) {
            return 0;
        }
        return beans.size();
    }

    public class PreOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_number)
        TextView tvNumber;


        public PreOrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
