package com.soonfor.repository.adapter.knowledge;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.repository.R;
import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.CommentBean;
import com.soonfor.repository.model.knowledge.ReplyBean;
import com.soonfor.repository.tools.CircleImageView;
import com.soonfor.repository.ui.RepApp;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018/7/20 13:50
 * 邮箱：dingyg012655@126.com
 * 回复评论
 */
public class CommentListAdapter extends RepBaseAdapter<CommentListAdapter.ViewHolder, CommentBean> {

    private List<CommentBean> list;
    private Context mContext;
    private View.OnClickListener listListener;
    private View.OnClickListener detailListener;
    private String auditStatus;//不为空：此详情页由我添加的客户进入 空：其它
    private boolean isScrollChildView;//是否滑动子View

    public CommentListAdapter(Context context, List<CommentBean> list,
                              View.OnClickListener listListener,
                              View.OnClickListener detailListener, String auditStatus) {
        super(context);
        mContext = context;
        this.list = list;
        this.listListener = listListener;
        this.detailListener = detailListener;
        this.auditStatus = auditStatus;
        isScrollChildView = false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_zs_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position), position);
        if(auditStatus.equals("")||auditStatus.equals("审核通过")){
            holder.txtclickhuifu.setVisibility(View.VISIBLE);
            holder.txtclickhuifu.setTag(R.id.position, position);
            holder.txtclickhuifu.setTag(R.id.item_object, list.get(position));
            holder.txtclickhuifu.setOnClickListener(listListener);
        }else {
            holder.txtclickhuifu.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void notifyDataSetChanged(List<CommentBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivhead;
        TextView txtname;
        TextView txtlouceng;
        TextView txthuifudate;
        TextView txtcomment;
        TextView txtclickhuifu;
        CommentDetailListAdapter adapter;
        List<ReplyBean> commentDetailBeans;
        RecyclerView rlvcommentdetail;
        GridLayoutManager manager;
        View view_fenge;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivhead = itemView.findViewById(R.id.comImageHead);
            this.txtname = itemView.findViewById(R.id.txtname);
            this.txtlouceng = itemView.findViewById(R.id.txtlouceng);
            this.txthuifudate = itemView.findViewById(R.id.txthuifudate);
            this.txtcomment = itemView.findViewById(R.id.txtcomment);
            this.txtclickhuifu = itemView.findViewById(R.id.txtclickhuifu);
            this.rlvcommentdetail = itemView.findViewById(R.id.rlvcommentdetail);
            this.view_fenge = itemView.findViewById(R.id.view_fenge);

            manager = new GridLayoutManager(mContext, 1);
            commentDetailBeans = new ArrayList<ReplyBean>();
            adapter = new CommentDetailListAdapter(mContext, commentDetailBeans, detailListener, auditStatus);
            rlvcommentdetail.setLayoutManager(manager);
            rlvcommentdetail.setAdapter(adapter);
            rlvcommentdetail.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        }

        public void setData(CommentBean gpBean, int position) {
            String avatar = gpBean.getUserInfo().getAvatar();
            if(!avatar.equals("")){
                if (!avatar.contains("http")) {
                    avatar = RepApp.DOWNLOAD_FILE + avatar;
                }
                imageLoading(avatar, ivhead, R.mipmap.avatar_default);
            }
            txtname.setText(gpBean.getUserInfo().getNickName());
            //txtlouceng.setText(list.size()-position+ "楼");
            txthuifudate.setText(gpBean.getTimeString());
            txtcomment.setText(gpBean.getContent());
            if(gpBean.getReplyList()==null || gpBean.getReplyList().size()==0){
                view_fenge.setVisibility(View.GONE);
            }else {
                view_fenge.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged(gpBean.getId(), gpBean.getReplyList());
        }
    }
}
