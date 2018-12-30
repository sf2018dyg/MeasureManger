package com.soonfor.measuremanager.afflatus.adpter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.CommentBean;
import com.soonfor.measuremanager.afflatus.bean.CommentDetailBean;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/26 10:12
 * 邮箱：suibozhu@139.com
 * 回复评论
 */
public class CommentListAdapter extends BaseAdapter<CommentListAdapter.ViewHolder, CommentBean> {

    private List<CommentBean> list;
    static Context mContext;
    static View.OnClickListener huifuListener;
    static View.OnClickListener huifuRepeatListener;

    public CommentListAdapter(Context context, List<CommentBean> list, View.OnClickListener huifuListener, View.OnClickListener huifuRepeatListener) {
        super(context);
        mContext = context;
        this.list = list;
        this.huifuListener = huifuListener;
        this.huifuRepeatListener = huifuRepeatListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircularImageView ivhead;
        TextView txtname;
        TextView txtlouceng;
        TextView txthuifudate;
        TextView txtcomment;
        TextView txtclickhuifu;
        CommentDetailListAdapter adapter;
        List<CommentDetailBean> commentDetailBeans;
        RecyclerView rlvcommentdetail;
        GridLayoutManager manager;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivhead = itemView.findViewById(R.id.ivhead);
            this.txtname = itemView.findViewById(R.id.txtname);
            this.txtlouceng = itemView.findViewById(R.id.txtlouceng);
            this.txthuifudate = itemView.findViewById(R.id.txthuifudate);
            this.txtcomment = itemView.findViewById(R.id.txtcomment);
            this.txtclickhuifu = itemView.findViewById(R.id.txtclickhuifu);
            this.rlvcommentdetail = itemView.findViewById(R.id.rlvcommentdetail);

            manager = new GridLayoutManager(mContext, 1);
            commentDetailBeans = new ArrayList<CommentDetailBean>();
            adapter = new CommentDetailListAdapter(mContext, commentDetailBeans, huifuRepeatListener);
            rlvcommentdetail.setLayoutManager(manager);
            rlvcommentdetail.setAdapter(adapter);

            txtclickhuifu.setOnClickListener(huifuListener);
        }

        public void setData(CommentBean gpBean) {
            try {
                String finalurl = ImageUtil.backCrmPicPath(gpBean.getUserInfo().getAvatar());
                ImageUtil.loadImageWithCache(mContext, finalurl, ivhead, R.mipmap.default_s,false);
                txtname.setText(CommonUtils.formatStr(gpBean.getUserInfo().getNickName()));
                txtlouceng.setText("");
                try {
                    txthuifudate.setText(DateTool.getTimeTimestamp(gpBean.getCreateTime(), "yyyy-MM-dd"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                txtcomment.setText(CommonUtils.formatStr(gpBean.getContent()));
                adapter.setBigID(gpBean.getId());
                adapter.notifyDataSetChanged(gpBean.getReplyList());
                txtclickhuifu.setTag(gpBean);
            }catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
