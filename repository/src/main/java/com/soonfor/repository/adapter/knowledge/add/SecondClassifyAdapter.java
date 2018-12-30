package com.soonfor.repository.adapter.knowledge.add;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.repository.R;

import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.CategoryBean;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.popupwindow.SelectAddClassifyPopupWindow;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-06-23 8:47
 * 邮箱：dingyg012655@126.com
 */
public class SecondClassifyAdapter extends RepBaseAdapter<SecondClassifyAdapter.ViewHolder, CategoryBean> implements View.OnClickListener {

    private List<CategoryBean> list;
    private CategoryBean selecSecondType = null;//选中的二级分类
    private SelectAddClassifyPopupWindow.refresh refresh;

    public SecondClassifyAdapter(Context context, List<CategoryBean> list, CategoryBean sSType, SelectAddClassifyPopupWindow.refresh refresh) {
        super(context);
        this.list = list;
        this.selecSecondType = sSType;
        this.refresh = refresh;
    }


    @Override
    public void notifyDataSetChanged(List<CategoryBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_classify_secondlevl, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvfSecondLevl.setTag(position);
        holder.tvfSecondLevl.setOnClickListener(this);
        holder.setData(position);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvfSecondLevl) {
            int pos = (int) v.getTag();
            selecSecondType = list.get(pos);
            CategoryBean fcb = null;
            for (int f = 0; f < DataTools.fTypes.size(); f++) {
                if (DataTools.fTypes.get(f).getId().equals(selecSecondType.getParentId())) {
                    fcb = DataTools.fTypes.get(f);
                }
            }
            notifyDataSetChanged();
            if (fcb != null) {
                refresh.refreshLayout(new CategoryBean[]{fcb, selecSecondType});
            }

        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvfSecondLevl;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfSecondLevl = itemView.findViewById(R.id.tvfSecondLevl);
        }

        public void setData(int position) {
            tvfSecondLevl.setText(list.get(position).getName());
            if(selecSecondType==null){
                tvfSecondLevl.setTextColor(Color.parseColor("#888888"));
            }else {
                if (selecSecondType.getParentId().equals(list.get(position).getParentId())) {
                    if (selecSecondType.getId().equals(list.get(position).getId())) {
                        tvfSecondLevl.setTextColor(Color.parseColor("#EB433A"));
                    } else {
                        tvfSecondLevl.setTextColor(Color.parseColor("#888888"));
                    }
                } else {
                    if (selecSecondType.getId().equals(list.get(position).getId())) {
                        tvfSecondLevl.setTextColor(Color.parseColor("#EB433A"));
                    } else {
                        tvfSecondLevl.setTextColor(Color.parseColor("#888888"));
                    }
                }
            }
        }
    }
}
