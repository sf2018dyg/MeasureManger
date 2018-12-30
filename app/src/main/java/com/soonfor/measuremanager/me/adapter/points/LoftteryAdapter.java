package com.soonfor.measuremanager.me.adapter.points;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.me.bean.WorkPointsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoftteryAdapter extends BaseAdapter<LoftteryAdapter.ViewHolder, WorkPointsBean.LotteryAreaBean> implements View.OnClickListener {

    private List<WorkPointsBean.LotteryAreaBean> list;
    private int type;
    private View.OnClickListener listener;

    public LoftteryAdapter(Context context, int type, View.OnClickListener listener) {
        super(context);
        this.type = type;
        this.listener = listener;
    }

    @Override
    public void notifyDataSetChanged(List<WorkPointsBean.LotteryAreaBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public LoftteryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LoftteryAdapter.ViewHolder(mInflater.inflate(R.layout.adapter_exchange_lottery, parent, false));
    }

    @Override
    public void onBindViewHolder(LoftteryAdapter.ViewHolder holder, int position) {
        holder.setData(context, list.get(position));
        holder.btnfLottery.setTag(R.id.tag_first, position);
        holder.btnfLottery.setOnClickListener(listener);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvfName;
        TextView tvfFrequency;
        TextView tvfAmount;
        Button btnfLottery;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.img_lottery_one);
            this.tvfName = itemView.findViewById(R.id.tv_lottery_one_name);
            this.tvfFrequency = itemView.findViewById(R.id.tv_lottery_one_frequency);
            this.tvfAmount = itemView.findViewById(R.id.tv_work_points_one_amount);
            this.btnfLottery = itemView.findViewById(R.id.bt_lottery_one);
        }

        public void setData(Context mContext, WorkPointsBean.LotteryAreaBean areaBean) {
            if(type==0){
                this.btnfLottery.setText("抽奖");
            }else if(type==1){
                this.btnfLottery.setText("兑奖");
            }
            imageLoading(areaBean.getThumbnail(), imageView, R.mipmap.icn_anli);
            tvfName.setText(areaBean.getName());
            tvfFrequency.setText(areaBean.getDescription());
            tvfAmount.setText(areaBean.getWorkPoints() + "工分");
        }
    }
    public void imageLoading(String imageUrl, ImageView view, int defaultResId) {
        if(imageUrl==null||imageUrl.equals("")){
            view.setImageResource(R.drawable.app_icon);
        }else {
            if (!imageUrl.contains("http")) {
                imageUrl = "http://"  + Hawk.get(SoonforApplication.ServerAdr_fj) + UserInfo.DOWNLOAD_FILE + imageUrl;
            }
            Picasso.with(context).load(imageUrl)
                    .placeholder(defaultResId)
                    .error(defaultResId)
                    .into(view);
        }
    }
}
