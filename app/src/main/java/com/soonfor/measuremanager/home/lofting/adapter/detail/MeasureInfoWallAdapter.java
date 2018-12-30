package com.soonfor.measuremanager.home.lofting.adapter.detail;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureComponentEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureWallEntity;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DingYg on 2018-03-20.
 */

public class MeasureInfoWallAdapter extends BaseAdapter<MeasureInfoWallAdapter.ViewHolder, MeasureWallEntity> implements View.OnClickListener {

    private List<MeasureWallEntity> beans;
    private Context mContext;
    private GridLayoutManager manager;
    private boolean isFuchi;

    public MeasureInfoWallAdapter(Context context, List<MeasureWallEntity> beans, boolean isFuchi) {
        super(context);
        mContext = context;
        this.beans = beans;
        this.isFuchi = isFuchi;
    }

    @Override
    public void notifyDataSetChanged(List<MeasureWallEntity> dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_wall_itme, parent, false);
        return new MeasureInfoWallAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.headtxt.setText(CommonUtils.formatStr(beans.get(position).getName()));
        if (isFuchi) {
            holder.tvfSurveryPicT.setText("复尺照片");
        } else {
            holder.tvfSurveryPicT.setText("量尺照片");
        }
        Map<Integer, String[]> propMap = JsonUtils.getKeyAndTitle(beans.get(position).getProp());
        String wallLength = "", vBaseLine = "", hBaseLine = "";
        if (propMap.size() > 0) {
            wallLength = JsonUtils.getValue(propMap, 0, 0) + "  " + JsonUtils.getValue(propMap, 1, 0);
            vBaseLine = JsonUtils.getValue(propMap, 0, 1) + "  " + JsonUtils.getValue(propMap, 1, 1);
            hBaseLine = JsonUtils.getValue(propMap, 0, 2) + "  " + JsonUtils.getValue(propMap, 1, 2);
        }
        if (wallLength.trim().equals("")) {
            wallLength = "墙长";
        } else if (!wallLength.contains("mm")) {
            wallLength = wallLength + "mm";
        }

        if (vBaseLine.trim().equals("")) {
            vBaseLine = "垂直基线";
        } else if (!vBaseLine.contains("mm")) {
            vBaseLine = vBaseLine + "mm";
        }

        if (hBaseLine.trim().equals("")) {
            hBaseLine = "水平基线";
        } else if (!hBaseLine.contains("mm")) {
            hBaseLine = hBaseLine + "mm";
        }

        viewHolder.tvfWallLength.setText(wallLength);
        viewHolder.tvfVerticalBaseLine.setText(vBaseLine);
        viewHolder.tvfHorizontalBaseLine.setText(hBaseLine);
        viewHolder.rlfhead.setTag(R.id.show_wall_key, position);
        viewHolder.rlfhead.setOnClickListener(this);
        //boolean bl = (boolean)viewHolder.rlfhead.getTag(R.id.show_wall_key);
        boolean bl = beans.get(position).isShow();
        if (bl) {
            viewHolder.headimg.setImageResource(R.mipmap.icon_up);
            viewHolder.llfTrunkingPics.setVisibility(View.VISIBLE);
        } else {
            viewHolder.headimg.setImageResource(R.mipmap.icon_down);
            viewHolder.llfTrunkingPics.setVisibility(View.GONE);
        }
        if (beans.get(position).getLivePictures() != null) {
            List<String> livepics = beans.get(position).getLivePictures();
            if (livepics.size() > 0) {
                List<ImageInfo> liveInfos = new ArrayList<>();
                for (int i = 0; i < livepics.size(); i++) {
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo.setThumbnailUrl(livepics.get(i));
                    imageInfo.setBigImageUrl(livepics.get(i));
                    liveInfos.add(imageInfo);
                }
                viewHolder.ngfLfLive.setAdapter(new NineGridViewClickAdapter(context, liveInfos));
            }
        }
        if (beans.get(position).getFixPictures() != null) {
            List<String> fixpics = beans.get(position).getFixPictures();
            if (fixpics.size() > 0) {
                List<ImageInfo> fixInfos = new ArrayList<>();
                for (int i = 0; i < fixpics.size(); i++) {
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo.setThumbnailUrl(fixpics.get(i));
                    imageInfo.setBigImageUrl(fixpics.get(i));
                    fixInfos.add(imageInfo);
                }
                viewHolder.ngfLfFix.setAdapter(new NineGridViewClickAdapter(context, fixInfos));
            }
        }
        //初始化child的数据
        viewHolder.childAdpter.notifyDataSetChanged(beans.get(position).getComponents());
    }

    @Override
    public int getItemCount() {
        return beans == null ? 0 : beans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView headtxt;
        public TextView tvfWallLength;//墙长
        public ImageView headimg;
        public RelativeLayout rlfhead;
        public TextView tvfVerticalBaseLine;//垂直基线
        public TextView tvfHorizontalBaseLine;//水平基线
        public RecyclerView rlviewChild;
        private LinearLayout llfTrunkingPics;
        private NineGridView ngfLfLive;
        private NineGridView ngfLfFix;
        private TextView tvfSurveryPicT;

        public MeasureComponentAdapter childAdpter;

        public ViewHolder(View itemView) {
            super(itemView);
            headtxt = itemView.findViewById(R.id.headtxt);
            tvfWallLength = itemView.findViewById(R.id.tvfWallLength);
            headimg = itemView.findViewById(R.id.headimg);
            rlfhead = itemView.findViewById(R.id.rlfhead);
            tvfVerticalBaseLine = itemView.findViewById(R.id.tvfVerticalBaseLine);
            tvfHorizontalBaseLine = itemView.findViewById(R.id.tvfHorizontalBaseLine);
            rlviewChild = itemView.findViewById(R.id.rlviewWallComponent);
            llfTrunkingPics = itemView.findViewById(R.id.llfTrunkingPics);
            tvfSurveryPicT = itemView.findViewById(R.id.tvfSurveryPicT);
            ngfLfLive = itemView.findViewById(R.id.ngfLoftingPhotos);
            ngfLfFix = itemView.findViewById(R.id.ngfLoftingDrawings);
            manager = new GridLayoutManager(mContext, 1);
            childAdpter = new MeasureComponentAdapter(mContext, new ArrayList<MeasureComponentEntity>());
            rlviewChild.setLayoutManager(manager);
            rlviewChild.setAdapter(childAdpter);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.show_wall_key);
        if (beans.get(position).isShow()) {
            beans.get(position).setShow(false);
            notifyDataSetChanged();
        } else {
            beans.get(position).setShow(true);
            notifyDataSetChanged();
        }
    }
}
