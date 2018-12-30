package com.soonfor.measuremanager.afflatus.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.partsBean;
import com.soonfor.measuremanager.bases.BaseAdapter;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/20 16:56
 * 邮箱：suibozhu@139.com
 */
public class PartsListAdapter extends BaseAdapter<PartsListAdapter.ViewHolder, partsBean> {

    private List<partsBean> list;
    static Context mContext;
    static int selected = -1;

    public PartsListAdapter(Context context, List<partsBean> list) {
        super(context);
        mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_parts, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.llfitem.setTag(position);
        if(selected == position){
            holder.llfitem.setBackgroundResource(R.drawable.button_jieshou_bg);
        }else{
            holder.llfitem.setBackgroundResource(R.drawable.button_jushou_bg);
        }
    }

    @Override
    public void notifyDataSetChanged(List<partsBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout llfitem;
        TextView imglogo;

        public ViewHolder(View itemView) {
            super(itemView);
            this.llfitem = itemView.findViewById(R.id.llfitem);
            this.imglogo = itemView.findViewById(R.id.imglogo);

            llfitem.setOnClickListener(listener);
        }

        public void setData(partsBean gpBean) {
            imglogo.setText(gpBean.getName());
            /*Picasso.with(mContext)
                    .load(gpBean.getLogo())
                    .placeholder(R.mipmap.loading)
                    .error(R.mipmap.zanuw)
                    .into(imglogo);*/
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.llfitem:
                        int posi = (int)v.getTag();
                        selected = posi;
                        notifyDataSetChanged();
                        break;
                }
            }
        };

    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
