package com.soonfor.repository.adapter.knowledge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.soonfor.repository.R;

import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.MyAddedKnowLedgeBean;
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
public class MyAddedKnowLedgeListAdapter extends RepBaseAdapter<MyAddedKnowLedgeListAdapter.ViewHolder, MyAddedKnowLedgeBean> implements View.OnClickListener {

    private List<MyAddedKnowLedgeBean> list;
    Activity activity;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    public MyAddedKnowLedgeListAdapter(Activity context, List<MyAddedKnowLedgeBean> list) {
        super(context);
        activity = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_addedknowledge, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MyAddedKnowLedgeBean gpBean = list.get(position);
        String title = gpBean.getTitle().replace("<em>", "<font color='#0599fd'>").replace("</em>", "</font>");
        holder.tvfTitle.setText(Html.fromHtml(title));
        String content = gpBean.getSummary().replace("<em>", "<font color='#0599fd'>").replace("</em>", "</font>");
        String Ncontent = content.replace("/n", "").trim();
        holder.tvfContent.setText(content);
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
                    gpBean.getVideo(), JZVideoPlayer.SCREEN_WINDOW_LIST,"");
            imageLoading(gpBean.getVideoFramPic(), holder.jzVideoPlayer.thumbImageView, R.color.black);
        }

        holder.txt_shzt.setText(gpBean.getAuditStatusStr());

        if (gpBean.getAuditStatusStr().contains("待审核")) {
            holder.txt_shzt.setTextColor(Color.parseColor("#ff8321"));
            holder.txt_tiJiaoTime.setText("提交时间: " + DateTools.getTimestamp(gpBean.getCreateTime(), "yyyy-MM-dd HH:mm"));
            holder.txt_tiJiaoTime.setOnClickListener(null);
        } else if (gpBean.getAuditStatusStr().contains("审核不通过")) {
            holder.txt_shzt.setTextColor(Color.parseColor("#eb433a"));
            holder.txt_tiJiaoTime.setText("查看原因 >>");
            holder.txt_tiJiaoTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = (String) v.getTag();
                    new QMUIDialog.MessageDialogBuilder(context)
                            .setTitle("原因")
                            .setMessage(s)
                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .create(mCurrentDialogStyle).show();
                }
            });
        } else if (gpBean.getAuditStatusStr().contains("审核通过")) {
            holder.txt_shzt.setTextColor(Color.parseColor("#2dcf2d"));
            holder.txt_tiJiaoTime.setText("提交时间: " + DateTools.getTimestamp(gpBean.getCreateTime(), "yyyy-MM-dd HH:mm"));
            holder.txt_tiJiaoTime.setOnClickListener(null);
        } else {
            holder.txt_shzt.setTextColor(Color.parseColor("#ff8321"));
            holder.txt_tiJiaoTime.setText("提交时间: " + DateTools.getTimestamp(gpBean.getCreateTime(), "yyyy-MM-dd HH:mm"));
            holder.txt_tiJiaoTime.setOnClickListener(null);
        }

        holder.txt_tiJiaoTime.setTag(gpBean.getAuditMsg());
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.knowledge_list_key);
        Intent intent = new Intent(context, KnowledgeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ITEM_ID", list.get(position).getId());
        bundle.putParcelable("ITEM_BEAN", list.get(position));
        bundle.putInt("LIST_POSITION", position);
        bundle.putString("LAST_VIEWTYPE", "MyAddedKnowLedgeActivity");
        bundle.putString("AUDITSTATUS", list.get(position).getAuditStatusStr());
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent, Tokens.Knowledge.CLICK_TO_DETAIL);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void notifyDataSetChanged(List<MyAddedKnowLedgeBean> dataList) {
        list = dataList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llfCItem;
//        ImageView imgfHead;
//        TextView tvfOfficial;
//        TextView tvfUserName;//用户名称
//        TextView tvfPublishTime;//发布时间
        TextView tvfTitle;//标题
        TextView tvfContent;//内容
        NineGridView nineGrid;
        JZVideoPlayerStandard jzVideoPlayer;
        TextView txt_shzt;
        TextView txt_tiJiaoTime;

        public ViewHolder(View itemView) {
            super(itemView);
            llfCItem = itemView.findViewById(R.id.llfCItem);
//            imgfHead = itemView.findViewById(R.id.imgfHead);
//            tvfOfficial = itemView.findViewById(R.id.tvfOfficial);
//            tvfUserName = itemView.findViewById(R.id.tvfUserName);
//            tvfPublishTime = itemView.findViewById(R.id.tvfPublishTime);
            tvfTitle = itemView.findViewById(R.id.tvfTitle);
            tvfContent = itemView.findViewById(R.id.tvfContent);
            nineGrid = itemView.findViewById(R.id.nineGrid);
            jzVideoPlayer = itemView.findViewById(R.id.videoplayer);
            txt_shzt = itemView.findViewById(R.id.txt_shzt);
            txt_tiJiaoTime = itemView.findViewById(R.id.txt_tiJiaoTime);
        }
    }
}
