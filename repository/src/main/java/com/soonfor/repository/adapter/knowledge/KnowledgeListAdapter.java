package com.soonfor.repository.adapter.knowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.soonfor.repository.R;

import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.KUserInfo;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.tools.CircleImageView;
import com.soonfor.repository.tools.ComTools;
import com.soonfor.repository.tools.DateTools;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.ui.RepApp;
import com.soonfor.repository.ui.activity.knowledge.KnowledgeDetailActivity;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 作者：DC-DingYG on 2018-06-22 14:31
 * 邮箱：dingyg012655@126.com
 */
public class KnowledgeListAdapter extends RepBaseAdapter<KnowledgeListAdapter.ViewHolder, KnowledgeBean> implements View.OnClickListener {

    private List<KnowledgeBean> list;
    Activity activity;
    private View.OnClickListener listener;
    private int viewtype;//使用adapter的界面类型(为区别共用此adapter的View 0：BaseKnowledgeFragment 1:其它)

    public KnowledgeListAdapter(Activity context, List<KnowledgeBean> list, View.OnClickListener listener, int viewtype) {
        super(context);
        activity = context;
        this.list = list;
        this.listener = listener;
        this.viewtype = viewtype;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_knowledge, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final KnowledgeBean gpBean = list.get(position);
        KUserInfo userInfo = gpBean.getUserInfo();
        if (userInfo != null) {
            holder.tvfUserName.setText(userInfo.getNickName());
            if (userInfo.getAvatar().equals("")) {
                holder.imgfHead.setImageResource(R.mipmap.avatar_default);
            } else {
                String imageUrl = userInfo.getAvatar();
                if (!imageUrl.contains("http")) {
                    imageUrl = RepApp.DOWNLOAD_FILE + imageUrl;
                }
                imageLoading(imageUrl, holder.imgfHead, R.mipmap.avatar_default);
            }
        }
        holder.tvfPublishTime.setText(DateTools.getTimestamp(gpBean.getPublishTime(),"yyyy年MM月dd日"));
        holder.tvfTitle.setText(Html.fromHtml(gpBean.getTitle()));
        holder.tvfContent.setText(Html.fromHtml(gpBean.getSummary()));
        if(gpBean.getIsLike()==0){
            ComTools.setTextWithDraw((Activity)context, holder.tvfLike, gpBean.getLikeCount(), R.mipmap.support);
        }else {
            ComTools.setTextWithDraw((Activity)context, holder.tvfLike, gpBean.getLikeCount(), R.mipmap.support2);
        }
        ComTools.setTextWithDraw((Activity)context, holder.tvfComment, gpBean.getCommentCount(), R.mipmap.comment);
        ComTools.setTextWithDraw((Activity)context, holder.tvfShare, gpBean.getCommentCount(), R.mipmap.share);
        holder.llfCItem.setTag(R.id.knowledge_list_key, position);
        holder.llfCItem.setOnClickListener(this);
        ArrayList<String> pics = gpBean.getPicList();
        if (gpBean.getVideo().equals("")) {
            holder.jzVideoPlayer.setVisibility(View.GONE);
            if (pics != null && pics.size() > 0) {
                holder.nineGrid.setVisibility(View.VISIBLE);
                ArrayList<ImageInfo> liveInfos = new ArrayList<>();
                for (int i = 0; i < pics.size(); i++) {
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo.setThumbnailUrl(pics.get(i));
                    imageInfo.setBigImageUrl(pics.get(i));
                    liveInfos.add(imageInfo);
                }
                holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context, liveInfos));
            } else {
                holder.nineGrid.setVisibility(View.GONE);
            }
        } else {
            holder.nineGrid.setVisibility(View.GONE);
            holder.jzVideoPlayer.setVisibility(View.VISIBLE);
            holder.jzVideoPlayer.setUp(
                    gpBean.getVideo(), JZVideoPlayer.SCREEN_WINDOW_LIST,
                    "");
            imageLoading(gpBean.getFramePath(), holder.jzVideoPlayer.thumbImageView, R.color.black);
        }
        holder.llfLike.setTag(R.id.knowledge_list_key, position);
        holder.llfShare.setTag(R.id.knowledge_list_key, position);
        holder.llfLike.setTag(R.id.item_object, list.get(position));
        holder.llfShare.setTag(R.id.item_object, list.get(position));
        holder.llfComment.setTag(R.id.knowledge_list_key, position);
        //holder.llfComment.setTag(R.id.isComment, true);
        holder.llfComment.setOnClickListener(this);
        holder.llfLike.setOnClickListener(listener);
        holder.llfShare.setOnClickListener(listener);

    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.knowledge_list_key);
//        boolean isComment = false;
//        try {
//            isComment = (boolean) v.getTag(R.id.isComment);
//        }catch (Exception e){}
        Intent intent = new Intent(context, KnowledgeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ITEM_ID", list.get(position).getId());
        bundle.putParcelable("ITEM_BEAN", list.get(position));
        bundle.putInt("LIST_POSITION", position);
        //bundle.putBoolean("ISCOMMENT", isComment);
        if(viewtype==0){
            bundle.putString("LAST_VIEWTYPE", "BaseKnowledgeFragment");
        }else if(viewtype==1){
            bundle.putString("LAST_VIEWTYPE", "SearchActivity");
        }
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent, Tokens.Knowledge.CLICK_TO_DETAIL);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void notifyDataSetChanged(List<KnowledgeBean> dataList) {
        list = dataList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llfCItem;
        CircleImageView imgfHead;
        TextView tvfOfficial;
        TextView tvfUserName;//用户名称
        TextView tvfPublishTime;//发布时间
        TextView tvfTitle;//标题
        TextView tvfContent;//内容
        TextView tvfLike;//点赞
        TextView tvfComment;//评论
        TextView tvfShare;
        LinearLayout llfLike;
        LinearLayout llfComment;
        LinearLayout llfShare;
        NineGridView nineGrid;
        JZVideoPlayerStandard jzVideoPlayer;


        public ViewHolder(View itemView) {
            super(itemView);
            llfCItem = itemView.findViewById(R.id.llfCItem);
            imgfHead = itemView.findViewById(R.id.imgfCustomerHead);
            tvfOfficial = itemView.findViewById(R.id.tvfOfficial);
            tvfUserName = itemView.findViewById(R.id.tvfUserName);
            tvfPublishTime = itemView.findViewById(R.id.tvfPublishTime);
            tvfTitle = itemView.findViewById(R.id.tvfTitle);
            tvfContent = itemView.findViewById(R.id.tvfContent);
            tvfLike = itemView.findViewById(R.id.tvfLike);
            llfComment = itemView.findViewById(R.id.llfComment);
            tvfComment = itemView.findViewById(R.id.tvfComment);
            llfLike = itemView.findViewById(R.id.llfLike);
            tvfShare = itemView.findViewById(R.id.tvfShare);
            llfShare = itemView.findViewById(R.id.llfShare);
            nineGrid = itemView.findViewById(R.id.nineGrid);
            jzVideoPlayer = itemView.findViewById(R.id.videoplayer);
        }
    }
}
