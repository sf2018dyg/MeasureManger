package com.soonfor.repository.adapter.knowledge.add;

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
import com.soonfor.repository.tools.popupwindow.SelectAddClassifyPopupWindow;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-06-23 8:47
 * 邮箱：dingyg012655@126.com
 */
public class FirstClassifyAdapter extends RepBaseAdapter<FirstClassifyAdapter.ViewHolder, CategoryBean> implements View.OnClickListener {

    private List<CategoryBean> list;
    private RecyclerView mRecyclerView;
    private SelectAddClassifyPopupWindow.refresh refresh;
    private CategoryBean selecFirstType = null;//选中的一级分类
    private CategoryBean selectSecondType = null;

    public FirstClassifyAdapter(Context context, List<CategoryBean> list, CategoryBean fType,CategoryBean sType, RecyclerView mRecyclerView,
                                SelectAddClassifyPopupWindow.refresh refresh) {
        super(context);
        this.list = list;
        this.mRecyclerView = mRecyclerView;
        if(fType==null){this.selecFirstType = list.get(0);}
        else {this.selecFirstType = fType;}
        this.selectSecondType = sType;
        this.refresh = refresh;
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
            if (selecFirstType == null || !list.get(pos).getId().equals(selecFirstType.getId())) {
                selecFirstType = list.get(pos);
                notifyDataSetChanged();
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
//
            if (selecFirstType.getId().equals(list.get(position).getId())) {
                llfFirstlevl.setBackgroundColor(context.getResources().getColor(R.color.bg_color));
                imgfSelected.setVisibility(View.VISIBLE);
            } else {
                llfFirstlevl.setBackgroundColor(context.getResources().getColor(R.color.white));
                imgfSelected.setVisibility(View.INVISIBLE);
            }
            LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

            SecondClassifyAdapter slcAdapter = new SecondClassifyAdapter(context, DataTools.sTypes.get(selecFirstType.getId()),selectSecondType, refresh);
            // 设置布局管理器
            mRecyclerView.setLayoutManager(mLayoutManager2);
            // 设置adapter
            mRecyclerView.setAdapter(slcAdapter);
        }
    }
}
