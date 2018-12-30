package com.soonfor.measuremanager.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.home.othertask.adapter.FileAdapter;
import com.soonfor.measuremanager.home.othertask.adapter.VoiceAdapter;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.FileBean;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.VoiceBean;
import com.soonfor.measuremanager.other.adapter.PhotoAdapter;
import com.soonfor.measuremanager.other.adapter.layoutmanager.FullyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 作者：DC-DingYG on 2018-10-18 14:39
 * 邮箱：dingyg012655@126.com
 */
public class ImageVoiceView extends LinearLayout {

    @BindView(R.id.rvfImgPics)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerViewVoice)
    RecyclerView recyclerViewVoice;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.voice)
    LinearLayout voice;
    @BindView(R.id.rl_location)
    RelativeLayout rl_location;
    @BindView(R.id.rvfFile)
    RecyclerView rvfFile;
    private LayoutInflater mInflater;

    private OnClickListener listener;

    public OnClickListener getListener() {
        return listener;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public ImageVoiceView(Context context) {
        this(context, null);
    }

    public ImageVoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageVoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        mInflater = LayoutInflater.from(getContext());
        initView();

    }

    private Context context;
    private GridLayoutManager manager;
    private GridLayoutManager managerVoice;
    private GridLayoutManager managerFile;
    private PhotoAdapter adapter;
    private VoiceAdapter voiceAdpter;
    private FileAdapter fileAdapter;

    private void initView() {
        final View view = mInflater.inflate(R.layout.view_image_voice, this, true);
        ButterKnife.bind(this, view);
        manager = new FullyGridLayoutManager(context, 3);
        adapter = new PhotoAdapter(context, null, PhotoAdapter.TYPE_URL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerViewVoice.setNestedScrollingEnabled(false);

        managerVoice = new GridLayoutManager(context,1);
        voiceAdpter = new VoiceAdapter(context,null, null);
        recyclerViewVoice.setLayoutManager(managerVoice);
        recyclerViewVoice.setHasFixedSize(true);
        recyclerViewVoice.setNestedScrollingEnabled(false);

        managerFile = new GridLayoutManager(context, 1);
        fileAdapter = new FileAdapter(context, null);
        rvfFile.setLayoutManager(managerFile);
        rvfFile.setHasFixedSize(true);
        rvfFile.setNestedScrollingEnabled(false);

        adapter.setListener(new PhotoAdapter.onItemClick() {

            @Override
            public void deleteClick(View view, ArrayList<String> beans, int adapterPosition) {

            }

            @Override
            public void itemClick(View view, ArrayList<String> images, int adapterPosition) {
                if (listener != null) {
                    listener.onImageClick(adapterPosition);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerViewVoice.setAdapter(voiceAdpter);
        rvfFile.setAdapter(fileAdapter);

    }

    public interface OnClickListener {
        void onImageClick(int position);

    }

    public void setImages(List<String> urls) {
        recyclerView.setVisibility(VISIBLE);
        adapter.notifyDataSetChanged(urls);
    }
    public void setLocation(String location) {
        rl_location.setVisibility(VISIBLE);
        tv_location.setText(location);
    }

    public void setVoice(final List<VoiceBean> voices) {
        voice.setVisibility(VISIBLE);
        voiceAdpter.notifyDataSetChanged(voices);
    }

    public void setFiles(List<FileBean> files){
        rvfFile.setVisibility(VISIBLE);
        fileAdapter.notifyDataSetChanged(files);
    }

}
