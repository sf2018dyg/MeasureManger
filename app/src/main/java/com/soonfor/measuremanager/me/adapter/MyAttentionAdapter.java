package com.soonfor.measuremanager.me.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.me.bean.PersonDataBean;
import com.soonfor.measuremanager.view.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAttentionAdapter extends BaseAdapter<MyAttentionAdapter.ViewHolder, PersonDataBean> {

    private List<PersonDataBean> informList;
    int curPosi = -1;

    public MyAttentionAdapter(Context context, List<PersonDataBean> informBeans) {
        super(context);
        this.informList = informBeans;
    }

    @Override
    public void notifyDataSetChanged(List<PersonDataBean> dataList) {
        this.informList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return new ViewHolder(mInflater.inflate(R.layout.adapter_myattention, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (informList != null && informList.get(position) != null)
            holder.setData(informList.get(position));
        holder.llfItem.setOnClickListener(holder.listener);
        holder.llfItem.setTag(position);
        if (position == getCurPosi()) {
            holder.showSelected.setVisibility(View.VISIBLE);
        } else {
            holder.showSelected.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return informList == null ? 0 : informList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout llfItem;
        public RoundImageView imgpath;
        public TextView title;
        public TextView tvfFens;
        public TextView tvfAddress;
        public TextView tvfExperience;

        RelativeLayout showSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            llfItem = itemView.findViewById(R.id.llfItem);
            imgpath = itemView.findViewById(R.id.imgpath);
            title = itemView.findViewById(R.id.tvfName);
            tvfFens = itemView.findViewById(R.id.fens);
            tvfAddress = itemView.findViewById(R.id.tvfAddress);
            tvfExperience = itemView.findViewById(R.id.tvfExperience);
            showSelected = itemView.findViewById(R.id.showSelected);
            showSelected.setVisibility(View.INVISIBLE);
        }

        public void setData(PersonDataBean cb) {
            String name = cb.getNickName();
            String post = cb.getPost();
            if(name.length()>0) {
                SpannableStringBuilder spannableString = new SpannableStringBuilder(name);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")),
                        0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                if(post.length()>0){
                    spannableString.append("["+post+"]");
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#888888")),
                            name.length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                this.title.setText(spannableString);
            }
            this.tvfFens.setText(cb.getFens());
            this.tvfAddress.setText(cb.getShop());
            this.tvfExperience.setText(cb.getDesign_experience());
            if (cb.getAvatar().equals("")) {
                this.imgpath.setImageResource(R.drawable.avatar_default);
            } else {
                String imageUrl = cb.getAvatar();
                if (!cb.getAvatar().contains("http")) {
                    imageUrl = "http://" + Hawk.get(SoonforApplication.ServerAdr_fj) + UserInfo.DOWNLOAD_FILE + imageUrl;
                }
                Picasso.with(context).load(imageUrl)
                        .placeholder(R.drawable.avatar_default)
                        .error(R.drawable.avatar_default)
                        .into(imgpath);
            }
        }

        public View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.llfItem:
                        int position = (int) v.getTag();
                        if (getCurPosi() >= 0 && getCurPosi() == position) {
                            setCurPosi(-1);
                        } else {
                            setCurPosi(position);
                        }
                        notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    public int getCurPosi() {
        return curPosi;
    }

    public void setCurPosi(int curPosi) {
        this.curPosi = curPosi;
    }
}
