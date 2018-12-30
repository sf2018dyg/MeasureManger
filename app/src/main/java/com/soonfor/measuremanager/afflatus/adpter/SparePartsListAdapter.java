package com.soonfor.measuremanager.afflatus.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.activity.SparePartsDetailActivity;
import com.soonfor.measuremanager.afflatus.bean.SparePartsBean;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.view.RoundJiaoImageView;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/22 8:27
 * 邮箱：suibozhu@139.com
 */
public class SparePartsListAdapter extends BaseAdapter<SparePartsListAdapter.ViewHolder, SparePartsBean> {

    private List<SparePartsBean> list;
    static Context mContext;

    public SparePartsListAdapter(Context context, List<SparePartsBean> list) {
        super(context);
        mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_spareparts, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.llfitem.setTag(list.get(position));
    }

    @Override
    public void notifyDataSetChanged(List<SparePartsBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llfitem;
        ImageView imglogo;
        RoundJiaoImageView imgpath;
        TextView txttitle;

        public ViewHolder(View itemView) {
            super(itemView);
            llfitem = itemView.findViewById(R.id.llfitem);
            imglogo = itemView.findViewById(R.id.imglogo);
            imgpath = itemView.findViewById(R.id.imgpath);
            txttitle = itemView.findViewById(R.id.txttitle);

            llfitem.setOnClickListener(listener);
        }

        public void setData(SparePartsBean gpBean) {
            ImageUtil.loadCaselibImage(mContext, gpBean.getSurfacePlotUrl(),imgpath);
            txttitle.setText(CommonUtils.formatStr(gpBean.getName()));
        }

        public View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.llfitem:
                        SparePartsBean sb = (SparePartsBean) v.getTag();
                        Intent intent = new Intent(mContext, SparePartsDetailActivity.class);
                        intent.putExtra("SparePartsBean", sb);
                        mContext.startActivity(intent);
                        break;
                }
            }
        };
    }
}
