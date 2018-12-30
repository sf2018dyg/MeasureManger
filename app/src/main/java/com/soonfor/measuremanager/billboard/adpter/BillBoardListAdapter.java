package com.soonfor.measuremanager.billboard.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.billboard.bean.BillBoardBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/20 8:23
 * 邮箱：suibozhu@139.com
 */
public class BillBoardListAdapter extends BaseAdapter<BillBoardListAdapter.ViewHolder, BillBoardBean> {

    private List<BillBoardBean> list;
    static Context mContext;
    static int SType;

    public BillBoardListAdapter(Context context, List<BillBoardBean> list,int stype) {
        super(context);
        mContext = context;
        this.list = list;
        SType = stype;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_billboard, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public void notifyDataSetChanged(List<BillBoardBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtbillno;
        CircularImageView ivhead;
        TextView txtdesignerName;
        TextView txtdesignFamous;
        TextView txtnum;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtbillno = itemView.findViewById(R.id.txtbillno);
            this.ivhead = itemView.findViewById(R.id.ivhead);
            this.txtdesignerName = itemView.findViewById(R.id.txtdesignerName);
            this.txtdesignFamous = itemView.findViewById(R.id.txtdesignFamous);
            this.txtnum = itemView.findViewById(R.id.txtnum);
        }

        public void setData(BillBoardBean gpBean) {
            this.txtbillno.setText(CommonUtils.formatStr(gpBean.getBillno()) + "");
            Picasso.with(mContext)
                    .load(gpBean.getHeadPath())
                    .placeholder(R.mipmap.default_s)
                    .error(R.mipmap.default_s)
                    .into(ivhead);
            txtdesignerName.setText(CommonUtils.formatStr(gpBean.getDesignerName()) + "");
            txtdesignFamous.setText(CommonUtils.formatStr(gpBean.getDesignFamous()) + "");
            switch (SType){
                case 0:
                    txtnum.setText(CommonUtils.formatStr(gpBean.getYeji()));
                    break;
                case 1:
                    txtnum.setText(CommonUtils.formatStr(gpBean.getDianzan()));
                    break;
                case 2:
                    txtnum.setText(CommonUtils.formatStr(gpBean.getFenshishu()));
                    break;
            }
        }
    }
}
