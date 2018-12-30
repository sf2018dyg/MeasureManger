package com.soonfor.measuremanager.home.othertask.adapter.editcustomerprofile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.othertask.model.bean.CustomerProfileBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ljc on 2018/1/16.
 */

public class CustomerProfileAdapter extends BaseAdapter {


    private List<CustomerProfileBean.DataBean.ListBean.ItemsBean> beans;

    public List<CustomerProfileBean.DataBean.ListBean.ItemsBean> getBeans() {
        return beans;
    }

    private OnItemClickListener listener;

    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CustomerProfileAdapter(Context context, List<CustomerProfileBean.DataBean.ListBean.ItemsBean> beans) {
        super(context);
        this.beans = beans;
        this.context = context;
    }

    private Context context;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_profile_edit, parent, false);
        CustomerProfileViewHolder holder = new CustomerProfileViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomerProfileViewHolder customOrderViewHolder = (CustomerProfileViewHolder) holder;
        customOrderViewHolder.tv.setText(beans.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (beans == null) {
            return 0;
        }
        return beans.size();
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    public class CustomerProfileViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv)
        TextView tv;

        private OnItemClickListener listener;

        public CustomerProfileViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
