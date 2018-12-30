package com.soonfor.measuremanager.home.lofting.adapter.detail;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureRoomEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureWallEntity;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DingYg on 2018-03-20.
 */

public class MeasureInfoRoomAdapter extends BaseAdapter<MeasureInfoRoomAdapter.ViewHolder, MeasureRoomEntity> implements View.OnClickListener {

    private List<MeasureRoomEntity> beans;
    private Context mContext;
    private GridLayoutManager manager;
    private boolean isFuchi;

    public MeasureInfoRoomAdapter(Context context, List<MeasureRoomEntity> beans, String measureType) {
        super(context);
        mContext = context;
        this.beans = beans;
        if (measureType.contains("复尺")) {
            isFuchi = true;
        } else {
            isFuchi = false;
        }
    }

    @Override
    public void notifyDataSetChanged(List<MeasureRoomEntity> dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_measurementhead, parent, false);
        return new MeasureInfoRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.headtxt.setText(CommonUtils.formatStr(beans.get(position).getName()));
        Map<Integer, String[]> propMap = JsonUtils.getKeyAndTitle(beans.get(position).getProp());
        String houseHeight = "", skirtingLine = "";
        if (propMap.size() > 0) {
            houseHeight = JsonUtils.getValue(propMap, 0, 0) + "  " + JsonUtils.getValue(propMap, 1, 0);
            skirtingLine = JsonUtils.getValue(propMap, 0, 1) + "  " + JsonUtils.getValue(propMap, 1, 1);
        }
        if (houseHeight.trim().equals("")) {
            houseHeight = "层高";
        } else if (!houseHeight.contains("mm")) {
            houseHeight = houseHeight + "mm";
        }
        if (skirtingLine.trim().equals("")) {
            skirtingLine = "踢脚线";
        } else if (!skirtingLine.contains("mm")) {
            skirtingLine = skirtingLine + "mm";
        }
        viewHolder.tvfHouseHeight.setText(houseHeight);
        viewHolder.tvfSkirtingLine.setText(skirtingLine);
        viewHolder.llfhead.setOnClickListener(this);
        boolean bl = (boolean) viewHolder.llfhead.getTag();
        if (bl) {
            viewHolder.headimg.setImageResource(R.mipmap.icon_up);
            viewHolder.rlviewChild.setVisibility(View.VISIBLE);
        } else {
            viewHolder.headimg.setImageResource(R.mipmap.icon_down);
            viewHolder.rlviewChild.setVisibility(View.GONE);
        }

        //初始化child的数据
        viewHolder.wallAdpter.notifyDataSetChanged(beans.get(position).getWalls());
    }


    @Override
    public int getItemCount() {
        return beans == null ? 0 : beans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView headtxt;
        public TextView tvfHouseHeight;//层高
        public TextView tvfSkirtingLine;//踢脚线
        public ImageView headimg;
        public RelativeLayout llfhead;
        public RecyclerView rlviewChild;
        public MeasureInfoWallAdapter wallAdpter;

        public ViewHolder(View itemView) {
            super(itemView);
            headtxt = itemView.findViewById(R.id.headtxt);
            tvfHouseHeight = itemView.findViewById(R.id.tvfHouseHeight);
            tvfSkirtingLine = itemView.findViewById(R.id.tvfSkirtingLine);
            headimg = itemView.findViewById(R.id.headimg);
            llfhead = itemView.findViewById(R.id.llfhead);
            rlviewChild = itemView.findViewById(R.id.rlviewChild);
            llfhead.setTag(true);

            manager = new GridLayoutManager(mContext, 1);
            wallAdpter = new MeasureInfoWallAdapter(mContext, new ArrayList<MeasureWallEntity>(), isFuchi);
            rlviewChild.setLayoutManager(manager);
            rlviewChild.setAdapter(wallAdpter);
        }
    }

    @Override
    public void onClick(View v) {
        boolean bl = (boolean) v.getTag();
        if (bl) {
            v.setTag(false);
            notifyDataSetChanged();
        } else {
            v.setTag(true);
            notifyDataSetChanged();
        }
    }
}
