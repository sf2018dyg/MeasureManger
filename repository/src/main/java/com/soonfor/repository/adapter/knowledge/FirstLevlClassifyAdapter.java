package com.soonfor.repository.adapter.knowledge;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.repository.R;

import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.CategoryBean;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.popupwindow.SelectClassifyPopupWindow;
import com.soonfor.repository.ui.fragment.KnowledgeFragment;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-06-23 8:47
 * 邮箱：dingyg012655@126.com
 */
public class FirstLevlClassifyAdapter extends RepBaseAdapter<FirstLevlClassifyAdapter.ViewHolder, CategoryBean> implements View.OnClickListener {

    private List<CategoryBean> list;
    private RecyclerView mRecyclerView;
    private SelectClassifyPopupWindow.refresh refresh;
    private CategoryBean selecFirstType = null;//选中的一级分类

    public FirstLevlClassifyAdapter(Context context, List<CategoryBean> list, RecyclerView mRecyclerView, SelectClassifyPopupWindow.refresh refresh) {
        super(context);
        this.list = list;
        this.mRecyclerView = mRecyclerView;
        this.refresh = refresh;
        selecFirstType = KnowledgeFragment.fType;
    }

    @Override
    public void notifyDataSetChanged(List<CategoryBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_classify_firstlevl, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.llfFirstlevl.setTag(position);
        holder.llfFirstlevl.setOnClickListener(this);
        holder.setData(position);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.llfFirstlevl) {
            int pos = (int) v.getTag();
            if (!list.get(pos).getId().equals(selecFirstType.getId())) {
                if (list.get(pos).getId().equals("hot")) {
                    KnowledgeFragment.fType = list.get(pos);
                    if (KnowledgeFragment.sType.getId().equals("all")) {
                        KnowledgeFragment.sType = new CategoryBean("hot", "热门", "hot", "热门");
                    } else {
                        KnowledgeFragment.sType = new CategoryBean("hot", "热门", KnowledgeFragment.sType.getParentId(), KnowledgeFragment.sType.getParentName());
                    }
                    notifyDataSetChanged();
                    refresh.refreshLayout();
                } else {
                    selecFirstType = list.get(pos);
                    KnowledgeFragment.isSelecFirstLevlByClick = true;
                    notifyDataSetChanged();
                    if (DataTools.sTypes == null || DataTools.sTypes.size() == 0
                            || DataTools.sTypes.get(selecFirstType.getId()) == null || DataTools.sTypes.get(selecFirstType.getId()).size() == 0) {
                        KnowledgeFragment.fType = selecFirstType;
                        refresh.refreshLayout();
                    }
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llfFirstlevl;
        public ImageView imgfSelected;
        public TextView tvfTypeName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.llfFirstlevl = itemView.findViewById(R.id.llfFirstlevl);
            this.imgfSelected = itemView.findViewById(R.id.imgfSelected);
            this.tvfTypeName = itemView.findViewById(R.id.tvfFirstLevl);
        }

        public void setData(int position) {
            tvfTypeName.setText(list.get(position).getName());
            if (selecFirstType.getId().equals(list.get(position).getId())) {
                llfFirstlevl.setBackgroundColor(context.getResources().getColor(R.color.bg_color));
                imgfSelected.setVisibility(View.VISIBLE);
            } else {
                llfFirstlevl.setBackgroundColor(context.getResources().getColor(R.color.white));
                imgfSelected.setVisibility(View.INVISIBLE);
            }
            LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

            SecondLevlClassifyAdapter slcAdapter = new SecondLevlClassifyAdapter(context, DataTools.sTypes.get(selecFirstType.getId()),refresh);
            // 设置布局管理器
            mRecyclerView.setLayoutManager(mLayoutManager2);
            // 设置adapter
            mRecyclerView.setAdapter(slcAdapter);
        }
    }
}
