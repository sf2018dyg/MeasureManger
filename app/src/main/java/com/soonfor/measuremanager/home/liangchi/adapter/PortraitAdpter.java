package com.soonfor.measuremanager.home.liangchi.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.headBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.itemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/23 0023 11:52
 * 邮箱：suibozhu@139.com
 */

public class PortraitAdpter extends BaseAdapter<PortraitAdpter.ViewHolder, headBean> {

    private List<headBean> list;
    Context mContext;
    View.OnClickListener listener;

    public PortraitAdpter(Context context, List<headBean> list, View.OnClickListener listener) {
        super(context);
        mContext = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_portrait, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position), position);
    }

    @Override
    public void notifyDataSetChanged(List<headBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView rlportrait;
        GridLayoutManager manager;
        PortraitItemAdpter itemAdpter;
        List<itemBean> itemBeans;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rlportrait = itemView.findViewById(R.id.rlportrait);
            itemBeans = new ArrayList<>();
            manager = new GridLayoutManager(mContext, 3);
            rlportrait.setLayoutManager(manager);
            itemAdpter = new PortraitItemAdpter(mContext, itemBeans, listener);
            rlportrait.setAdapter(itemAdpter);
        }

        public void setData(headBean gpBean, int posit) {
            name.setText(gpBean.getName());
            gpBean.setPosi(posit);
            for (itemBean bean : gpBean.getItems()) {
                bean.setPosi(posit);
            }
            itemAdpter.notifyDataSetChanged(gpBean.getItems());
            setColor(name, posit);
        }
    }

    private void setColor(TextView name, int posit) {
        switch (posit) {
            case 0:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait1));
                break;
            case 1:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait2));
                break;
            case 2:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait3));
                break;
            case 3:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait4));
                break;
            case 4:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait5));
                break;
            case 5:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait6));
                break;
            case 6:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait7));
                break;
            case 7:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait8));
                break;
            case 8:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait9));
                break;
            default:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait9));
                break;
        }
    }

}