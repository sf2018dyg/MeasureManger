package com.soonfor.measuremanager.afflatus.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.CommentDetailBean;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/26 10:12
 * 邮箱：suibozhu@139.com
 * 回复评论的回复
 */
public class CommentDetailListAdapter extends BaseAdapter<CommentDetailListAdapter.ViewHolder, CommentDetailBean> {

    private List<CommentDetailBean> list;
    static Context mContext;
    static View.OnClickListener huifuRepeatListener;
    static String bigID;

    public CommentDetailListAdapter(Context context, List<CommentDetailBean> list, View.OnClickListener huifuRepeatListener) {
        super(context);
        mContext = context;
        this.list = list;
        this.huifuRepeatListener = huifuRepeatListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_commentdetail, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        if (!list.get(position).getReplyUserId().equals("")) {
            holder.txtbeihuifuzhe.setVisibility(View.VISIBLE);
        } else {
            holder.txtbeihuifuzhe.setVisibility(View.GONE);
        }
    }

    @Override
    public void notifyDataSetChanged(List<CommentDetailBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txthuifuzhe;
        TextView txtbeihuifuzhe;
        TextView txtcomment;
        TextView txtclickhuifu;

        public ViewHolder(View itemView) {
            super(itemView);
            txthuifuzhe = itemView.findViewById(R.id.txthuifuzhe);
            txtbeihuifuzhe = itemView.findViewById(R.id.txtbeihuifuzhe);
            txtcomment = itemView.findViewById(R.id.txtcomment);
            txtclickhuifu = itemView.findViewById(R.id.txtclickhuifu);
            txtclickhuifu.setOnClickListener(huifuRepeatListener);
        }

        public void setData(CommentDetailBean gpBean) {
            txthuifuzhe.setText(CommonUtils.formatStr(gpBean.getUser()));
            txtbeihuifuzhe.setText(CommonUtils.formatStr(gpBean.getReplyUser()));
            txtcomment.setText(CommonUtils.formatStr(gpBean.getContent()));
            gpBean.setBigID(bigID);
            txtclickhuifu.setTag(gpBean);
        }

    }

    public static String getBigID() {
        return bigID;
    }

    public static void setBigID(String bigID) {
        CommentDetailListAdapter.bigID = bigID;
    }
}
