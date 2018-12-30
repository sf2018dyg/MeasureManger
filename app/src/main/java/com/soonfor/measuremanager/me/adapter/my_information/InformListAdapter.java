package com.soonfor.measuremanager.me.adapter.my_information;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.me.bean.InformBean;

import java.util.List;

/**
 * Created by Administrator on 2018-02-26.
 */

public class InformListAdapter extends BaseAdapter<InformListAdapter.ViewHolder, InformBean> {

    private List<InformBean> informList;
    public InformListAdapter(Context context, List<InformBean> informBeans) {
        super(context);
        this.informList = informBeans;
    }

    @Override
    public void notifyDataSetChanged(List<InformBean> dataList) {
        this.informList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_me_informlist, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(informList!=null && informList.get(position)!=null)
        holder.setData(informList.get(position));
    }

    @Override
    public int getItemCount() {
        return informList==null?0:informList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgfType;
        TextView tvfName;
        TextView tvfTime;
        TextView tvfStatus;
        TextView tvfContent;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imgfType = itemView.findViewById(R.id.imgfInformType);
            this.tvfName = itemView.findViewById(R.id.tvfInformName);
            this.tvfTime = itemView.findViewById(R.id.tvfInformTime);
            this.tvfStatus = itemView.findViewById(R.id.tvfStatus);
            this.tvfContent = itemView.findViewById(R.id.tvfInformContent);
        }
        public void setData(InformBean inform){
            switch (inform.getInformType()){
                case 0:
                    imgfType.setImageResource(R.mipmap.icn_citong);
                    tvfContent.setText(inform.getInformContent());
                    break;
                case 1:
                    imgfType.setImageResource(R.mipmap.icn_renwu);
                    String content = inform.getInformContent();
                    SpannableStringBuilder spannableString = new SpannableStringBuilder(content);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#888888"));
                    spannableString.setSpan(colorSpan, content.indexOf("方"), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvfContent.setText(spannableString);
                    break;
                case 2:
                    imgfType.setImageResource(R.mipmap.icn_huifu);
                    content = inform.getInformContent();
                    spannableString = new SpannableStringBuilder(content);
                    colorSpan = new ForegroundColorSpan(Color.parseColor("#0599fd"));
                    spannableString.setSpan(colorSpan, 0, content.indexOf("您")+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvfContent.setText(spannableString);
                    break;
            }
            tvfName.setText(inform.getInformName());
            if(inform.getInformStauts()==0){
                tvfStatus.setText("未读");
                tvfStatus.setPressed(true);
            }else if(inform.getInformStauts()==1){
                tvfStatus.setText("已读");
                tvfStatus.setPressed(false);
            }
        }
    }
}
