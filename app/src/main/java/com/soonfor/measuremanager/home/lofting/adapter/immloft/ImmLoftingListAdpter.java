package com.soonfor.measuremanager.home.lofting.adapter.immloft;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.activity.immloft.ImmediateLoftingActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.LoftLookDetailActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.LoftItemBean;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.RoundJiaoImageView;

import java.util.List;

/**
 * Created by DingYg on 2018-02-02.
 */

public class ImmLoftingListAdpter extends BaseAdapter<ImmLoftingListAdpter.ViewHolder, LoftItemBean> {

    private List<LoftItemBean> list;
    private ImmediateLoftingActivity mContext;

    public ImmLoftingListAdpter(ImmediateLoftingActivity context, List<LoftItemBean> list) {
        super(context);
        mContext = context;
        this.list = list;
    }

    @Override
    public void notifyDataSetChanged(List<LoftItemBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_liangchi_immediately, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemCount()>0) {
            holder.setData(list.get(position));
            holder.llfItem.setTag(position);
            holder.llfItem.setOnClickListener(holder.listener);
//            holder.imgfDelete.setTag(R.id.item_id);
//            holder.imgfDelete.setOnClickListener(dlistener);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llfItem;
        ImageView imgpath;
        TextView tvfcustom;
        TextView tvfhuxingtype;
        TextView tvfmodifytime;
        TextView tvfstatus;
//        ImageView imgfDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            llfItem = itemView.findViewById(R.id.llfItem);
            imgpath = itemView.findViewById(R.id.imgpath);
            tvfcustom = itemView.findViewById(R.id.tvfcustom);
            tvfhuxingtype = itemView.findViewById(R.id.tvfhuxingtype);
            tvfmodifytime = itemView.findViewById(R.id.tvfmodifytime);
            tvfstatus = itemView.findViewById(R.id.tvfstatus);
//            imgfDelete = itemView.findViewById(R.id.imgfDelete);
        }

        public void setData(LoftItemBean gpBean) {
            try {
                ImageUtil.loadImage(mContext, gpBean.getFimgpath(), imgpath);
                if(!gpBean.getCustomBulid().equals("")){
                    tvfcustom.setText(gpBean.getCustomBulid() + "-" + gpBean.getFcname());
                }else{
                    tvfcustom.setText(gpBean.getFcname());
                }
                tvfhuxingtype.setText(gpBean.getFmeafloor());
                tvfmodifytime.setText(DateTool.getTimeTimestamp(gpBean.getFcdate(), "yyyy-MM-dd HH:mm") + "");
                switch (Integer.parseInt(gpBean.getFstatus())){
                    case 0:
                        tvfstatus.setText("待放样");
                        tvfstatus.setTextColor(mContext.getResources().getColor(R.color.red));
                        break;
                    case 1:
                        tvfstatus.setText("放样中");
                        tvfstatus.setTextColor(mContext.getResources().getColor(R.color.orange));
                        break;
                    case 2:
                        tvfstatus.setText("放样完成");
                        tvfstatus.setTextColor(mContext.getResources().getColor(R.color.blue));
                        break;
                    default:
                        tvfstatus.setText("状态未知");
                        tvfstatus.setTextColor(mContext.getResources().getColor(R.color.red));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.llfItem:
                        int position = (int) v.getTag();
                        Bundle b = new Bundle();
                        LoftItemBean bean = list.get(position);
                        b.putParcelable(Tokens.Lofing.SKIP_IMMT_TO_LOFTDETAIL, bean);
                        DataTools.Loft.isEnterSdk = false;
                        DataTools.Loft.statusCode = 0;
                        DataTools.Loft.loftListJson = null;
                        mContext.startNewAct(LoftLookDetailActivity.class, b);
                        break;
                }
            }
        };
    }

}
