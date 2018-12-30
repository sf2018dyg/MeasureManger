package com.soonfor.measuremanager.me.adapter.my_information;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.me.activity.my_information.AnnouncementDetailActivity;
import com.soonfor.measuremanager.me.bean.AnnouncementBean;
import com.soonfor.measuremanager.tools.Tokens;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnnouncementListAdapter extends BaseAdapter<AnnouncementListAdapter.ViewHolder, AnnouncementBean>{

    private List<AnnouncementBean> annList;
    public AnnouncementListAdapter(Context context, List<AnnouncementBean> annList) {
        super(context);
        this.annList = annList;
    }

    @Override
    public void notifyDataSetChanged(List<AnnouncementBean> dataList) {
        this.annList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_me_ammouncement, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(annList!=null && annList.get(position)!=null)
            holder.setData(annList.get(position));
        holder.llfLookDetail.setTag(R.id.tag_first, position);
        holder.llfLookDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag(R.id.tag_first);
                Intent intent = new Intent(context, AnnouncementDetailActivity.class);
                intent.putExtra(Tokens.Mine.SKIP_TO_ANNOUNCEMENTDETAIL, annList.get(index));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return annList==null?0:annList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvfAnnTime;
        public TextView tvfTitle;
        public TextView tvfAnnDate;
        public ImageView imgfAnn;
        public TextView tvfDesc;
        public LinearLayout llfLookDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfAnnTime = itemView.findViewById(R.id.tvfAmmounTime);
            this.tvfAnnDate = itemView.findViewById(R.id.tvfAmmounDate);
            this.tvfTitle = itemView.findViewById(R.id.tvfAnnTitle);
            this.imgfAnn = itemView.findViewById(R.id.imgcover);
            this.tvfDesc = itemView.findViewById(R.id.tvfContent);
            this.llfLookDetail = itemView.findViewById(R.id.llfLookDetail);
        }
        public void setData(AnnouncementBean accBean){
            tvfTitle.setText(accBean.getfAnnTheme());
            tvfAnnTime.setText(accBean.getfAnnTime());
            tvfAnnDate.setText(getDateFromTime(accBean.getfAnnTime()));
            tvfDesc.setText(accBean.getfAnnDesc());
            if(!accBean.getfAnnImagePath().equals("")) {
                Picasso.with(context)
                        .load(accBean.getfAnnImagePath())
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.zanuw)
                        .into(imgfAnn);
            }
        }
    }
    private String getDateFromTime(String time){
        String result = "";
        if(!time.equals("") && time.contains("年")){
            result = time.substring(time.indexOf("年")+1, time.indexOf("日")+1);
        }
        return result;
    }
}
