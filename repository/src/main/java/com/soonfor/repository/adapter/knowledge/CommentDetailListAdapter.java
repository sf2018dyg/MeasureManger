package com.soonfor.repository.adapter.knowledge;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.repository.R;
import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.ReplyBean;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018/7/20 13:50
 * 邮箱：dingyg012655@126.com
 * 回复评论的回复
 */
public class CommentDetailListAdapter extends RepBaseAdapter<CommentDetailListAdapter.ViewHolder, ReplyBean> {

    private List<ReplyBean> list;
    Context mContext;
    View.OnClickListener detailListener;
    private String auditStatus;//不为空：此详情页由我添加的客户进入 空：其它
    private String _commentId;
    public CommentDetailListAdapter(Context context, List<ReplyBean> list, View.OnClickListener detailListener, String auditStatus) {
        super(context);
        mContext = context;
        this.list = list;
        this.detailListener = detailListener;
        this.auditStatus = auditStatus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_zs_commentdetail, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        if(auditStatus.equals("")||auditStatus.equals("审核通过")) {
            ReplyBean replyBean = list.get(position);
            replyBean.set_commentid(_commentId);
            holder.tvfReply.setVisibility(View.VISIBLE);
            holder.tvfReply.setTag(R.id.child_position, position);
            holder.tvfReply.setTag(R.id.item_chile_object, replyBean);
            holder.tvfReply.setOnClickListener(detailListener);
        }else {
            holder.tvfReply.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void notifyDataSetChanged(List<ReplyBean> dataList) {

    }
    public void notifyDataSetChanged(String _commentId, List<ReplyBean> dataList){
        this.list = dataList;
        this._commentId = _commentId;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvfContent;
        TextView tvfReply;

        public ViewHolder(View itemView) {
            super(itemView);
            tvfContent = itemView.findViewById(R.id.tvfContent);
            tvfReply = itemView.findViewById(R.id.tvfReply);
        }

        public void setData(ReplyBean gpBean) {
            String text = gpBean.getUser().trim() + "：@" + gpBean.getReplyUser().trim() + "   " + gpBean.getContent();
            Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
            ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(Color.parseColor("#0599fd"));
            spannable.setSpan(foregroundColorSpan1, 0, text.indexOf("："), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(Color.parseColor("#333333"));
            spannable.setSpan(foregroundColorSpan2, text.indexOf("：")-1, text.indexOf("@"), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannable.setSpan(foregroundColorSpan1, text.indexOf("@"), text.indexOf(" "), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannable.setSpan(foregroundColorSpan2, text.indexOf(" ")+1, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tvfContent.setText(spannable);
        }

    }
}
