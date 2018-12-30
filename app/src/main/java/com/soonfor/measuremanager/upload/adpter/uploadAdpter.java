package com.soonfor.measuremanager.upload.adpter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynamicpicpro.model.UploadGoodsBean;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.upload.model.bean.uploadBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/4/2 0002 10:06
 * 邮箱：suibozhu@139.com
 */
public class uploadAdpter extends BaseAdapter<uploadAdpter.ViewHolder, uploadBean> {

    private List<uploadBean> list;
    Activity mContext;
    View.OnClickListener selector, preview, delpic;

    public uploadAdpter(Activity context, List<uploadBean> list, View.OnClickListener selector, View.OnClickListener preview, View.OnClickListener delpic) {
        super(context);
        mContext = context;
        this.list = list;
        this.selector = selector;
        this.preview = preview;
        this.delpic = delpic;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_upload, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position), position);
    }

    @Override
    public void notifyDataSetChanged(List<uploadBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView subname;
        RecyclerView my_imgs_GV;
        GridLayoutManager manager;
        GridImgAdapter gridImgsAdapter;
        List<UploadGoodsBean> img_uri;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            subname = itemView.findViewById(R.id.subname);
            my_imgs_GV = itemView.findViewById(R.id.my_goods_GV);
            img_uri = new ArrayList<UploadGoodsBean>();
            gridImgsAdapter = new GridImgAdapter(mContext, img_uri, selector, preview, delpic);
            manager = new GridLayoutManager(mContext, 4);
            my_imgs_GV.setLayoutManager(manager);
            my_imgs_GV.setAdapter(gridImgsAdapter);
        }

        public void setData(uploadBean gpBean, int posit) {
            name.setText(gpBean.getName());
            subname.setText(gpBean.getSubname());
            img_uri = gpBean.getUpPics();
            gridImgsAdapter.setIndex(gpBean.getIndex());
            gridImgsAdapter.notifyDataSetChanged(img_uri);
        }
    }

}