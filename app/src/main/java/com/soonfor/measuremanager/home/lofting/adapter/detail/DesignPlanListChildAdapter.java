package com.soonfor.measuremanager.home.lofting.adapter.detail;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Design.DesignPlanBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYg on 2018-02-01.
 */

public class DesignPlanListChildAdapter extends BaseAdapter<DesignPlanListChildAdapter.ViewHolder, DesignPlanBean.DesignDrawing> implements View.OnClickListener {

    private List<DesignPlanBean.DesignDrawing> list;
    public DesignPlanListChildAdapter(Context context, List<DesignPlanBean.DesignDrawing> list) {
        super(context);
        this.list = list;
    }
    @Override
    public DesignPlanListChildAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_designplan_list_child, parent, false));
    }

    @Override
    public void onBindViewHolder(DesignPlanListChildAdapter.ViewHolder holder, int position) {
        holder.setData(context,list.get(position));
        holder.flfQuanjing.setTag(R.id.tag_first, position);
        holder.rlfShare.setTag(R.id.tag_second,position);
        holder.flfQuanjing.setOnClickListener(this);
        holder.rlfShare.setOnClickListener(this);
    }
    @Override
    public void notifyDataSetChanged(List<DesignPlanBean.DesignDrawing> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.flfQuanjing:
                int position1 = (int) v.getTag(R.id.tag_first);
                if (list.get(position1)!= null && !list.get(position1).getOverallView().equals("")) {

                }else {
                    MyToast.showToast(context, "暂无全景图");
                }
                break;
            case R.id.rlfShare:
                int position2 = (int) v.getTag(R.id.tag_second);
                if (list.get(position2)!= null && !list.get(position2).getOverallView().equals("")) {
                    // TODO
                    // 在这里进行 http request.网络请求相关操作
                    PermissionsUtil.requestPermission(context, new PermissionListener() {
                                @Override
                                public void permissionGranted(@NonNull String[] permissions) {
                                    new ImageUtil.downloadImageAndShareAsyncTask(context).execute("欢迎光临数夫家具软件",   "数夫为您分享了一张设计效果图", "http://www.fdatacraft.com", list.get(position2).getOverallView());
                                }

                                @Override
                                public void permissionDenied(@NonNull String[] permissions) {
                                }
                            }, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);

                } else {
                    MyToast.showToast(context, "暂无可分享的全景图");
                }
                break;
        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvfDrawingName;//设计图的名称
        TextView tvfDrawingNo;//设计图的编号
        NineGridView ngView;//九宫格图
        FrameLayout flfQuanjing;//全景图
        RelativeLayout rlfShare;//分享
        private ArrayList<ImageInfo> imgList;//图片集合
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfDrawingName = itemView.findViewById(R.id.tvfDesignPicsName);
            this.tvfDrawingNo = itemView.findViewById(R.id.tvfDesignPicsNo);
            this.ngView = itemView.findViewById(R.id.nineGrid);
            this.flfQuanjing = itemView.findViewById(R.id.flfQuanjing);
            this.rlfShare = itemView.findViewById(R.id.rlfShare);
       }

       public void setData(Context mContext, DesignPlanBean.DesignDrawing dpBean) {
            this.tvfDrawingName.setText(dpBean.getName());
            this.tvfDrawingNo.setText(dpBean.getNumber());
            this.ngView.setAdapter(new NineGridViewClickAdapter(mContext,dpBean.getImgList()));
        }
    }
}
