package com.soonfor.repository.adapter.knowledge;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.repository.R;

import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.CategoryBean;
import com.soonfor.repository.tools.popupwindow.SelectClassifyPopupWindow;
import com.soonfor.repository.ui.fragment.KnowledgeFragment;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-06-23 8:47
 * 邮箱：dingyg012655@126.com
 */
public class SecondLevlClassifyAdapter extends RepBaseAdapter<SecondLevlClassifyAdapter.ViewHolder, CategoryBean> implements View.OnClickListener {

    private List<CategoryBean> list;
    private CategoryBean selecSecondType = null;//选中的二级分类
    private SelectClassifyPopupWindow.refresh refresh;

    public SecondLevlClassifyAdapter(Context context, List<CategoryBean> list, SelectClassifyPopupWindow.refresh refresh) {
        super(context);
        this.list = list;
        if(list!=null) {
            this.selecSecondType = list.get(0);
        }else {
            this.selecSecondType = null;
        }
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
            KnowledgeFragment.sType = list.get(pos);
            if (KnowledgeFragment.sType.getId().equals("hot")) {
                return;
            }
            KnowledgeFragment.fType = new CategoryBean(KnowledgeFragment.sType.getParentId(), KnowledgeFragment.sType.getParentName());
            notifyDataSetChanged();
            refresh.refreshLayout();
            KnowledgeFragment.isSelecFirstLevlByClick = false;

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
            if (KnowledgeFragment.sType.getParentId().equals(list.get(position).getParentId())) {
                if (KnowledgeFragment.isSelecFirstLevlByClick) {
                    if (selecSecondType!=null && selecSecondType.getId().equals(list.get(position).getId())) {
                        tvfSecondLevl.setTextColor(Color.parseColor("#EB433A"));;
                    } else {
                        tvfSecondLevl.setTextColor(Color.parseColor("#888888"));
                    }
                } else {
                    if (KnowledgeFragment.sType.getId().equals(list.get(position).getId())) {
                        tvfSecondLevl.setTextColor(Color.parseColor("#EB433A"));;
                    } else {
                        tvfSecondLevl.setTextColor(Color.parseColor("#888888"));
                    }
                }
            } else {
                if (selecSecondType!=null && selecSecondType.getId().equals(list.get(position).getId())) {
                    tvfSecondLevl.setTextColor(Color.parseColor("#EB433A"));;
                } else {
                    tvfSecondLevl.setTextColor(Color.parseColor("#888888"));
                }
            }
        }
    }
}
