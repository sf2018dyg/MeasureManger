package com.soonfor.measuremanager.afflatus.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.activity.TintplateDetailActivity;
import com.soonfor.measuremanager.afflatus.bean.TintplateBean;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.view.RoundJiaoImageView;

import java.util.List;

;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/20 16:56
 * 邮箱：suibozhu@139.com
 */
public class TintplateListAdapter extends BaseAdapter<TintplateListAdapter.ViewHolder, TintplateBean> {

    private List<TintplateBean> list;
    static Context mContext;

    public TintplateListAdapter(Context context, List<TintplateBean> list) {
        super(context);
        mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_tintplate, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.imgpath.setTag(list.get(position));
    }

    @Override
    public void notifyDataSetChanged(List<TintplateBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RoundJiaoImageView imgpath;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            imgpath = itemView.findViewById(R.id.imgpath);
            name = itemView.findViewById(R.id.name);

            imgpath.setOnClickListener(listener);
        }

        public void setData(TintplateBean gpBean) {
            ImageUtil.loadCaselibImage(mContext, gpBean.getImgUrl(),imgpath);
            name.setText(CommonUtils.formatStr(gpBean.getTitle()));
        }

        public View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.imgpath:
                        TintplateBean tb = (TintplateBean) v.getTag();
                        Intent intent = new Intent(mContext, TintplateDetailActivity.class);
                        intent.putExtra("TintplateBean", tb);
                        mContext.startActivity(intent);
                        break;
                }
            }
        };
    }
}
