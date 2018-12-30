package com.soonfor.measuremanager.home.liangchi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.itemBean;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/23 0023 11:52
 * 邮箱：suibozhu@139.com
 */

public class PortraitItemAdpter extends BaseAdapter<PortraitItemAdpter.ViewHolder, itemBean> {

    private List<itemBean> list;
    View.OnClickListener listener;
    Context mContext;

    public PortraitItemAdpter(Context context, List<itemBean> list, View.OnClickListener listener) {
        super(context);
        mContext = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_portrait_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.namebg.setTag(list.get(position).getName());
    }

    @Override
    public void notifyDataSetChanged(List<itemBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RelativeLayout namebg;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            namebg = itemView.findViewById(R.id.namebg);

            namebg.setOnClickListener(listener);
        }

        public void setData(itemBean gpBean) {
            name.setText(gpBean.getName());
            setColor(name, namebg, gpBean.getPosi());
            if(gpBean.isSelected()){
                setColor(name,namebg,9);
            }
        }
    }

    private void setColor(TextView name, RelativeLayout rlv, int posit) {
        switch (posit) {
            case 0:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait1));
                rlv.setBackgroundResource(R.drawable.bg_round_raid);
                break;
            case 1:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait2));
                rlv.setBackgroundResource(R.drawable.bg_round_raid1);
                break;
            case 2:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait3));
                rlv.setBackgroundResource(R.drawable.bg_round_raid2);
                break;
            case 3:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait4));
                rlv.setBackgroundResource(R.drawable.bg_round_raid3);
                break;
            case 4:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait5));
                rlv.setBackgroundResource(R.drawable.bg_round_raid4);
                break;
            case 5:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait6));
                rlv.setBackgroundResource(R.drawable.bg_round_raid5);
                break;
            case 6:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait7));
                rlv.setBackgroundResource(R.drawable.bg_round_raid6);
                break;
            case 7:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait8));
                rlv.setBackgroundResource(R.drawable.bg_round_raid7);
                break;
            case 8:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait9));
                rlv.setBackgroundResource(R.drawable.bg_round_raid8);
                break;
            case 9:
                name.setTextColor(mContext.getResources().getColor(R.color.gray));
                rlv.setBackgroundResource(R.drawable.bg_round_raid9);
                break;
            default:
                name.setTextColor(mContext.getResources().getColor(R.color.portrait9));
                rlv.setBackgroundResource(R.drawable.bg_round_raid8);
                break;
        }
    }
}