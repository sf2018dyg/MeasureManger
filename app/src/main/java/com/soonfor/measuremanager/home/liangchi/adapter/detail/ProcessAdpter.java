package com.soonfor.measuremanager.home.liangchi.adapter.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 9:12
 * 邮箱：suibozhu@139.com
 */
public class ProcessAdpter extends BaseAdapter {

    private  List<DataBean.ListBean> beans;
    private Context mContext;
    boolean isfinish = true;
    boolean iswait = false;
    String MainProcess;
    String status;
    int statu = 0;

    public ProcessAdpter(Context context, List<DataBean.ListBean> beans, String mainProcess,String status) {
        super(context);
        mContext = context;
        this.beans = beans;
        MainProcess = mainProcess;
        this.status = status;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_process, parent, false);
        return new topAchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        topAchievementViewHolder viewHolder = (topAchievementViewHolder) holder;
        if(position ==0 ){
            viewHolder.imgfLineLeft.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.imgfLineLeft.setVisibility(View.VISIBLE);
        }
        if (MainProcess.equals(beans.get(position).getCode())) {
            statu = 1;
        }

        switch (statu){
            case 0:
                isfinish = true;
                iswait = false;
                break;
            case 1:
                //根据状态来判断图标
                if(status.equals("0")){
                    isfinish = false;
                    iswait = true;
                }else{
                    isfinish = true;
                    iswait = false;
                }
                statu = 2;
                break;
            case 2:
                isfinish = false;
                iswait = true;
                statu = 3;
                break;
            case 3:
                isfinish = false;
                iswait = false;
                break;
        }


        setProcessImg(viewHolder.one, isfinish, iswait);
        viewHolder.txtone.setText(CommonUtils.formatStr(beans.get(position).getName()));
        if (position == beans.size() - 1) {
            viewHolder.oneside.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class topAchievementViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgfLineLeft;
        public ImageView one;
        public TextView txtone;
        public ImageView oneside;

        public topAchievementViewHolder(View itemView) {
            super(itemView);
            imgfLineLeft = itemView.findViewById(R.id.imgfLineLeft);
            one = itemView.findViewById(R.id.one);
            txtone = itemView.findViewById(R.id.txtone);
            oneside = itemView.findViewById(R.id.oneside);
        }
    }

    private void setProcessImg(ImageView resid, boolean isfinish, boolean iswait) {
        if (isfinish) {
            resid.setImageResource(R.mipmap.icon_finish);
        } else {
            if (iswait) {
                resid.setImageResource(R.mipmap.icon_waiting);
            } else {
                resid.setImageResource(R.mipmap.icon_zero);
            }
        }
    }
}