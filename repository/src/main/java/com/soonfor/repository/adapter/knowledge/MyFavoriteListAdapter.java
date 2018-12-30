package com.soonfor.repository.adapter.knowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.orhanobut.hawk.Hawk;
import com.soonfor.repository.R;

import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.KUserInfo;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.tools.CircleImageView;
import com.soonfor.repository.tools.ComTools;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.DateTools;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.ui.activity.knowledge.KnowledgeDetailActivity;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/2 0002 11:28
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class MyFavoriteListAdapter extends RepBaseAdapter<MyFavoriteListAdapter.ViewHolder, KnowledgeBean> implements View.OnClickListener {

    private List<KnowledgeBean> list;
    Activity activity;
    private View.OnClickListener listener, checkListener;
    boolean fullChecked = false;

    public MyFavoriteListAdapter(Activity context, List<KnowledgeBean> list, View.OnClickListener listener, View.OnClickListener checkListener) {
        super(context);
        activity = context;
        this.list = list;
        this.listener = listener;
        this.checkListener = checkListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        KnowledgeBean gpBean = list.get(position);
        KUserInfo userInfo = gpBean.getUserInfo();
        if (userInfo != null) {
            holder.tvfUserName.setText(userInfo.getNickName());
            if (userInfo.getAvatar().equals("")) {
                holder.imgfHead.setImageResource(R.mipmap.zanuw);
            } else {
                String imageUrl = userInfo.getAvatar();
                if (!imageUrl.contains("http")) {
                    imageUrl = Hawk.get(DataTools.ServerAdr, "") + imageUrl;
                }
                imageLoading(imageUrl, holder.imgfHead, R.mipmap.zanuw);
            }
//                if(userInfo.getNickName().equals("梦天木门")){
//                    tvfOfficial.setVisibility(View.VISIBLE);
//                }else {
//                    tvfOfficial.setVisibility(View.GONE);
//                }
        }
        holder.tvfPublishTime.setText(DateTools.getTimestamp(gpBean.getPublishTime(), "yyyy年MM月dd日"));
        String title = gpBean.getTitle().replace("<em>", "<font color='#0599fd'>").replace("</em>", "</font>");
        holder.tvfTitle.setText(Html.fromHtml(title));
        String content = gpBean.getSummary().replace("<em>", "<font color='#0599fd'>").replace("</em>", "</font>");
        holder.tvfContent.setText(content);
        if (gpBean.getIsLike() == 0) {
            ComTools.setTextWithDraw((Activity) context, holder.tvfLike, gpBean.getLikeCount(), R.mipmap.support);
        } else {
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
        holder.cbChecked.setChecked(gpBean.isEditable());
        holder.cbChecked.setTag(position);
        if(fullChecked){
            holder.rlleft.setVisibility(View.VISIBLE);
        }else{
            holder.rlleft.setVisibility(View.GONE);
        }
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
        bundle.putString("LAST_VIEWTYPE", "MyFavoriteActivity");
        //bundle.putBoolean("ISCOMMENT", isComment);
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

    public void notifyDataSetChanged(List<KnowledgeBean> dataList,boolean fullChecked) {
        list = dataList;
        this.fullChecked = fullChecked;
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
        CheckBox cbChecked;
        RelativeLayout rlleft;

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
            tvfComment = itemView.findViewById(R.id.tvfComment);
            llfComment = itemView.findViewById(R.id.llfComment);
            llfLike = itemView.findViewById(R.id.llfLike);
            tvfShare = itemView.findViewById(R.id.tvfShare);
            llfShare = itemView.findViewById(R.id.llfShare);
            nineGrid = itemView.findViewById(R.id.nineGrid);
            jzVideoPlayer = itemView.findViewById(R.id.videoplayer);
            rlleft = itemView.findViewById(R.id.rlleft);
            cbChecked = itemView.findViewById(R.id.cbChecked);
            cbChecked.setOnClickListener(checkListener);
        }
    }
}
