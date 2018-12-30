package com.soonfor.measuremanager.home.othertask.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.VoiceBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.MediaUtil;

import java.util.List;


/**
 * 作者：DC-ZhuSuiBo on 2018/8/27 0027 10:59
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class VoiceAdapter extends BaseAdapter {

    private List<VoiceBean> beans;
    private Context context;
    private boolean isLocalPath;
    private View.OnLongClickListener longClickListener;

    public static final int TYPE_LOCAL = 1;

    public static final int TYPE_URL = 2;
    public static final int TYPE_URL_2 = 3;

    public VoiceAdapter(Context context, List<VoiceBean> beans, View.OnLongClickListener longClickListener) {
        super(context);
        this.context = context;
        this.beans = beans;
        this.isLocalPath = false;
        this.longClickListener = longClickListener;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }
    public void notifyDataSetChanged(List dataList, boolean isLocalPath) {
        this.beans = dataList;
        notifyDataSetChanged();
        this.isLocalPath = isLocalPath;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_voice, parent, false);
        VoiceViewHolder holder = new VoiceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final VoiceViewHolder vh = (VoiceViewHolder) holder;
        Long durlong = beans.get(position).getDuration();
        Long realLong = durlong;
        String durS = "";
        if(durlong==0){
            durS = DateTool.formatTime(realLong);
        }else if(durlong> 100){
            durS = DateTool.formatTime(durlong);
        }else {
            realLong = durlong*1000;
            durS = durlong+"'";
        }
        if(durS.equals("")||durS.equals("0")){
            vh.tv_voice_length.setText("1'");
            realLong = new Long(1);
        }else {
            vh.tv_voice_length.setText(durS);
        }
        vh.rlfVoice.setTag(R.id.item_voice, position);
        vh.rlfVoice.setTag(R.id.item_voice_time, realLong);
        vh.rlfVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag(R.id.item_voice);
                long duration = (long) view.getTag(R.id.item_voice_time);
                if(duration <= 0){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            long realLong = CommonUtils.getRingDuring(beans.get(position).getPath(), false);
                            new Handler(context.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    MediaUtil.play(context, beans.get(pos).getPath(),isLocalPath);
                                    vh.iv_voice.setImageResource(R.drawable.voice_list);
                                    final AnimationDrawable animationDrawable = (AnimationDrawable) vh.iv_voice.getDrawable();
                                    animationDrawable.start();
                                    new android.os.Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            vh.iv_voice.setImageResource(R.drawable.ic_sound_03);
                                            if (animationDrawable != null) {
                                                animationDrawable.stop();
                                            }
                                        }
                                    },realLong);
                                }
                            });
                        }
                    }).start();
                }else {
                    MediaUtil.play(context, beans.get(pos).getPath(),isLocalPath);
                    vh.iv_voice.setImageResource(R.drawable.voice_list);
                    final AnimationDrawable animationDrawable = (AnimationDrawable) vh.iv_voice.getDrawable();
                    animationDrawable.start();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            vh.iv_voice.setImageResource(R.drawable.ic_sound_03);
                            if (animationDrawable != null) {
                                animationDrawable.stop();
                            }
                        }
                    },duration);
                }
            }
        });
        if(longClickListener!=null) {
            vh.rlfVoice.setOnLongClickListener(longClickListener);
        }
    }

    @Override
    public int getItemCount() {
        if (beans == null
                ) {
            return 0;
        }
        return beans.size();
    }

    public class VoiceViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlfVoice;
        public ImageView iv_voice;
        public TextView tv_voice_length;

        public VoiceViewHolder(final View itemView) {
            super(itemView);
            rlfVoice = itemView.findViewById(R.id.rlfVoice);
            iv_voice = itemView.findViewById(R.id.iv_voice);
            tv_voice_length = itemView.findViewById(R.id.tv_voice_length);
        }
    }
}
