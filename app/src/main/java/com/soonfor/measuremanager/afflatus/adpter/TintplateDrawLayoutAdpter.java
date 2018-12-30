package com.soonfor.measuremanager.afflatus.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.cehua.TintplateCehuaBean;
import com.soonfor.measuremanager.bases.BaseAdapter;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/21 10:33
 * 邮箱：suibozhu@139.com
 */
public class TintplateDrawLayoutAdpter extends BaseAdapter<TintplateDrawLayoutAdpter.ViewHolder, TintplateCehuaBean> {

    private List<TintplateCehuaBean> beans;
    private Context mContext;
    private int selected = 0;

    public TintplateDrawLayoutAdpter(Context context, List<TintplateCehuaBean> beans) {
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
        View view = mInflater.inflate(R.layout.adpter_drawlayout_tintplate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setDatas(beans.get(position).getName());
        holder.llfitem.setTag(position);
        if (selected == position) {
            holder.txtname.setTextColor(Color.parseColor("#eb433a"));
            holder.imgtick.setVisibility(View.VISIBLE);
        } else {
            holder.txtname.setTextColor(Color.parseColor("#333333"));
            holder.imgtick.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return beans == null ? 0 : beans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llfitem;
        private TextView txtname;
        private ImageView imgtick;

        public ViewHolder(View itemView) {
            super(itemView);
            llfitem = itemView.findViewById(R.id.llfitem);
            txtname = itemView.findViewById(R.id.txtname);
            imgtick = itemView.findViewById(R.id.imgtick);

            llfitem.setOnClickListener(listener);
        }

        public void setDatas(String name) {
            txtname.setText(name + "");
        }

        public View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.llfitem:
                        int posi = (int) llfitem.getTag();
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
