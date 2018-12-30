package com.soonfor.measuremanager.other.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.tools.BitmapUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/1/16.
 */

public class PhotoAdapter extends BaseAdapter {

    private ArrayList<String> beans;
    private Context context;

    /**
     * 类型
     */
    private int type;

    /**
     * 是否显示删除按钮
     */
    private boolean isShowDelete;

    public static final int TYPE_LOCAL = 1;

    public static final int TYPE_URL = 2;
    public static final int TYPE_URL_2 = 3;

    public interface onItemClick {
        void itemClick(View view, ArrayList<String> beans, int adapterPosition);
        void deleteClick(View view, ArrayList<String> beans, int adapterPosition);
    }

    private onItemClick listener;

    public onItemClick getListener() {
        return listener;
    }

    public void setListener(onItemClick listener) {
        this.listener = listener;
    }

    public PhotoAdapter(Context context, ArrayList<String> beans, int type) {
        super(context);
        this.context = context;
        this.beans = beans;
        this.type = type;
    }

    public PhotoAdapter(Context context, ArrayList<String> beans, int type, boolean isShowDelete) {
        super(context);
        this.context = context;
        this.beans = beans;
        this.type = type;
        this.isShowDelete = isShowDelete;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.beans = (ArrayList<String>) dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = mInflater.inflate(R.layout.adapter_item_photo, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (type == TYPE_LOCAL) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            Bitmap bitmap = BitmapFactory.decodeFile(beans.get(position), options);

            bitmap = BitmapUtils.getSmallerBitmap(bitmap);

            ((PhotoViewHolder) holder).iv.setImageBitmap(bitmap);
        } else {

//            if (type == TYPE_URL) {
               ImageUtil.loadImage(context, beans.get(position), ((PhotoViewHolder) holder).iv);
//            } else {
//                ImageUtil.loadImage(context, beans.get(position), ((PhotoViewHolder) holder).iv, R.mipmap.zanuw, false);
//            }
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

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private onItemClick listener;
        public ImageView iv;
        public ImageView imgfDelete;

        public PhotoViewHolder(final View itemView, final onItemClick listener) {
            super(itemView);
            this.listener = listener;

            iv = itemView.findViewById(R.id.iv);
            imgfDelete = itemView.findViewById(R.id.imgfDelete);
            if(isShowDelete){

                imgfDelete.setVisibility(View.VISIBLE);
            }else {
                imgfDelete.setVisibility(View.GONE);
            }
            if (listener != null) {
                iv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        listener.itemClick(view, beans, getAdapterPosition());
                    }
                });
                imgfDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.deleteClick(view, beans, getAdapterPosition());
                    }
                });
            }
        }
    }
}
