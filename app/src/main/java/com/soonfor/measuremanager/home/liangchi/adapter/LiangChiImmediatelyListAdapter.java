package com.soonfor.measuremanager.home.liangchi.adapter;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 16:11
 * 邮箱：suibozhu@139.com
 * 立刻量尺
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiImmediatelyActivity;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiLookDetailActivity;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiImmeaditelyBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.view.RoundJiaoImageView;

import java.util.ArrayList;
import java.util.List;


public class LiangChiImmediatelyListAdapter extends BaseAdapter<LiangChiImmediatelyListAdapter.ViewHolder, LiangChiImmeaditelyBean> {

    private List<LiangChiImmeaditelyBean> list;
    private static LiangChiImmediatelyActivity mainActivity;
    public static boolean isFuchi;
    private View.OnClickListener dlistener;

    public LiangChiImmediatelyListAdapter(Context context, LiangChiImmediatelyActivity mainActivity, List<LiangChiImmeaditelyBean> list, boolean isFuchi, View.OnClickListener dlistener) {
        super(context);
        this.mainActivity = mainActivity;
        this.list = list;
        this.isFuchi = isFuchi;
        this.dlistener = dlistener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_liangchi_immediately, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.llfItem.setOnClickListener(holder.listener);
        holder.llfItem.setTag(list.get(position));
        holder.imgfDelete.setTag(R.id.item_id, position);
        holder.imgfDelete.setOnClickListener(dlistener);
    }

    @Override
    public void notifyDataSetChanged(List<LiangChiImmeaditelyBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(List<LiangChiImmeaditelyBean> dataList, boolean isFuchi) {
        list = new ArrayList<>();
        this.list = dataList;
        this.isFuchi = isFuchi;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llfItem;
        ImageView imgpath;
        TextView tvfcustom;
        TextView tvfhuxingtype;
        TextView tvfmodifytime;
        TextView tvfstatus;
        private ImageView imgfDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            llfItem = itemView.findViewById(R.id.llfItem);
            imgpath = itemView.findViewById(R.id.imgpath);
            tvfcustom = itemView.findViewById(R.id.tvfcustom);
            tvfhuxingtype = itemView.findViewById(R.id.tvfhuxingtype);
            tvfmodifytime = itemView.findViewById(R.id.tvfmodifytime);
            tvfstatus = itemView.findViewById(R.id.tvfstatus);
            imgfDelete = itemView.findViewById(R.id.imgfDelete);
        }

        public void setData(LiangChiImmeaditelyBean gpBean) {
            ImageUtil.loadImage(mainActivity, gpBean.getFimgpath(), imgpath);

            String loupan = CommonUtils.formatStr(gpBean.getCustomBulid()).equals("") ? "" : (CommonUtils.formatStr(gpBean.getCustomBulid()) + "-");
            tvfcustom.setText(loupan + CommonUtils.formatStr(gpBean.getFcname()));
            tvfhuxingtype.setText(CommonUtils.formatStr(gpBean.getFmeafloor()));
            tvfmodifytime.setText(DateTool.getTimeTimestamp(gpBean.getFcdate(), "yyyy-MM-dd HH:mm:ss") + "");
            if(isFuchi){
                imgfDelete.setVisibility(View.INVISIBLE);
            }else {
                imgfDelete.setVisibility(View.VISIBLE);
            }
            if (gpBean.getFstatus().equals("0")) {//待量尺
                tvfstatus.setTextColor(mainActivity.getResources().getColor(R.color.red));
                tvfstatus.setText(isFuchi ? "待复尺" : "待量尺");
            } else if (gpBean.getFstatus().equals("1")) {//量尺中
                tvfstatus.setTextColor(mainActivity.getResources().getColor(R.color.orange));
                tvfstatus.setText(isFuchi ? "复尺中" : "量尺中");
            } else if (gpBean.getFstatus().equals("2")) {//量尺完成
                tvfstatus.setTextColor(mainActivity.getResources().getColor(R.color.blue));
                tvfstatus.setText(isFuchi ? "复尺完成" : "量尺完成");
            }
        }

        public View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.llfItem:
                        Bundle b = new Bundle();
                        LiangChiImmeaditelyBean bean = (LiangChiImmeaditelyBean) v.getTag();
                        b.putParcelable("LiangChiImmeaditelyBean", bean);
                        mainActivity.startNewAct(mainActivity, LiangChiLookDetailActivity.class, b, 56000);
                        //mainActivity.startNewAct(LiangChiLookDetailActivity.class, b,56000);
                        break;
                }
            }
        };
    }
}

/*public class LiangChiImmediatelyListAdapter extends RepBaseAdapter {

    private List<LiangChiImmeaditelyBean> list;
    private static LiangChiImmediatelyActivity mainActivity;

    public LiangChiImmediatelyListAdapter(LiangChiImmediatelyActivity mainActivity, List<LiangChiImmeaditelyBean> list) {
        this.mainActivity = mainActivity;
        this.list = list;
    }

    public void notifyDataSetChanged(List<LiangChiImmeaditelyBean> lt) {
        list = lt;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public LiangChiImmeaditelyBean getItem(int position) {
        if (list == null) {
            return null;
        } else {
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mainActivity).inflate(R.layout.adapter_liangchi_immediately, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        setData(list.get(position),vh);
        //vh.llfItem.setOnClickListener(vh.listener);
        //vh.llfItem.setTag(list.get(position));

        vh.imgpath.setOnClickListener(listener);
        vh.imgpath.setTag(list.get(position));
        vh.clickllr.setOnClickListener(listener);
        vh.clickllr.setTag(list.get(position));
        vh.clickrrl.setOnClickListener(listener);
        vh.clickrrl.setTag(list.get(position));
        return convertView;
    }

    public void setData(LiangChiImmeaditelyBean gpBean,ViewHolder vh) {
        if (gpBean.getFimgpath().equals("")) {
            Glide.with(mainActivity).load(gpBean.getFimgpath()).into(vh.imgpath);
        }

        vh.tvfcustom.setText(CommonUtils.formatStr(gpBean.getCustomBulid()) + "-" + CommonUtils.formatStr(gpBean.getFcname()));
        vh.tvfhuxingtype.setText(CommonUtils.formatStr(gpBean.getFmeafloor()));
        vh.tvfmodifytime.setText(DateTool.getTimeTimestamp(gpBean.getFcdate(), "yyyy-MM-dd HH:mm:ss") + "");

        if (gpBean.getFstatus().equals("0")) {//待量尺
            vh.tvfstatus.setTextColor(mainActivity.getResources().getColor(R.color.red));
            vh.tvfstatus.setText("待量尺");
        } else if (gpBean.getFstatus().equals("1")) {//量尺中
            vh.tvfstatus.setTextColor(mainActivity.getResources().getColor(R.color.orange));
            vh.tvfstatus.setText("量尺中");
        } else if (gpBean.getFstatus().equals("2")) {//量尺完成
            vh.tvfstatus.setTextColor(mainActivity.getResources().getColor(R.color.blue));
            vh.tvfstatus.setText("量尺完成");
        }
    }

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle b = new Bundle();
            LiangChiImmeaditelyBean bean = (LiangChiImmeaditelyBean) v.getTag();
            b.putParcelable("LiangChiImmeaditelyBean", bean);
            mainActivity.startNewAct(LiangChiLookDetailActivity.class, b, 56000);
        }
    };

    class ViewHolder {
        LinearLayout llfItem;
        RoundJiaoImageView imgpath;
        TextView tvfcustom;
        TextView tvfhuxingtype;
        TextView tvfmodifytime;
        TextView tvfstatus;
        LinearLayout clickllr;
        RelativeLayout clickrrl;

        public ViewHolder(View itemView) {
            llfItem = itemView.findViewById(R.id.llfItem);
            imgpath = itemView.findViewById(R.id.imgpath);
            tvfcustom = itemView.findViewById(R.id.tvfcustom);
            tvfhuxingtype = itemView.findViewById(R.id.tvfhuxingtype);
            tvfmodifytime = itemView.findViewById(R.id.tvfmodifytime);
            tvfstatus = itemView.findViewById(R.id.tvfstatus);
            clickllr = itemView.findViewById(R.id.clickllr);
            clickrrl = itemView.findViewById(R.id.clickrrl);
        }
    }
}*/
