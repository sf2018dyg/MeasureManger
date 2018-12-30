package com.soonfor.measuremanager.home.lofting.adapter.detail;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Mark.MarkResultEntity;
import com.soonfor.measuremanager.home.lofting.presenter.detail.LoftingInfoPresenter;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-08-09 10:00
 * 邮箱：dingyg012655@126.com
 */
public class MarkInfoTabAdapter extends BaseAdapter<MarkInfoTabAdapter.ViewHolder, MarkResultEntity> {

    List<MarkResultEntity> tabEntityList;
    LoftingInfoPresenter presenter;

    public MarkInfoTabAdapter(Context context, List<MarkResultEntity> tabEntityList, LoftingInfoPresenter presenter) {
        super(context);
        this.tabEntityList = tabEntityList;
        this.presenter = presenter;
    }

    @Override
    public void notifyDataSetChanged(List<MarkResultEntity> dataList) {
        tabEntityList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_lofting_detail_loftinfo_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tabname.setTag(position);
        holder.tabname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                for(int i=0; i<tabEntityList.size(); i++){
                    if(pos == i){
                        tabEntityList.get(pos).setShowing(false);
                    }else {
                        tabEntityList.get(i).setShowing(true);
                    }
                }
                notifyDataSetChanged();
                presenter.refreshView(tabEntityList.get(pos));
            }
        });
        MarkResultEntity resultEntity = tabEntityList.get(position);
        holder.setData(resultEntity);
    }

    @Override
    public int getItemCount() {
        return tabEntityList == null ? 0 : tabEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tabname;

        public ViewHolder(View itemView) {
            super(itemView);
            tabname = itemView.findViewById(R.id.tvfTab);
        }

        public void setData(MarkResultEntity resultEntity) {
            tabname.setText(resultEntity.getUnitName());
            if (resultEntity.isShowing()) {
                tabname.setEnabled(true);
            } else {
                tabname.setEnabled(false);
            }
        }
    }
}
