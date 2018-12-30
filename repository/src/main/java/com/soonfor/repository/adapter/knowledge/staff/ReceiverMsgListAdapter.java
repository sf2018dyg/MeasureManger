package com.soonfor.repository.adapter.knowledge.staff;

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
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.ui.activity.knowledge.KnowledgeDetailActivity;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 作者：DC-DingYG on 2018-08-23 13:57
 * 邮箱：dingyg012655@126.com
 */
public class ReceiverMsgListAdapter extends RepBaseAdapter<ReceiverMsgListAdapter.ViewHolder, KnowledgeBean> implements View.OnClickListener {

    private List<KnowledgeBean> list;
    Activity activity;

    public ReceiverMsgListAdapter(Activity context, List<KnowledgeBean> list) {
        super(context);
        activity = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_receivermsg, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        KnowledgeBean gpBean = list.get(position);
        holder.tvfOfficial.setText("知识");
        String title = gpBean.getTitle().replace("<em>","<font color='#0599fd'>").replace("</em>","</font>");
        // content = gpBean.getSummary().replace("<em>","<font color='#0599fd'>").replace("</em>","</font>");
        holder.tvfTitle.setText(Html.fromHtml(title));
        holder.tvfContent.setText(Html.fromHtml(gpBean.getSummary()));
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
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.knowledge_list_key);
        Intent intent = new Intent(context, KnowledgeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ITEM_ID", list.get(position).getId());
        bundle.putParcelable("ITEM_BEAN", list.get(position));
        bundle.putInt("LIST_POSITION", position);
        bundle.putString("LAST_VIEWTYPE", "OnlineStaffActivity");
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
        TextView tvfOfficial;
        TextView tvfTitle;//标题
        TextView tvfContent;//内容
        NineGridView nineGrid;
        JZVideoPlayerStandard jzVideoPlayer;


        public ViewHolder(View itemView) {
            super(itemView);
            llfCItem = itemView.findViewById(R.id.llfCItem);
            tvfOfficial = itemView.findViewById(R.id.tvfOfficial);
            tvfTitle = itemView.findViewById(R.id.tvfTitle);
            tvfContent = itemView.findViewById(R.id.tvfContent);
            nineGrid = itemView.findViewById(R.id.nineGrid);
            jzVideoPlayer = itemView.findViewById(R.id.videoplayer);
        }
    }
}